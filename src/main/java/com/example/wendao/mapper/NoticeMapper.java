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

    void insertNotice(Notice notice);

    Notice selectAllNotice();

    void updateAllNoticeHasRead(String userId);

    int countNoticeHasRead(String userId);

    List<Notice> noticeList(String userId);

}
