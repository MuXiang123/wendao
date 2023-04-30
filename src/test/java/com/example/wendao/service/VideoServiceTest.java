package com.example.wendao.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/22 9:53
 * @version: 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class VideoServiceTest {

    @Autowired
    VideoService videoService;

    @Test
    void test1(){
        String json = "{\n" +
                "    \"code\": 0,\n" +
                "    \"message\": \"0\",\n" +
                "    \"ttl\": 1,\n" +
                "    \"data\": {\n" +
                "        \"page\": {\n" +
                "            \"num\": 1,\n" +
                "            \"size\": 1,\n" +
                "            \"count\": 193\n" +
                "        },\n" +
                "        \"archives\": [\n" +
                "            {\n" +
                "                \"aid\": 438771916,\n" +
                "                \"videos\": 1,\n" +
                "                \"tid\": 228,\n" +
                "                \"tname\": \"人文历史\",\n" +
                "                \"copyright\": 1,\n" +
                "                \"pic\": \"http://i0.hdslb.com/bfs/archive/4a12e58b82deb841fa95e4cee1a1d03c102e6525.jpg\",\n" +
                "                \"title\": \"“朕提三尺剑以定四海，远夷率服，亿兆乂安”｜《贞观政要》中的经典治世哲理，看唐太宗李世民如何治国理政\",\n" +
                "                \"pubdate\": 1679373248,\n" +
                "                \"ctime\": 1679373248,\n" +
                "                \"desc\": \"-\",\n" +
                "                \"state\": 0,\n" +
                "                \"duration\": 236,\n" +
                "                \"rights\": {\n" +
                "                    \"bp\": 0,\n" +
                "                    \"elec\": 0,\n" +
                "                    \"download\": 0,\n" +
                "                    \"movie\": 0,\n" +
                "                    \"pay\": 0,\n" +
                "                    \"hd5\": 1,\n" +
                "                    \"no_reprint\": 0,\n" +
                "                    \"autoplay\": 1,\n" +
                "                    \"ugc_pay\": 0,\n" +
                "                    \"is_cooperation\": 0,\n" +
                "                    \"ugc_pay_preview\": 0,\n" +
                "                    \"no_background\": 0,\n" +
                "                    \"arc_pay\": 0,\n" +
                "                    \"pay_free_watch\": 0\n" +
                "                },\n" +
                "                \"owner\": {\n" +
                "                    \"mid\": 1636457044,\n" +
                "                    \"name\": \"崇文书馆\",\n" +
                "                    \"face\": \"https://i1.hdslb.com/bfs/face/899eef6dec5592a7d96a8f1b022958d1526fb842.jpg\"\n" +
                "                },\n" +
                "                \"stat\": {\n" +
                "                    \"aid\": 438771916,\n" +
                "                    \"view\": 859,\n" +
                "                    \"danmaku\": 0,\n" +
                "                    \"reply\": 3,\n" +
                "                    \"favorite\": 43,\n" +
                "                    \"coin\": 8,\n" +
                "                    \"share\": 0,\n" +
                "                    \"now_rank\": 0,\n" +
                "                    \"his_rank\": 0,\n" +
                "                    \"like\": 51,\n" +
                "                    \"dislike\": 0,\n" +
                "                    \"vt\": 789,\n" +
                "                    \"vv\": 859\n" +
                "                },\n" +
                "                \"dynamic\": \"\",\n" +
                "                \"cid\": 1062898902,\n" +
                "                \"dimension\": {\n" +
                "                    \"width\": 1920,\n" +
                "                    \"height\": 1080,\n" +
                "                    \"rotate\": 0\n" +
                "                },\n" +
                "                \"short_link\": \"https://b23.tv/BV1tL411d7vo\",\n" +
                "                \"short_link_v2\": \"https://b23.tv/BV1tL411d7vo\",\n" +
                "                \"up_from_v2\": 35,\n" +
                "                \"first_frame\": \"http://i1.hdslb.com/bfs/storyff/n230321qn1qmtl7wuqx17t16ew0tk599_firsti.jpg\",\n" +
                "                \"pub_location\": \"安徽\",\n" +
                "                \"bvid\": \"BV1tL411d7vo\",\n" +
                "                \"season_type\": 0,\n" +
                "                \"is_ogv\": false,\n" +
                "                \"ogv_info\": null,\n" +
                "                \"rcmd_reason\": \"\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONArray ac = jsonObject.getJSONObject("data").getJSONArray("archives");
//        for (Object o: ac) {
//
//        }
    }

    @Test
    void test2(){
        JSONArray objects = videoService.recommend(739152895);
        String s = objects.toJSONString();
        System.out.println(s);
    }

    @Test
    public void testVideoInformation() {
        Integer aid = 640081214;
        JSONObject videoInfo = videoService.videoInformation(aid);
        System.out.println(videoInfo.toJSONString());
        assertNotNull(videoInfo);
        assertTrue(videoInfo.containsKey("title"));
        assertTrue(videoInfo.containsKey("description"));
        assertTrue(videoInfo.containsKey("play"));
        assertTrue(videoInfo.containsKey("pic"));
    }

    @Test
    public void test3(){
        UUID uuid = UUID.randomUUID();
        long mostSigBits = uuid.getMostSignificantBits();
        long leastSigBits = uuid.getLeastSignificantBits();
        long unsignedInt = (mostSigBits & Long.MAX_VALUE) | (leastSigBits >>> 1);
        System.out.println("随机的32位无符号整数为：" + unsignedInt);
    }
}
