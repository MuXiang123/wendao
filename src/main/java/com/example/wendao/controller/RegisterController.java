package com.example.wendao.controller;


import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.example.wendao.dto.RegisterDto;
import com.example.wendao.dto.UserDto;
import com.example.wendao.entity.User;
import com.example.wendao.redis.JedisService;

import com.example.wendao.service.UserService;
import com.example.wendao.utils.*;

import com.example.wendao.vo.UserInfoVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
     *
     * @param registerDto
     * @return
     */
    @PostMapping("/verifyRegisterInfo")
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
        //在腾讯im中注册账户
        RestTemplate restTemplate = new RestTemplate();
        String baseURL = "https://console.tim.qq.com/v4/im_open_login_svc/account_import";
        long appid = 1400807520;
        String identifier = "administrator";
        String key = "ba24710d781eec44402a5fbd1c98915393b8c3b080ed6f43bdc4b9f3f3ccd478";
        TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(appid, key);
        String userSig = tlsSigAPIv2.genUserSig(identifier, 1000 * 60 * 24 * 7);
        UUID uuid = UUID.randomUUID();
        long mostSigBits = uuid.getMostSignificantBits();
        long leastSigBits = uuid.getLeastSignificantBits();
        long unsignedInt = (mostSigBits & Long.MAX_VALUE) | (leastSigBits >>> 1);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("UserID", user.getUserId());
        jsonObject.put("Nick", user.getNickname());

        Map<String, Object> map = new HashMap<>();
        map.put("sdkappid", appid);
        map.put("identifier", identifier);
        map.put("usersig", userSig);
        map.put("random", unsignedInt);
        map.put("contenttype", "json");
        log.info(appid+" "+ identifier+" "+ userSig+" "+ unsignedInt+" "+ jsonObject);
        String responseEntity =  HttpRequest.get(baseURL + "?sdkappid=" + appid + "&identifier=" +
                        identifier + "&usersig=" + userSig + "&random=" + unsignedInt + "&contenttype=json")
                .body(jsonObject.toJSONString())
                .execute().body();
        log.info("TIM注册: " + responseEntity);
        return Result.success(CodeMsg.SUCCESS);
    }

}
