package com.example.wendao.service.impl;

import com.example.wendao.entity.User;
import com.example.wendao.mapper.UserMapper;
import com.example.wendao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/1 21:09
 * @version: 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired(required = false)
    UserMapper userMapper;

    @Override
    public void insert(User user) {
        userMapper.insertUser(user);
    }

    @Override
    public User selectByUserId(String userId) {
        return userMapper.selectByUserId(userId);
    }

    @Override
    public void updateByUserId(User user) {
        userMapper.updateByUserId(user);
    }

    @Override
    public void resetAchieveValue() {
        userMapper.resetAchieveValue();
    }

    @Override
    public List<User> top10LeaderBoard() {
        return userMapper.top10LeaderBoard();
    }
}
