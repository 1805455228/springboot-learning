package com.hins.sp09redis.services;

import com.hins.sp09redis.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : chenqixuan
 * @date : 2021/2/3
 */
@Service
public class UserService {

    public List<UserEntity> queryAll() {
        return null;
    }

    public UserEntity query(String uuid) {
        return null;
    }

    public Long count() {
        return 1L;
    }
}
