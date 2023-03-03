package com.example.wendao.service.impl;

import com.example.wendao.entity.Notice;
import com.example.wendao.mapper.NoticeMapper;
import com.example.wendao.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 14:59
 * @version: 1.0
 */
public class NoticeServiceImpl implements NoticeService {
    @Autowired(required = false)
    NoticeMapper noticeMapper;

    @Override
    public void insertNotice(Notice notice) {
        noticeMapper.insertNotice(notice);
    }

    @Override
    public Notice selectNotice() {
        return noticeMapper.selectAllNotice();
    }

    @Override
    public void updateAllNoticeHasRead(String userId) {
        noticeMapper.updateAllNoticeHasRead(userId);
    }

    @Override
    public int countNoticeHasRead(String userId) {
        return noticeMapper.countNoticeHasRead(userId);
    }

    @Override
    public List<Notice> noticeList(String userId) {
        return noticeMapper.noticeList(userId);
    }

}
