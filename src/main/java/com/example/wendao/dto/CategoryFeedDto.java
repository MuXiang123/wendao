package com.example.wendao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/22 11:10
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryFeedDto {
    private int pageNum;
    private int pageSize;
    private int tid;
}
