package com.example.wendao.vo;

import com.example.wendao.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<Article> articleList;
    private List<FansVo> fansVoList;
    private List<FollowVo> followVoList;
}
