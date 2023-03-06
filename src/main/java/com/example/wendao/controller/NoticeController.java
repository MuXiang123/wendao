package com.example.wendao.controller;

import com.example.wendao.controller.LoginController;
import com.example.wendao.entity.Notice;
import com.example.wendao.entity.User;
import com.example.wendao.service.NoticeService;
import com.example.wendao.service.UserService;
import com.example.wendao.utils.CodeMsg;
import com.example.wendao.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 16:51
 * @version: 1.0
 */
@RestController
public class NoticeController {
    @Autowired
    LoginController loginController;

    @Autowired
    NoticeService noticeService;

    @Autowired
    UserService userService;

    /**
     * 异步通知和前端联调设计思路：notice表中有一个has_read属性，has_read 0表示的是未读，
     * 1 表示的是已读，如果有未读的消息，则给前端返回true，没有未读的消息，则给前端返回false
     * 前端拿到true之后，应该出现红点，当用户点击红点的时候，后端逻辑给前端返回和该用户相关的
     * 数据，并将所有的数据has_read属性设置为1表示已读
     */
    @GetMapping("/hasReadNotice")
    @ResponseBody
    public Result<Boolean> hasReadIsZero(HttpServletRequest request) {
        User user = loginController.getUserInfo(request);
        if (user == null) {
            return Result.error(CodeMsg.NOT_LOGIN);
        }
        //User user = userService.selectByUserId("18392710807");
        //通过数据库查询notice表中是否有has_read属性是否为0,0表示的是未读
        int count = noticeService.countNoticeHasRead(user.getUserId());
        if (count > 0) {
            return Result.success(true);
        } else {
            return Result.success(false);
        }
    }

    @GetMapping("/notice/list")
    @ResponseBody
    public Result<List<Notice>> noticeList(HttpServletRequest request) {
        User user = loginController.getUserInfo(request);
        if (user == null) {
            return Result.error(CodeMsg.NOT_LOGIN);
        }
        //User user = userService.selectByUserId("18392710807");
        noticeService.updateAllNoticeHasRead(user.getUserId());
        List<Notice> notices = noticeService.noticeList(user.getUserId());
        return Result.success(notices);
    }
}
