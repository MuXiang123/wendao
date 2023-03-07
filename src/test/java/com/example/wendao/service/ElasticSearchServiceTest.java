package com.example.wendao.service;

import com.example.wendao.entity.Article;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.rest.RestStatus;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class ElasticSearchServiceTest {

    @Autowired
    ElasticsearchRestTemplate restTemplate;

    @Autowired
    ElasticSearchService elasticSearchService;

    @Autowired
    ArticleService articleService;

    @Test
    void updateArticle() {
        // 创建一个测试Article对象
        Article article = new Article();
        article.setArticleId(123);
        article.setArticleTitle("Test Article Title");
        article.setArticleContent("This is a test article.");
        article.setArticleViewCount(0);
        article.setArticleLikeCount(0);
        article.setArticleCommentCount(0);
        article.setCreatedTime(new Date());
        article.setUpdateTime(new Date());
        article.setIsDeleted(0);
        article.setArticleCategoryId(1);
        article.setArticleCategoryName("Test Category");
        article.setArticleUserId("Test User");

        // 将Article对象保存到ES
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withObject(article)
                .build();
        String res = restTemplate.index(indexQuery, IndexCoordinates.of("wendao"));
//        Article save = restTemplate.save(article);
        System.out.println(res);

        // 更新Article对象的title和content字段
        article.setArticleTitle("Updated Article Title");
        article.setArticleContent("This is an updated article.");
        elasticSearchService.updateArticle(article);

        // 从ES中获取更新后的Article对象
        Article article1 = restTemplate.get(String.valueOf(article.getArticleId()), Article.class);
        System.out.println(article1);

        // 验证更新结果是否正确
//        Map<String, Object> source = getResponse.getSource();
//        assertEquals("Updated Article Title", source.get("article_title"));
//        assertEquals("This is an updated article.", source.get("article_content"));
//        assertEquals(0, source.get("article_view_count"));
//        assertEquals(0, source.get("article_like_count"));
//        assertEquals(0, source.get("article_comment_count"));
//        assertNotNull(source.get("created_time"));
//        assertNotNull(source.get("update_time"));
//        assertEquals(0, source.get("is_deleted"));
//        assertEquals(1, source.get("article_category_id"));
//        assertEquals("Test Category", source.get("article_category_name"));
//        assertEquals("Test User", source.get("article_user_id"));
    }

    @Test
    void searchArticle() {
        // 构造测试数据
        Article article1 = new Article(1, "测试文章1", "测试文章1的摘要", "测试文章1的内容", 100, 50, 30, new Date(), new Date(), 0, 1, "技术", "张三");
        Article article2 = new Article(2, "测试文章2", "测试文章2的摘要", "测试文章2的内容", 80, 40, 20, new Date(), new Date(), 0, 1, "产品", "李四");
        Article article3 = new Article(3, "Java开发", "Java开发工程师的职责和技能", "Java开发工程师的职责和技能详解", 200, 80, 50, new Date(), new Date(), 0, 1, "技术", "王五");

        // 将测试数据保存到ES中
        restTemplate.save(article1, IndexCoordinates.of("wendao"));
        restTemplate.save(article2, IndexCoordinates.of("wendao"));
        restTemplate.save(article3, IndexCoordinates.of("wendao"));
        restTemplate.indexOps(Article.class).refresh();

        // 执行测试代码
        List<Article> result1 = elasticSearchService.searchArticle("Java开发");
        List<Article> result2 = elasticSearchService.searchArticle("测试");

        // 断言测试结果
        assertEquals(1, result1.size());
        assertEquals(2, result2.size());
    }

    @Test
    void saveArticle() {
//            Article article = new Article();
//            article.setArticleId(4);
//            article.setArticleTitle("测试文章");
//            article.setArticleContent("这是一篇测试文章。");
//            article.setArticleSummary("这是一篇测试文章的摘要。");
//            article.setArticleCategoryId(1);
//            article.setArticleCategoryName("测试分类");
//            article.setArticleUserId("testuser");
//            article.setArticleViewCount(0);
//            article.setArticleLikeCount(0);
//            article.setArticleCommentCount(0);
//            article.setCreatedTime(new Date());
//            article.setUpdateTime(new Date());
//            article.setIsDeleted(0);
        for (int i = 1; i < 41; i++) {
            Article article = articleService.selectArticleByArticleId(i);
            if(article != null){
                elasticSearchService.saveArticle(article);
            }
//            List<Article> articles = elasticSearchService.searchArticle("1");
//            assertEquals(1, articles.size());
//            assertEquals(article, articles.get(0));
        }


        // 查询保存的文章，确认文章已保存成功


    }

    @Test
    public void testDeleteArticle() {
        // 创建一个 Article 对象作为测试数据
        Article article = new Article();
        article.setArticleId(1);
        article.setArticleTitle("测试标题");
        article.setArticleContent("测试内容");
        article.setArticleViewCount(0);
        article.setArticleLikeCount(0);
        article.setArticleCommentCount(0);
        article.setCreatedTime(new Date());
        article.setUpdateTime(new Date());
        article.setIsDeleted(0);
        article.setArticleCategoryId(1);
        article.setArticleCategoryName("测试分类");
        article.setArticleUserId("测试用户");

        // 保存 Article 对象
//        elasticSearchService.saveArticle(article);

        // 删除 Article 对象
        elasticSearchService.deleteArticle("2");
        elasticSearchService.deleteArticle("3");
        elasticSearchService.deleteArticle("4");


        // 查询 Article 对象
        Article optional = restTemplate.get(String.valueOf(article.getArticleId()), Article.class);
        assertFalse(optional != null);
    }

}