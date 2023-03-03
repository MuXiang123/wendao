package com.example.wendao.vo;

import com.example.wendao.entity.Comment;

/**
 * @author: zhk
 * @description: 评论的用户vo
 * @date: 2023/3/3 9:40
 * @version: 1.0
 */
public class CommentUserVo extends Comment {
    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户昵称
     */
    private String nickname;
}
