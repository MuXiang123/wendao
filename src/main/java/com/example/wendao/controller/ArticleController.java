package com.example.wendao.controller;

import com.example.wendao.entity.Article;
import com.example.wendao.entity.Category;
import com.example.wendao.entity.User;
import com.example.wendao.redis.JedisService;
import com.example.wendao.service.ArticleService;
import com.example.wendao.service.CategoryService;
import com.example.wendao.service.ElasticSearchService;
import com.example.wendao.service.UserService;
import com.example.wendao.utils.CodeMsg;
import com.example.wendao.utils.Result;
import com.example.wendao.vo.ArticleUserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 15:13
 * @version: 1.0
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Autowired
    ElasticSearchService elasticSearchService;

    @Autowired
    JedisService jedisService;

    @Autowired
    LoginController loginController;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);

    @PostMapping("/insert")
    @ResponseBody
    public Result<Boolean> insertArticle(HttpServletRequest request, @RequestBody Article article) {
        User user = loginController.getUserInfo(request);
        if (user == null) {
            log.info("用户未登录");
            return Result.error(CodeMsg.NOT_LOGIN);
        }

        log.info(article.toString());
        // 需要通过传过来的categoryName来查找出categoryId
        Category category = categoryService.selectCategoryByName(article.getArticleCategoryName());
        log.info("category对象为：{}", category.toString());

        article.setArticleCategoryId(category.getCategoryId());
        article.setCreatedTime(new Date());
        article.setUpdateTime(new Date());
        article.setArticleUserId(user.getUserId());

        // 发表一篇文章用户的成就值+10分
        User publishUser = userService.selectByUserId(user.getUserId());
        publishUser.setAchieveValue(publishUser.getAchieveValue() + 10);
        userService.updateByUserId(publishUser);
        articleService.insertArticle(article);

        elasticSearchService.saveArticle(article);
        return Result.success(true);
    }

    @GetMapping("/can/edit")
    @ResponseBody
    public Result<Boolean> canEditArticle(HttpServletRequest request, int articleId) {
        User user = loginController.getUserInfo(request);
        if (user == null) {
            log.info("用户未登录");
            return Result.error(CodeMsg.NOT_LOGIN);
        }
        Article article = articleService.selectArticleByArticleId(articleId);
        if (article.getArticleUserId().equals(user.getUserId())) {
            // 是发表文章的作者，才有权更新文章
            return Result.success(true);
        } else {
            return Result.success(false);
        }
    }

    @PostMapping("/edit")
    @ResponseBody
    public Result<Boolean> editArticle(@RequestBody Article article) {
        article.setUpdateTime(new Date());
        Category category = categoryService.selectCategoryByName(article.getArticleCategoryName());
        log.info("category对象为：{}", category.toString());
        article.setArticleCategoryId(category.getCategoryId());
        articleService.updateArticle(article);
        elasticSearchService.updateArticle(article);
        return Result.success(true);
    }

    @GetMapping("/delete")
    @ResponseBody
    public Result<Boolean> deleteArticle(HttpServletRequest request, int articleId) {
        User user = loginController.getUserInfo(request);
        if (user == null) {
            log.info("用户未登录");
            return Result.error(CodeMsg.NOT_LOGIN);
        }
        Article article = articleService.selectArticleByArticleId(articleId);
        if (article.getArticleUserId().equals(user.getUserId())) {
            // 是发表文章的作者，才能有权操作删除文章
            articleService.deleteArticle(articleId);
            elasticSearchService.deleteArticle(String.valueOf(articleId));
            return Result.success(true);
        } else {
            return Result.success(false);
        }

    }

    @GetMapping("/search")
    @ResponseBody
    public Result<List<ArticleUserVo>> searchArticle(String keyword) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 该方法是调用ElasticSearch的接口来查询的
        List<ArticleUserVo> articleList = articleService.selectArticleByKeywords(keyword);
        stopWatch.stop();
        log.info("使用ES来搜索文章的耗时为：{}ms", stopWatch.getTotalTimeMillis());
        return Result.success(articleList);
    }

    /**
     * 这个代码并无和业务代码相关，是为了测试下同等情况下和ES的查询效率哪个更高
     *
     * @param keyword
     * @return
     */
    @GetMapping("/search/mysql")
    @ResponseBody
    public Result<List<ArticleUserVo>> searchArticleByMysql(String keyword) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 该方法是调用ElasticSearch的接口来查询的
        List<ArticleUserVo> articleList = articleService.selectArticleByKeyword(keyword);
        stopWatch.stop();
        log.info("使用Mysql的模糊查询来搜索文章的耗时为：{}ms", stopWatch.getTotalTimeMillis());
        return Result.success(articleList);
    }


    /**
     * 展示10条热点文章，热点文章是根据文章的浏览量来进行排序
     * @return
     */
    @GetMapping("/hotspot")
    @ResponseBody
    public Result<List<ArticleUserVo>> hostSpotArticle() {
        List<ArticleUserVo> articleList = articleService.selectArticleByViewCount();
        return Result.success(articleList);
    }

    @GetMapping("/select/school/category/{categoryId}")
    @ResponseBody
    public Result<List<ArticleUserVo>> SameSchoolArticle(HttpServletRequest request, @PathVariable("categoryId") int categoryId) {
        /**
         * 这里又发现一个小bug,原因是：在更新用户个人信息之后，只是更改了数据库的信息，但是并没有更改redis
         * 中的token值，这样就导致了直接从redis中读取的数据是历史数据，因此就会出现逻辑错误
         */

        User user = loginController.getUserInfo(request);
        log.info("userInfo：{}",user.toString());
        // 这里通过用户获取学校信息，由于历史问题出现错误，现在先将其写死为西安科技大学
        String schoolName = user.getSchool();
        log.info("schoolName is :{}", schoolName);

        // 写成这样进行测试的话，前端仍然无法根据具体的分类进行展示数据，只能对第一个数据进行展示？？
        List<ArticleUserVo> articleUserVoList = articleService.selectArticleBySchool(schoolName,categoryId);
        /*
        for(ArticleUserVo articleUserVo : articleUserVoList){
            log.info("articleUserVo:{}", articleUserVo.toString() + "userSchool:" + user.getSchool());
        }
        */
        return Result.success(articleUserVoList);
    }

}

