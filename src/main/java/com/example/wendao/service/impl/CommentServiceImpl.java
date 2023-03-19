package com.example.wendao.service.impl;

import com.example.wendao.entity.Comment;
import com.example.wendao.mapper.CommentMapper;
import com.example.wendao.service.CommentService;
import com.example.wendao.vo.CommentUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
//        //对于每一个顶级评论，都将子评论归结到一个集合中
        for (CommentUserVo topComment : rawComments) {
            System.out.println("评论内容："+topComment.getCommentContent());
            // 将顶级评论的子孙评论归结到一个集合中
            LinkedList<CommentUserVo> comments = new LinkedList<>();
            List<CommentUserVo> replyCmtsByTopCmt = topComment.getReplyCommentList();
            for (CommentUserVo replyComment : replyCmtsByTopCmt) {
                handleChild(replyComment, comments);
                replyComment.setReplyNickname(topComment.getNickname());
                replyComment.setReplyId(topComment.getCommentUserId());
                replyComment.setCommentContent(topComment.getCommentContent());
                replyComment.setCommentLikeCount(topComment.getCommentLikeCount());
                replyComment.setCommentCreatedTime(topComment.getCommentCreatedTime());
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
    private void handleChild(CommentUserVo replyComment, List<CommentUserVo> parent) {
        List<CommentUserVo> grandchildren = replyComment.getReplyCommentList();
        replyComment.setReplyCommentList(null);
        parent.add(replyComment);
        for (CommentUserVo grandChild : grandchildren) {
            grandChild.setReplyNickname(replyComment.getNickname());
            grandChild.setReplyId(replyComment.getCommentUserId());
            grandChild.setCommentContent(replyComment.getCommentContent());
            grandChild.setCommentLikeCount(replyComment.getCommentLikeCount());
            grandChild.setCommentCreatedTime(replyComment.getCommentCreatedTime());
            if (grandChild.getReplyCommentList() != null) {
                handleChild(grandChild, parent);
            }
        }
    }

    @Override
    public void updateCommentCount(int commentId) {
        Comment comment = commentMapper.selectCommentById(commentId);
        commentMapper.updateCommentCount(commentId, comment.getCommentCount() + 1);
    }
}
