package com.example.wendao.mapper;

import com.example.wendao.entity.Like;

import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/4/5 11:13
 * @version: 1.0
 */
public interface LikeMapper {

    /**
     * 添加
     * @param like
     */
    void addLike(Like like);

    /**
     * 删除
     * @param userId
     * @param articleId
     */
    void deleteLikeById(int userId, int articleId);

    /**
     * 获取所有点赞列表
     * @return
     */
    List<Like> getAllLikes();

    /**
     * 更新
     * @param like
     */
    void updateLike(Like like);

    /**
     * 获取该用户的点赞列表
     * @param userId
     * @return
     */
    List<Like> getLikesByUserId(int userId);
}
