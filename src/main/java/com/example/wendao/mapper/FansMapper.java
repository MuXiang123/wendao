package com.example.wendao.mapper;

import com.example.wendao.entity.Fans;


import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 14:25
 * @version: 1.0
 */
public interface FansMapper {
    /**
     * 新增粉丝
     * @param fans
     */
    void insertFans(Fans fans);

    /**
     * 删除粉丝
     * @param userId
     * @param fansId
     */
    void deleteFans(String userId, String fansId);

    /**
     * 展示用户的所有粉丝
     * @param userId
     * @return
     */
    List<Fans> selectAllFansByUserId(String userId);

}
