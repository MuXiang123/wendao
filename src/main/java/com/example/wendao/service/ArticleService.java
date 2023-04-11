package com.example.wendao.service;

import com.example.wendao.entity.Article;
import com.example.wendao.vo.ArticleUserVo;

import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 11:05
 * @version: 1.0
 */
public interface ArticleService {
    /**
     * 新增
     * @param article
     */
    void insertArticle(Article article);

    /**
     * 更新
     * @param article
     */
    void updateArticle(Article article);

    /**
     * 查询
     * @param articleId
     */
    void deleteArticle(int articleId);

    /**
     * 根据id查询
     * @param articleId
     * @return
     */
    Article selectArticleByArticleId(int articleId);

    /**
     * 查询目录下面的所有
     * @param categoryId
     * @return
     */
    List<Article> selectArticleByCategoryId(int categoryId);

    /**
     * 根据点赞数排序
     * @return
     */
    List<Article> selectArticleByLikeCount();

    /**
     * 根据评论数排序
     * @return
     */
    List<Article> selectArticleByCommentCount();

    /**
     * 根据浏览数排序
     * @return
     */
    List<ArticleUserVo> selectArticleByViewCount();

    /**
     * 根据学校不同进行推荐 未实现
     * @param schoolName
     * @param categoryId
     * @return
     */
    List<ArticleUserVo> selectArticleBySchool(String schoolName, int categoryId);

    /**
     * 根据关键字进行查询文章
     * @param keyword
     * @return
     */
    List<ArticleUserVo> selectArticleByKeyword(String keyword);

    /**
     * 查找文章作者
     * @param articleId
     * @return
     */
    Article selectArticleByUserId(int articleId);

    /**
     * 所有文章
     * @return
     */
    List<Article> selectAllArtilce();

    /**
     * 从es中获取所有文章
     * @return
     */
    List<Article> selectAllArticleByES();

    /**
     * 查询所有用户发布的文章
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<ArticleUserVo> selectAllArticleIndexViewData(Integer pageNum, Integer pageSize);

    /**
     * 查询所有目录下的文章
     * @param categoryId
     * @return
     */
    List<ArticleUserVo> selectAllArticleCategoryData(Integer categoryId, Integer pageNum, Integer pageSize);

    /**
     * 查询文章详情
     * @param articleId
     * @return
     */
    ArticleUserVo selectAllArticleDetail(int articleId);

    /**
     * 根据关键字查询文章
     * @param keywords
     * @return
     */
    List<ArticleUserVo> selectArticleByKeywords(String keywords);


    /**
     * 获取首页推荐文章
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<ArticleUserVo> selectArticleList(String userId, int pageNum, int pageSize);

    /**
     * 获取用户发布的文章
     * @param pageNum
     * @param pageSize
     * @param userId
     * @return
     */
    List<Article> selectArticleListByUserId(int pageNum, int pageSize, String userId);

    /**
     * 获取目录下的id
     * @param pageNum
     * @param pageSize
     * @param category
     * @return
     */
    List<ArticleUserVo> selectArticleListByCategoryId(int pageNum, int pageSize, int category);

    /**
     * 获取文章详情
     * @param articleId
     * @param userId
     * @return
     */
    ArticleUserVo getArticleDetail(int articleId ,String userId);

    /**
     * 增加点赞数
     * @param articleId
     */
    void addLike(int articleId);

    /**
     * 删除点赞数
     * @param articleId
     */
    void delLike(int articleId);

    /**
     * 查询最后一个文章
     * @return
     */
    Article selectArticleLast();
}
