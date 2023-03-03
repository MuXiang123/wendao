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
    void insertArticle(Article article);

    void updateArticle(Article article);

    void deleteArticle(int articleId);

    Article selectArticleByArticleId(int articleId);

    List<Article> selectArticleByCategoryId(int categoryId);

    List<Article> selectArticleByLikeCount();

    List<Article> selectArticleByCommentCount();

    List<ArticleUserVo> selectArticleByViewCount();

    List<ArticleUserVo> selectArticleBySchool(String schoolName, int categoryId);

    List<ArticleUserVo> selectArticleByKeyword(String keyword);

    Article selectArticleByTwoUserId(int articleId);

    List<Article> selectAllArtilce();

    List<Article> selectAllArticleByES();

    List<ArticleUserVo> selectAllArticleIndexViewData();

    List<ArticleUserVo> selectAllArticleCategoryData(int categoryId);

    ArticleUserVo selectAllArticleDetail(int articleId);

    List<ArticleUserVo> selectArticleByKeywords(String keywords);

}
