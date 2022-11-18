package com.hins.sp24.sp24myioc.context;

import com.hins.sp24.sp24myioc.annotation.MyComponent;
import com.hins.sp24.sp24myioc.util.ClassFileScanUtil;

import java.util.List;

/**
 * 带包扫描的上下文
 */
public class MySpringPackageContext extends MySpringDefaultContext {
    public MySpringPackageContext(String packageName){
        //扫描出这个包下面的所有类
        List<Class> classes= ClassFileScanUtil.scanPackageRetClass(packageName);
        for (Class cls:classes){
            //对需要IOC管理的进行解析初始化容器
            MyComponent myComponent=(MyComponent) cls.getAnnotation(MyComponent.class);
            if (myComponent!=null){
                super.genObjectByClass(cls);
            }
        }
    }


}
