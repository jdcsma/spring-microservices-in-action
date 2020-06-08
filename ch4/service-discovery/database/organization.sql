/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : localhost:3306
 Source Schema         : organization-default

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 07/06/2020 18:47:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for organizations
-- ----------------------------
DROP TABLE IF EXISTS `organizations`;
CREATE TABLE `organizations` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `organization_id` varchar(127) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(127) COLLATE utf8_unicode_ci NOT NULL,
  `contact_name` varchar(127) COLLATE utf8_unicode_ci NOT NULL,
  `contact_email` varchar(127) COLLATE utf8_unicode_ci NOT NULL,
  `contact_phone` varchar(127) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNION KEY `organization_id` (`organization_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

SET FOREIGN_KEY_CHECKS = 1;
