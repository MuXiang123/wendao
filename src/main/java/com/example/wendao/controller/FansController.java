package com.example.wendao.controller;

import com.example.wendao.entity.Fans;
import com.example.wendao.entity.User;
import com.example.wendao.redis.FansKey;
import com.example.wendao.redis.JedisService;
import com.example.wendao.service.FansService;
import com.example.wendao.service.UserService;
import com.example.wendao.utils.CodeMsg;
import com.example.wendao.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 16:43
 * @version: 1.0
 */
@RestController
public class FansController {
    private static final Logger log = LoggerFactory.getLogger(FansController.class);

    @Autowired
    FansService fansService;

    @Autowired
    JedisService jedisService;

    @Autowired
    LoginController loginController;

    @Autowired
    UserService userService;

    @GetMapping("/fans/list")
    @ResponseBody
    public Result<List<User>> fansList(@RequestParam String userId) {
        String realKey = FansKey.fansKey.getPrefix() + userId;
        Set<String> set = jedisService.smembers(realKey);
        List<User> usersList = new ArrayList<>();
        if (!set.isEmpty()) {
            // 这个set里面全部存储的userId,注意是String类型,然后根据这个来查询出User的信息
            for (String str : set) {
                User u = userService.selectByUserId(str);
                usersList.add(u);
            }
            log.info("从Redis中获取我的粉丝列表");
        } else {
            // 如果从Redis拿不到数据的话，就要从mysql中取数据
            List<Fans> fansList = fansService.selectAllFansByUserId(userId);
            for (Fans fans : fansList) {
                User u = userService.selectByUserId(fans.getFansId());
                usersList.add(u);
            }
            log.info("从mysql中获取粉丝列表");
        }
        return Result.success(usersList);
    }
}
