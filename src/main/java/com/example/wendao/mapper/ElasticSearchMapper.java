package com.example.wendao.mapper;

import com.example.wendao.entity.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 11:10
 * @version: 1.0
 */
public interface ElasticSearchMapper extends ElasticsearchRepository<Article, Integer> {
}
