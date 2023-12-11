/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50725 (5.7.25-log)
 Source Host           : 127.0.0.1:3306
 Source Schema         : tia

 Target Server Type    : MySQL
 Target Server Version : 50725 (5.7.25-log)
 File Encoding         : 65001

 Date: 30/10/2023 16:59:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for db_main_body
-- ----------------------------
DROP TABLE IF EXISTS `db_main_body`;
CREATE TABLE `db_main_body`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `nameSpace` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `appSecret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `mainStatus` int(11) NULL DEFAULT NULL,
  `appId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `appid`(`appId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of db_main_body
-- ----------------------------
INSERT INTO `db_main_body` VALUES ('11', 'tia-java测试', '/tia-java', 'jvZJhHtp3vOVmpool6QlMw==', 0, '987654321');

-- ----------------------------
-- Table structure for db_menu
-- ----------------------------
DROP TABLE IF EXISTS `db_menu`;
CREATE TABLE `db_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pid` bigint(20) NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sort` int(11) NULL DEFAULT NULL,
  `status` tinyint(4) NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` tinyint(4) NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of db_menu
-- ----------------------------
INSERT INTO `db_menu` VALUES (1, '', 2, '', 3, 1, '菜单管理', 2, '/menu/index');
INSERT INTO `db_menu` VALUES (2, 'fa fa-cog', 0, '', 2, 1, '系统管理', 1, '#');
INSERT INTO `db_menu` VALUES (4, '', 2, '', 2, 1, '命名空间管理', 2, '/namespace/index');
INSERT INTO `db_menu` VALUES (6, 'layui-icon layui-icon-home', 0, '', 1, 1, '主页', 1, '/index');
INSERT INTO `db_menu` VALUES (7, '', 2, '', 1, 1, '用户管理', 2, '/user/index');

-- ----------------------------
-- Table structure for db_message
-- ----------------------------
DROP TABLE IF EXISTS `db_message`;
CREATE TABLE `db_message`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `from_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `to_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `contents` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `message_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `read_flag` int(8) NULL DEFAULT NULL,
  `times` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of db_message
-- ----------------------------
INSERT INTO `db_message` VALUES ('021ff96cb6754404a2203d5f8a507971', '2472cd81-2384-443b-8235-3453fb477670', '2472cd81-2384-443b-8235-3453fb477670', '111', 'text', 0, '2023-04-14 15:47:08');
INSERT INTO `db_message` VALUES ('1887e96b4c52490787396a0865422065', '2472cd81-2384-443b-8235-3453fb477670', '222222', '111', 'text', 0, '2023-04-14 15:43:53');
INSERT INTO `db_message` VALUES ('4a65ed20709648c5bd97a30d919adde1', '2472cd81-2384-443b-8235-3453fb477670', '222222', '111', 'text', 0, '2023-04-14 15:46:06');
INSERT INTO `db_message` VALUES ('53905705b35d41bb8566341c7d769573', '222222', '222222', '1', 'text', 0, '2023-04-14 15:56:20');
INSERT INTO `db_message` VALUES ('7a8fdb0cab1a47349146ca66d0f5b92b', '222222', '2472cd81-2384-443b-8235-3453fb477670', '33333', 'text', 0, '2023-04-14 15:56:49');
INSERT INTO `db_message` VALUES ('8056bddd508c42f2af3abdc52305f957', '222222', '2472cd81-2384-443b-8235-3453fb477670', '3', 'text', 0, '2023-04-14 15:55:42');
INSERT INTO `db_message` VALUES ('944429b698274e10890ff075253ed95a', '222222', '2472cd81-2384-443b-8235-3453fb477670', '33333', 'text', 0, '2023-04-14 15:56:31');
INSERT INTO `db_message` VALUES ('97e516b72bca4ce89f24793345a65167', '222222', '2472cd81-2384-443b-8235-3453fb477670', '333', 'text', 0, '2023-04-14 15:56:46');
INSERT INTO `db_message` VALUES ('db0dd1009f934115b101a2422e54dab1', '2472cd81-2384-443b-8235-3453fb477670', '222222', '4444', 'text', 0, '2023-04-14 15:47:45');
INSERT INTO `db_message` VALUES ('e23ecab667294001bb26ccca50d23feb', '222222', '2472cd81-2384-443b-8235-3453fb477670', '1', 'text', 0, '2023-04-14 15:53:29');
INSERT INTO `db_message` VALUES ('fbcd33659d65486f95e92df58ac3ce71', '222222', '2472cd81-2384-443b-8235-3453fb477670', '2', 'text', 0, '2023-04-14 15:55:37');

-- ----------------------------
-- Table structure for db_user
-- ----------------------------
DROP TABLE IF EXISTS `db_user`;
CREATE TABLE `db_user`  (
  `id` varbinary(50) NOT NULL DEFAULT '',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `time` bigint(20) NULL DEFAULT NULL,
  `avatarUrl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `deviceType` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `currId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `nameSpace` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `nameUni`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of db_user
-- ----------------------------
INSERT INTO `db_user` VALUES (0x30633239363937382D663866352D346466652D623461372D383562396561616137346461, '30', 'edac215a4e25b60fb1fade8fec88ea5d', 1676874554226, 'img/20180414165827.jpg', '127.0.0.1', 'pc', 'f830d5e8-406d-48c3-81b5-cd818ada6706', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x31396630663664632D363430622D346434612D393666612D663138343762363432633535, '29', '3f6fb3db769bcdbf9ff1cfb41955316b', 1676874554380, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '63e627ad-f6b2-4beb-b62c-50624af1c664', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x31636639373661662D666261312D343335612D626233632D616434353232386533373862, '14', '77c33c487b4e4ae8b813070669e4adca', 1676874553846, 'img/20180414165827.jpg', '127.0.0.1', 'pc', 'c03c34ac-e9da-44c5-a4d6-69620df541fa', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x31663038313038322D353566322D343136612D623966362D353835616337303737363432, '21', '9a6e40c334e68fd38a8b315b3143cc5a', 1676874554236, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '4902c096-4669-4f46-bf72-81bf48acf336', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x323232323232, '222', 'cfdbaeb90a9b071fb4eed47355bb91a0', 1681458797720, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '03e71d6c-4093-47d8-bf20-254e5a2e8ef5', 'user', '/tia-java');

SET FOREIGN_KEY_CHECKS = 1;
