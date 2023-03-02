package com.example.wendao.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author: zhk
 * @description: 通用工具类
 * @date: 2023/3/2 9:10
 * @version: 1.0
 */
public class CommonUtils {
    /**
     * 将Github中的日期转为Date类型的日期
     *
     * @param createTime
     * @return
     */
    public static Date githubDateToDate(String createTime) {
        char[] ch = createTime.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] >= 'A' && ch[i] <= 'Z') {
                ch[i] = ' ';
            }
        }
        String value = String.valueOf(ch);
        Date createDate = null;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            createDate = sf.parse(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createDate;
    }

    /**
     * 生成随机的uuid值，用做token
     */

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
