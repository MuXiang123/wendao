package com.example.wendao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author: zhk
 * @description: 文章实体
 * @date: 2023/3/1 15:57
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "wego", type = "_doc", createIndex = false)
public class Article {

    /**
     * 文章id
     */
    @Id
    @Field(type = FieldType.Integer, name = "article_id")
    private int articleId;

    /**
     * 文章标题
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", name = "article_title", searchAnalyzer = "ik_max_word")
    private String articleTitle;

    /**
     * 文章摘要
     */
    @Field(type = FieldType.Text, name = "article_summary")
    private String articleSummary;

    /**
     * 文章内容
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", name = "article_content", searchAnalyzer = "ik_max_word")
    private String articleContent;

    /**
     * 文章浏览量
     */
    @Field(type = FieldType.Integer, name = "article_view_count")
    private int articleViewCount;

    /**
     * 文章点赞数
     */
    @Field(type = FieldType.Integer, name = "article_like_count")
    private int articleLikeCount;

    /**
     * 文章评论数
     */
    @Field(type = FieldType.Integer, name = "article_comment_count")
    private int articleCommentCount;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Field(type = FieldType.Date, name = "created_time", format = DateFormat.date_optional_time)
    private Date createdTime;

    /**
     * 更新时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Field(type = FieldType.Date, name = "update_time", format = DateFormat.date_optional_time)
    private Date updateTime;

    /**
     * isDelete表示两种状态，0表示未删除，1表示已删除
     */
    @Field(type = FieldType.Integer, name = "is_deleted")
    private int isDeleted;

    /**
     * 外键 对应category——id
     */
    @Field(type = FieldType.Integer, name = "article_category_id")
    private int articleCategoryId;

    /**
     * 外键，对应user——id
     */
    @Field(type = FieldType.Text, name = "article_category_name")
    private String articleCategoryName;

    /**
     * 分类表中的category——name
     */
    @Field(type = FieldType.Text, name = "article_user_id")
    private String articleUserId;
}

