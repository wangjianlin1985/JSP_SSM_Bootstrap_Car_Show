/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : chezhan_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2018-07-17 13:43:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL default '',
  `password` varchar(32) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `t_infotype`
-- ----------------------------
DROP TABLE IF EXISTS `t_infotype`;
CREATE TABLE `t_infotype` (
  `typeId` int(11) NOT NULL auto_increment COMMENT '类别id',
  `typeName` varchar(20) NOT NULL COMMENT '类别名称',
  PRIMARY KEY  (`typeId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_infotype
-- ----------------------------
INSERT INTO `t_infotype` VALUES ('1', '性能展示');
INSERT INTO `t_infotype` VALUES ('2', '销售策略');
INSERT INTO `t_infotype` VALUES ('3', '能源介绍');
INSERT INTO `t_infotype` VALUES ('4', '汽车理赔');
INSERT INTO `t_infotype` VALUES ('5', '汽车保养');
INSERT INTO `t_infotype` VALUES ('6', '购车政策');
INSERT INTO `t_infotype` VALUES ('7', '汽车文化');

-- ----------------------------
-- Table structure for `t_leaveword`
-- ----------------------------
DROP TABLE IF EXISTS `t_leaveword`;
CREATE TABLE `t_leaveword` (
  `leaveWordId` int(11) NOT NULL auto_increment COMMENT '留言id',
  `leaveTitle` varchar(80) NOT NULL COMMENT '留言标题',
  `leaveContent` varchar(2000) NOT NULL COMMENT '留言内容',
  `userObj` varchar(30) NOT NULL COMMENT '留言人',
  `leaveTime` varchar(20) default NULL COMMENT '留言时间',
  `replyContent` varchar(1000) default NULL COMMENT '管理回复',
  `replyTime` varchar(20) default NULL COMMENT '回复时间',
  PRIMARY KEY  (`leaveWordId`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_leaveword_ibfk_1` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_leaveword
-- ----------------------------
INSERT INTO `t_leaveword` VALUES ('1', '我要产品11111', '产品11111好，给我一个吧', 'user1', '2018-03-30 21:03:40', '可以给你哈', '2018-03-30 21:03:43');
INSERT INTO `t_leaveword` VALUES ('2', '我还要产品22222', '也给我发货吧！', 'user1', '2018-04-04 17:57:40', '没问题啊', '2018-04-04 22:34:39');

-- ----------------------------
-- Table structure for `t_newsinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_newsinfo`;
CREATE TABLE `t_newsinfo` (
  `newsId` int(11) NOT NULL auto_increment COMMENT '信息id',
  `infoTypeObj` int(11) NOT NULL COMMENT '信息类别',
  `title` varchar(80) NOT NULL COMMENT '信息标题',
  `newsContent` varchar(8000) NOT NULL COMMENT '信息内容',
  `readNum` int(11) NOT NULL COMMENT '阅读次数',
  `addTime` varchar(20) default NULL COMMENT '发布时间',
  PRIMARY KEY  (`newsId`),
  KEY `infoTypeObj` (`infoTypeObj`),
  CONSTRAINT `t_newsinfo_ibfk_1` FOREIGN KEY (`infoTypeObj`) REFERENCES `t_infotype` (`typeId`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_newsinfo
-- ----------------------------
INSERT INTO `t_newsinfo` VALUES ('1', '1', '性能展示111111', '<p><span style=\"font-family: Simsun; font-size: 13px; text-align: center; background-color: rgb(255, 228, 141);\">性能展示111111111</span></p>', '2', '2018-03-30 20:54:29');
INSERT INTO `t_newsinfo` VALUES ('2', '1', '性能展示22222', '<p><span style=\"font-family: Simsun; font-size: 13px; text-align: center; background-color: rgb(255, 228, 141);\">性能展示2222222</span></p>', '3', '2018-03-30 21:02:47');
INSERT INTO `t_newsinfo` VALUES ('3', '2', '销售策略11111111', '<p>销售策略11111111</p>', '1', '2018-04-04 22:25:41');
INSERT INTO `t_newsinfo` VALUES ('4', '2', '销售策略222222', '<p>销售策略2222222</p>', '1', '2018-04-04 22:25:54');
INSERT INTO `t_newsinfo` VALUES ('5', '3', '能源介绍1111111', '<p>能源介绍1111111</p>', '1', '2018-04-04 22:26:20');
INSERT INTO `t_newsinfo` VALUES ('6', '3', '能源介绍22222222', '<p>能源介绍2222222</p>', '2', '2018-04-04 22:26:33');
INSERT INTO `t_newsinfo` VALUES ('7', '4', '汽车理赔11111111', '<p>汽车理赔11111111</p>', '1', '2018-04-04 22:27:05');
INSERT INTO `t_newsinfo` VALUES ('8', '4', '汽车理赔22222222', '<p>汽车理赔2222222</p>', '1', '2018-04-04 22:27:18');
INSERT INTO `t_newsinfo` VALUES ('9', '5', '汽车保养11111111', '<p><span style=\"font-family: Simsun; font-size: 13px; text-align: center; background-color: rgb(234, 242, 255);\">汽车保养1111111</span></p>', '1', '2018-04-04 22:31:37');
INSERT INTO `t_newsinfo` VALUES ('10', '5', '汽车保养222222', '<p><span style=\"font-family: Simsun; font-size: 13px; text-align: center; background-color: rgb(234, 242, 255);\">汽车保养2222222</span></p>', '1', '2018-04-04 22:31:46');
INSERT INTO `t_newsinfo` VALUES ('11', '6', '购车政策111111', '<p>购车政策111111</p>', '1', '2018-04-04 22:33:20');
INSERT INTO `t_newsinfo` VALUES ('12', '6', '购车政策222222', '<p>购车政策222222</p>', '1', '2018-04-04 22:33:31');
INSERT INTO `t_newsinfo` VALUES ('13', '7', '汽车文化11111111', '<p>汽车文化11111111</p>', '1', '2018-04-04 22:33:54');
INSERT INTO `t_newsinfo` VALUES ('14', '7', '汽车文化2222222', '<p>汽车文化2222222</p>', '1', '2018-04-04 22:34:04');

-- ----------------------------
-- Table structure for `t_orderinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_orderinfo`;
CREATE TABLE `t_orderinfo` (
  `orderId` int(11) NOT NULL auto_increment COMMENT '订单id',
  `userObj` varchar(30) NOT NULL COMMENT '下单用户',
  `productObj` int(11) NOT NULL COMMENT '购买商品',
  `orderCount` int(11) NOT NULL COMMENT '购买数量',
  `totalMoney` float NOT NULL COMMENT '订单总价',
  `orderStateObj` varchar(20) NOT NULL COMMENT '订单状态',
  `orderTime` varchar(20) default NULL COMMENT '下单时间',
  `receiveName` varchar(20) NOT NULL COMMENT '收货人',
  `telephone` varchar(20) NOT NULL COMMENT '收货人电话',
  `address` varchar(80) NOT NULL COMMENT '收货人地址',
  `orderMemo` varchar(500) default NULL COMMENT '订单备注',
  PRIMARY KEY  (`orderId`),
  KEY `userObj` (`userObj`),
  KEY `productObj` (`productObj`),
  CONSTRAINT `t_orderinfo_ibfk_1` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`),
  CONSTRAINT `t_orderinfo_ibfk_2` FOREIGN KEY (`productObj`) REFERENCES `t_product` (`productId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_orderinfo
-- ----------------------------
INSERT INTO `t_orderinfo` VALUES ('1', 'user1', '1', '1', '35.5', '已付款', '2018-03-22 22:57:49', '王立', '15983080834', '四川成都红星路13号', 'test');
INSERT INTO `t_orderinfo` VALUES ('2', 'user1', '2', '2', '85', '待处理', '2018-04-04 23:03:12', '李明才', '13985080834', '四川成都红星路13号', '测试买货');

-- ----------------------------
-- Table structure for `t_product`
-- ----------------------------
DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product` (
  `productId` int(11) NOT NULL auto_increment COMMENT '商品id',
  `productClassObj` int(11) NOT NULL COMMENT '商品类别',
  `productName` varchar(50) NOT NULL COMMENT '商品名称',
  `mainPhoto` varchar(60) NOT NULL COMMENT '商品主图',
  `price` float NOT NULL COMMENT '商品价格',
  `productCount` int(11) NOT NULL COMMENT '商品库存',
  `productDesc` varchar(5000) NOT NULL COMMENT '商品简介',
  `addTime` varchar(20) default NULL COMMENT '发布时间',
  PRIMARY KEY  (`productId`),
  KEY `productClassObj` (`productClassObj`),
  CONSTRAINT `t_product_ibfk_1` FOREIGN KEY (`productClassObj`) REFERENCES `t_productclass` (`classId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_product
-- ----------------------------
INSERT INTO `t_product` VALUES ('1', '1', '商品1号', 'upload/6697e272-e150-4761-9c66-95c791f68d29.jpg', '35.5', '12', '<p>商品1号简介，这是一个测试商品，内容自己填写</p>', '2018-03-30 20:53:32');
INSERT INTO `t_product` VALUES ('2', '2', '商品2号', 'upload/8ad3eca2-a80d-42e1-8069-34c1eb215d15.jpg', '42.5', '18', '<p>商品2号，这里写关于这个商品的介绍，谢谢支持</p>', '2018-04-04 22:30:27');

-- ----------------------------
-- Table structure for `t_productclass`
-- ----------------------------
DROP TABLE IF EXISTS `t_productclass`;
CREATE TABLE `t_productclass` (
  `classId` int(11) NOT NULL auto_increment COMMENT '类别id',
  `className` varchar(40) NOT NULL COMMENT '类别名称',
  `classDesc` varchar(500) NOT NULL COMMENT '类别描述',
  PRIMARY KEY  (`classId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_productclass
-- ----------------------------
INSERT INTO `t_productclass` VALUES ('1', '商品类别1', '类别1，类别1');
INSERT INTO `t_productclass` VALUES ('2', '商品类别2', '类别2，类别2');

-- ----------------------------
-- Table structure for `t_userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_userinfo`;
CREATE TABLE `t_userinfo` (
  `user_name` varchar(30) NOT NULL COMMENT 'user_name',
  `password` varchar(30) NOT NULL COMMENT '登录密码',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `gender` varchar(4) NOT NULL COMMENT '性别',
  `birthDate` varchar(20) default NULL COMMENT '出生日期',
  `userPhoto` varchar(60) NOT NULL COMMENT '用户照片',
  `telephone` varchar(20) NOT NULL COMMENT '联系电话',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `address` varchar(80) default NULL COMMENT '家庭地址',
  `regTime` varchar(20) default NULL COMMENT '注册时间',
  PRIMARY KEY  (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_userinfo
-- ----------------------------
INSERT INTO `t_userinfo` VALUES ('user1', '123', '王中强', '男', '2018-03-13', 'upload/7fc4b844-f292-419e-95f3-ba543d42bcb9.jpg', '13980980834', 'zhognq@163.com', '四川成都红星路13号', '2018-03-30 20:53:08');
INSERT INTO `t_userinfo` VALUES ('user2', '王晓芬', '女', '女', '2018-04-02', 'upload/39a52352-7284-4f27-b618-1adc63818172.jpg', '13980508342', 'xiaofen@163.com', '福建福州滨海路', '2018-04-04 23:00:50');
