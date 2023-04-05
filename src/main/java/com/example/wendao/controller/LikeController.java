package com.example.wendao.controller;

import com.example.wendao.async.EventModel;
import com.example.wendao.async.EventProducer;
import com.example.wendao.async.EventType;
import com.example.wendao.entity.Article;
import com.example.wendao.entity.User;
import com.example.wendao.redis.JedisService;
import com.example.wendao.redis.LikeKey;
import com.example.wendao.service.ArticleService;
import com.example.wendao.service.LikeService;
import com.example.wendao.service.UserService;
import com.example.wendao.utils.CodeMsg;
import com.example.wendao.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 16:47
 * @version: 1.0
 */
@RestController
public class LikeController {

    @Autowired
    JedisService jedisService;

    @Autowired
    EventProducer eventProducer;

    @Autowired
    LoginController loginController;

    @Autowired
    LikeService likeService;

    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;

    @GetMapping("/like")
    @ResponseBody
    public Result<Long> likeArticle(HttpServletRequest request, String articleId) {
        User user = loginController.getUserInfo(request);
        if (user == null) {
            return Result.error(CodeMsg.ERROR);
        }
        // User user = userService.selectByUserId("17643537768")
        // 这里的值，应该是userId，因为使用userId可以防止用户重复点赞
        long likeCount = likeService.like(articleId, user.getUserId());
        Integer articleInt = Integer.parseInt(articleId);
        Article article = articleService.selectArticleByUserId(articleInt);
        String articleAuthor = article.getArticleUserId();
        EventModel eventModel = new EventModel();
        // 点完赞之后，立即异步通知给用户
        eventProducer.fireEvent(eventModel.setEventType(EventType.LIKE).setActorId(user.getUserId())
                .setEntityType(1).setEntityId(2).setEntityOwnerId(articleAuthor).setExts("articleId", articleId + ""));
        // 点赞之后，articleCount的数据也会对应进行增加，这里使用Quartz设置每多长时间将redis中的数据
        // 更新到mysql中，在优化阶段每隔一小时将redis中存的点赞数量更新到数据库中去
        // 首先根据文章的id, 查找出这篇文章的发布者，然后通过文章发布者的id查找出user对象，然后更新其成就值
        User publishUser = userService.selectByUserId(articleAuthor);
        publishUser.setAchieveValue(publishUser.getAchieveValue() + 5);
        userService.updateByUserId(publishUser);
        return Result.success(likeCount);
    }


    @GetMapping("/dislike")
    @ResponseBody
    public Result<Long> dislikeArticle(HttpServletRequest request, int articleId) {
        User user = loginController.getUserInfo(request);
        if (user == null) {
            return Result.error(CodeMsg.ERROR);
        }
        // User user = userService.selectByUserId("17643537768");

        // 进入这里说明用户是登录过了，然后把用户的相关信息存储到Redis的Set中

        // 这里的值，应该是userId，因为使用userId可以防止用户重复点赞
        long likeCount = likeService.dislike(articleId, user.getUserId());

        /**
         Article article = articleService.selectArticleByTwoUserId(articleId);
         这里就没必要进行异步通知了,并且也不需要即使更新进数据库，设置了定时任务每两个小时将redis的点赞数量
         定时更新到数据库中
         article.setArticleLikeCount(article.getArticleLikeCount() - 1);
         */
        return Result.success(likeCount);
    }
}
