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
    void insertNotice(Notice notice);

    Notice selectNotice();

    void updateAllNoticeHasRead(String userId);

    int countNoticeHasRead(String userId);

    List<Notice> noticeList(String userId);

}
