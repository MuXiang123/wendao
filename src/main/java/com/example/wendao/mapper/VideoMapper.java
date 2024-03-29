package com.example.wendao.mapper;

import com.example.wendao.entity.VideoCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/21 17:13
 * @version: 1.0
 */
public interface VideoMapper {
    /**
     * 分页获取视频目录
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<VideoCategory> selectCategoryList(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    /**
     * 获取主分区
     * @return
     */
    List<VideoCategory> selectCategoryMainList();

    /**
     * 根据父id查询子id
     * @param parentId
     * @return
     */
    List<VideoCategory> selectCategoryChild(@Param("parentId") int parentId);
}
