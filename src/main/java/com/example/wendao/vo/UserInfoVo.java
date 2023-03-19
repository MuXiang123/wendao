package com.example.wendao.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/18 16:13
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVo {

    /**
     * 这里可以看做是手机号
     */
    private String userId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 成就值
     */
    private int achieveValue;

    /**
     * 学校
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
     * 添加性别属性 1表示male,0表示female
     */
    private int sex;

    /**
     * 添加个性签名的属性
     */
    private String signature;
}
