package com.hins.sp09redis.services.impl;

import cn.hutool.core.collection.CollUtil;
import com.hins.sp09redis.services.SignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * @author : chenqixuan
 * @date : 2021/2/3
 *  @Description: 基于Redis位图的用户签到功能实现类
 *  <p>
 * 实现功能：
 * 1. 用户签到
 * 2. 检查用户是否签到
 * 3. 获取当月签到次数
 * 4. 获取当月连续签到次数
 * 5. 获取当月首次签到日期
 * 6. 获取当月签到情况
 */
@Service
@Slf4j
public class SignServiceImpl implements SignService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void checkIn(Long userId, LocalDate date) {
        long offset = getOffset(date);
        redisTemplate.opsForValue().setBit(getKey(userId, date), offset, true);
    }

    @Override
    public Boolean isCheckIn(Long userId, LocalDate date) {
        Long offset = getOffset(date);
        String key = getKey(userId, date);
        return redisTemplate.opsForValue().getBit(key, offset);
    }

    @Override
    public Long getSignCount(Long userId, LocalDate date) {
        Long result = (Long) redisTemplate.execute((RedisCallback<Long>) connection -> connection.bitCount(getKey(userId, date).getBytes()));
        return Optional.ofNullable(result).orElse(0L);
    }

    /**
     * 查询今天之前最近的连续签到的天数
     *
     * @param userId /
     * @param date   /
     * @return 连续签到天数
     */
    @Override
    public long getContinuousSignCount(Long userId, LocalDate date) {
        int signCount = 0;


        List<Long> list = bitfield(getKey(userId, date), date.getDayOfMonth(), 0);


        if (CollUtil.isNotEmpty(list)) {
            // 取低位连续不为0的个数即为连续签到次数，需考虑当天尚未签到的情况
            Long v = list.get(0) == null ? 0L : list.get(0);
            for (int i = 0; i < date.getDayOfMonth(); i++) {
                //先右移再左移 如果相等则低位为0
                if (v >> 1 << 1 == v) {
                    // 低位为0且非当天 说明连续签到中断了
                    if (i > 0) {
                        break;
                    }
                } else {
                    signCount += 1;
                }
                v >>= 1;
            }
        }
        return signCount;
    }

    @Override
    public Map<String, Boolean> getSignInfo(Long userId, LocalDate date) {
        Map<String, Boolean> signMap = new TreeMap<>();
        List<Long> list = bitfield(getKey(userId, date), date.lengthOfMonth(), 0);
        if (CollUtil.isNotEmpty(list)) {
            long v = list.get(0) == null ? 0 : list.get(0);
            for (int i = date.lengthOfMonth(); i > 0; i--) {
                LocalDate day = date.withDayOfMonth(i);
                signMap.put(formatDate(day, "yyyy-MM-dd"), v >> 1 << 1 != v);
                v >>= 1;
            }
        }
        return signMap;
    }

    @Override
    public LocalDate getFirstSignDate(Long userId, LocalDate date) {
        Long result = (Long) redisTemplate.execute((RedisCallback<Long>) con -> con.bitPos(getKey(userId, date).getBytes(), true));
        return result < 0 ? null : date.withDayOfMonth((int) (result + 1L));
    }

    private static String getKey(Long userId, LocalDate date) {
        return String.format("user:sign:%d:%s", userId, formatDate(date));
    }

    private static Long getOffset(LocalDate date) {
        int i = date.getDayOfMonth() - 1;
        return Long.parseLong(String.valueOf(i));
    }

    private static String formatDate(LocalDate date) {
        return formatDate(date, "yyyyMM");
    }

    private static String formatDate(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public List<Long> bitfield(String key, int limit, int offset) {
        return (List<Long>) redisTemplate.execute((RedisCallback<List<Long>>) con -> con.bitField(key.getBytes(),
                BitFieldSubCommands.create().get(BitFieldSubCommands.BitFieldType.unsigned(limit)).valueAt(offset)
        ));
    }
}
