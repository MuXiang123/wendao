package com.example.wendao.mapper;

import com.example.wendao.entity.Comment;
import com.example.wendao.vo.CommentUserVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 11:37
 * @version: 1.0
 */
public interface CommentMapper {

    /**
     * 新增评论
     * @param comment
     */
    void insertComment(Comment comment);

    /**
     * 删除评论
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
     * 查询最后插入的评论
     * @return
     */
    int selectLastInsertCommentId();


    /**
     * 查询评论的回复列表
     * @param commentArticleId
     * @return
     */
    List<CommentUserVo> selectCommentLists(int commentArticleId);

}
