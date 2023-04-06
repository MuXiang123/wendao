package com.example.wendao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zhk
 * @description:
 * @date: 2023/4/5 11:12
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Like {
    private int likeId;
    private int articleId;
    private String userId;
}
