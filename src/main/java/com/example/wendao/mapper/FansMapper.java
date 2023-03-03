package com.example.wendao.mapper;

import com.example.wendao.entity.Fans;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 14:25
 * @version: 1.0
 */
public interface FansMapper {
    String TABLE_NAME = " fans ";
    String INSERT_VALUE = " user_id, fans_id, created_time ";
    String SELECT_VALUE = " id, user_id, fans_id, created_time ";

    void insertFans(Fans fans);

    void deleteFans(String userId, String fansId);

    List<Fans> selectAllFansByUserId(String userId);

}
