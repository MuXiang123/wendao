package com.example.wendao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/3 17:44
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String userId;
    private String password;
}
