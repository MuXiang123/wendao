package com.example.wendao.service.impl;

import com.alibaba.druid.sql.builder.UpdateBuilder;
import com.alibaba.fastjson.JSON;
import com.example.wendao.entity.Article;
import com.example.wendao.mapper.ElasticSearchMapper;
import com.example.wendao.service.ElasticSearchService;
import lombok.AllArgsConstructor;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.UpdateByQueryRequestBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/7 10:02
 * @version: 1.0
 */
@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {
    private Logger logger = LoggerFactory.getLogger(ElasticSearchServiceImpl.class);

    @Autowired
    ElasticsearchRestTemplate restTemplate;

    @Autowired
    ElasticSearchMapper searchMapper;

    @Override
    public List<Article> searchArticle(String keywords) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery()
                        .should(QueryBuilders.matchPhraseQuery("article_title", keywords))
                        .should(QueryBuilders.matchPhraseQuery("article_summary", keywords))
                        .should(QueryBuilders.matchPhraseQuery("article_content", keywords))
                )
                .withPageable(PageRequest.of(0, 9))
                .build();
        SearchHits<Article> search = restTemplate.search(nativeSearchQuery, Article.class);
        logger.info("total:{}", search.getTotalHits());
        Stream<SearchHit<Article>> searchHitStream = search.get();
        List<Article> articleList = searchHitStream.map(SearchHit::getContent).collect(Collectors.toList());
        logger.info("es搜索数量:{}",articleList.size());
        articleList.forEach(item->{
            logger.info(item.toString());
        });
        return articleList;
    }

    @Override
    public void saveArticle(Article article) {
        restTemplate.save(article);
    }

    @Override
    public void deleteArticle(String articleId) {
        restTemplate.delete(articleId, Article.class);
    }

    @Override
    public void updateArticle(Article article) {
        Document document = Document.create();
        Map<String, String> map = JSON.parseObject(JSON.toJSONString(article), Map.class);
        for(Map.Entry<String,String> entry : map.entrySet()){
            if(entry.getValue()!= null && !"".equals(entry.getValue())){
                document.put(entry.getKey(), entry.getValue());
            }
        }
        UpdateQuery updateQuery = UpdateQuery.builder(String.valueOf(article.getArticleId()))
                .withDocument(document)
                .build();
        UpdateResponse response = restTemplate.update(updateQuery, IndexCoordinates.of("wendao"));

    }


}
