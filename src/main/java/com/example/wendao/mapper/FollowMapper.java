package com.example.wendao.mapper;

import com.example.wendao.entity.Follow;


import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 14:32
 * @version: 1.0
 */
public interface FollowMapper {

    /**
     * 关注用户
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
     * 所有关注
     * @param userId
     * @return
     */
    List<Follow> selectAllFollowByUserId(String userId);

    /**
     * 关注的粉丝
     * @param followId
     * @return
     */
    List<Follow> selectAllFollowByFollowId(String followId);

}
