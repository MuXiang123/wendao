package com.example.wendao.async.handler;

import com.example.wendao.async.EventHandler;
import com.example.wendao.async.EventModel;
import com.example.wendao.async.EventType;
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
 * @date: 2023/3/3 16:02
 * @version: 1.0
 */
@Component
public class FollowHandler implements EventHandler {
    private static final Logger logger = LoggerFactory.getLogger(FollowHandler.class);

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
        //关注的用户
        String fromId = eventModel.getActorId();
        //被关注的用户
        String toId = eventModel.getEntityOwnerId();
        User userFrom = userService.selectByUserId(fromId);
        //通知 没必要在获取用户本身了，只需要写明谁关注了你
        Notice notice = new Notice();
        notice.setFromId(fromId);
        notice.setToId(toId);
        notice.setContent(userFrom.getNickname() + "关注了用户你");
        notice.setConversationId(fromId + "_" + toId);
        notice.setCreatedDate(new Date());
        notice.setHasRead(0);
        logger.info("notice:{}", notice);
        noticeService.insertNotice(notice);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.FOLLOW);
    }
}
