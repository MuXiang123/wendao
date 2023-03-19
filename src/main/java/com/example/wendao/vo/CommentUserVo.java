package com.example.wendao.vo;

import com.example.wendao.entity.Comment;
import lombok.Data;

import java.util.List;

/**
 * @author: zhk
 * @description: 评论的用户vo
 * @date: 2023/3/3 9:40
 * @version: 1.0
 */
@Data
public class CommentUserVo extends Comment {
    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 该评论下回复的评论，子评论
     */
    private List<CommentUserVo> replyCommentList;

    /**
     * 子评论回复的人,当前评论是根评论时为空
     */
    private String replyNickname;

    /**
     * 子评论回复的人,当前评论是根评论时为空
     */
    private String replyId;
}
