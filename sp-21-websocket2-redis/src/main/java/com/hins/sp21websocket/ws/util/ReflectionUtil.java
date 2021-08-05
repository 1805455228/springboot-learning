package com.hins.sp21websocket.ws.util;


import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaClass;
import org.apache.ibatis.reflection.ReflectionException;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.invoker.Invoker;
import org.apache.ibatis.reflection.property.PropertyNamer;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

/**
 * Description: 反射工具类
 *
 * @author mpg
 * @date 2021/06/25
 */
@Slf4j
public class ReflectionUtil {

    private static final Map<String, List<Field>> CLASS_ANNOTATION_FIELD_MAP = new ConcurrentHashMap<>();
    private static final Map<String, Class> INTERFACE_TYPE_MAP = new ConcurrentHashMap<>();

    private static ReflectorFactory reflectorFactory = new DefaultReflectorFactory();

    /**
     * 判断一个field是否list
     *
     * @param field
     * @return
     */
    public static boolean isList(Field field) {
        if (null == field) {
            return false;
        }
        Class<?> type = field.getType();
        return List.class.equals(type);
    }

    /**
     * 获得List内部参数的Class属性
     *
     * @param field
     * @return
     */
    public static Class<?> getListInnerClazz(Field field) {

        if (null == field) {
            return null;
        }
        Class<?> type = field.getType();
        if (!List.class.equals(type)) {
            return null;
        }
        Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType)) {
            return null;
        }
        ParameterizedType listGenericType = (ParameterizedType) genericType;
        Type[] actualTypeArguments = listGenericType.getActualTypeArguments();
        if (null == actualTypeArguments) {
            return null;
        }

        for (Type actualTypeArgument : actualTypeArguments) {
            if (actualTypeArgument instanceof Class) {
                return (Class) actualTypeArgument;
            }
        }
        return null;
    }

    /**
     * 获得类的所有属性
     * 不要使用setAccessible()方法，避免改掉了缓存中Field的属性。
     *
     * @param clazz
     * @return
     */
    public static List<Field> getCacheFieldList(Class<?> clazz) {
        return ReflectionKit.getFieldList(clazz);
    }

    /**
     * 通过注解，获得一个对象的唯一一个标注注解的属性Field
     * 不要使用setAccessible()方法，避免改掉了缓存中Field的属性。
     *
     * @param aClass
     * @param annotationClass
     * @param <T>
     * @return
     */
    public static <T extends Annotation> Field getCacheFieldWithAnnotation(Class<?> aClass, Class<T> annotationClass) throws Exception {
        if (null == aClass || null == annotationClass) {
            return null;
        }
        List<Field> fieldList = getCacheFieldListWithAnnotation(aClass, annotationClass);
        if (CollectionUtils.isEmpty(fieldList)) {
            throw new Exception("配置错误，一个对象必须配置一个" + annotationClass.getSimpleName());
        }
        if (fieldList.size() > 1) {
            throw new Exception("配置错误，一个对象只能配置一个" + annotationClass.getSimpleName());
        }
        return fieldList.get(0);
    }

    /**
     * 获得类上所有标注注解的Field
     * 不要使用setAccessible()方法，避免改掉了缓存中Field的属性。
     *
     * @param aClass
     * @param annotationClass
     * @param <T>
     * @return
     */
    public static <T extends Annotation> List<Field> getCacheFieldListWithAnnotation(Class<?> aClass, Class<T> annotationClass) {
        if (null == aClass || null == annotationClass) {
            return Collections.emptyList();
        }
        String cacheKey = aClass.getName() + "_" + annotationClass.getName();
        List<Field> fields = CLASS_ANNOTATION_FIELD_MAP.get(cacheKey);
        if (null != fields) {
            return fields;
        }

        List<Field> allFields = getCacheFieldList(aClass);
        List<Field> annotationFieldList = new ArrayList<>();
        for (Field field : allFields) {
            T annotation = field.getAnnotation(annotationClass);
            if (null != annotation) {
                annotationFieldList.add(field);
            }
        }
        CLASS_ANNOTATION_FIELD_MAP.put(cacheKey, annotationFieldList);
        return annotationFieldList;
    }

    /**
     * 获得私有属性的属性值
     *
     * @param o
     * @param fieldName
     * @return
     * @throws IllegalAccessException
     */
    public static Object getPrivateFieldValue(Object o, String fieldName) throws IllegalAccessException {
        if (null == o) {
            return null;
        }
        if (StringUtils.isBlank(fieldName)) {
            return null;
        }

        Field declaredField = FieldUtils.getDeclaredField(o.getClass(), fieldName, true);
        if (null == declaredField) {
            return null;
        }
        return FieldUtils.readField(declaredField, o);
    }

    /**
     * 安全获取属性值
     * 设置accessible为true，保证获取到属性值
     *
     * @param obj       需要获取属性值的对象
     * @param fieldName 属性名称
     * @return 获取失败返回 {@code null}
     */
    @Nullable
    public static Object safeGetFieldValue(Object obj, String fieldName) {
        if (obj == null) {
            return null;
        }
        if (StringUtils.isBlank(fieldName)) {
            return null;
        }
        try {
            Field declaredField = getField(obj.getClass(), fieldName);
            if (null == declaredField) {
                return null;
            }
            ReflectionUtils.makeAccessible(declaredField);
            return declaredField.get(obj);
        } catch (IllegalAccessException e) {
            return null;
        }
    }


    public static Field getField(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            if (clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class) {
                return getField(clazz.getSuperclass(), fieldName);
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T safeGetFieldValue(Object obj, String fieldName, Class<T> clazz) {
        Object val = safeGetFieldValue(obj, fieldName);
        if (null == val) {
            return null;
        }
        if (clazz.isAssignableFrom(val.getClass())) {
            return (T) val;
        }
        return null;
    }

    /**
     * 安全设置属性值
     * 设置accessible为true，保证属性设置成功
     *
     * @param obj       需要获取属性值的对象
     * @param fieldName 属性名称
     * @param setVal    设置新的属性值
     * @return 设置结果
     */
    public static boolean safeSetFieldValue(Object obj, String fieldName, Object setVal) {
        if (obj == null) {
            return false;
        }
        if (StringUtils.isBlank(fieldName)) {
            return false;
        }
        Field declaredField = null;
        try {
            declaredField = obj.getClass().getDeclaredField(fieldName);
            if (null == declaredField) {
                return false;
            }
            ReflectionUtils.makeAccessible(declaredField);
            declaredField.set(obj, setVal);
            return true;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return false;
        }
    }


    /**
     * 获得接口上的泛型
     *
     * @param aClass
     * @param interfaceClass
     * @param typeIndex
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getInterfaceType(Class aClass, Type interfaceClass, int typeIndex) {
        String key = aClass.getName() + interfaceClass.getTypeName() + typeIndex;

        Class resultClass = INTERFACE_TYPE_MAP.get(key);
        if (null != resultClass) {
            return (Class<T>) resultClass;
        }

        Type[] interfaceTypes = aClass.getGenericInterfaces();
        for (Type interfaceType : interfaceTypes) {
            if ((interfaceType instanceof ParameterizedType)) {
                ParameterizedType parameterizedType = (ParameterizedType) interfaceType;
                if (parameterizedType.getRawType() == interfaceClass) {
                    Type[] typeArguments = parameterizedType.getActualTypeArguments();
                    Class<T> typeArgument = (Class<T>) typeArguments[typeIndex];
                    INTERFACE_TYPE_MAP.put(key, typeArgument);
                    return typeArgument;
                }
            }
        }
        return null;
    }

    /**
     * 向上(super class)寻找满足条件的Class
     *
     * @param clazz
     * @param clazzPredicate
     * @return
     */
    public static Class<?> findClass(Class<?> clazz, Predicate<Class<?>> clazzPredicate) {
        if (clazzPredicate.test(clazz)) {
            return clazz;
        }
        if (clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class) {
            return findClass(clazz.getSuperclass(), clazzPredicate);
        }
        return null;
    }

    public static <T> T newInstance(Class<T> clazz) throws Exception {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new Exception("实例化对象失败", e);
        }
    }

    public static <T extends Annotation> void findFieldByAnnotation(Class<?> clazz, Class<T> annotationClass, BiConsumer<Field, T> fieldConsumer) {
        if (null == clazz) {
            return;
        }
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(annotationClass)) {
                fieldConsumer.accept(declaredField, declaredField.getAnnotation(annotationClass));
            }
        }
        findFieldByAnnotation(clazz.getSuperclass(), annotationClass, fieldConsumer);
    }

    public static Object getField(Field field, Object target) {
        ReflectionUtils.makeAccessible(field);
        return ReflectionUtils.getField(field, target);
    }

    public static Invoker getGetterInvoker(@NonNull Class<?> clazz, @NonNull String fieldName) throws ReflectionException {
        MetaClass metaClass = MetaClass.forClass(clazz, reflectorFactory);
        return metaClass.getGetInvoker(fieldName);
    }

    public static Invoker getSetterInvoker(@NonNull Class<?> clazz, @NonNull String fieldName) throws ReflectionException {
        MetaClass metaClass = MetaClass.forClass(clazz, reflectorFactory);
        return metaClass.getSetInvoker(fieldName);
    }


//    /**
//     * 获取字段名
//     *
//     * @param column
//     * @param <T>
//     * @return
//     */
//    public static <T> String columnToString(SFunction<T, ?> column) {
//        return getColumn(LambdaUtils.resolve(column));
//    }

    private static String getColumn(SerializedLambda lambda) {
        return PropertyNamer.methodToProperty(lambda.getImplMethodName());
    }
}
