# Android_Studio_Legal_Forum
安卓Android法律论坛设计可以导入Studio毕业源码案例设计

1、登录，注册；

2、个人中心模块：显示且可以编辑自己的头像和个人信息，显示我关注的人和我的粉丝，设置模块（新消息通知，密码管理，通用设置，隐私）；

3、首页模块（由上及下）：顶部有banner滚动，显示自己头像和个性签名，论坛主体（显示发帖人的姓名，发帖时间，查看数量和回复数量），发布新帖（带有可以设置隐私权限的功能）
*论坛主体可以参考“法硕联盟”网页论坛

4、知识库模块：可以自行选择一门法律进行学习，类似电子书式，但是可以标记重点，可以在旁边记录笔记

5、自主测试模块：选择一门法律试题，类似“驾考宝典”那样子以选择题方式出题

## 实体ER属性：
用户: 用户名,登录密码,姓名,性别,出生日期,用户照片,联系电话,邮箱,家庭地址,注册时间

新闻: 新闻id,标题,新闻内容,浏览量,发布时间

帖子: 帖子id,帖子标题,帖子内容,浏览量,发帖人,发帖时间

帖子回复: 回复id,被回帖子,回复内容,回复人,回复时间

法律知识: 记录id,知识标题,知识图片,知识简介,作者,出版社,出版日期,阅读量,知识文件

文章: 文章id,标题,文章分类,内容,点击率,发布用户,发布时间

文章分类: 文章分类id,文章分类名称

## 开发环境: Myclipse/Eclipse/Idea(服务器端) + Eclipse/Android Studio(手机客户端) + mysql数据库
## 系统客户端和服务器端架构技术: 界面层，业务逻辑层，数据层3层分离技术，MVC设计思想！
## 服务器和客户端数据通信格式： XML格式(用于传输查询的记录集)和json格式(用于传输单个的对象信息)
