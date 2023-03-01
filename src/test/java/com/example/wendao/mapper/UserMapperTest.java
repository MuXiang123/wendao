package com.example.wendao.mapper;

import com.example.wendao.entity.User;
import org.junit.Assert;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserMapperTest {

    @Resource
    private UserMapper userMapper;

    @Test
    void insert() {
        User user = new User();
        user.setUserId("12345678902");
        user.setNickname("John");
        user.setPassword("password");
        user.setSalt("salt");
        user.setAvatar("avatar");
        user.setAchieveValue(100);
        user.setSchool("school");
        user.setLoginIp("192.168.1.1");
        user.setCreateTime(new Date());
        user.setLoginType("web");
        user.setSex(1);
        user.setSignature("signature");
        userMapper.insertUser(user);

        User insertedUser = userMapper.selectByUserId(user.getUserId());
        Assert.assertEquals(user.getNickname(), insertedUser.getNickname());
        Assert.assertEquals(user.getPassword(), insertedUser.getPassword());
        Assert.assertEquals(user.getSalt(), insertedUser.getSalt());
        Assert.assertEquals(user.getAvatar(), insertedUser.getAvatar());
        Assert.assertEquals(user.getAchieveValue(), insertedUser.getAchieveValue());
        Assert.assertEquals(user.getSchool(), insertedUser.getSchool());
        Assert.assertEquals(user.getLoginIp(), insertedUser.getLoginIp());
        Assert.assertEquals(user.getCreateTime(), insertedUser.getCreateTime());
        Assert.assertEquals(user.getLoginType(), insertedUser.getLoginType());
        Assert.assertEquals(user.getSex(), insertedUser.getSex());
        Assert.assertEquals(user.getSignature(), insertedUser.getSignature());

    }

    @Test
    void selectByUserId() {
        User user = new User();
        user.setUserId("123456789");
        user.setNickname("张三");
        user.setPassword("123456");
        user.setSalt("abc");
        user.setAvatar("http://xxx.com/avatar.png");
        user.setAchieveValue(100);
        user.setSchool("清华大学");
        user.setLoginIp("127.0.0.1");
        user.setCreateTime(new Date());
        user.setLoginType("PC");
        user.setSex(1);
        user.setSignature("我是张三");

        // 插入测试数据
        userMapper.insertUser(user);

        // 查询测试数据
        User result = userMapper.selectByUserId("123456789");
        System.out.println(result);

        // 断言查询结果是否符合预期
        Assert.assertNotNull(result);
        Assert.assertEquals("123456789", result.getUserId());
        Assert.assertEquals("张三", result.getNickname());
        Assert.assertEquals("123456", result.getPassword());
        Assert.assertEquals("abc", result.getSalt());
        Assert.assertEquals("http://xxx.com/avatar.png", result.getAvatar());
        Assert.assertEquals(100, result.getAchieveValue());
        Assert.assertEquals("清华大学", result.getSchool());
        Assert.assertEquals("127.0.0.1", result.getLoginIp());
        Assert.assertEquals("PC", result.getLoginType());
        Assert.assertEquals(1, result.getSex());
        Assert.assertEquals("我是张三", result.getSignature());
    }

    @Test
    void updateByUserId() {
        User user = new User();
        user.setUserId("123456789");
        user.setNickname("张三");
        user.setPassword("123456");
        user.setSalt("abc");
        user.setAvatar("http://xxx.com/avatar.png");
        user.setAchieveValue(100);
        user.setSchool("清华大学");
        user.setLoginIp("127.0.0.1");
        user.setCreateTime(new Date());
        user.setLoginType("PC");
        user.setSex(1);

        userMapper.updateByUserId(user);
        User result = userMapper.selectByUserId(user.getUserId());

        // 断言查询结果是否符合预期
        Assert.assertNotNull(result);
        Assert.assertEquals("123456789", result.getUserId());
        Assert.assertEquals("张三", result.getNickname());
        Assert.assertEquals("123456", result.getPassword());
        Assert.assertEquals("abc", result.getSalt());
        Assert.assertEquals("http://xxx.com/avatar.png", result.getAvatar());
        Assert.assertEquals(100, result.getAchieveValue());
        Assert.assertEquals("清华大学", result.getSchool());
        Assert.assertEquals("127.0.0.1", result.getLoginIp());
        Assert.assertEquals("PC", result.getLoginType());
        Assert.assertEquals(1, result.getSex());
        Assert.assertEquals("我是张三", result.getSignature());
    }

    @Test
    void resetAchieveValue() {
        userMapper.resetAchieveValue();
    }

    @Test
    void top10LeaderBoard() {
        List<User> users = userMapper.top10LeaderBoard();
        // 校验查询结果
        assertNotNull(users);
        assertEquals(10, users.size());
        // 校验每个用户的成就值都比后面的用户大或相等
        int previousAchieveValue = Integer.MAX_VALUE;
        for (User user : users) {
            assertTrue(user.getAchieveValue() <= previousAchieveValue);
            previousAchieveValue = user.getAchieveValue();
        }
    }
}