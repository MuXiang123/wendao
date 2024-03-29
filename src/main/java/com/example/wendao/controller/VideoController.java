package com.example.wendao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.wendao.dto.CategoryFeedDto;
import com.example.wendao.dto.VideoActionDto;
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

    /**
     * 分页获取目录
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/category")
    public Result<List<VideoCategory>> categoryList(@RequestParam int pageNum, @RequestParam int pageSize){
        List<VideoCategory> videoCategories = videoService.categoryList(pageNum, pageSize);
        if(videoCategories.size() != 0){
            return new Result<>(videoCategories);
        }
        return new Result<>(null);
    }

    /**
     * 获取主分区
     * @return
     */
    @GetMapping("/category/parent")
    public Result<List<VideoCategory>> categoryList(){
        List<VideoCategory> videoCategories = videoService.categoryMainList();
        if(videoCategories.size() != 0){
            return new Result<>(videoCategories);
        }
        return new Result<>(null);
    }

    /**
     * 获取子分区
     * @param parentId
     * @return
     */
    @GetMapping("/category/child")
    public Result<List<VideoCategory>> categoryList(@RequestParam int parentId){
        List<VideoCategory> videoCategories = videoService.categoryChildList(parentId);
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
    public Result categoryFeedList(@RequestBody CategoryFeedDto categoryFeedDto){
        JSONObject objects = videoService.categoryFeedList(categoryFeedDto.getPageNum(), categoryFeedDto.getPageSize(), categoryFeedDto.getTid());
        return new Result<>(objects);
    }

    /**
     * 视频播放地址
     * @param videoActionDto
     * @return
     */
    @PostMapping("/action")
    public Result action(@RequestBody VideoActionDto videoActionDto){
        JSONObject action = videoService.action(videoActionDto);
        return new Result<>(action);
    }

    /**
     * 获取某个视频下面的推荐视频
     * @param aid
     * @return
     */
    @GetMapping("/recommend")
    public Result recommend(@RequestParam int aid){
        JSONArray array = videoService.recommend(aid);
        return new Result(array);
    }

    /**
     * 视频信息
     * @param aid
     * @return
     */
    @GetMapping("/information")
    public Result videoDetail(@RequestParam Integer aid){
        JSONObject object = videoService.videoInformation(aid);
        return new Result(object);
    }

    /**
     * 热门推荐
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/popular")
    public Result popular(@RequestParam Integer pageNum, @RequestParam Integer pageSize){
        JSONObject object = videoService.popular(pageNum, pageSize);
        return new Result(object);
    }
}
