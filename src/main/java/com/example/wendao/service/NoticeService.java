package com.example.wendao.service;

import com.example.wendao.entity.Notice;

import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 14:59
 * @version: 1.0
 */
public interface NoticeService {
    /**
     * 新增通知
     * @param notice
     */
    void insertNotice(Notice notice);

    /**
     * 查询通知
     * @return
     */
    Notice selectNotice();

    /**
     * 更新所有通知已读
     * @param userId
     */
    void updateAllNoticeHasRead(String userId);

    /**
     * 已读通知数
     * @param userId
     * @return
     */
    int countNoticeHasRead(String userId);

    /**
     * 通知列表
     * @param userId
     * @return
     */
    List<Notice> noticeList(String userId);

}
