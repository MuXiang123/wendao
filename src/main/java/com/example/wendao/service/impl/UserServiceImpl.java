package com.example.wendao.service.impl;

import com.example.wendao.controller.RegisterController;
import com.example.wendao.entity.User;
import com.example.wendao.mapper.UserMapper;
import com.example.wendao.redis.JedisService;
import com.example.wendao.redis.VerifyCodeKey;
import com.example.wendao.service.UserService;
import com.example.wendao.utils.GenerateRandomCode;
import com.example.wendao.utils.Result;
import com.example.wendao.vo.UserData;
import com.example.wendao.vo.UserInfoVo;
import com.mchange.io.FileUtils;
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
import java.util.UUID;

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
    public UserInfoVo selectByUserInfoId(String userId) {
        return userMapper.selectByUserInfoId(userId);
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

    @Override
    public String uploadImages(MultipartFile multipartFile) {
        //MultipartFile转file
        //获取最后一个点的索引值
        int doPos = multipartFile.getOriginalFilename().lastIndexOf(".");
        //获取扩展名
        String fileExt = multipartFile.getOriginalFilename().substring(doPos + 1).toLowerCase();
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
        // 若须要防止生成的临时文件重复,能够在文件名后添加随机码
        try {
            File localFile = File.createTempFile(fileName, fileExt);
            multipartFile.transferTo(localFile);
            log.info(multipartFile.getOriginalFilename());
            log.info(String.valueOf(multipartFile.getSize()));
            // 指定文件将要存放的存储桶
            String bucketName = "wendao-1316683032";
            // 指定文件上传到 COS 上的路径，即对象键。例如对象键为 folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下

            String key = "avatar/" + fileName;
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            log.info("putObjectResult" + putObjectResult.toString());

            //设置过期时间
            Date expiration = new Date(System.currentTimeMillis() + 5 * 60 * 10000);
            //获取图片地址
            URL oldUrl = cosClient.generatePresignedUrl(bucketName, key, expiration);
            String url = oldUrl.toString();
            // 直接查找到第一个？的位置
            url = url.substring(0, url.indexOf("?"));
            log.info("头像地址: " + url);
            return url;
        } catch (IOException e) {
            log.error("文件创建错误:{}",e);
            return "";
        }
    }

    @Override
    public User selectByUserIdAll(String userId) {
        return userMapper.selectByUserIdAll(userId);
    }

    @Override
    public UserData getUserData(String userId) {
        return userMapper.getUserData(userId);
    }
}
