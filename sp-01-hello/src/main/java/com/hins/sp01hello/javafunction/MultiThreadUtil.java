package com.hins.sp01hello.javafunction;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 并行分批，串行分批工具类
 * @param <DB>
 * @param <BO>
 */
@Data
public class MultiThreadUtil<DB,BO> {

    //private Executor executor;
    private ExecutorService executor;

    private String jobName;

    private Integer pageSize = 100;

    private Supplier<List<DB>> getDBData; // 查询数据方法

    //private BiFunction<Integer,Integer,List<DB>> getDataFromDB;//分页查询方法

    private Function<List<DB>,List<BO>> proFun; //汇总方法

    private Boolean multiThreadFlag = false;//多线程开关


    public List<BO> summary() throws Exception{
        if(null == executor){
            executor = Executors.newFixedThreadPool(3);
        }

        StopWatch sw = new StopWatch();
        List<DB> dbList = getDBData.get();//执行方法
        double totalPage = Math.ceil(dbList.size() * 1.0 / pageSize);
        if(multiThreadFlag){
            sw.start();
            List<List<DB>> dataList = Lists.partition(dbList,pageSize);
            List<CompletableFuture<List<BO>>> completableFutureList = dataList.stream().map(itemList ->{
                CompletableFuture<List<BO>> taskFuture = CompletableFuture.supplyAsync(() ->{
                   return summaryByPage(itemList);
                },executor);
                return taskFuture;
            }).collect(Collectors.toList());

            List<BO> list = Lists.newLinkedList();
            completableFutureList.forEach(item ->{
                list.addAll(item.join());
            });
            sw.stop();
            System.out.println("多线程分批耗时："+sw.getTotalTimeSeconds());
            return list;
        }

        //单线程
        sw.start();
        List<BO> resultList = new ArrayList<>();
        Lists.partition(dbList,pageSize).forEach(item ->{
            resultList.addAll(summaryByPage(item));
        });
        sw.stop();
        System.out.println("单线分批汇总线程耗时："+sw.getTotalTimeSeconds());
        return resultList;
    }

    private List<BO> summaryByPage(List<DB> list){
        StopWatch sw = new StopWatch();
        sw.start();
        List<BO> resultList = proFun.apply(list);
        sw.stop();
        System.out.println(sw.getTotalTimeSeconds());
        return resultList;
    }








}
