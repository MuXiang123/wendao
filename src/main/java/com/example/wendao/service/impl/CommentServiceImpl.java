package com.example.wendao.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.wendao.entity.Comment;
import com.example.wendao.mapper.CommentMapper;
import com.example.wendao.service.CommentService;
import com.example.wendao.vo.CommentUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
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
        if (commentArticleId < 0) {
            return null;
        }
        List<CommentUserVo> raw = commentMapper.selectParentComment(commentArticleId, -1);
        return getParent(raw);
    }

    /**
     * 处理每个根评论
     * @param rawComments
     * @return
     */
    private List<CommentUserVo> getParent(List<CommentUserVo> rawComments) {
        for (CommentUserVo topComment : rawComments) {
            // 将顶级评论的子孙评论归结到一个集合中
            LinkedList<CommentUserVo> comments = new LinkedList<>();
            List<CommentUserVo> replyCmtsByTopCmt = topComment.getReplyCommentList();
            for (CommentUserVo replyComment : replyCmtsByTopCmt) {
                handleChild(replyComment, comments, topComment.getNickname(), topComment.getCommentUserId(),
                        topComment.getCommentContent(), topComment.getCommentLikeCount(), topComment.getCommentCreatedTime());
            }
            topComment.setReplyCommentList(comments);
        }
        return rawComments;
    }

    /**
     * 处理二级评论以及子评论
     * @param replyComment
     * @param parent
     */
    private void handleChild(CommentUserVo replyComment, List<CommentUserVo> parent, String replyNickname,
                             String replyId, String commentContent, int commentLikeCount, Date commentCreatedTime) {
        List<CommentUserVo> grandchildren = replyComment.getReplyCommentList();
        replyComment.setReplyCommentList(null);
        replyComment.setReplyNickname(replyNickname);
        replyComment.setReplyId(replyId);
        replyComment.setCommentContent(replyComment.getCommentContent() != null ? replyComment.getCommentContent() : commentContent);
        replyComment.setCommentLikeCount(replyComment.getCommentLikeCount() > 0 ? replyComment.getCommentLikeCount() : commentLikeCount);
        replyComment.setCommentCreatedTime(replyComment.getCommentCreatedTime() != null ? replyComment.getCommentCreatedTime() : commentCreatedTime);
        parent.add(replyComment);
        if (grandchildren != null) {
            for (CommentUserVo grandChild : grandchildren) {
                handleChild(grandChild, parent, replyComment.getNickname(), replyComment.getCommentUserId(),
                        replyComment.getCommentContent(), replyComment.getCommentLikeCount(), replyComment.getCommentCreatedTime());
            }
        }
    }

    @Override
    public void updateCommentCount(int commentId) {
        Comment comment = commentMapper.selectCommentById(commentId);
        commentMapper.updateCommentCount(commentId, comment.getCommentCount() + 1);
    }

    @Override
    public void commentLike(int commentId) {
        commentMapper.updateCommentLike(commentId);
    }
}
