package com.example.wendao.controller;

import com.example.wendao.entity.User;
import com.example.wendao.service.UserService;
import com.example.wendao.utils.IpUtils;
import com.example.wendao.utils.RandomUtils;
import com.example.wendao.utils.Result;
import com.example.wendao.vo.UserData;
import com.example.wendao.vo.UserInfoVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;


/**
 * @author: zhk
 * @description:
 * @date: 2023/3/1 21:10
 * @version: 1.0
 */
@RestController
public class UserController {
    private static Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;

    @Autowired
    LoginController loginController;

    /**
     * 默认展示用户的基本信息，
     *
     * @return
     */
    @GetMapping("/userInfo")
    @ResponseBody
    public Result<UserInfoVo> userInfo(HttpServletRequest request) {
        User user = loginController.getUserInfo(request);
        String userId = user.getUserId();
        return Result.success(userService.selectByUserInfoId(userId));
    }

    @GetMapping("/userInfoId")
    @ResponseBody
    public Result<UserInfoVo> userInfoById(@RequestParam String userId) {
        return Result.success(userService.selectByUserInfoId(userId));
    }

    @GetMapping("/userData")
    public Result<UserData> userData(@RequestParam String userId){
        UserData userData = userService.getUserData(userId);
        return Result.success(userData);
    }

    @PostMapping("/update/userInfo")
    @ResponseBody
    public Result<Boolean> updateUserInfo(HttpServletResponse response, HttpServletRequest request,@RequestBody User user) {
        // 更新之前，需要将从前端传过来的图片信息，上传到腾讯云上去，然后存入数据库的话是一个链接
        // 需要更新的是用户昵称，用户头像，用户性别，用户学校，用户的个性签名
        // 重新设置Cookie，即更新Redis中User的信息
        String salt = RandomUtils.randomSalt();
        user.setSalt(salt);
        user.setPassword(DigestUtils.md5Hex((user.getPassword() + salt)));
        userService.updateByUserId(user);
        loginController.addCookie(response, request, user);
        return Result.success(true);
    }

    /**
     * 而且在postman测试工具的时候，必须要不仅选择文件，还要在key中写multipartFile, 不然会报错空指针异常
     *
     * @param file
     * @return
     */
    @PostMapping("/upload/images")
    @ResponseBody
    public Result<String> uploadImages(MultipartFile file) {
        if (file != null) {
            if (file.getSize() > 1024 * 1024 * 4) {
                return new Result<>("文件大小不能大于4M");
            }
            String suffix = file.getOriginalFilename().
                    substring(file.getOriginalFilename().lastIndexOf(".") + 1,
                            file.getOriginalFilename().length());
            if (!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())) {
                return new Result<>("请选择jpg,jpeg,gif,png格式的图片");
            }
            String url = userService.uploadImages(file);
            HashMap<String, String> stringStringHashMap = new HashMap<>();
            stringStringHashMap.put("url", url);
            if (url != null && !"".equals(url)) {
                return new Result(stringStringHashMap);
            } else {
                return new Result<>("上传失败");
            }
        } else {
            return new Result<>("图片不能为空");
        }

    }



}
