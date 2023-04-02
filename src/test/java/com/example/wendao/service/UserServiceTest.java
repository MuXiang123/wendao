package com.example.wendao.service;

import com.example.wendao.entity.User;
import com.example.wendao.utils.RandomUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void insert() {
        User user = new User();
        user.setUserId("13800138000");
        user.setNickname("test");
        user.setPassword("123456");
        user.setSalt("abc");
        user.setAvatar("default.png");
        user.setAchieveValue(0);
        user.setSchool("Tsinghua University");
        user.setLoginIp("127.0.0.1");
        user.setCreateTime(new Date());
        user.setLoginType("mobile");
        user.setSex(1);
        user.setSignature("Hello, world!");

        userService.insert(user);

        User insertedUser = userService.selectByUserId(user.getUserId());

        Assert.assertEquals(user.getNickname(), insertedUser.getNickname());
        Assert.assertEquals(user.getPassword(), insertedUser.getPassword());
        Assert.assertEquals(user.getSalt(), insertedUser.getSalt());
        Assert.assertEquals(user.getAvatar(), insertedUser.getAvatar());
        Assert.assertEquals(user.getAchieveValue(), insertedUser.getAchieveValue());
        Assert.assertEquals(user.getSchool(), insertedUser.getSchool());
        Assert.assertEquals(user.getLoginIp(), insertedUser.getLoginIp());
//        Assert.assertEquals(user.getCreateTime(), insertedUser.getCreateTime());
        Assert.assertEquals(user.getLoginType(), insertedUser.getLoginType());
        Assert.assertEquals(user.getSex(), insertedUser.getSex());
        Assert.assertEquals(user.getSignature(), insertedUser.getSignature());
    }

    @Test
    void selectByUserId() {
    }

    @Test
    void updateByUserId() {
        User user = new User();
        user.setUserId("18420051370");
        user.setNickname("zhk");
        user.setPassword("123456");
        user.setAvatar("default.png");
        user.setAchieveValue(0);
        user.setSchool("Tsinghua University");
        user.setSex(1);
        user.setSignature("Hello, world!");
        String salt = RandomUtils.randomSalt();
        user.setSalt(salt);
        user.setPassword(DigestUtils.md5Hex((user.getPassword() + salt)));
        userService.updateByUserId(user);
    }

    @Test
    void resetAchieveValue() {
    }

    @Test
    void top10LeaderBoard() {
    }

    @Test
    void sendSMSCode() {
    }

    @Test
    void uploadImages() {
    }
}