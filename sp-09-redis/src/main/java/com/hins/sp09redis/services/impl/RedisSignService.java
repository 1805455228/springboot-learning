package com.hins.sp09redis.services.impl;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;


/**
 * 用户签到表 服务实现类 写的太复杂!
 * </p>
 * 还可以加的补签功能,bitField set
 * @author : chenqixuan
 * @date : 2021/2/3
 */
public class RedisSignService {



    //    @Autowired
//    Jedis jedis = SpringUtil.getBean(JedisUtil.class).getJedisPool().getResource();
//    Jedis jedis = new Jedis();

//    @Autowired
//    JedisUtil jedisUtil;
//
//
//    @Resource
//    SignMapper mapper;
//
//    /**
//     * 写的过于复杂/(ㄒoㄒ)/~~
//     * 签到并返回相关签到信息,用户要再次查看今日的签到状况可以再次调用该方法
//     */
//    @Transactional
//    @Override
//    public SignDto sign(Integer uid) {
//        Jedis jedis = jedisUtil.getJedis();
//        LocalDate date = LocalDate.now();
//        // 获取签到状况true 已经签到 false 未签到
//        boolean isSign = checkSign(uid, date);
//        String key1 = RedisKeyUtil.getUserSignKey(uid, date);
//        String key2 = RedisKeyUtil.getDailySign(date);
//        String key3 = RedisKeyUtil.getMonthlySign(date);
//        long day = date.getDayOfMonth();
//        Long zrank = null;// 本次签到排名
//        // 这个不提前会有错 redis签到 设置bit数字 [day-1]=1 从0开始
//        if (!isSign) { // 进行签到-------redis操作
//            jedis.setbit(key1, day - 1, true);
//            // 加入有序的集合,根据时间戳来排序
//            jedis.zadd(key2, System.currentTimeMillis(), uid.toString());
//            // 每签到一次,用户的月签到记录加一
//            jedis.zincrby(key3, 1, uid.toString());
//            zrank = jedis.zrank(key2, uid.toString());
//            // 如果是第一个签到的人 设置下过期时间为明天0点
//            if (zrank == 0) {
//                long current = System.currentTimeMillis();
//                long zero = current - (current + TimeZone.getDefault().getRawOffset()) % (1000 * 3600 * 24) + 86400000;
//                jedis.pexpireAt(key2, zero);
//            }
//        } else {
//            // 签到过了就直接返回排名 甚至去获取分数
//            zrank = jedis.zrank(key2, uid.toString());
//        }
//        // 0001111000串
//        String signInfo = this.getSignInfo(uid, date);
//        // 获取用户签到信息(mysql+redis)
//        Sign userSignInfo = this.getSignByUid(uid);
//        // 获取这个月连续签到次数
//        Integer ContSignCount = this.getUserCheckContTime(signInfo.toCharArray());
//        // 截至当前连续签到次数 如果这个月全勤,那么就加上这个月之前连续签到个记录,否则就只有这个月的
//        Integer contCount = (day == ContSignCount)
//                ? userSignInfo.getSignContNow() + ContSignCount
//                : ContSignCount;
//        // 更新最长连续签到记录
//        Integer contMaxCount = contCount > userSignInfo.getSignContMax()
//                ? contCount
//                : userSignInfo.getSignContMax();
//        // 总签到次数
//        Integer allCount = userSignInfo.getSignContAll();
//        if (!isSign) { // 进行签到----数据库表操作
//            allCount++; // 签到成功总签到数+1
//            UpdateWrapper<Sign> updateWrapper = new UpdateWrapper<>();
//            updateWrapper.set("sign_cont_all", allCount)
//                    .set("sign_cont_max", contMaxCount)
//                    .eq("uid", uid);
//            this.update(updateWrapper);
//        }
//        // 数据统计返回给前端展示
//        jedis.close();
//        return new SignDto()
//                .setFirstSignDate(this.getFirstSignDate(uid, date))
//                .setSignCount(this.getSignCount(uid, date))
//                .setSignInfo(signInfo)
//                .setSignResult(isSign)
//                .setSignRank(zrank + 1) // 排名 从0开始
//                .setSignPoint(null) // TODO 获取积分
//                .setSignContNow(contCount)
//                .setSignContAll(allCount)
//                .setSignContMax(contMaxCount);
//    }
//
//    // 判断是否签到
//    @Override
//    public boolean checkSign(Integer uid, LocalDate date) {
//        Jedis jedis = jedisUtil.getJedis();
//        long offset = date.getDayOfMonth() - 1;
//        String key = RedisKeyUtil.getUserSignKey(uid, date);
//        Boolean getbit = jedis.getbit(key, offset);
//        jedis.close();
//        return getbit;
//    }
//
//    @Override
//    public SitesInfoDto getSitesSignInfo(LocalDate date) {
//        Jedis jedis = jedisUtil.getJedis();
//        List<SignInfoDto> allSignRank = mapper.getAllSignRank();
//        List<SignInfoDto> nowSignRank = mapper.getNowSignRank();
//        String key2 = RedisKeyUtil.getDailySign(date);
//        String key3 = RedisKeyUtil.getMonthlySign(date);
//        Long daily = jedis.zcard(key2);
//        Long monthly = jedis.zcard(key3);
//        // System.out.println(allSignRank);
//        jedis.close();
//        return new SitesInfoDto().setAllSignRank(allSignRank)
//                .setNowSignRank(nowSignRank)
//                .setDailyCount(daily)
//                .setMonthlyCount(monthly);
//    }
//
//    // 获取当月签到次数
//    @Override
//    public Integer getSignCount(Integer uid, LocalDate date) {
//        Jedis jedis = jedisUtil.getJedis();
//        String key = RedisKeyUtil.getUserSignKey(uid, date);
//        int i = jedis.bitcount(key).intValue();
//        jedis.close();
//        return i;
//    }
//
//    // 从redis中获取010101的字符串按照当月长度进行补全
//    @Override
//    public String getSignInfo(int uid, LocalDate date) {
//        // 落库的时候可以用
//        int lengthOfMonth = date.lengthOfMonth();
//        char[] arr = this.getCurrentSignInfoFromRedis(uid, date).toCharArray();
//        char[] result = new char[lengthOfMonth];
//        int range = lengthOfMonth - arr.length;// 需要补0 的长度
//        Arrays.fill(result, 0, range, '0');
//        System.arraycopy(arr, 0, result, range, arr.length);
//        return new String(result);
//    }
//
//    // 从redis中获取第一个以开始的1xxx0字符串,从第一个1开始(例:不存在001011,只有1011)
//    public String getCurrentSignInfoFromRedis(int uid, LocalDate date) {
//        Jedis jedis = jedisUtil.getJedis();
//        int lengthOfMonth = date.lengthOfMonth();
//        String key = RedisKeyUtil.getUserSignKey(uid, date);
//        Long get = jedis.bitfield(key, "GET", "u" + lengthOfMonth, "0").get(0);
//        jedis.close();
//        return Long.toBinaryString(get);
//    }
//
//    // 从redis中获取本月第一次签到日期(也就是第一个1的索引)
//    @Override
//    public LocalDate getFirstSignDate(int uid, LocalDate date) {
//        Jedis jedis = jedisUtil.getJedis();
//        long pos = jedis.bitpos(RedisKeyUtil.getUserSignKey(uid, date), true);
//        jedis.close();
//        return pos < 0 ? null : date.withDayOfMonth((int) (pos + 1));
//    }
//
//    // 根据uid获取签到信息从mysql
//    @Override
//    public Sign getSignByUid(int uid) {
//        return this.getOne(new UpdateWrapper<Sign>().eq("uid", uid), false);
//    }
//
//    // 根据当月签到记录(11010101)获取用户连续签到次数
//    @Override
//    public int getUserCheckContTime(char[] chars) {
//        int flag = -1; // 判断是否断签
//        int cont = 0; // 连续签到次数
//        for (int i = chars.length - 1; i >= 0; i--) {
//            // 如果首次遇到1后再遇到0 设置标志,表示断签了
//            if (flag == 0 && chars[i] == '0') {
//                break;
//            } else if (chars[i] == '1') {
//                flag = 0;
//                cont++;
//            }
//        }
//
//        return cont;
//    }
//
//    public void redisDataToDB(LocalDate date) {
//        Jedis jedis = jedisUtil.getJedis();
//        Set<String> keys = jedis.keys(RedisKeyUtil.KEY_USER_SIGN_PREFIX + "*");
//        keys.forEach(key -> {
//            int uid = Integer.parseInt(key.split("_")[2]);
//            String signInfo = this.getSignInfo(uid, date);
//            String str = String.format("[%s]%s_", date.format(DateTimeFormatter.ofPattern("yyyy-MM")), signInfo);
//            System.out.println(str);
//            mapper.updateSignRecordWithConcat(str, uid);
//            jedis.del(key);
//        });
//        jedis.close();
//    }

}