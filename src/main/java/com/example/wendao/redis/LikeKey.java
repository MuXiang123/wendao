package com.example.wendao.redis;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/2 10:37
 * @version: 1.0
 */
public class LikeKey extends BasePrefix{
    public LikeKey(String prefix) {
        super(prefix);
    }

    public LikeKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 在这里的话，其实应该可以理解为这个key，他是作为一个消息队列来发送消息
     */
    public static LikeKey LIKE_ASYNC_KEY = new LikeKey("likeAsync");

    public static LikeKey LIKE_KEY = new LikeKey("like");

}
