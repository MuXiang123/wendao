package com.example.wendao.controller;

import com.alibaba.fastjson.JSONArray;
import com.example.wendao.dto.CategoryFeedDto;
import com.example.wendao.entity.VideoCategory;
import com.example.wendao.service.CategoryService;
import com.example.wendao.service.VideoService;
import com.example.wendao.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/21 17:01
 * @version: 1.0
 */
@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    VideoService videoService;

    @GetMapping("/category")
    public Result<List<VideoCategory>> categoryList(@RequestParam int pageNum, @RequestParam int pageSize){
        List<VideoCategory> videoCategories = videoService.categoryList(pageNum, pageSize);
        if(videoCategories.size() != 0){
            return new Result<>(videoCategories);
        }
        return new Result<>(null);
    }

    /**
     * 获取分类下的视频推荐
     * @param categoryFeedDto
     * @return
     */
    @PostMapping("/category/feed")
    public JSONArray categoryFeedList(@RequestBody CategoryFeedDto categoryFeedDto){
        return videoService.categoryFeedList(categoryFeedDto.getPageNum(), categoryFeedDto.getPageSize(), categoryFeedDto.getTid());
    }
}
