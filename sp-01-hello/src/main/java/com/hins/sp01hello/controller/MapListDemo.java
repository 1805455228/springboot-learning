package com.hins.sp01hello.controller;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author qixuan.chen
 * @date 2019-07-31 16:02
 */
public class MapListDemo {

    public static void main(String[] args) {
        Map<String,Object>  map =new HashMap();
        map.put("key","vvvv");

        //转线程安全
        Map m = Collections.synchronizedMap(map);

        List list = new ArrayList();
        list.add("000");

        List<String> list2 = new LinkedList();
        list2.add("2222");

        Set<String> set = new HashSet();
        set.add("22223");

        Map<String,Object> m2 = new ConcurrentHashMap();
        m2.put("dd","eeee");

    }

}
