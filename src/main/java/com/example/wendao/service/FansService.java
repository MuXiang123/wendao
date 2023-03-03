package com.example.wendao.service;

import com.example.wendao.entity.Fans;

import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 14:29
 * @version: 1.0
 */
public interface FansService {
    void insertFans(Fans fans);

    void deleteFans(String userId, String followId);

    List<Fans> selectAllFansByUserId(String userId);
}
