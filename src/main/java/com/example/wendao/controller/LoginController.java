package com.example.wendao.controller;

import com.example.wendao.dto.UserDto;
import com.example.wendao.entity.User;
import com.example.wendao.redis.JedisService;
import com.example.wendao.redis.UserTokenKey;
import com.example.wendao.redis.VerifyCodeKey;
import com.example.wendao.service.UserService;
import com.example.wendao.utils.CodeMsg;
import com.example.wendao.utils.CommonUtils;
import com.example.wendao.utils.GenerateRandomCode;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/2 9:15
 * @version: 1.0
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    private Logger log = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    JedisService jedisService;

    @Autowired
    UserService userService;
    /**
     * 发送短信验证码
     *
     * @return
     */
    public static final String USER_TOKEN = "token";

    @GetMapping("/verifyLoginInfo")
    public Result<CodeMsg> loginVerify(String userId, String code, HttpServletResponse response, HttpServletRequest request) {
        String verifyCode = jedisService.getKey(VerifyCodeKey.verifyCodeKeyLogin, code, String.class);
        User user = userService.selectByUserId(userId);
        if (user == null) {
            return Result.error(CodeMsg.UNREGISTER_PHONE);
        }
        if (verifyCode == null || (!verifyCode.equals(code))) {
            return Result.error(CodeMsg.VERIFY_CODE_ERROR);
        }
        // 这里证明登录成功了，拿到用户信息了，这里我应该把用户的信息放在cookie和redis中
        addCookie(response, request, user);
        return Result.success(CodeMsg.SUCCESS);
    }

    /**
     * 提供一个可以提供手机号+密码的方式进行登录
     */
    @PostMapping("/loginPassword")
    public Result<CodeMsg> loginPassword(@RequestBody UserDto userDto, HttpServletResponse response, HttpServletRequest request) {
        User user = userService.selectByUserIdAll(userDto.getUserId());
        if (user == null) {
            return Result.error(CodeMsg.UNREGISTER_PHONE);
        }

        log.info(userDto.getPassword());
        log.info(user.getSalt());

        if (!(user.getPassword().equals(DigestUtils.md5Hex(userDto.getPassword() + user.getSalt())))) {
            return Result.error(CodeMsg.PASSWORD_ERROR);
        } else {
            addCookie(response, request, user);
            return new Result<>(CodeMsg.SUCCESS);
        }
    }

    public void addCookie(HttpServletResponse response, HttpServletRequest request, User user) {
        String token = CommonUtils.uuid();
        log.info("token=" + token);
        // 将token值以及user保存进redis
        jedisService.setKey(UserTokenKey.userTokenKey, token, user);
//        Cookie cookie = new Cookie(USER_TOKEN, token);
//        // 设置60天的有效期
//        cookie.setMaxAge(UserTokenKey.userTokenKey.expireSeconds());
//        cookie.setPath("/");

        ResponseCookie cookie = ResponseCookie.from(USER_TOKEN, token)
                // 在http下也传输
                .secure(true)
                .path("/")
                // 60天
                .maxAge(Duration.ofDays(60))
                .httpOnly(false)
                // 大多数情况也是不发送第三方 Cookie，但是导航到目标网址的 Get 请求除外
                .sameSite("None")
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
    }

    public String getUserToken(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length <= 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }


    public User getUserInfo(HttpServletRequest request) {
        String token = getUserToken(request, LoginController.USER_TOKEN);
        return jedisService.getKey(UserTokenKey.userTokenKey, token, User.class);
    }

    /**
     * 当用户点击退出的时候，应该退出登录，并且应该清除Cookie
     */
    @GetMapping("/logout")
    @ResponseBody
    public Result<Boolean> logout(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(LoginController.USER_TOKEN)) {
                // 马上设置该cookie过期
                String token = cookie.getValue();
                cookie.setMaxAge(0);
                cookie.setPath("/");
                // 设置完cookie过期之后，也应该清空Redis保存的Cookie值
                jedisService.del(UserTokenKey.userTokenKey, token);

            }
        }
        return Result.success(true);
    }
}
