package com.example.wendao.redis;

/**
 * @author: zhk
 * @description: 粉丝key 直接调用BasePrefix
 * @date: 2023/3/2 10:18
 * @version: 1.0
 */
public class FansKey extends BasePrefix{
    public FansKey(String prefix) {
        super(prefix);
    }

    public FansKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static FansKey fansKey = new FansKey("myFans");

}
