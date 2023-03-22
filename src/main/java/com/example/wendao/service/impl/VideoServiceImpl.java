package com.example.wendao.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.wendao.dto.VideoActionDto;
import com.example.wendao.entity.VideoCategory;
import com.example.wendao.mapper.VideoMapper;
import com.example.wendao.service.VideoService;
import com.example.wendao.utils.BvToAvUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final static String VIDEO_ACTION = "https://api.bilibili.com/x/player/playurl";
    private final static String ENCODE = "UTF-8";

    @Value("${cookie}")
    private String COOKIE;
    @Resource
    VideoMapper videoMapper;

    @Override
    public List<VideoCategory> categoryList(int pageNum, int pageSize) {
        return videoMapper.selectCategoryList(pageNum, pageSize);
    }

    @Override
    public JSONArray categoryFeedList(int pageNum, int pageSize, int rid) {
        RestTemplate restTemplate = new RestTemplate();
        // 设置Cookie
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", COOKIE);
        // 设置参数
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(VIDEO_CATEGORY_FEED)
                .queryParam("pn", pageNum)
                .queryParam("ps", pageSize)
                .queryParam("rid", rid);
        // 发送GET请求并获取响应
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, entity, String.class);
        String resp = response.getBody();
        JSONObject jsonObject = JSONObject.parseObject(resp);
        JSONObject data = jsonObject.getJSONObject("data");
        if (data == null) {
            List<Object> list = new ArrayList<>();
            list.add("请求错误");
            return new JSONArray(list);
        }
        JSONArray archives = data.getJSONArray("archives");
        for (Object o : archives) {
            JSONObject jsonObject1 = (JSONObject) o;
            String bvid = jsonObject1.getString("bvid");
            int avid = BvToAvUtils.bvidToAid(bvid);
            log.info("bvid:{}\tavid:{}", bvid, avid);
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

    @Override
    public JSONObject action(VideoActionDto videoActionDto) {
        RestTemplate restTemplate = new RestTemplate();
        // 设置Cookie
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", COOKIE);
        // 设置参数
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(VIDEO_ACTION)
                .queryParam("avid", videoActionDto.getAvid())
                .queryParam("cid", videoActionDto.getCid())
                .queryParam("qn", videoActionDto.getQn())
                .queryParam("fnval", videoActionDto.getFnval())
                .queryParam("fnver", videoActionDto.getFnver())
                .queryParam("fourk", videoActionDto.getFourk());
        // 发送GET请求并获取响应
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, entity, String.class);
        String resp = response.getBody();
        JSONObject jsonObject = JSONObject.parseObject(resp);
        if (jsonObject.getIntValue("code") == -400) {
            Map<String, Object> error = new HashMap<>();
            error.put("msg", "请求错误");
            return new JSONObject(error);
        } else if (jsonObject.getIntValue("code") == -404) {
            Map<String, Object> error = new HashMap<>();
            error.put("msg", "无视频");
            return new JSONObject(error);
        }
        JSONObject data = jsonObject.getJSONObject("data");
        //精简json 去除不需要的字段
        data.remove("from");
        data.remove("result");
        data.remove("message");
        data.remove("high_format");
        data.remove("last_play_time");
        data.remove("last_play_cid");
        return data;
    }

}
