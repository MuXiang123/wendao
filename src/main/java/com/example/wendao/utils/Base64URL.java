package com.example.wendao.utils;
import java.util.Base64;
/**
 * @author: zhk
 * @description:
 * @date: 2023/4/30 14:45
 * @version: 1.0
 */
public class Base64URL {
    public static byte[] base64EncodeUrl(byte[] input) {
        byte[] base64 = Base64.getEncoder().encode(input);
        for (int i = 0; i < base64.length; ++i)
            switch (base64[i]) {
                case '+':
                    base64[i] = '*';
                    break;
                case '/':
                    base64[i] = '-';
                    break;
                case '=':
                    base64[i] = '_';
                    break;
                default:
                    break;
            }
        return base64;
    }
}