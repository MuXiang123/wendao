package com.example.wendao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: zhk
 * @description: 评论
 * @date: 2023/3/1 16:01
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    /**
     * 评论id
     */
    private int commentId;

    /**
     * 文章id
     */
    private int commentArticleId;

    /**
     * 用户id
     */
    private String commentUserId;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 评论点赞数
     */
    private int commentLikeCount;

    /**
     * 评论回复数
     */
    private int commentCount;

    /**
     * 评论创建时间
     */
    private Date commentCreatedTime;


}