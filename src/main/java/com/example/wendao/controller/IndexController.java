package com.example.wendao.controller;

import com.example.wendao.entity.Article;
import com.example.wendao.redis.JedisService;
import com.example.wendao.redis.LikeKey;
import com.example.wendao.service.ArticleService;
import com.example.wendao.utils.Result;
import com.example.wendao.vo.ArticleUserVo;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 15:09
 * @version: 1.0
 */
@RestController
public class IndexController {
    private static Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    ArticleService articleService;

    @Autowired
    JedisService jedisService;

    /**
     * 首页
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/")
    @ResponseBody
    public Result<List<ArticleUserVo>> index(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        List<ArticleUserVo> articleList = articleService.selectAllArticleIndexViewData(pageNum, pageSize);
        return Result.success(articleList);
    }

    /**
     * 文章的详情页
     *
     * @param articleId
     * @return
     */
    @GetMapping("/detail/{articleId}")
    @ResponseBody
    public Result<ArticleUserVo> articleDetail(@PathVariable("articleId") int articleId) {

        //用户请求一次这个接口，相当于用户浏览量+1
        Article article = articleService.selectArticleByArticleId(articleId);
        article.setArticleViewCount(article.getArticleViewCount() + 1);
        articleService.updateArticle(article);

        // 这里查出来的数据，是从数据库查询出来的暂时没有like的数据，redis的数据才是实时最新的数据
        ArticleUserVo articleUserVo = articleService.selectAllArticleDetail(articleId);
        String likeKey = LikeKey.LIKE_KEY.getPrefix() + articleId;
        int likeCount = (int) jedisService.scard(likeKey);

        log.info("从Redis获取点赞数为：" + likeCount);
        articleUserVo.setArticleLikeCount(likeCount);

        return Result.success(articleUserVo);
    }

    /**
     * 根据目录id查询文章
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/category/{categoryId}")
    @ResponseBody
    public Result<List<ArticleUserVo>> articleCategory(@PathVariable("categoryId") Integer categoryId,
                                                       @RequestParam("pageNum") Integer pageNum,
                                                       @RequestParam("pageSize")Integer pageSize) {
        List<ArticleUserVo> articleList = articleService.selectAllArticleCategoryData(categoryId, pageNum, pageSize);
        return Result.success(articleList);
    }
}
