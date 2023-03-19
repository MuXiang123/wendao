package com.example.wendao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/18 15:35
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    private String userId;
    private String password;
    private String nickName;
}