package com.example.wendao.redis;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/2 10:38
 * @version: 1.0
 */
public class UserTokenKey extends BasePrefix{
    /**
     * 设置token的有效期为60天
     */
    public static final int TOKEN_EXPIRE = 3600 * 24 * 60;

    public UserTokenKey(String prefix) {
        super(prefix);
    }

    public UserTokenKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static UserTokenKey userTokenKey = new UserTokenKey(TOKEN_EXPIRE, "tk");

}
