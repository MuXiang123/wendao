package com.example.wendao.controller;

import com.example.wendao.entity.User;
import com.example.wendao.redis.JedisService;
import com.example.wendao.redis.VerifyCodeKey;
import com.example.wendao.service.UserService;
import com.example.wendao.utils.CodeMsg;
import com.example.wendao.utils.GenerateRandomCode;
import com.example.wendao.utils.RandomUtils;
import com.example.wendao.utils.Result;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 这里的参数传递应该是传递的是校验码,校验验证码，判断用户填的校验码和用Redis临时存储的验证码是否匹配
     *
     * @param code
     * @return
     */
    @GetMapping("/verifyRegisterInfo")
    public Result<CodeMsg> registerVerify(String code, String userId, String password) {
        String verifyCode = jedisService.getKey(VerifyCodeKey.verifyCodeKeyRegister, code, String.class);
        if (verifyCode == null) {
            return Result.error(CodeMsg.VERIFY_CODE_ERROR);
        }

        // 判断该手机号是否注册过了
        User u = userService.selectByUserId(userId);
        if (u != null) {
            return Result.error(CodeMsg.DUPLICATE_REGISTRY);
        }
        // 随机生成一个6位数的小写字符串
        String salt = RandomUtils.randomSalt();
        String nickname = "用户" + RandomUtils.randomNickName() + "号";

        User user = new User();
        user.setUserId(userId);
        user.setSalt(salt);
        user.setPassword(DigestUtils.md5Hex((password + salt)));
        user.setNickname(nickname);
        user.setLoginIp("phone");
        user.setCreateTime(new Date());
        userService.insert(user);
        return Result.success(CodeMsg.SUCCESS);
    }

}
