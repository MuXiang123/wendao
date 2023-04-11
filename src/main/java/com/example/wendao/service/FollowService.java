package com.example.wendao.service;

import com.example.wendao.entity.Follow;
import com.example.wendao.entity.User;

import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 14:39
 * @version: 1.0
 */
public interface FollowService {
    /**
     * 新增关注
     * @param follow
     */
    void insertFollow(Follow follow);

    /**
     * 取消关注
     * @param userId
     * @param followId
     */
    void deleteFollow(String userId, String followId);

    /**
     * 查询当前用户的所有关注
     * @param userId
     * @return
     */
    List<Follow> selectAllFollowByUserId(String userId);

    /**
     * 查询全部关注者关注 ID
     * @param followId
     * @return
     */
    List<Follow> selectAllFollowByFollowId(String followId);

    /**
     * 查询是否关注该用户
     * @param userId
     * @param followId
     * @return
     */
    Follow isFollow(String userId, String followId);
}
