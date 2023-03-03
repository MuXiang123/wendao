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
     * @return
     */
    List<ArticleUserVo> selectAllArticleIndexViewData();

    /**
     * 查询所有目录下的文章
     * @param categoryId
     * @return
     */
    List<ArticleUserVo> selectAllArticleCategoryData(int categoryId);

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

}
