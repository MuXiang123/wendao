package com.example.wendao.redis;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/2 10:38
 * @version: 1.0
 */
public class VerifyCodeKey extends BasePrefix{
    public VerifyCodeKey(String prefix) {
        super(prefix);
    }

    public VerifyCodeKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static VerifyCodeKey verifyCodeKeyRegister = new VerifyCodeKey(180, "register");
    public static VerifyCodeKey verifyCodeKeyLogin = new VerifyCodeKey(180, "login");

}
