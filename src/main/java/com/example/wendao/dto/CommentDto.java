package com.example.wendao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/18 20:27
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private String content;
    private int articleId;
    private int parentId;
}
