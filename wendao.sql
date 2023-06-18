/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : localhost:3306
 Source Schema         : wendao

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 18/06/2023 17:19:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `article_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `article_title` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '文章标题',
  `article_summary` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '文章摘要',
  `article_content` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '文章内容',
  `article_view_count` int(0) NULL DEFAULT NULL COMMENT '文章浏览量',
  `article_like_count` int(0) NULL DEFAULT NULL COMMENT '文章点赞量',
  `article_comment_count` int(0) NULL DEFAULT NULL COMMENT '文章评论数量',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NULL DEFAULT NULL COMMENT '逻辑删除0表示未删除，1表示删除',
  `article_category_id` int(0) NULL DEFAULT NULL COMMENT '外键，对应category_id',
  `article_user_id` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '外键，对应user_id',
  `article_category_name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '分类表中对应category_name',
  PRIMARY KEY (`article_id`) USING BTREE,
  INDEX `article_category_id`(`article_category_id`) USING BTREE,
  INDEX `article_user_id`(`article_user_id`) USING BTREE,
  CONSTRAINT `article_ibfk_1` FOREIGN KEY (`article_category_id`) REFERENCES `category` (`category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `article_ibfk_2` FOREIGN KEY (`article_user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `category_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键，分类表id',
  `category_name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '分类名',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '考研交流');
INSERT INTO `category` VALUES (2, '找工作交流');
INSERT INTO `category` VALUES (3, '日常学习');
INSERT INTO `category` VALUES (4, '寻物启事');
INSERT INTO `category` VALUES (5, '拼单拼车');
INSERT INTO `category` VALUES (6, '表白墙');
INSERT INTO `category` VALUES (7, '生活趣事');
INSERT INTO `category` VALUES (8, '竞赛组队');
INSERT INTO `category` VALUES (9, '编程技术');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `comment_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键，评论主键',
  `comment_article_id` int(0) NOT NULL COMMENT '外键，对应article_id',
  `comment_user_id` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '外键，对应user_id',
  `comment_content` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '评论内容',
  `comment_like_count` int(0) NULL DEFAULT NULL COMMENT '评论点赞数',
  `comment_count` int(0) NULL DEFAULT NULL COMMENT '评论的评论数',
  `comment_created_time` datetime(0) NULL DEFAULT NULL COMMENT '评论的创建时间',
  `parent_comment_id` int(0) NULL DEFAULT NULL COMMENT '父评论id',
  PRIMARY KEY (`comment_id`) USING BTREE,
  INDEX `comment_article_id`(`comment_article_id`) USING BTREE,
  INDEX `comment_user_id`(`comment_user_id`) USING BTREE,
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`comment_article_id`) REFERENCES `article` (`article_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`comment_user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for fans
-- ----------------------------
DROP TABLE IF EXISTS `fans`;
CREATE TABLE `fans`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键，粉丝表id',
  `user_id` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `fans_id` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '粉丝id',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for follow
-- ----------------------------
DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键，关注表id',
  `user_id` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `follow_id` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '关注的id',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

- ----------------------------
-- Table structure for like
-- ----------------------------
DROP TABLE IF EXISTS `like`;
CREATE TABLE `like`  (
  `like_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户id',
  `article_id` int(0) NOT NULL COMMENT '文章id',
  PRIMARY KEY (`like_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '通知表id',
  `from_id` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '发送方id',
  `to_id` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '接收方id',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '内容',
  `created_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `has_read` bigint(0) NULL DEFAULT NULL COMMENT '是否已读，1表示已读，0表示未读',
  `conversation_id` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '对话id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 221 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '主键，手机号',
  `nickname` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '昵称',
  `password` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '密码',
  `salt` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '加强密码',
  `avatar` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '头像',
  `achieve_value` int(0) NULL DEFAULT NULL COMMENT '成就值',
  `school` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '学校',
  `login_ip` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '登录ip地址',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '登录时间',
  `login_type` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '登录类型',
  `sex` int(0) NULL DEFAULT NULL COMMENT '用户性别',
  `signature` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '个性签名',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for video_category
-- ----------------------------
DROP TABLE IF EXISTS `video_category`;
CREATE TABLE `video_category`  (
  `tid` int(0) NOT NULL COMMENT '分区id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分区名称',
  `profile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '简介',
  `parent_id` int(0) NULL DEFAULT NULL COMMENT '父标签id',
  PRIMARY KEY (`tid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of video_category
-- ----------------------------
INSERT INTO `video_category` VALUES (21, '日常', '记录日常生活，分享生活故事', 160);
INSERT INTO `video_category` VALUES (36, '知识', '知识主分区', -1);
INSERT INTO `video_category` VALUES (37, '人文·历史', '除宣传片、影视剪辑外的，人文艺术历史纪录剧集或电影、预告、花絮、二创、5分钟以上纪录短片', 177);
INSERT INTO `video_category` VALUES (95, '数码', '科技数码产品大全，一起来做发烧友', 188);
INSERT INTO `video_category` VALUES (122, '野生技术协会', '技能党集合，是时候展示真正的技术了', 36);
INSERT INTO `video_category` VALUES (124, '社科·法律·心理', '基于社会科学、法学、心理学展开或个人观点输出的知识视频', 36);
INSERT INTO `video_category` VALUES (138, '搞笑', '各种沙雕有趣的搞笑剪辑，挑战，表演，配音等视频	', 160);
INSERT INTO `video_category` VALUES (160, '生活', '生活主分区', -1);
INSERT INTO `video_category` VALUES (161, '手工', '	手工制品的制作过程或成品展示、教程、测评类视频', -1);
INSERT INTO `video_category` VALUES (164, '健身', '	与健身相关的视频，包括但不限于瑜伽、crossfit、健美、力量举、普拉提、街健等相关内容', 234);
INSERT INTO `video_category` VALUES (177, '纪录片', '纪录片主分区', -1);
INSERT INTO `video_category` VALUES (178, '科学·探索·自然', '除演讲、网课、教程外的，科学探索自然纪录剧集或电影、预告、花絮、二创、5分钟以上纪录短片', 177);
INSERT INTO `video_category` VALUES (188, '科技', '科技主分区', -1);
INSERT INTO `video_category` VALUES (201, '科学科普', '回答你的十万个为什么', 188);
INSERT INTO `video_category` VALUES (202, '资讯', '资讯主分区', -1);
INSERT INTO `video_category` VALUES (203, '热点', '全民关注的时政热门资讯', 202);
INSERT INTO `video_category` VALUES (204, '环球', '全球范围内发生的具有重大影响力的事件动态', 202);
INSERT INTO `video_category` VALUES (207, '财经商业', '说金融市场，谈宏观经济，一起畅聊商业故事', 36);
INSERT INTO `video_category` VALUES (208, '校园学习', '老师很有趣，学生也有才，我们一起搞学习', 36);
INSERT INTO `video_category` VALUES (209, '职业职场', '职业分享、升级指南，一起成为最有料的职场人', 36);
INSERT INTO `video_category` VALUES (228, '人文历史', '看看古今人物，聊聊历史过往，品品文学典籍', 36);
INSERT INTO `video_category` VALUES (229, '设计·创意', '天马行空，创意设计，都在这里', 36);
INSERT INTO `video_category` VALUES (230, '软件应用', '超全软件应用指南', 188);
INSERT INTO `video_category` VALUES (231, '计算机技术', '研究分析、教学演示、经验分享......有关计算机技术的都在这里', 188);
INSERT INTO `video_category` VALUES (232, '科工机械', '前方高能，机甲重工即将出没', 188);
INSERT INTO `video_category` VALUES (234, '运动', '运动主分区', -1);
INSERT INTO `video_category` VALUES (235, '篮球', '与篮球相关的视频，包括但不限于篮球赛事、教学、评述、剪辑、剧情等相关内容', 234);
INSERT INTO `video_category` VALUES (249, '足球', '与足球相关的视频，包括但不限于足球赛事、教学、评述、剪辑、剧情等相关内容', 234);
INSERT INTO `video_category` VALUES (250, '出行', '为达到观光游览、休闲娱乐为目的的远途旅行、中近途户外生活、本地探店	', 160);

SET FOREIGN_KEY_CHECKS = 1;
