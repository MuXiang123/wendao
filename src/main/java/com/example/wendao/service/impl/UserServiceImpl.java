package com.example.wendao.service.impl;

import com.example.wendao.controller.RegisterController;
import com.example.wendao.entity.User;
import com.example.wendao.mapper.UserMapper;
import com.example.wendao.redis.JedisService;
import com.example.wendao.redis.VerifyCodeKey;
import com.example.wendao.service.UserService;
import com.example.wendao.utils.GenerateRandomCode;
import com.example.wendao.utils.Result;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/1 21:09
 * @version: 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    private Logger log = LoggerFactory.getLogger(RegisterController.class);
    @Autowired
    COSClient cosClient;
    @Autowired(required = false)
    UserMapper userMapper;

    @Autowired
    JedisService jedisService;

    @Override
    public void insert(User user) {
        userMapper.insertUser(user);
    }

    @Override
    public User selectByUserId(String userId) {
        return userMapper.selectByUserId(userId);
    }

    @Override
    public void updateByUserId(User user) {
        userMapper.updateByUserId(user);
    }

    @Override
    public void resetAchieveValue() {
        userMapper.resetAchieveValue();
    }

    @Override
    public List<User> top10LeaderBoard() {
        return userMapper.top10LeaderBoard();
    }

    //todo 短信验证平台申请
    @Override
    public String sendSMSCode(String userId) {
        String randomCode = "";
        try {
            // 这里里面传递的参数是在腾讯云验证平台下提供的个人秘钥：SecretId, SecretKey
            Credential cred = new Credential("AKIDMWaQ8SIQPF6g4WuqnfJwSOI9nDPRqpBf", "PbXFlQr4gQQDmeZv4w4AHBqGROnw97qI");
            HttpProfile httpProfile = new HttpProfile();
            // 调用腾讯提供的接口
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            SmsClient client = new SmsClient(cred, "", clientProfile);
            SendSmsRequest req = new SendSmsRequest();

            String phone = "+86" + userId;
            String[] phoneNumberSet1 = new String[]{phone};
            req.setPhoneNumberSet(phoneNumberSet1);

            // 绑定的是模板id
            req.setTemplateID("907863");
            req.setSign("欣然向上");

            // 这是给固定模板里面传递的验证码,注意是数组格式
            randomCode = GenerateRandomCode.generateRandomVerificationCode();
            jedisService.setKey(VerifyCodeKey.verifyCodeKeyLogin, randomCode, randomCode);
            String[] templateParamSet1 = new String[]{randomCode};
            req.setTemplateParamSet(templateParamSet1);

            // 腾讯云提供的smsSdkAppid
            req.setSmsSdkAppid("1400500804");

            SendSmsResponse resp = client.SendSms(req);
            log.info(SendSmsResponse.toJsonString(resp));
            return randomCode;
        } catch (TencentCloudSDKException e) {
            log.error(e.toString());
        }
        return randomCode;
    }

    @Override
    public String uploadImages(MultipartFile multipartFile, String userId) {
        //MultipartFile转file
        File localFile = new File(multipartFile.getOriginalFilename());
        try {
            multipartFile.transferTo(localFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 指定文件将要存放的存储桶
        String bucketName = "wendao-1316683032";
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为 folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        String key = "avatar/"+userId;
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        log.info("putObjectResult"+putObjectResult.toString());
        //关闭连接
        cosClient.shutdown();
        //设置过期时间
        Date expiration = new Date(System.currentTimeMillis() + 5 * 60 * 10000);
        //获取图片地址
        URL oldUrl = cosClient.generatePresignedUrl(bucketName, key, expiration);
        String url = oldUrl.toString();
        // 直接查找到第一个？的位置
        url = url.substring(0, url.indexOf("?"));
        log.info("用户: "+userId+"\n头像地址: "+url);
        return url;
    }
}
