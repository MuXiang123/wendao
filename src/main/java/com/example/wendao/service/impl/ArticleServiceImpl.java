package com.example.wendao.service.impl;

import com.example.wendao.entity.Article;
import com.example.wendao.entity.User;
import com.example.wendao.mapper.ArticleMapper;
import com.example.wendao.mapper.ElasticSearchMapper;
import com.example.wendao.redis.JedisService;
import com.example.wendao.redis.LikeKey;
import com.example.wendao.service.ArticleService;
import com.example.wendao.service.ElasticSearchService;
import com.example.wendao.service.UserService;
import com.example.wendao.vo.ArticleUserVo;
import com.google.common.collect.Lists;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 11:06
 * @version: 1.0
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    private static Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Autowired(required = false)
    private ArticleMapper articleMapper;

    @Autowired(required = false)
    private ElasticSearchMapper elasticSearchMapper;

    @Autowired
    ElasticSearchService elasticSearchService;

    @Autowired
    UserService userService;

    @Autowired
    JedisService jedisService;


    @Override
    public void insertArticle(Article article) {
        articleMapper.insertArticle(article);
    }

    @Override
    public void updateArticle(Article article) {
        articleMapper.updateArticle(article);
    }

    @Override
    public void deleteArticle(int articleId) {
        articleMapper.deleteArticle(articleId);
    }

    @Override
    public Article selectArticleByArticleId(int articleId) {
        return articleMapper.selectArticleByArticleId(articleId);
    }

    @Override
    public List<Article> selectArticleByCategoryId(int categoryId) {
        return articleMapper.selectArticleByCategoryId(categoryId);
    }

    @Override
    public List<Article> selectArticleByLikeCount() {
        return articleMapper.selectArticleByLikeCount();
    }

    @Override
    public List<Article> selectArticleByCommentCount() {
        return articleMapper.selectArticleByCommentCount();
    }

    @Override
    public List<ArticleUserVo> selectArticleByViewCount() {
        return articleMapper.selectArticleByViewCount();
    }

    @Override
    public List<ArticleUserVo> selectArticleBySchool(String schoolName, int categoryId) {
        return articleMapper.selectArticleBySchool(schoolName, categoryId);
    }

    @Override
    public List<ArticleUserVo> selectArticleByKeyword(String keyword) {
        // 这个是从数据库查询出来除了点赞数量的数据， 点赞量从Redis中获取
        List<Article> articleList = articleMapper.selectArticleByKeyword(keyword);
        return dealWithArticleVo(articleList);
    }

    @Override
    public Article selectArticleByUserId(int articleId) {
        return articleMapper.selectArticleByUserId(articleId);
    }

    @Override
    public List<Article> selectAllArtilce() {
        return articleMapper.selectAllArticle();
    }

    @Override
    public List<Article> selectAllArticleByES() {
        List<Article> articleList = Lists.newArrayList(elasticSearchMapper.findAll());
        return articleList;
    }

    @Override
    public List<ArticleUserVo> selectAllArticleIndexViewData(Integer pageNum, Integer pageSize) {
        return articleMapper.selectAllArticleIndexViewData(pageNum, pageSize);
    }

    @Override
    public List<ArticleUserVo> selectAllArticleCategoryData(Integer categoryId, Integer pageNum, Integer pageSize) {
        return articleMapper.selectAllArtilceCategoryData(categoryId, pageNum, pageSize);
    }


    @Override
    public ArticleUserVo selectAllArticleDetail(int articleId) {
        return articleMapper.selectAllArticleDetail(articleId);
    }

    @Override
    public List<ArticleUserVo> selectArticleByKeywords(String keywords) {
        List<Article> articleList = elasticSearchService.searchArticle(keywords);
        return dealWithArticleVo(articleList);
    }

    @Override
    public List<ArticleUserVo> selectArticleList(String userId, int pageNum, int pageSize) {
        return articleMapper.selectArticleList(pageNum, pageSize, userId);
    }

    @Override
    public List<Article> selectArticleListByUserId(int pageNum, int pageSize, String userId) {
        return articleMapper.selectArticleListByUserId(pageNum, pageSize, userId);
    }

    @Override
    public List<ArticleUserVo> selectArticleListByCategoryId(int pageNum, int pageSize, int category) {
        return articleMapper.selectArticleListByCategoryId(pageNum, pageSize, category);
    }

    @Override
    public ArticleUserVo getArticleDetail(int articleId, String userId) {
        articleMapper.addView(articleId);
        return articleMapper.getArticleDetail(articleId, userId);
    }

    @Override
    public void addLike(int articleId) {
        articleMapper.addLike(articleId);
    }

    @Override
    public void delLike(int articleId) {
        articleMapper.delLike(articleId);
    }

    @Override
    public Article selectArticleLast() {

        return articleMapper.selectArticleLast();
    }

    public List<ArticleUserVo> dealWithArticleVo(List<Article> articleList) {
        List<ArticleUserVo> articleUserVos = new ArrayList<>();
        for (Article article : articleList) {
            // 找到这篇文章的发布者
            User user = userService.selectByUserId(article.getArticleUserId());

            System.out.println("user--------------" + user);
            ArticleUserVo articleUserVo = new ArticleUserVo();
            articleUserVo.setArticleId(article.getArticleId());
            articleUserVo.setArticleTitle(article.getArticleTitle());
            articleUserVo.setArticleSummary(article.getArticleSummary());
            articleUserVo.setArticleContent(article.getArticleContent());
            articleUserVo.setArticleViewCount(article.getArticleViewCount());
            // 这里需要单独处理下点赞的数量，先从Redis中那这个数据，如果没有再从MYSQL中去取
            // 这里文章点赞的Redis的设计是 LikeKey:like+articleId, =>LikeKey.getPrefix() + articleId
            int articleLikeCount = 0;
            if (jedisService.exists(LikeKey.LIKE_KEY, article.getArticleId() + "")) {
                // 这里说明在Redis中存在Key
                articleLikeCount = (int) jedisService.scard(LikeKey.LIKE_KEY.getPrefix() + article.getArticleId() + "");
            } else {
                // 这里可以直接去数据库中查，也可以直接给默认值0
                // 也就是可以不处理
            }
            articleUserVo.setArticleLikeCount(articleLikeCount);
            articleUserVo.setArticleCommentCount(article.getArticleCommentCount());
            articleUserVo.setCreatedTime(article.getCreatedTime());
            articleUserVo.setUpdateTime(article.getUpdateTime());
            articleUserVo.setIsDeleted(article.getIsDeleted());
            articleUserVo.setArticleCategoryId(article.getArticleCategoryId());
            articleUserVo.setArticleUserId(article.getArticleUserId());
            articleUserVo.setNickname(user.getNickname());
            articleUserVo.setAvatar(user.getAvatar());
            articleUserVos.add(articleUserVo);
        }

        return articleUserVos;
    }
}
