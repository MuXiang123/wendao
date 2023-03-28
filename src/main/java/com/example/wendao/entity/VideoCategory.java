package com.example.wendao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/21 17:08
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoCategory {
    private int tid;
    private String name;
    private String profile;
    private String parentId;
}
