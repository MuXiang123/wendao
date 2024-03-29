package com.example.wendao.controller;

import com.example.wendao.async.EventModel;
import com.example.wendao.async.EventProducer;
import com.example.wendao.async.EventType;
import com.example.wendao.entity.Fans;
import com.example.wendao.entity.Follow;
import com.example.wendao.entity.User;
import com.example.wendao.redis.FansKey;
import com.example.wendao.redis.FollowKey;
import com.example.wendao.redis.JedisService;
import com.example.wendao.service.FansService;
import com.example.wendao.service.FollowService;
import com.example.wendao.service.UserService;
import com.example.wendao.utils.CodeMsg;
import com.example.wendao.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 16:44
 * @version: 1.0
 */
@RestController
@RequestMapping("/follow")
public class FollowController {
    private static final Logger log = LoggerFactory.getLogger(FollowController.class);

    @Autowired
    FollowService followService;

    @Autowired
    JedisService jedisService;

    @Autowired
    LoginController loginController;

    @Autowired
    UserService userService;

    @Autowired
    FansService fansService;

    @Autowired
    EventProducer eventProducer;

    /**
     *关注别人
     * @param followId :表示的是别人的userId，这里为了区分
     */
    @GetMapping("/add")
    @ResponseBody
    public Result<Boolean> addFollow(HttpServletRequest request, String followId) {
        User user = loginController.getUserInfo(request);
        if (user == null) {
            return Result.error(CodeMsg.ERROR);
        } else {
            // 说明用户已经登录，那么就可以拿到用户的userId,分别生成关注和粉丝的key
            // 我(userId)关注了你(followId)说明：在我followKey里面应该包含的是followId,表示我的关注者的列表
            // 在我fansKey里面应该包含的是userId,说明你(followId)的粉丝列表中有我(userId)
            // 首先生成set集合的key

            // User user = userService.selectByUserId("17643537768");
            String followRealKey = FollowKey.followKey.getPrefix() + user.getUserId();
            String fansRealKey = FansKey.fansKey.getPrefix() + followId;
            // 然后将当前用户自身的userId,保存到该set集合中去
            jedisService.sadd(followRealKey, followId);
            jedisService.sadd(fansRealKey, user.getUserId());

            // 关注成功后，要给用户发送通知，通知用户谁关注了你
            EventModel eventModel = new EventModel();
            eventModel.setActorId(user.getUserId()).setEntityId(1).setEntityType(2).setEventType(EventType.FOLLOW)
                    .setEntityOwnerId(followId);
            eventProducer.fireEvent(eventModel);
            // 添加进redis之后，然后将其同步至mysql的follow表和fans表中
            Follow follow = new Follow();
            follow.setUserId(user.getUserId());
            follow.setFollowId(followId);
            follow.setCreatedTime(new Date());
            Fans fans = new Fans();
            fans.setUserId(followId);
            fans.setFansId(user.getUserId());
            fans.setCreatedTime(new Date());
            followService.insertFollow(follow);
            fansService.insertFans(fans);

            // 给被关注者增加成就值，首先应该先根据followId查找出user,然后更新其成就值
            User followedUser = userService.selectByUserId(followId);
            followedUser.setAchieveValue(followedUser.getAchieveValue() + 10);
            userService.updateByUserId(followedUser);
            return Result.success(true);
        }
    }

    @GetMapping("/cancel")
    @ResponseBody
    public Result<Boolean> cancelFollow(HttpServletRequest request, String followId) {
        User user = loginController.getUserInfo(request);
        if (user == null) {
            return Result.error(CodeMsg.NOT_LOGIN);
        } else {

            String followRealKey = FollowKey.followKey.getPrefix() + user.getUserId();
            String fansRealKey = FansKey.fansKey.getPrefix() + followId;
            if (StringUtils.isEmpty(followRealKey) || StringUtils.isEmpty(fansRealKey)) {
                return Result.error(CodeMsg.ERROR);
            } else {
                // 首先是从redis中去删除这个key，然后在数据库去删除这个key
                jedisService.srem(followRealKey, followId);
                jedisService.srem(fansRealKey, user.getUserId());
                followService.deleteFollow(user.getUserId(), followId);
                fansService.deleteFans(user.getUserId(), followId);
                return Result.success(true);
            }
        }
    }

    /**
     * 我的关注
     *
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public Result<List<User>> followList(@RequestParam String userId) {
        String realKey = FollowKey.followKey.getPrefix() + userId;
        Set<String> set = jedisService.smembers(realKey);
        List<User> usersList = new ArrayList<>();
        if (!set.isEmpty()) {
            // 这个set里面全部存储的userId,注意是String类型,然后根据这个来查询出User的信息
            for (String str : set) {
                User u = userService.selectByUserId(str);
                usersList.add(u);
            }
            log.info("从Redis的Set集合中获取到我的关注者");
        } else {
            // 如果从Redis拿不到数据的话，就要从mysql中取数据
            List<Follow> followList = followService.selectAllFollowByUserId(userId);
            for (Follow f : followList) {
                User u = userService.selectByUserId(f.getFollowId());
                usersList.add(u);
            }
            log.info("从mysql中查询出来我的关注者");
        }
        return Result.success(usersList);
    }

    /**
     * 查询是否关注该用户
     * @param userId
     * @param followId
     * @return
     */
    @GetMapping("/isFollow")
    @ResponseBody
    public Result<Boolean> isFollow(@RequestParam String userId, @RequestParam String followId) {
        Follow follow = followService.isFollow(userId, followId);
        if(follow == null){
            return new Result(false);
        }else {
            return new Result(true);
        }
    }
}
