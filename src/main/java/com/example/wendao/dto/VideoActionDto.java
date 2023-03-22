package com.example.wendao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/22 15:28
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoActionDto {
    /**
     * 视频avid
     */
    private int avid;
    /**
     * 视频cid
     */
    private int cid;
    /**
     * 视频清晰度
     */
    private int qn;
    /**
     * 视频流格式标识 默认为0
     */
    private int fnval;
    /**
     * 默认为0
     */
    private int fnver;
    /**
     * 4k开启 默认为0
     */
    private int fourk;
}
