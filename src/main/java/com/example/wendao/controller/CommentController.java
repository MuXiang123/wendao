package com.example.wendao.controller;

import com.example.wendao.async.EventModel;
import com.example.wendao.async.EventProducer;
import com.example.wendao.async.EventType;
import com.example.wendao.dto.CommentDto;
import com.example.wendao.entity.Article;
import com.example.wendao.entity.Comment;
import com.example.wendao.entity.User;
import com.example.wendao.redis.JedisService;
import com.example.wendao.service.ArticleService;
import com.example.wendao.service.CommentService;
import com.example.wendao.service.UserService;
import com.example.wendao.utils.CodeMsg;
import com.example.wendao.utils.Result;
import com.example.wendao.vo.CommentUserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 15:14
 * @version: 1.0
 */
@RestController
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    LoginController loginController;

    @Autowired
    JedisService jedisService;

    @Autowired
    ArticleService articleService;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    EventProducer eventProducer;

    /**
     * 在文章的详情页进行评论, 从前端界面传过来文章id以及评论的内容
     */
    @PostMapping("/insert/comment")
    @ResponseBody
    public Result<Boolean> commentArticle(HttpServletRequest request, @RequestBody CommentDto commentDto) {
        User user = loginController.getUserInfo(request);
        if (user == null) {
            return Result.error(CodeMsg.ERROR);
        }

        // 为了方便前后端联调，先统一将user对象写死
        // User user = userService.selectByUserId("18392710807");
        // 进入到下面来说明用户登录了，将这条评论插入comment表
        Comment comment = new Comment();
        comment.setCommentArticleId(commentDto.getArticleId());
        comment.setCommentUserId(user.getUserId());
        comment.setCommentContent(commentDto.getContent());
        comment.setCommentCreatedTime(new Date());
        if(commentDto.getParentId() != -1){
            comment.setParentCommentId(commentDto.getParentId());
            commentService.updateCommentCount(commentDto.getParentId());
        }else{
            //根评论的父id 为-1
            comment.setParentCommentId(-1);
        }
        commentService.insertComment(comment);
        // 评论添加成功之后，文章的评论数+1
        Article article = articleService.selectArticleByArticleId(commentDto.getArticleId());
        article.setArticleCommentCount(article.getArticleCommentCount() + 1);
        articleService.updateArticle(article);

        // 然后，需要将评论这个异步通知，发给被评论的用户
        EventModel eventModel = new EventModel();

        String articleAuthor = articleService.selectArticleByUserId(commentDto.getArticleId()).getArticleUserId();
        // 获取Comment表中最新的comment_id,即表示当前的comment对象
        int commentId = commentService.selectLastInsertCommentId();
        logger.info("评论的id:" + commentId);


        eventModel.setActorId(user.getUserId()).setEntityType(1).setEntityId(1).setEntityOwnerId(articleAuthor)
                .setEventType(EventType.COMMNET).setExts("articleId", commentDto.getArticleId() + "").
                setExts("commentId", commentId + "");
        // 将该评论异步通知给文章的作者
        eventProducer.fireEvent(eventModel);

        // 获取文章作者信息，然后更新文章的成就值
        User publishUser = userService.selectByUserId(articleAuthor);
        publishUser.setAchieveValue(publishUser.getAchieveValue() + 10);
        userService.updateByUserId(publishUser);

        return Result.success(true);


    }

    @GetMapping("/comment/list")
    @ResponseBody
    public Result<List<CommentUserVo>> commentArticleLists(Integer articleId) {
        List<CommentUserVo> commentUserVoList = commentService.selectCommentLists(articleId);
        return Result.success(commentUserVoList);
    }
}
