package com.example.wendao.async;

import com.example.wendao.async.EventModel;
import com.example.wendao.redis.CommonKey;
import com.example.wendao.redis.JedisService;
import com.example.wendao.redis.LikeKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: zhk
 * @description: 事件生产者，将封装好的EventModel事件，
 * 塞进队列中，等待消费者的消费
 * @date: 2023/3/3 16:09
 * @version: 1.0
 */
@Service
public class EventProducer {
    @Autowired
    JedisService jedisService;

    /**
     * 将该事件推入到redis中的队列中
     *
     * @param eventModel
     */
    public boolean fireEvent(EventModel eventModel) {
        try {
            jedisService.lpush(LikeKey.LIKE_ASYNC_KEY, CommonKey.EVENT_LIKE_QUEUE, eventModel);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
