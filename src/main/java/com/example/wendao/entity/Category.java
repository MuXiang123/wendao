package com.example.wendao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zhk
 * @description: 分类表
 * @date: 2023/3/1 16:00
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    /**
     * 分类表id
     */
    int categoryId;

    /**
     * 分类名
     */
    String categoryName;
}

