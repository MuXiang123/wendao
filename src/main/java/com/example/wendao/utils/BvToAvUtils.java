package com.example.wendao.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/21 20:11
 * @version: 1.0
 */
public class BvToAvUtils {
    /**
     * 码表
     */
    private static final String TABLE = "fZodR9XQDSUm21yCkr6zBqiveYah8bt4xsWpHnJE7jL5VG3guMTKNPAwcF";
    /**
     * 字符顺序码表
     */
    private static final int[] S = new int[]{11, 10, 3, 8, 4, 6};
    private static final int XOR = 177451812;
    private static final long ADD = 8728348608L;
    private static final Map<Character, Integer> MAP = new HashMap<>();

    static {
        for (int i = 0; i < 58; i++) {
            MAP.put(TABLE.charAt(i), i);
        }
    }

    /**
     * avid 转 bvid 算法
     *
     * a = (avid 异或 177451812) + 8728348608
     * 以 i 为循环变量循环 6 次 b[i] = (a / 58 ^ i) % 58
     * 将 b[i] 中各个数字转换为以下码表中的字符
     * 初始化字符串 b[i]=BV1 4 1 7
     * 按照字符顺序编码表编码并填充至 b[i]
     *
     * @param aid
     * @return
     */
    public static String aidToBvid(int aid) {
        long x = (aid ^ XOR) + ADD;
        char[] chars = new char[]{'B', 'V', '1', ' ', ' ', '4', ' ', '1', ' ', '7', ' ', ' '};
        for (int i = 0; i < 6; i++) {
            int pow = (int) Math.pow(58, i);
            long i1 = x / pow;
            int index = (int) (i1 % 58);
            chars[S[i]] = TABLE.charAt(index);
        }
        return String.valueOf(chars);
    }

    /**
     * bvid转avid算法
     *
     * @param bvid
     * @return
     */
    public static int bvidToAid(String bvid) {
        long r = 0;
        for (int i = 0; i < 6; i++) {
            r += MAP.get(bvid.charAt(S[i])) * Math.pow(58, i);
        }
        return (int) ((r - ADD) ^ XOR);
    }

    public static void main(String[] args) {
        int aidOld = 653841742;
        String bidOld = "BV1WY4y1D7JA";
        int aidNew = bvidToAid(bidOld);
        String bidNew = aidToBvid(aidOld);
        System.out.println("比较aid: " + (aidOld == aidNew));
        System.out.println("比较bid: " + bidNew.equals(bidOld));
    }
}
