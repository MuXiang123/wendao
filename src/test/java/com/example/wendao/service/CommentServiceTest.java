package com.example.wendao.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.wendao.vo.CommentUserVo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLOutput;
import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/19 10:06
 * @version: 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CommentServiceTest {
    @Autowired
    CommentService commentService;
    @Test
    void test1(){
        List<CommentUserVo> commentUserVos = commentService.selectCommentLists(3);
//        JSONObject jsonObject = JSON.parseObject(commentUserVos.toString());
        String s = JSONObject.toJSONString(commentUserVos);
        System.out.println(s);
    }

}
