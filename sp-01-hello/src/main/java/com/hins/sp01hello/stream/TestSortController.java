package com.hins.sp01hello.stream;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : chenqixuan
 * @date : 2021/5/12
 */
public class TestSortController {

    public static void main(String[] args) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            List<Pig> list = new ArrayList();

            list.add(new Pig(1L, "猪爸爸", 31, "M", sdf.parse("2021-01-23")));
            list.add(new Pig(2L, "猪妈妈", 28, "F", sdf.parse("2021-01-26")));
            list.add(new Pig(3L, "乔治", 2, "M", sdf.parse("2021-04-23")));
            list.add(new Pig(4L, "佩奇", 5, "F", sdf.parse("2021-02-23")));


            System.out.println("========排序前=========");
            list.forEach(mo ->{
                System.out.println(mo.toString());
            });

            //先以属性一降序,再进行属性二升序
            List<Pig> list2 = list.stream().sorted(Comparator.comparing(Pig::getId,Comparator.reverseOrder()).thenComparing(Pig::getAge)).collect(Collectors.toList());

            System.out.println("========排序后=========");
            list2.forEach(mo ->{
                System.out.println(mo.toString());
            });

            list.add(new Pig(null, "佩奇", 5, "F", sdf.parse("2021-07-23")));
            List<Pig> list3 = list.stream()
                    .sorted(Comparator
                            .comparing(Pig::getId,Comparator.nullsFirst(Long::compareTo).reversed())
                            .thenComparing(Pig::getAge)
                            .thenComparing(Pig::getCreateTime,Comparator.reverseOrder())
                    ).collect(Collectors.toList());


            System.out.println("========排序后3=========");
            list3.forEach(mo ->{
                System.out.println(mo.toString());
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


    @Data
    @AllArgsConstructor
    private static class Pig{
         private Long id;
         private String name;
         private Integer age;
         private String sex;
         private Date createTime;
    }
}


