package com.example.wendao.service.impl;

import com.example.wendao.entity.Article;
import com.example.wendao.redis.JedisService;
import com.example.wendao.redis.LikeKey;
import com.example.wendao.service.ArticleService;
import com.example.wendao.service.LikeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.xml.bind.DataBindingException;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 14:41
 * @version: 1.0
 */
@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    JedisService jedisService;

    @Autowired
    ArticleService articleService;

    private static final Logger log = LoggerFactory.getLogger(LikeServiceImpl.class);

    @Override
    public long like(String key, String userId) {
        jedisService.sadd(key, userId);
        return jedisService.scard(key);
    }

    @Override
    public long dislike(String key, String value) {
        jedisService.srem(key, value);
        return jedisService.scard(key);
    }

    @Override
    public long likeCount(String key) {
        return jedisService.scard(key);
    }

    @Override
    public Set<String> likeCountUserId(String key) {
        return jedisService.smembers(key);
    }

    @Override
    public void transLikedCountFromRedis2DB() {
        /**
         * 从Redis中拿到以"LikeKey:like+articleId为key的key，根据这些key就可以拿到具体的文章Id，就
         * 可以有针对性的更新有点赞的文章，优化了以前遍历所有的文章来进行更新。
         * 经过分析，下面的代码耗时就比较小了"
         */
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<String> matchLikeKey = jedisService.scan(LikeKey.LIKE_KEY.getPrefix() + "[0-9]*");
        for(String s : matchLikeKey){
            // 这里从LikeKey:like是12位数，所以12位开始遍历文章ID
            int articleId = Integer.parseInt(s.substring(12));
            Article article = articleService.selectArticleByArticleId(articleId);
            long likeCount = jedisService.scard(s);
            if (article != null){
                article.setArticleLikeCount((int) likeCount);
                articleService.updateArticle(article);
            }
        }
        stopWatch.stop();
        log.info("{}: 将缓存中的点赞数量落库,耗时{}ms",new Date(), stopWatch.getTotalTimeMillis());


    }
}
