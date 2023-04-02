package com.example.wendao.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/31 17:14
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    private int fansCount;
    private int followCount;
    private int likeCount;
}
