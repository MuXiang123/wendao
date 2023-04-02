package com.example.wendao.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.wendao.dto.VideoActionDto;
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
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<VideoCategory> categoryList(int pageNum, int pageSize);

    /**
     * 获取主分区
     * @return
     */
    List<VideoCategory> categoryMainList();

    /**
     * 获取子分区
     * @param parentId
     * @return
     */
    List<VideoCategory> categoryChildList(int parentId);

    /**
     * 获取分类下的视频推荐
     *
     * @param pageNum
     * @param pageSize
     * @param tid
     * @return
     */
    JSONObject categoryFeedList(int pageNum, int pageSize, int tid);

    /**
     * 获取热门推荐
     * @param pageNum
     * @param pageSize
     * @return
     */
    JSONObject popular(int pageNum, int pageSize);

    /**
     * 获取视频播放信息
     *
     * @param videoActionDto
     * @return
     */
    JSONObject action(VideoActionDto videoActionDto);

    /**
     * 获取某个视频下面的推荐视频
     *
     * @param aid
     * @return
     */
    JSONArray recommend(int aid);

    /**
     * 视频信息
     * @param aid
     * @return
     */
    JSONObject videoInformation(Integer aid);
}
