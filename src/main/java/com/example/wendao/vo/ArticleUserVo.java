package com.example.wendao.vo;

import com.example.wendao.entity.Article;

/**
 * @author: zhk
 * @description: 文章vo
 * @date: 2023/3/3 9:39
 * @version: 1.0
 */
public class ArticleUserVo extends Article {
    private String nickname;

    private String avatar;

    /**
     * 0 表示点赞了， 1 表示未点赞
     */
    private int isLiked;

    @Override
    public String toString() {
        return "ArticleUserVo{" + super.toString() +
                "nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", isLiked=" + isLiked +
                '}';
    }
}
