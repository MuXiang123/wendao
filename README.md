# 项目简介

2023年毕设、校园论坛、网上社区这一类的项目。

**前端: **

### 技术栈

- 后端： SpringBoot SpringMVC Mybatis Redis ElasticSearch Quartz
- 前端： vue Element-UI Axios

### 主要功能

本系统主要包括用户模块、文章模块、通信模块、视频模块、搜索功能等模块。其中，用户模块主要负责用户的注册、登录、信息修改等操作；文章管理模块主要负责问题的发布、浏览、回答等操作；私信管理模块主要负责私信的发送、接收等操作；视频管理模块主要负责视频的浏览等操作；搜索管理模块主要负责问题的搜索和答案的搜索等操作。

### 系统功能架构图

![image-20230618141102891](D:\JavaProject\wendao\README.assets\image-20230618141102891.png)



# 如何部署

## 环境配置

- JDK 1.8
- Elasticsearch-7.0.0
- Redis （本地开发下载的是windows版本）自己用的是3.0.x版本
- MySQL 8.0及以上
- Nginx （我使用的版本是1.22.1）

### 部署步骤

1. 导入数据库，数据库名为``wendao``
2. 修改application-dev.yml中的内容
3. 启动redis和ElasticSearch
4. 启动项目
