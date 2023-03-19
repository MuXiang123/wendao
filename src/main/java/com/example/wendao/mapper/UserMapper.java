package com.example.wendao.mapper;

import com.example.wendao.entity.User;
import com.example.wendao.vo.UserInfoVo;

import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/1 16:35
 * @version: 1.0
 */
public interface UserMapper {

    /**
     * 添加用户
     * @param user
     */
    void insertUser(User user);

    /**
     * 通过id查找用户
     * @param userId
     * @return
     */
    User selectByUserId(String userId);

    /**
     * 根据id查询userinfo
     * @param userId
     * @return
     */
    UserInfoVo selectByUserInfoId(String userId);
    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    void updateByUserId(User user);

    /**
     * 重置成就值
     */
    void resetAchieveValue();

    /**
     * 查询成就值排行前十
     * @return
     */
    List<User> top10LeaderBoard();
}
