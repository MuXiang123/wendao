package com.example.wendao.redis;

/**
 * @author: zhk
 * @description: 关注key
 * @date: 2023/3/2 10:20
 * @version: 1.0
 */
public class FollowKey extends BasePrefix{
    public FollowKey(String prefix) {
        super(prefix);
    }

    public FollowKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 我的关注
     */
    public static FollowKey followKey = new FollowKey("myFollow");

}
