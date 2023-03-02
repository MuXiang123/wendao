package com.example.wendao.redis;

/**
 * @author: zhk
 * @description: 过期秒数默认0，前缀为类名
 * @date: 2023/3/2 10:15
 * @version: 1.0
 */
public abstract class BasePrefix implements KeyPrefix {
    private int expireSeconds;

    private String prefix;

    public BasePrefix(String prefix) {
        this(0, prefix);
    }

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        // 获取该类的名字
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
