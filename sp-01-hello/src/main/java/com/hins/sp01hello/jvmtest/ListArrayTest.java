package com.hins.sp01hello.jvmtest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author qixuan.chen
 * @date 2019-08-17 18:14
 */
public class ListArrayTest {


    public static void main(String[] args){

        yiyongObject();

        listCopy();

        arrayCopy();
    }

    /**
     * 数组引用、深复制
     */
    private static void arrayCopy() {
        String[] array = new String[6];

        array[0] = "0000";
        array[1] = "1111";
        array[2] = "2222";

        String[] array2 = array;//数组引用 指向同一个地址
        array2[1] = "ddddd";

        String[] array3= array.clone();//数组复制 指向不同地址
        array3[0] = "diii";
        array3[1] = "xxxx";

        System.out.println();
        System.out.println("==1=="+ Arrays.toString(array));
        System.out.println("==2=="+Arrays.toString(array2));
        System.out.println("==3=="+Arrays.toString(array3));
    }

    /**
     * list的浅复制 （外层复制，对象的内存块还是同一个）
     */
    private static void listCopy() {
        ArrayList<User> list1 = new ArrayList<>();//接口方式创建实例
        User user = new User();
        user.setName("zhangsan1");
        list1.add(user);

        ArrayList<User> list2 = (ArrayList<User>)list1.clone();//list的浅复制 （外层复制，对象的内存块还是同一个）

        //深复制
        ArrayList<User> list3 = new ArrayList<>();
        for (User u:list1) {
            list3.add(u.clone());//重写User对象的clone方法
        }

        list2.get(0).setName("lisi00");//所以只修改了list2, 同样list1也会发生变化

        System.out.println();
        System.out.println("====22list1===="+list1.toString());
        System.out.println("====22list2===="+list2.toString());
        System.out.println("====22list3===="+list3.toString());
    }

    /**
     * 内存对象引用例子
     */
    private static void yiyongObject() {
        List<User> list1 = new ArrayList<>();//接口方式创建实例
        User user = new User();
        user.setName("zhangsan1");
        list1.add(user);
        user.setName("zhangsan2");
        list1.add(user);
        User user2 = new User();
        user2.setName("zhangsan2444");
        list1.add(user2);

        List<User> list2 = list1;//引用 list1、list2指向同一块内存


        list2.get(0).setName("lisi00");//所以只修改了list2, 同样list1也会发生变化
        System.out.println();
        System.out.println("====list1===="+list1.toString());
        System.out.println("====list2===="+list2.toString());

        list2.remove(0);//删除
        System.out.println("====list1rr===="+list1.toString());
        System.out.println("====list2rr===="+list2.toString());
    }


    static class User{

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    '}';
        }

        public User() {
        }

        public User(String name) {
            this.name = name;
        }

        @Override
        public User clone()  {
            User user = new User(this.name);
            return user;
        }
    }

}
