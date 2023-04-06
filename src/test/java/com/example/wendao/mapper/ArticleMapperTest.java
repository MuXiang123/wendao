package com.example.wendao.mapper;

import com.example.wendao.vo.ArticleUserVo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class ArticleMapperTest {

    @Autowired
    ArticleMapper articleMapper;

    @Test
    void addView() {
        ArticleUserVo articleDetail1 = articleMapper.getArticleDetail(36, "18420051370");
        articleMapper.addView(36);
        ArticleUserVo articleDetail = articleMapper.getArticleDetail(36, "18420051370");
        System.out.println(articleDetail1.getArticleViewCount()+"----"+articleDetail.getArticleViewCount());
    }

    @Test
    void addLike(){
        ArticleUserVo articleDetail1 = articleMapper.getArticleDetail(36, "18420051370");
        articleMapper.addLike(36);
        ArticleUserVo articleDetail = articleMapper.getArticleDetail(36, "18420051370");
        System.out.println(articleDetail1.getArticleLikeCount() +"-----"+ articleDetail.getArticleLikeCount());
    }
    @Test
    void delLike(){
        ArticleUserVo articleDetail1 = articleMapper.getArticleDetail(36, "18420051370");
        articleMapper.delLike(36);
        ArticleUserVo articleDetail = articleMapper.getArticleDetail(36, "18420051370");
        System.out.println(articleDetail1.getArticleLikeCount() == 1+articleDetail.getArticleLikeCount());
    }
}