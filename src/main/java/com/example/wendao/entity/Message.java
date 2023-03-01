package com.example.wendao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zhk
 * @description: 消息实体
 * @date: 2023/3/1 16:02
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    /**
     * id
     */
    int messageId;

    /**
     * 发送方id
     */
    String fromId;

    /**
     * 接收方
     */
    String toId;

    /**
     * 消息内容
     */
    String messageContent;

    /**
     * 0表示未读、1表示已读
     */
    int hasRead;

    /**
     * 点对点对话id
     */
    String conversationId;

}
