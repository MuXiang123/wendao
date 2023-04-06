package com.example.wendao.mapper;

import com.example.wendao.entity.Article;
import com.example.wendao.vo.ArticleUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 9:28
 * @version: 1.0
 */
public interface ArticleMapper {
    /**
     * 新增文章
     *
     * @param article
     */
    void insertArticle(Article article);

    /**
     * 更新文章
     * 可以根据需要，只更新部分字段 注意这里的写法，
     * test里面的单引号双引号使用不当会报错
     *
     * @param article
     */
    void updateArticle(Article article);

    /**
     * 逻辑删除文章 0:未删除  1：删除
     *
     * @param articleId
     */
    void deleteArticle(int articleId);

    /**
     * 根据id查询文章
     *
     * @param articleId
     * @return
     */
    Article selectArticleByArticleId(int articleId);

    /**
     * 根据不同的分类id来查找对应的文章，默认按照发表文章的更新时间来进行排序
     * 并保证初始的时候，创建时间和更新时间必须相同
     *
     * @param categoryId
     * @return
     */
    List<Article> selectArticleByCategoryId(int categoryId);

    /**
     * 根据点赞数量进行排序，如果点赞数相同的话，然后在按照更新时间进行排序
     *
     * @return
     */
    List<Article> selectArticleByLikeCount();

    /**
     * 根据评论数量进行排序，如果点赞数相同的话，然后在按照更新时间进行排序
     *
     * @return
     */
    List<Article> selectArticleByCommentCount();

    /**
     * 根据浏览数量进行排序，如果点赞数相同的话，然后在按照更新时间进行排序
     *
     * @return
     */
    List<ArticleUserVo> selectArticleByViewCount();

    /**
     * 根据学校的不同，来展示不同的文章效果，这块效果未实现
     *
     * @param schoolName
     * @param categoryId
     * @return
     */
    List<ArticleUserVo> selectArticleBySchool(String schoolName, int categoryId);

    /**
     * 根据关键字查询文章
     *
     * @param keyword
     * @return
     */
    List<Article> selectArticleByKeyword(String keyword);

    /**
     * 根据文章id查询作者
     *
     * @param articleId
     * @return
     */
    Article selectArticleByUserId(int articleId);

    /**
     * 查询所有文章
     *
     * @return
     */
    List<Article> selectAllArticle();

    /**
     * 根据所有用户查询所有文章
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<ArticleUserVo> selectAllArticleIndexViewData(@Param("pageNum") int pageNum,
                                                      @Param("pageSize") int pageSize);

    /**
     * 根据目录查询所有文章
     *
     * @param categoryId
     * @return
     */
    List<ArticleUserVo> selectAllArtilceCategoryData(int categoryId,
                                                     @Param("pageNum") int pageNum,
                                                     @Param("pageSize") int pageSize);

    /**
     * 查询文章详情
     *
     * @param articleId
     * @return
     */
    ArticleUserVo selectAllArticleDetail(int articleId);

    /**
     * 查询目录下的文章
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    List<ArticleUserVo> selectArticleListByCategoryId(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize, int categoryId);

    /**
     * 查询用户发表的文章
     * @param pageNum
     * @param pageSize
     * @param userId
     * @return
     */
    List<Article> selectArticleListByUserId(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize, String userId);

    /**
     * 查询所有文章
     * @param pageNum
     * @param pageSize
     * @param userId
     * @return
     */
    List<ArticleUserVo> selectArticleList( @Param("pageNum") int pageNum, @Param("pageSize") int pageSize, String userId);

    /**
     * 查询文章详情
     * @param articleId
     * @param userId
     * @return
     */
    ArticleUserVo getArticleDetail(int articleId, String userId);

    /**
     * 点赞数添加
     * @param articleId
     */
    void addLike(int articleId);

    /**
     * 点赞数减少
     * @param articleId
     */
    void delLike(int articleId);

    /**
     * 浏览数添加
     * @param articleId
     */
    void addView(int articleId);
}
