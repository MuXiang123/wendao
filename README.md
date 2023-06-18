# 目录

[TOC]



# 项目简介

2023年毕设、校园论坛、网上社区、仿牛客论坛这一类的项目。

**前端: **

### 技术栈

- 后端： SpringBoot SpringMVC Mybatis Redis ElasticSearch Quartz
- 前端： vue Element-UI Axios

### 主要功能

本系统主要包括用户模块、文章模块、通信模块、视频模块、搜索功能等模块。其中，用户模块主要负责用户的注册、登录、信息修改等操作；文章管理模块主要负责问题的发布、浏览、回答等操作；私信管理模块主要负责私信的发送、接收等操作；视频管理模块主要负责视频的浏览等操作；搜索管理模块主要负责问题的搜索和答案的搜索等操作。

### 系统功能架构图

![image-20230618141102891](https://github.com/MuXiang123/wendao/blob/master/README.assets/image-20230618141102891.png)



# 如何部署

## 环境配置

- JDK 1.8
- Elasticsearch-7.0.0
- Redis （本地开发下载的是windows版本）自己用的是5.0.x版本
- MySQL 8.0及以上
- Nginx （我使用的版本是1.22.1）

### 部署步骤

1. 导入数据库，数据库名为``wendao``
2. 修改application-dev.yml中的内容
3. 启动redis和ElasticSearch
4. 启动项目

# 中间件安装

### redis

在windows系统下安装redis  

redis下载链接 ：[Releases · tporadowski/redis (github.com)](https://github.com/tporadowski/redis/releases)

如果不会安装的，可以参考[Window下Redis的安装和部署详细图文教程（Redis的安装和可视化工具的使用）_redis安装_明金同学的博客-CSDN博客](https://blog.csdn.net/weixin_44893902/article/details/123087435)

### ElasticSearch

下载地址：[Releases · elastic/elasticsearch (github.com)](https://github.com/elastic/elasticsearch/releases) 找到自己合适的版本下载，本项目使用的是7.0.0

并且也要安装ik中文分词器，主要ik分词器的版本和es的版本要对应。readme文件中有讲：[medcl/elasticsearch-analysis-ik: The IK Analysis plugin integrates Lucene IK analyzer into elasticsearch, support customized dictionary. (github.com)](https://github.com/medcl/elasticsearch-analysis-ik)

ik分词器下载地址：[Releases · medcl/elasticsearch-analysis-ik (github.com)](https://github.com/medcl/elasticsearch-analysis-ik/releases)

不会安装可以百度教程。

# 配置文件中的问题

### b站的cookie 

该项目中的视频模块，是通过实时获取b站的视频直链和feed链接得到视频的。可能有些链接需要省份验证，因此需要登录b站 -> f12 -> application

获取SESSDATA填入yml中。

![image-20230618143345645](https://github.com/MuXiang123/wendao/blob/master/README.assets/image-20230618143345645.png) 

### 开通腾讯云对象存储

[对象存储数据处理_COS数据处理_数据处理方案-腾讯云 (tencent.com)](https://cloud.tencent.com/product/cos)

对象存储用来存储项目中出现的图片。腾讯云对象存储有免费额度。

首先按照腾讯云官方文档开通对象存储服务

[对象存储简介_对象存储购买指南_对象存储操作指南-腾讯云 (tencent.com)](https://cloud.tencent.com/document/product/436)

接着在控制台->访问密钥 中将SecretId 和 SecretKey 填到yml中。

### 开通腾讯云IM

使用腾讯云即时通讯作为本系统聊天模块的功能实现，可以使用IM体验版。

[即时通信_免费即时通信_即时通信价格 - 腾讯云 (tencent.com)](https://cloud.tencent.com/product/im)

首先开通腾讯云IM 参考官方文档[即时通信 IM Web-含 UI 集成方案（荐）-文档中心-腾讯云 (tencent.com)](https://cloud.tencent.com/document/product/269/79737)

本项目直接在前端项目中使用腾讯云，参考文档[即时通信 IM Web-含 UI 集成方案（荐）-文档中心-腾讯云 (tencent.com)](https://cloud.tencent.com/document/product/269/79737)

### 数据库账户名密码

记得填自己本机的MySQL用户名密码。



# 项目部分功能设计思路

### 1 查看热点文章实现思路

热点文章设计为：根据文章的浏览量降序排列，取TOP10进行展示

### 2 上传图片（使用七牛云服务器存储图片）实现思路

上传用户头像、修改用户头像操作将图片上传至腾讯云服务器上，自己上传了一张照片，返回的在线地址，这样就减少数据库存储图片带来的性能开销。

### 3 关注/取消关注别人，关注/粉丝列表实现思路

基于Redis的Set数据结构实现，原因是该结构类似于Java中的Hashset，可以实现去重功能，用sadd、scard来实现即可.

### 4 用户成就值排行榜实现思路

成就值加分规则：

- 当用户发表一篇文章的时候，成就值+10分
- 当用户的文章被别人点赞一次之后，成就值+5分
- 当用户的文章被别人评论一次之后，成就值+5分
- 当用户被一个人关注后，成就值+10分
  实现思路：使用Quartz定时器框架，每一个月将用户的成就值清零，在这一个月之内根据用户的成就值降序排列，取成就值TOP10.

# 项目重难点功能设计思路

### 1 异步通知功能设计

一般我们在购物或者博客的网站上都会收到系统推送的通知消息，而对应于校园论坛网站的开发中，我自己设计为当用户A对用户B发表的文章点赞、评论、以及用户A关注了用户B，都用到了异步通知的设计。 异步通知和同步通知(以点赞业务为例)的区别是：

- 同步通知，点赞之后必须等到该操作执行完所有操作（更新点赞数等业务），才能执行后面的代码；
- 异步通知，点赞之后启动一个新的线程去处理异步通知的业务逻辑，主线程执行后面的代码，互不影响，提升用户体验；
  ![image-20230618154257092](https://github.com/MuXiang123/wendao/blob/master/README.assets/image-20230618154257092.png)
  如上图所示，异步通知的设计思路如下，分为业务代码、生产者、消费者、消息队列、统一的接口以及实现类，即：业务代码（如点赞业务）首先将点赞的事件封装成一个eventModel对象，然后将其传给生产者，生产者将该事件推到Redis的list的消息队列中，然后消费者监听有哪些实现类实现了统一的接口，并将其保存至Map中，然后开启一个新的线程不断的从消息队列中消费事件，如果有事件则根据事件的类型（点赞/评论/我的关注），去执行对应的实现类。在实现类中的实现是将事件的具体的内容（如用户A点赞了用户B的文章）保存至数据库中，并且设置有一个属性为has_read属性，默认为0（表示有未读消息），然后前端的实现是启用3秒的定时器轮询的调用接口（这个接口的作用是给前端返回有没有未读数据），如果有未读消息，就会给用户提示，当用户查看了提示的内容之后，将所有通知的has_read属性设置为1（表示没有未读消息)，这样即实现了异步通知的功能。

### 2 ELK（ElasticSearch+Logstash+Kibana）同步MySQL以及ES搜索和MySQL模糊查询的搜索耗时对比

项目中考虑到文章的数据量只会越来越大，由于在大数据量的情况下，使用mysql的模糊查询的耗时十分巨大，如果给mysql中添加索引这又会带来新的开销，为此采用ES来搜索文章。首先则需要将MySQL的数据和ES的数据进行同步，采用Lostash工具就可以实现同步数据，kibana的作用是提供一个可视化的界面，方便开发人员搜索数据。同步完成之后，需要只需要集成Spring-data-elasticsearch,根据其api接口就可以实现按照文章标题和文章内容模糊查询，并实现了将关键字高亮展示（高亮展示是由前端来完成的，具体的思路为：前端通过搜索框拿到用户搜索的关键词，然后使用正则表达式匹配后端返回数据中的关键字，匹配之后将其样式改为红色标亮展示）。 

![image-20230618153725342](https://github.com/MuXiang123/wendao/blob/master/README.assets/image-20230618153725342.png)

在指导老师的督促下，要拿数据说话为什么在大数量的情况下ES的搜索耗时要低于MySQL的搜索耗时？为此，自己写程序随机生成了100、1000、10000、100000、600000条数据来进行对比，对比结果如上图所示，得出结论：在数据量大于100000的时候，ES的搜索耗时已经优于MySQL了，数据量越大时，差距更加明显。

### 3 点赞在高并发环境下的设计思路

- 业务场景：在高并发环境下用户频繁的给某一篇文章点赞，而点赞之后需要将点赞数更新至数据库中，这样就可能严重影响数据库的性能，甚至会导致数据的宕机。
- 设计思路：将文章的点赞信息全部缓存在redis的set类型的数据结构中，读的时候直接从redis中返回点赞数据，然后使用Quartz每隔两个小时将Redis的数据同步至MySQL中，保证Redis和MySQL的数据一致性。

### 4 私信实现

​	使用腾讯云IM实现

# 项目部分截图

![image-20230618145555356](https://github.com/MuXiang123/wendao/blob/master/README.assets/image-20230618145555356.png)

![image-20230618145547696](https://github.com/MuXiang123/wendao/blob/master/README.assets/image-20230618145547696.png)

![image-20230618145607492](https://github.com/MuXiang123/wendao/blob/master/README.assets/image-20230618145607492.png)

![image-20230618145620718](https://github.com/MuXiang123/wendao/blob/master/README.assets/image-20230618145620718.png)

![image-20230618145630355](https://github.com/MuXiang123/wendao/blob/master/README.assets/image-20230618145630355.png)

![image-20230618145636746](https://github.com/MuXiang123/wendao/blob/master/README.assets/image-20230618145636746.png)
