package com.example.wendao.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.wendao.entity.VideoCategory;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/21 17:11
 * @version: 1.0
 */
public interface VideoService {
    /**
     * 目录列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<VideoCategory> categoryList(int pageNum, int pageSize);

    /**
     * 获取分类下的视频推荐
     * @param pageNum
     * @param pageSize
     * @param tid
     * @return
     */
    JSONArray categoryFeedList(int pageNum, int pageSize, int tid);
}
