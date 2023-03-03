package com.example.wendao.async;

import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 15:35
 * @version: 1.0
 */
public interface EventHandler {
    /**
     * 处理EventModel给用户发送通知
     * @param eventModel
     */
    void doHandler(EventModel eventModel);

    /**
     * 获取支持事件类型
     * @return
     */
    List<EventType> getSupportEventTypes();
}
