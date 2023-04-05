package com.example.wendao.mapper;

import com.example.wendao.entity.Like;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class LikeMapperTest {


    @Autowired
    private LikeMapper likeMapper;

    @Test
    public void testAddLike() {
        Like like = new Like();
        like.setUserId(1);
        like.setArticleId(100);

        likeMapper.addLike(like);

        assertNotNull(like.getLikeId());
    }

    @Test
    public void testUpdateLike() {
        Like like = new Like();
        like.setLikeId(1);
        like.setUserId(2);

        likeMapper.updateLike(like);

//        Like updatedLike = likeMapper.getLikeById(1);
//        assertEquals(2, updatedLike.getUserId());
    }

    @Test
    public void testDeleteLikeById() {
        int userId = 1;
        int article_id = 1;
        likeMapper.deleteLikeById(userId, article_id);

//        Like deletedLike = likeMapper.getLikeById(likeId);
//        assertNull(deletedLike);
    }

    @Test
    public void testGetAllLikes() {
        List<Like> likes = likeMapper.getAllLikes();
        assertNotNull(likes);
        assertTrue(likes.size() > 0);
    }

    @Test
    public void testGetLikesByUserId() {
        int userId = 1;
        List<Like> likes = likeMapper.getLikesByUserId(userId);
        assertNotNull(likes);
        assertTrue(likes.size() > 0);
    }
}