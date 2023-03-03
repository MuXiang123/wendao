package com.example.wendao.service;

import com.example.wendao.entity.Follow;

import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 14:39
 * @version: 1.0
 */
public interface FollowService {
    void insertFollow(Follow follow);

    void deleteFollow(String userId, String followId);

    List<Follow> selectAllFollowByUserId(String userId);

    List<Follow> selectAllFollowByFollowId(String followId);

}
