package com.example.wendao.redis;

/**
 * @author: zhk
 * @description: BasePrefix抽象类实现该接口，并且有多个子类继承BasePrefix
 * @date: 2023/3/2 10:12
 * @version: 1.0
 */
public interface KeyPrefix {
    /**
     * 过期时间
     * @return
     */
    int expireSeconds();

    /**
     * 获取前缀
     * @return
     */
    String getPrefix();
}
