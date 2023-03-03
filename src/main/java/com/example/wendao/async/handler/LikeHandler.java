package com.example.wendao.async.handler;

import com.example.wendao.async.EventHandler;
import com.example.wendao.async.EventModel;
import com.example.wendao.async.EventType;
import com.example.wendao.entity.Article;
import com.example.wendao.entity.Notice;
import com.example.wendao.entity.User;
import com.example.wendao.redis.JedisService;
import com.example.wendao.service.ArticleService;
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
 * @date: 2023/3/3 16:05
 * @version: 1.0
 */
@Component
public class LikeHandler implements EventHandler {

    private static final Logger logger = LoggerFactory.getLogger(LikeHandler.class);

    @Autowired
    JedisService jedisService;

    @Autowired
    UserService userService;

    @Autowired
    ArticleService articleService;

    @Autowired
    NoticeService noticeService;

    @Override
    public void doHandler(EventModel eventModel) {
        // 给文章点赞的用户
        String fromId = eventModel.getActorId();
        User user = userService.selectByUserId(fromId);
        // 发表该篇文章的作者
        String toId = eventModel.getEntityOwnerId();
        // 这里要通过用户的Id来关联文章，来获取到用户发表的文章，获取文章的标题信息
        int articleId = Integer.valueOf(eventModel.getExts("articleId"));
        logger.info(toId);
        logger.info(String.valueOf(articleId));
        //获取文章信息
        Article article = articleService.selectArticleByUserId(articleId);
        logger.info("文章信息为:{}", article);
        //进行通知
        Notice notice = new Notice();
        notice.setFromId(fromId);
        notice.setToId(toId);
        notice.setCreatedDate(new Date());
        notice.setContent(user.getNickname() + "点赞了您的文章：" + article.getArticleTitle());
        notice.setConversationId(fromId + "_" + toId);
        logger.info("notice:{}", notice);
        noticeService.insertNotice(notice);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
