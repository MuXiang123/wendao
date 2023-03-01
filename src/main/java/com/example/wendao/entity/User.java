package com.example.wendao.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author: zhk
 * @description: 用户实体
 * @date: 2023/3/1 16:02
 * @version: 1.0
 */
@Data
public class User {

    /**
     * 这里可以看做是手机号
     */
    private String userId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 加密密码
     */
    private String salt;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 成就值
     */
    private int achieveValue;

    /**
     * 学习
     */
    private String school;

    /**
     * 登录ip地址
     */
    private String loginIp;

    /**
     * 登录时间
     */
    private Date createTime;

    /**
     * 登录类型
     */
    private String loginType;

    /**
     * 添加性别属性 1表示male,0表示female
     */
    private int sex;

    /**
     * 添加个性签名的属性
     */
    private String signature;
}

