package com.example.wendao.controller;

import com.example.wendao.entity.User;
import com.example.wendao.service.UserService;
import com.example.wendao.utils.CodeMsg;
import com.example.wendao.utils.Result;
import com.google.gson.Gson;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/1 21:10
 * @version: 1.0
 */
@Controller
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
    public Result<User> userInfo(HttpServletRequest request) {
        User user = loginController.getUserInfo(request);
        String userId = user.getUserId();

        return Result.success(userService.selectByUserId(userId));
    }


    @PostMapping("/update/userInfo")
    @ResponseBody
    public Result<Boolean> updateUserInfo(HttpServletResponse response, @RequestBody User user) {
        // 更新之前，需要将从前端传过来的图片信息，上传到七牛云上去，然后存入数据库的话是一个链接
        // 需要更新的是用户昵称，用户头像，用户性别，用户学校，用户的个性签名
        String nickname = user.getNickname();
        // 这里应该获取的是前端传过来的url链接
        String avatar = user.getAvatar();
        int sex = user.getSex();
        String school = user.getSchool();
        String signature = user.getSignature();
        String userId = user.getUserId();

        //User u = new User();

        user.setNickname(nickname);
        user.setAvatar(avatar);
        user.setSex(sex);
        user.setSchool(school);
        user.setSignature(signature);
        user.setUserId(userId);


        // 重新设置Cookie，即更新Redis中User的信息
        loginController.addCookie(response, user);
        userService.updateByUserId(user);

        return Result.success(true);

    }

    /**
     * 而且在postman测试工具的时候，必须要不仅选择文件，还要在key中写file, 不然会报错空指针异常
     *
     * @param multipartFile
     * @return
     */
    @PostMapping("/upload/images")
    @ResponseBody
    public Result<String> uploadImages(MultipartFile multipartFile, String userId) {
        if (multipartFile != null) {
            String url = userService.uploadImages(multipartFile, userId);
            if (url != null && "".equals(url)) {
                return Result.success(url);
            } else {
                return new Result<>("上传失败");
            }
        } else {
            return new Result<>("图片不能为空");
        }

    }
}
