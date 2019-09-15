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

        Map<String,Object>  map3 =new Hashtable<>();
        map3.put("key","vvvv");

        String[] keys ={"qqqq","2222","444444","中国馆过"};
        int n = 20;//数组的长度（数组允许存储的范围）
        //计算这个key应该存储到数组的那个位置
        for (String key:keys) {
            int h;
            int hash = (h = key.hashCode()) ^ (h >>> 16);//根据hashcode 计算hash
            int i = (n - 1) & hash;
            //System.out.println("====="+i);

            System.out.println("===1==="+hash(key.hashCode()));
            //System.out.println("===2==="+indexFor(key.hashCode(),n));
        }

    }

    /**
     * 根据hashcode 计算hash
     */
    static int hash(int h) {
        //h为hashcode
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /**
     * 计算位置
     * @param h
     * @param length
     * @return
     */
    static int indexFor(int h, int length) {
        //h为hashcode
        return h & (length-1);
    }


}
