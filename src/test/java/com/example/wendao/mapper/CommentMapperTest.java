package com.example.wendao.mapper;

import com.example.wendao.vo.CommentUserVo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/19 9:27
 * @version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentMapperTest {
    @Autowired
    CommentMapper commentMapper;
    @Test
    void test1(){
        List<CommentUserVo> commentUserVos = commentMapper.selectParentComment(3, -1);
        for (CommentUserVo item:
             commentUserVos) {
            System.out.println(item.getCommentCreatedTime());
        }
    }
}
