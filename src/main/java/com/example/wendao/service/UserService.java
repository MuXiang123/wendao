package com.example.wendao.service;

import com.example.wendao.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/1 21:07
 * @version: 1.0
 */
public interface UserService {
    /**
     * 添加用户
     * @param user
     */
    void insert(User user);

    /**
     * 通过id获取用户信息
     * @param userId
     * @return
     */
    User selectByUserId(String userId);

    /**
     * 更新用户信息
     * @param user
     */
    void updateByUserId(User user);

    /**
     * 重置成就值
     */
    void resetAchieveValue();

    /**
     * 查询成就值前十的用户
     * @return
     */
    List<User> top10LeaderBoard();

    /**
     * 发送验证码
     * @return
     */
    String sendSMSCode(String userId);

    /**
     * 腾讯云对象存储上传图片
     * @param multipartFile
     * @param userId
     * @return
     */
    String uploadImages(MultipartFile multipartFile, String userId);
}
