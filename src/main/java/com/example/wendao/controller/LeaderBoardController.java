package com.example.wendao.controller;

import com.example.wendao.entity.User;
import com.example.wendao.service.UserService;
import com.example.wendao.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: zhk
 * @description: 排行榜
 * @date: 2023/3/3 16:46
 * @version: 1.0
 */

@RestController
public class LeaderBoardController {
    @Autowired
    UserService userService;


    /**
     * 返回成就值排行
     *
     * @return
     */
    @GetMapping("/TopAchieve")
    @ResponseBody
    public Result<List<User>> Top10LeaderBoard() {
        return Result.success(userService.top10LeaderBoard());
    }

}
