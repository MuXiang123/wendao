package com.example.wendao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: zhk
 * @description: 关注实体
 * @date: 2023/3/1 16:01
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Follow {

    /**
     * id
     */
    private int id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 关注的id
     */
    private String followId;

    /**
     * 创建时间
     */
    private Date createdTime;
}