package com.example.wendao.service;

import com.example.wendao.entity.Comment;
import com.example.wendao.vo.CommentUserVo;

import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 11:47
 * @version: 1.0
 */
public interface CommentService {

    /**
     * 新增
     * @param comment
     */
    void insertComment(Comment comment);

    /**
     * 删除
     * @param commentId
     */
    void deleteComment(int commentId);

    /**
     * 根据id查询评论
     * @param commentId
     * @return
     */
    Comment selectCommentById(int commentId);

    /**
     * 查询所有评论
     * @param commentArticleId
     * @return
     */
    List<Comment> selectAllComment(int commentArticleId);

    /**
     * 获取最后一条评论
     * @return
     */
    int selectLastInsertCommentId();

    /**
     * 查询评论的回复
     * @param articleCommentId
     * @return
     */
    List<CommentUserVo> selectCommentLists(int articleCommentId);

    /**
     * 增加子评论数
     * @param commentId
     */
    void updateCommentCount(int commentId);
}
