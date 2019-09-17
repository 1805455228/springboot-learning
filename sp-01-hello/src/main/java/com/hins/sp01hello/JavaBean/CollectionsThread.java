package com.hins.sp01hello.JavaBean;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 集合之线程安全
 * @author qixuan.chen
 * @date 2019-09-15 18:01
 */
public class CollectionsThread {

    public static void main(String[] args){
        //list set map集合之线程安全使用
        //list
        List<String> synlist = Collections.synchronizedList(new ArrayList<>());
        List<String> cwlist = new CopyOnWriteArrayList();//写时复制技术（juc）

        //HashSet底层是hashmap
        Set<String> synchronizedSet = Collections.synchronizedSet(new HashSet<>());
        Set<String> cwSet = new CopyOnWriteArraySet<>();//写时复制技术（juc）

        //map
        Map<String,Object> synchronizedMap = Collections.synchronizedMap(new HashMap<>());
        Map<String,Object> map = new ConcurrentHashMap<>();//juc
    }
}
