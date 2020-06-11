/*
Navicat MySQL Data Transfer

Source Server         : yunmysql
Source Server Version : 50562
Source Host           : localhost:3306
Source Database       : db_sd

Target Server Type    : MYSQL
Target Server Version : 50562
File Encoding         : 65001

Date: 2020-06-11 13:11:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_userName` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('8', '1', '1');
INSERT INTO `user` VALUES ('25', 'mjc', '1234');
INSERT INTO `user` VALUES ('26', 'CXQ', '12456');
INSERT INTO `user` VALUES ('29', 'qqq', '111');
INSERT INTO `user` VALUES ('31', 'qqqq', '111');
INSERT INTO `user` VALUES ('32', '123', '111');
INSERT INTO `user` VALUES ('33', '111', '22222');
INSERT INTO `user` VALUES ('40', '123456', '123456');
INSERT INTO `user` VALUES ('41', 'chenyu', '123456');
