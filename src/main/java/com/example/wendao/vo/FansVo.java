package com.example.wendao.vo;

import com.example.wendao.entity.Fans;
import lombok.Data;

/**
 * @author: zhk
 * @description:
 * @date: 2023/4/10 10:26
 * @version: 1.0
 */
@Data
public class FansVo extends Fans {
    private String nickname;
    private String avatar;
}
