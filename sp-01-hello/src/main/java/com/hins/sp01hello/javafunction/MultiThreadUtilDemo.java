package com.hins.sp01hello.javafunction;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MultiThreadUtilDemo {


    public static void main(String[] args) throws Exception {
        String idType = "1";
        Integer pageNum = 1;
        MultiThreadUtil<StudentDO,ClassBO> util = new MultiThreadUtil();
        util.setJobName("班级统计");
        util.setMultiThreadFlag(true);//是否并行分批
        util.setGetDBData(()->getStudentList());
        util.setProFun((list) ->getClasstList(idType,list,pageNum));
        List<ClassBO> classBOList = util.summary();

        System.out.println("记录数："+classBOList.size());
    }


    private static List<StudentDO> getStudentList(){
        List<StudentDO> result = new ArrayList<>();
        for(int i=0;i<100;i++){
            StudentDO classBO = new StudentDO();
            classBO.setId(i+"");
            classBO.setName("bj"+i);
            classBO.setSxAvgScore(1f+i);
            classBO.setYwAvgScore(1f+i);
            result.add(classBO);
        }
        return result;
    }


    private static List<ClassBO> getClasstList(String idType,List<StudentDO> list,Integer pageNum){
        List<ClassBO> result = new ArrayList<>();
        for(int i=0;i<100;i++){
            ClassBO classBO = new ClassBO();
            classBO.setId(i+"");
            classBO.setName("bj"+i);
            classBO.setPersonNum(1+i);
            classBO.setSxAvgScore(1f+i);
            classBO.setYwAvgScore(1f+i);
            result.add(classBO);
        }
        return result;
    }


    @Data
   static class ClassBO{
        private String id;
        private String name;//班级名称
        private Float sxAvgScore;
        private Float ywAvgScore;
        private Integer personNum;
    }

    @Data
   static class StudentDO{
        private String id;
        private String name;
        private Integer age;
        private Date dateTime;
        private Float sxAvgScore;
        private Float ywAvgScore;
    }

}
