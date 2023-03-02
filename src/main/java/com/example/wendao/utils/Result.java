package com.example.wendao.utils;

import lombok.Data;

/**
 * @author: zhk
 * @description: 统一返回结果
 * @date: 2023/3/2 9:05
 * @version: 1.0
 */
@Data
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    public Result(CodeMsg cm) {
        if (null == cm) {
            return;
        }
        this.code = cm.getCode();
        this.msg = cm.getMsg();
    }


    public static <T> Result<T> success(T data) {
        return new Result<T>(data);
    }

    public static <T> Result<T> error(CodeMsg cm) {
        return new Result<T>(cm);
    }

}
