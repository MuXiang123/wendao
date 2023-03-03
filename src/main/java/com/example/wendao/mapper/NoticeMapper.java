package com.example.wendao.mapper;

import com.example.wendao.entity.Notice;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 14:52
 * @version: 1.0
 */
public interface NoticeMapper {

    /**
     * 新增通知
     * @param notice
     */
    void insertNotice(Notice notice);

    /**
     * 选择所有通知
     * @return
     */
    Notice selectAllNotice();

    /**
     * 更新所有已读通知
     * 是否已读，1表示已读，0表示未读
     * @param userId
     */
    void updateAllNoticeHasRead(String userId);

    /**
     * 已读通知数 是否已读，1表示已读，0表示未读
     * @param userId
     * @return
     */
    int countNoticeHasRead(String userId);

    /**
     * 查询通知列表
     * @param userId
     * @return
     */
    List<Notice> noticeList(String userId);

}
