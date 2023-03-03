package com.example.wendao.service;

import java.util.Set;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 14:40
 * @version: 1.0
 */
public interface LikeService {
    /**
     * 将点赞的人添加进set集合，返回当前的点赞数
     *
     * @param key
     * @param value
     * @return
     */
    long like(String key, String value);

    /**
     * 将点赞的人从set集合中删除，返回当前的点赞数
     *
     * @param key
     * @param value
     * @return
     */
    long dislike(String key, String value);

    /**
     * 返回当前的点赞数
     *
     * @param key
     * @return
     */
    long likeCount(String key);

    Set<String> likeCountUserId(String key);

    void transLikedCountFromRedis2DB();

}
