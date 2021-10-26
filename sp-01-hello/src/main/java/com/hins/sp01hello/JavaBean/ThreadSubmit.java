package com.hins.sp01hello.JavaBean;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 并行调用接口
 * @author : chenqixuan
 * @date : 2021/10/26
 */
@Slf4j
public class ThreadSubmit {

    private static ThreadPoolExecutor taskExecutor;


    public static void main(String[] args) {
        taskExecutor = new ThreadPoolExecutor(10, 20, 0L,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());


        try {
            test2();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 并行调用接口
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void test2() throws ExecutionException, InterruptedException {
        double upTime;//方法的执行时间(秒)
        long startTime=System.currentTimeMillis(); //获取开始时间

        Future<Map<String,Object>> submit = taskExecutor.submit(() -> {
            //todo 业务处理
            Thread.sleep(5000);
            Map<String,Object> result = new HashMap<>();
            result.put("id",12343);
            result.put("name","xxx密码");
            result.put("createTime",new Date());
            return result;
        });
        Future<Map<String,Object>> submit2 = taskExecutor.submit(() -> {
            //todo 业务处理
            Thread.sleep(2000);
            Map<String,Object> result = new HashMap<>();
            result.put("id",12343);
            result.put("name","xxx密码");
            result.put("createTime",new Date());
            return result;
        });
        Map<String,Object> s1 = submit.get();
        Map<String,Object> s2 = submit2.get();
        log.info("接口1：{}，接口2：{}",s1,s2);

        long endTime=System.currentTimeMillis(); //获取结束时间
        upTime = new BigDecimal(endTime-startTime).divide(new BigDecimal(1000)).doubleValue();//耗时(秒)
        log.info("耗时：{}",upTime);
    }



    /**
     * 并行调用接口
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void test() throws ExecutionException, InterruptedException {
        Future<String> submit = taskExecutor.submit(() -> {
            //todo 业务处理
            Thread.sleep(3000);
            return "333333";
        });
        Future<String> submit2 = taskExecutor.submit(() -> {
            //todo 业务处理
            Thread.sleep(2000);
            return "44444";
        });
        String s1 = submit.get();
        String s2 = submit2.get();
        log.info("接口1：{}，接口2：{}",s1,s2);
    }
}
