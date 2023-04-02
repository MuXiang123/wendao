package com.example.wendao.controller;


import com.example.wendao.dto.RegisterDto;
import com.example.wendao.dto.UserDto;
import com.example.wendao.entity.User;
import com.example.wendao.redis.JedisService;

import com.example.wendao.service.UserService;
import com.example.wendao.utils.CodeMsg;

import com.example.wendao.utils.IpUtils;
import com.example.wendao.utils.RandomUtils;
import com.example.wendao.utils.Result;

import com.example.wendao.vo.UserInfoVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/2 9:15
 * @version: 1.0
 */
@RestController
@RequestMapping(value = "/register")
public class RegisterController {
    private Logger log = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    JedisService jedisService;

    @Autowired
    UserService userService;

    /**
     * 注册
     * @param registerDto
     * @return
     */
    @PostMapping ("/verifyRegisterInfo")
    public Result<CodeMsg> registerVerify(@RequestBody RegisterDto registerDto, HttpServletRequest request) {
        // 判断该手机号是否注册过了
        UserInfoVo u = userService.selectByUserInfoId(registerDto.getUserId());
        if (u != null) {
            return Result.error(CodeMsg.DUPLICATE_REGISTRY);
        }
        // 随机生成一个6位数的小写字符串
        String salt = RandomUtils.randomSalt();

        User user = new User();
        user.setUserId(registerDto.getUserId());
        user.setSalt(salt);
        user.setPassword(DigestUtils.md5Hex((registerDto.getPassword() + salt)));
        user.setNickname(registerDto.getNickName());
        user.setLoginIp(IpUtils.getIpAddr(request));
        user.setCreateTime(new Date());
        userService.insert(user);
        return Result.success(CodeMsg.SUCCESS);
    }

}
