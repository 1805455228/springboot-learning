package com.hins.sp01hello;

import java.util.*;

/**
 * @author qixuan.chen
 * @date 2019-09-10 10:13
 */
public class demo {


    public static void main(String[] args){
        String[] arr = {"z","a","y","c","b"};
        List<String> list = (List) Arrays.asList(arr);
        Collections.sort(list);
        for (String a:list) {
            System.out.println(a);
        }



        String[] keys = {"10A","21A","10A","3GF"};
        Map<String,Integer> map = new HashMap<>();
        for (String key:keys) {
            if(map.containsKey(key)){
                int num = map.get(key);
               map.put(key,num+1);
            } else{
               map.put(key,0);
            }
        }
        for(String k:map.keySet()){
            System.out.println(k+"次数:"+map.get(k));
        }

    }
}
