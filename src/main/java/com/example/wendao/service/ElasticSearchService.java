package com.example.wendao.service;

import com.example.wendao.entity.Article;

import java.util.List;

/**
 * @author: zhk
 * @description: es的crud
 * @date: 2023/3/7 9:58
 * @version: 1.0
 */
public interface ElasticSearchService {
    /**
     * 搜索文章
     * @param keywords
     * @return
     */
    List<Article> searchArticle(String keywords);

    /**
     * 保存文章
     * @param article
     */
    void saveArticle(Article article);

    /**
     * 删除文章
     * @param articleId
     */
    void deleteArticle(String articleId);

    /**
     * 更新文章
     * @param article
     */
    void updateArticle(Article article);

}
