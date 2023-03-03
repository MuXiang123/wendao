package com.example.wendao.service.impl;

import com.example.wendao.entity.Fans;
import com.example.wendao.mapper.FansMapper;
import com.example.wendao.service.FansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 14:30
 * @version: 1.0
 */
@Service
public class FansServiceImpl implements FansService {
    @Autowired(required = false)
    FansMapper fansMapper;

    @Override
    public void insertFans(Fans fans) {
        fansMapper.insertFans(fans);
    }

    @Override
    public void deleteFans(String userId, String fansId) {
        fansMapper.deleteFans(userId, fansId);
    }

    @Override
    public List<Fans> selectAllFansByUserId(String userId) {
        return fansMapper.selectAllFansByUserId(userId);
    }
}
