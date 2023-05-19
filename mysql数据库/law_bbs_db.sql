/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : law_bbs_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2019-04-03 02:07:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `article`
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `articleId` int(11) NOT NULL auto_increment,
  `title` varchar(20) default NULL,
  `articleClassObj` int(11) default NULL,
  `content` longtext,
  `hitNum` int(11) default NULL,
  `userObj` varchar(20) default NULL,
  `addTime` varchar(20) default NULL,
  PRIMARY KEY  (`articleId`),
  KEY `FK379164D6BBBA1899` (`articleClassObj`),
  KEY `FK379164D6C80FC67` (`userObj`),
  CONSTRAINT `FK379164D6BBBA1899` FOREIGN KEY (`articleClassObj`) REFERENCES `articleclass` (`articleClassId`),
  CONSTRAINT `FK379164D6C80FC67` FOREIGN KEY (`userObj`) REFERENCES `userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES ('1', 'aa', '1', 'bbb', '3', 'user1', '2019-03-29 13:25:52');
INSERT INTO `article` VALUES ('2', '11', '1', '22', '3', 'user2', '2019-04-02 00:17:17');
INSERT INTO `article` VALUES ('3', '今天吃牛排了', '2', '今天生意还可以，心情好去吃好的了！', '2', 'user1', '2019-04-09 15:20:08');

-- ----------------------------
-- Table structure for `articleclass`
-- ----------------------------
DROP TABLE IF EXISTS `articleclass`;
CREATE TABLE `articleclass` (
  `articleClassId` int(11) NOT NULL auto_increment,
  `articleClassName` varchar(20) default NULL,
  PRIMARY KEY  (`articleClassId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of articleclass
-- ----------------------------
INSERT INTO `articleclass` VALUES ('1', '心情随笔');
INSERT INTO `articleclass` VALUES ('2', '趣闻趣事');

-- ----------------------------
-- Table structure for `flzs`
-- ----------------------------
DROP TABLE IF EXISTS `flzs`;
CREATE TABLE `flzs` (
  `zsId` int(11) NOT NULL auto_increment,
  `title` varchar(50) default NULL,
  `zsPhoto` varchar(50) default NULL,
  `zsDesc` longtext,
  `author` varchar(20) default NULL,
  `publish` varchar(30) default NULL,
  `publishDate` datetime default NULL,
  `viewNum` int(11) default NULL,
  `zsFile` varchar(50) default NULL,
  PRIMARY KEY  (`zsId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of flzs
-- ----------------------------
INSERT INTO `flzs` VALUES ('1', '医疗事故诉讼流程详解', 'upload/1D1ECD3F747722D343FED33B28ED19D8.jpg', '医疗事故是每个病人或者家属都不愿意发生的事，但是手术毕竟有一定的风险。如果出现医疗事故之后很容易医患很容易发生纠纷，这时就要进行相关的诉讼来解决。那么，医疗事故诉讼流程详解是什么内容?华律网小编整理了以下内容为您解答，希望对您有所帮助。', '王涛', '人民教育出版社', '2019-04-02 00:00:00', '24', 'upload/23EAC3117187AA3E40EB62240B655A22.doc');
INSERT INTO `flzs` VALUES ('2', '婚前财产后老伴能继承吗', 'upload/7E64FE26B07D080976EC9C2E7367F97F.jpg', '在继承遗产方面如果有遗嘱的话，一般是按照遗嘱来进行继承的，如果没有艺术应当按照法定继承的顺序来进行继承，那么如果是婚前财产之后的老伴儿是否能够继承呢？下面，为了帮助大家更好的了解相关法律知识，华律网小编整理了以下的内容，希望对您有所帮助。', '张夏婷', '北京大学出版社', '2019-04-09 00:00:00', '14', 'upload/9418F82CA90BCA6CE7775A2978C1A789.doc');

-- ----------------------------
-- Table structure for `news`
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `newsId` int(11) NOT NULL auto_increment,
  `title` varchar(80) default NULL,
  `content` longtext,
  `viewNum` int(11) default NULL,
  `publishDate` varchar(30) default NULL,
  PRIMARY KEY  (`newsId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES ('1', '111111', '2222', '1', '2019-03-29 15:13:42');

-- ----------------------------
-- Table structure for `postinfo`
-- ----------------------------
DROP TABLE IF EXISTS `postinfo`;
CREATE TABLE `postinfo` (
  `postInfoId` int(11) NOT NULL auto_increment,
  `title` varchar(80) default NULL,
  `content` longtext,
  `hitNum` int(11) default NULL,
  `userObj` varchar(20) default NULL,
  `addTime` varchar(30) default NULL,
  PRIMARY KEY  (`postInfoId`),
  KEY `FK30F4858EC80FC67` (`userObj`),
  CONSTRAINT `FK30F4858EC80FC67` FOREIGN KEY (`userObj`) REFERENCES `userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of postinfo
-- ----------------------------
INSERT INTO `postinfo` VALUES ('1', '老百姓的涉诉涉法信访问题就没人管了吗？', '涉诉涉法信访问题归法律解决是明摆骗老百姓的把戏。', '38', 'user1', '2019-03-29 14:15:56');
INSERT INTO `postinfo` VALUES ('2', '大家好', '初来乍到，大家多多关照！', '16', 'user2', '2019-04-01 00:51:54');
INSERT INTO `postinfo` VALUES ('3', '老百姓要依法维权可是路已被封死怎么办？', '几年前进京上访，可以到全国人大，最高法等进行合法诉求，自从规定涉诉涉法问题归法律解决之后，在也没人管了。因一些老上访案件大部是少则几年到几十年的案件，目前我国实行的法律规定还是停在过去一点没动，例如：不服一审上访时效为15天，不服二审进行申诉时效是6个月，检察抗诉时效为2年，超过了法律规定的时效人民法院不在受理，这不明摆着的吗？先把维权路已封死了，然后在去号召老百姓要依法维权？这不玩人呢吗？', '4', 'user1', '2019-04-09 15:17:20');

-- ----------------------------
-- Table structure for `reply`
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply` (
  `replyId` int(11) NOT NULL auto_increment,
  `postInfoObj` int(11) default NULL,
  `content` longtext,
  `userObj` varchar(20) default NULL,
  `replyTime` varchar(30) default NULL,
  PRIMARY KEY  (`replyId`),
  KEY `FK4B322CAC80FC67` (`userObj`),
  KEY `FK4B322CA82E86259` (`postInfoObj`),
  CONSTRAINT `FK4B322CA82E86259` FOREIGN KEY (`postInfoObj`) REFERENCES `postinfo` (`postInfoId`),
  CONSTRAINT `FK4B322CAC80FC67` FOREIGN KEY (`userObj`) REFERENCES `userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reply
-- ----------------------------
INSERT INTO `reply` VALUES ('1', '1', '城市套路深，俺要回农村', 'user2', '2019-03-29 18:52:47');
INSERT INTO `reply` VALUES ('2', '1', '太吓人了！', 'user2', '2019-03-29 18:52:47');
INSERT INTO `reply` VALUES ('3', '1', '22', 'user1', '2019-03-29 18:52:48');
INSERT INTO `reply` VALUES ('4', '1', '3333', 'user1', '2019-03-29 18:52:50');
INSERT INTO `reply` VALUES ('5', '2', '11', 'user1', '2019-04-01 23:55:50');
INSERT INTO `reply` VALUES ('6', '2', 'very good!', 'user1', '2019-04-01 23:56:04');
INSERT INTO `reply` VALUES ('7', '2', '支持你 新人', 'user1', '2019-04-09 15:16:52');

-- ----------------------------
-- Table structure for `userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `user_name` varchar(20) NOT NULL,
  `password` varchar(30) default NULL,
  `name` varchar(20) default NULL,
  `gender` varchar(4) default NULL,
  `birthDate` datetime default NULL,
  `userPhoto` varchar(50) default NULL,
  `telephone` varchar(20) default NULL,
  `email` varchar(50) default NULL,
  `address` varchar(80) default NULL,
  `regTime` varchar(30) default NULL,
  PRIMARY KEY  (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('user1', '123', '双鱼林', '男', '2019-03-01 00:00:00', 'upload/810878EDC6EAFC18E192E63D14DDB182.jpg', '13573598343', 'syl@163.com', '四川成都红星路13号', '2019-03-29 14:15:15');
INSERT INTO `userinfo` VALUES ('user2', '123', '李明生', '男', '2019-03-30 00:00:00', 'upload/F482558D03BE6C633158FCDE5F6FFAA8.jpg', '13459830843', 'limings@163.com', '福建福州', '2019-04-01 00:19:48');
