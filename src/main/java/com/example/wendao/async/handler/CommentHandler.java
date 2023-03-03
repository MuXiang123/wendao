package com.example.wendao.async.handler;

import com.example.wendao.async.EventHandler;
import com.example.wendao.async.EventModel;
import com.example.wendao.async.EventType;
import com.example.wendao.entity.Article;
import com.example.wendao.entity.Comment;
import com.example.wendao.entity.Notice;
import com.example.wendao.entity.User;
import com.example.wendao.service.ArticleService;
import com.example.wendao.service.CommentService;
import com.example.wendao.service.NoticeService;
import com.example.wendao.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 15:42
 * @version: 1.0
 */
@Component
public class CommentHandler implements EventHandler {

    private static final Logger logger = LoggerFactory.getLogger(CommentHandler.class);

    @Autowired
    UserService userService;

    @Autowired
    ArticleService articleService;

    @Autowired
    NoticeService noticeService;

    @Autowired
    CommentService commentService;

    @Override
    public void doHandler(EventModel eventModel) {
        //对文章进行评论的用户
        String fromId = eventModel.getActorId();
        //用户信息
        User user = userService.selectByUserId(fromId);
        //发表该文章的作者
        String toId = eventModel.getEntityOwnerId();
        //通过用户id，关联文章id，通过文章id获取文章标题
        Integer articleId = Integer.valueOf(eventModel.getExts("articleId"));
        Article article = articleService.selectArticleByUserId(articleId);
        logger.info("文章信息为:{}", article);

        //获取评论id
        Integer commentId = Integer.valueOf(eventModel.getExts("commentId"));
        //查询评论内容
        Comment comment = commentService.selectCommentById(commentId);
        logger.info("评论信息为:{}", comment);

        //向作者发送通知
        Notice notice = new Notice();
        notice.setFromId(fromId);
        notice.setToId(toId);
        notice.setCreatedDate(new Date());
        notice.setContent(user.getNickname() + "评论您的文章：" + article.getArticleTitle() +
                ",评论的内容为：" + comment.getCommentContent());
        notice.setConversationId(fromId + "_" + toId);
        noticeService.insertNotice(notice);
        logger.info("notice:{}", notice);

    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.COMMNET);
    }
}
