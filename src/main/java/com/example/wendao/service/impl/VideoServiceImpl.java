package com.example.wendao.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.wendao.entity.VideoCategory;
import com.example.wendao.mapper.VideoMapper;
import com.example.wendao.service.VideoService;
import com.example.wendao.utils.BvToAvUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/21 17:12
 * @version: 1.0
 */
@Service
@Slf4j
public class VideoServiceImpl implements VideoService {
    private final static String VIDEO_CATEGORY_FEED = "https://api.bilibili.com/x/web-interface/dynamic/region";
    private final static String ENCODE = "UTF-8";
    @Autowired
    VideoMapper videoMapper;
    @Override
    public List<VideoCategory> categoryList(int pageNum, int pageSize) {
        return videoMapper.selectCategoryList(pageNum, pageSize);
    }

    @Override
    public JSONArray categoryFeedList(int pageNum, int pageSize, int tid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pn", pageNum);
        map.put("ps", pageSize);
        map.put("rid", tid);
        String resp = HttpUtil.get(VIDEO_CATEGORY_FEED, map);
        JSONObject jsonObject = JSONObject.parseObject(resp);
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray archives = data.getJSONArray("archives");
        for (Object o : archives) {
            JSONObject jsonObject1 = (JSONObject) o;
            String bvid = jsonObject1.getString("bvid");
            int avid = BvToAvUtils.bvidToAid(bvid);
            log.info("bvid:{}\tavid:{}", bvid,avid);
            //清除json中无用的信息
            jsonObject1.remove("state");
            jsonObject1.remove("duration");
            jsonObject1.remove("mission_id");
            jsonObject1.remove("rights");
            jsonObject1.remove("dynamic");
            jsonObject1.remove("short_link");
            jsonObject1.remove("short_link_v2");
            jsonObject1.remove("season_type");
            jsonObject1.remove("is_ogv");
            jsonObject1.remove("ogv_info");
            jsonObject1.remove("rcmd_reason");
        }
        return archives;
    }

}
