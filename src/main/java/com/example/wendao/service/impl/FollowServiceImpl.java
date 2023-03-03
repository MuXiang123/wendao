package com.example.wendao.service.impl;

import com.example.wendao.entity.Follow;
import com.example.wendao.mapper.FollowMapper;
import com.example.wendao.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 14:39
 * @version: 1.0
 */
@Service
public class FollowServiceImpl implements FollowService {
    @Autowired(required = false)
    FollowMapper followMapper;


    @Override
    public void insertFollow(Follow follow) {
        followMapper.insertFollow(follow);
    }

    @Override
    public void deleteFollow(String userId, String followId) {
        followMapper.deleteFollow(userId, followId);
    }

    @Override
    public List<Follow> selectAllFollowByUserId(String userId) {
        return followMapper.selectAllFollowByUserId(userId);
    }

    @Override
    public List<Follow> selectAllFollowByFollowId(String followId) {
        return followMapper.selectAllFollowByFollowId(followId);
    }
}
