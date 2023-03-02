package com.example.wendao.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;


/**
 * @author: zhk
 * @description: 6位随机验证码生成工具类
 * @date: 2023/3/1 22:01
 * @version: 1.0
 */
public class GenerateRandomCode {
    private static Logger log = LoggerFactory.getLogger(GenerateRandomCode.class);
    private static Random random = new Random();
    private static Set<Integer> usedCodes = new HashSet<Integer>();

    public static int generateRandomVerificationCode() {
        while (true) {
            // 生成6位数
            int code = random.nextInt(900000) + 100000;
            // 检查是否已经生成过
            if (!usedCodes.contains(code)) {
                // 添加到哈希表
                usedCodes.add(code);
                // 返回6位数
                log.info("你的验证码为:" + usedCodes.toString());
                return code;
            }
        }
    }

}
