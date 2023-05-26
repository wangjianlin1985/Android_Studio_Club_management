/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : shetuan_appdb

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2018-11-24 18:00:20
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
-- Table structure for `classinfo`
-- ----------------------------
DROP TABLE IF EXISTS `classinfo`;
CREATE TABLE `classinfo` (
  `classNo` varchar(20) NOT NULL,
  `className` varchar(20) default NULL,
  `collegeName` varchar(20) default NULL,
  `specialName` varchar(20) default NULL,
  `bornDate` datetime default NULL,
  `banzhuren` varchar(20) default NULL,
  PRIMARY KEY  (`classNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of classinfo
-- ----------------------------
INSERT INTO `classinfo` VALUES ('BJ001', '计算机3班', '信息工程学院', '计算机专业', '2018-11-06 00:00:00', '李明涛');
INSERT INTO `classinfo` VALUES ('BJ002', '计算机4班', '信息工程学院', '计算机专业', '2018-11-08 00:00:00', '王兴中');

-- ----------------------------
-- Table structure for `huodong`
-- ----------------------------
DROP TABLE IF EXISTS `huodong`;
CREATE TABLE `huodong` (
  `huodongId` int(11) NOT NULL auto_increment,
  `huodongName` varchar(80) default NULL,
  `huodongDesc` longtext,
  `huodongTime` datetime default NULL,
  `shetuanObj` varchar(20) default NULL,
  `huodongMemo` longtext,
  PRIMARY KEY  (`huodongId`),
  KEY `FKAEB2FF66FC34FBF5` (`shetuanObj`),
  CONSTRAINT `FKAEB2FF66FC34FBF5` FOREIGN KEY (`shetuanObj`) REFERENCES `shetuan` (`stUserName`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of huodong
-- ----------------------------
INSERT INTO `huodong` VALUES ('1', '下个周末一起学吉他', '本吉他社决定下周在学校音乐室举办吉他演奏课，爱好音乐的朋友们都可以来', '2018-11-30 00:00:00', 'jts', '测试');
INSERT INTO `huodong` VALUES ('3', '2018年冬季书法比赛', '为了弘扬中国博大文化，本协会将于12月初举办一次书法比赛，有兴趣的同学赶快联系我们社团报名', '2018-12-20 00:00:00', 'sfs', '测试比赛');
INSERT INTO `huodong` VALUES ('4', '书法学习课', '社团书法大神带领大家写好中国字', '2018-11-28 00:00:00', 'sfs', '测试');

-- ----------------------------
-- Table structure for `notice`
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `noticeId` int(11) NOT NULL auto_increment,
  `title` varchar(80) default NULL,
  `content` longtext,
  `publishDate` varchar(30) default NULL,
  PRIMARY KEY  (`noticeId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES ('1', '社团app正式上线', '这里有各种社团，同学们看见有兴趣的社团赶快加入吧！', '2018-11-24');
INSERT INTO `notice` VALUES ('2', '好的社团，怎么挑选社团有窍门', '同学们是不是想参加好多的社团，同学们可以想想自己的兴趣在哪里，要做有的放矢，选择自己真正感兴趣的哦！', '2018-11-25');

-- ----------------------------
-- Table structure for `shenqing`
-- ----------------------------
DROP TABLE IF EXISTS `shenqing`;
CREATE TABLE `shenqing` (
  `shenqingId` int(11) NOT NULL auto_increment,
  `shentuanObj` varchar(20) default NULL,
  `name` varchar(20) default NULL,
  `xuehao` varchar(20) default NULL,
  `zysj` longtext,
  `rhyy` longtext,
  `userObj` varchar(20) default NULL,
  `sqTime` varchar(20) default NULL,
  `shenHeState` varchar(20) default NULL,
  `shenHeResult` longtext,
  PRIMARY KEY  (`shenqingId`),
  KEY `FKDE2624AFC80FC67` (`userObj`),
  KEY `FKDE2624AF49E6B327` (`shentuanObj`),
  CONSTRAINT `FKDE2624AF49E6B327` FOREIGN KEY (`shentuanObj`) REFERENCES `shetuan` (`stUserName`),
  CONSTRAINT `FKDE2624AFC80FC67` FOREIGN KEY (`userObj`) REFERENCES `userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shenqing
-- ----------------------------
INSERT INTO `shenqing` VALUES ('1', 'jts', '王晓彤', '201834022144', '高中的时候就获取到音乐证书', '酷爱音乐', 'STU001', '2018-11-24 14:12:52', '待审核', '--');
INSERT INTO `shenqing` VALUES ('2', 'sfs', '张祥', '20183508342', '初中就开始学习书法，高中参加书法比赛获得一等奖', '喜欢中国书法文化', 'STU001', '2018-11-24 16:31:02', '待面试', '你周末来面试吧');

-- ----------------------------
-- Table structure for `shetuan`
-- ----------------------------
DROP TABLE IF EXISTS `shetuan`;
CREATE TABLE `shetuan` (
  `stUserName` varchar(20) NOT NULL,
  `password` varchar(20) default NULL,
  `shetuanName` varchar(20) default NULL,
  `shetuanPhoto` varchar(50) default NULL,
  `shetuanDesc` longtext,
  `bornDate` datetime default NULL,
  `fuzeren` varchar(20) default NULL,
  `telephone` varchar(20) default NULL,
  `shetuanMemo` longtext,
  PRIMARY KEY  (`stUserName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shetuan
-- ----------------------------
INSERT INTO `shetuan` VALUES ('jts', '123', '吉他社', 'upload/C3C9D275A2A3E27FCDCC7ACA5C35574E.jpg', '大家一起来玩吉他，玩音乐', '2018-11-06 00:00:00', '李丽静', '15920083043', '我们都是喜欢音乐的人');
INSERT INTO `shetuan` VALUES ('sfs', '123', '书法社', 'upload/5FF999680BB7C3FEEE274A5C030B603A.jpg', '爱好中国书法的朋友们，来这里展现你的书法才能，也可以一起学习书法', '2018-11-08 00:00:00', '王志勇', '13985083040', '大家一起学书法');

-- ----------------------------
-- Table structure for `userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `user_name` varchar(20) NOT NULL,
  `password` varchar(30) default NULL,
  `classObj` varchar(20) default NULL,
  `name` varchar(20) default NULL,
  `gender` varchar(4) default NULL,
  `birthDate` datetime default NULL,
  `userPhoto` varchar(50) default NULL,
  `telephone` varchar(20) default NULL,
  `email` varchar(50) default NULL,
  `address` varchar(80) default NULL,
  `regTime` varchar(30) default NULL,
  PRIMARY KEY  (`user_name`),
  KEY `FKF3F34B39A91D8B03` (`classObj`),
  CONSTRAINT `FKF3F34B39A91D8B03` FOREIGN KEY (`classObj`) REFERENCES `classinfo` (`classNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('STU001', '123', 'BJ001', '张晓芬', '女', '2018-11-06 00:00:00', 'upload/4FB3A2857608897903CF9AE5F1BAD053.jpg', '13508340853', 'xiaofen@163.com', '四川成都红星路13号', '2018-11-24 15:32:25');
INSERT INTO `userinfo` VALUES ('STU002', '123', 'BJ002', '黄小倩', '女', '2018-11-02 00:00:00', 'upload/614BCBF67E3ECD40331A4B29BC6988E5.jpg', '13085080342', 'xiaoqian@126.com', '四川南充滨江路aa', '2018-11-24 16:44:57');
