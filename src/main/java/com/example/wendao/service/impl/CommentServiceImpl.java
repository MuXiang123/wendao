package com.example.wendao.service.impl;

import com.example.wendao.entity.Comment;
import com.example.wendao.mapper.CommentMapper;
import com.example.wendao.service.CommentService;
import com.example.wendao.vo.CommentUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 11:48
 * @version: 1.0
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired(required = false)
    CommentMapper commentMapper;

    @Override
    public void insertComment(Comment comment) {
        commentMapper.insertComment(comment);
    }

    @Override
    public void deleteComment(int commentId) {
        commentMapper.deleteComment(commentId);
    }

    @Override
    public Comment selectCommentById(int commentId) {
        return commentMapper.selectCommentById(commentId);
    }

    @Override
    public List<Comment> selectAllComment(int commentArticleId) {
        return commentMapper.selectAllComment(commentArticleId);
    }

    @Override
    public int selectLastInsertCommentId() {
        return commentMapper.selectLastInsertCommentId();
    }

    @Override
    public List<CommentUserVo> selectCommentLists(int commentArticleId) {
        return commentMapper.selectCommentLists(commentArticleId);
    }
}
