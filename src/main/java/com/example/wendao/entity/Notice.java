package com.example.wendao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: zhk
 * @description: 通知实体
 * @date: 2023/3/1 16:02
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notice {

    /**
     * 通知id
     */
    private int id;

    /**
     * 发送方ID
     */
    private String fromId;

    /**
     * 接收方ID
     */
    private String toId;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 1 已读 0 未读
     */
    private int hasRead;

    /**
     * 这次对话的ID
     */
    private String conversationId;
}

