package com.example.wendao.async;

/**
 * @author: zhk
 * @description: 标识事件类型
 * @date: 2023/3/3 15:53
 * @version: 1.0
 */
public enum EventType {
    LIKE(0),
    DISLIKE(1),
    COMMNET(2),
    FOLLOW(3),
    UNFOLLOW(4);

    private int value;

    EventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
