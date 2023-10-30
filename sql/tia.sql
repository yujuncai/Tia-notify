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
INSERT INTO `db_user` VALUES (0x32336331616163632D366463352D343064662D623733392D393464393433386339646533, '40', '2bccdc3c92e55f0c72e910d792fb8fb2', 1676874554226, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '5fcf38a7-bca8-452f-8fa9-4acd8135972b', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x32343732636438312D323338342D343433622D383233352D333435336662343737363730, '111', 'cfdbaeb90a9b071fb4eed47355bb91a0', 1686033852188, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '28a053dc-13b3-46b3-92ea-04894b0a7ef2', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x33366632343132362D623136382D346266622D623366392D343436313834653936313439, '37', '52f4f9070bbd64f6afe843b968fef070', 1676874554440, 'img/20180414165827.jpg', '127.0.0.1', 'pc', 'cdb3a104-5eeb-444d-af3c-efecc17ea790', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x33393163616531612D346566382D343630332D386630302D663531353330393038333235, '1', 'f8f3c2abd76f4a86cf9e8aae6fb43802', 1676874554236, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '631305d4-e6fe-4f4a-a406-4b3d776775d1', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x33616166396137322D316632382D343134342D616531632D663235363934383264653466, '26', '7354ced79020f96d01681c5b1c7b5e5e', 1676874554226, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '60053002-72e6-4413-827a-a478cf66a9c9', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x34323534616161612D653264612D346139642D383936612D613562353934316361353437, '15', 'c916b4772a40b357b41f6e326cc91d14', 1676874554770, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '3374b097-4eef-4959-9f63-43912093ad91', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x35316635656231362D343865632D346165352D626234372D383539373938663530623766, '42', '99cffca93780a1d7bec6d827cf13b346', 1676874554786, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '2a3c70f5-8ed1-412f-8cf8-5d3c161304f3', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x35323937353531312D633732612D343533612D396234302D633936303538626132373632, '47', 'edea11ecacddb2e798ea9a6936d2eb40', 1676874554785, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '27e3d2ae-7a3e-4f73-96d9-cda382131b01', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x35333432643766662D303363632D343933642D393239382D383733353632366139396366, '49', '38c0a3131990b96c9f26c01ce65cd768', 1676874554822, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '4ade0807-fc7d-4358-9acb-50b3ef0f40e9', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x35353436633061642D346135352D343363322D383866612D366130616130613533316432, '2', 'b829f9c1450573a3f63f0d7827541dde', 1676874554388, 'img/20180414165827.jpg', '127.0.0.1', 'pc', 'ce1bc113-d365-4140-abce-e6f1209ce43a', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x35393536626539392D653939312D343866382D616236652D386233383333346464663138, '25', '85bb33a6ad38997a39577f7a95c5ca58', 1676874554679, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '9900ae0d-739d-40b7-be0f-fe46882c8805', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x36313736663163652D663433312D346262662D616234392D366366623264366239366638, '24', '1436442c9642b1a5cc0d56506253d3bf', 1676874554226, 'img/20180414165827.jpg', '127.0.0.1', 'pc', 'ee418f71-04bb-4b10-897b-576fac6a380d', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x36333930346638662D333263392D346165642D393533662D613837346638393430346464, '45', '0e1436d708bbc2f2e54529191d1bc8c1', 1676874554420, 'img/20180414165827.jpg', '127.0.0.1', 'pc', 'cc4ba582-4d6f-45e6-a340-62e899a2d1d0', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x36343962323237392D353933662D346334312D616430372D353938366463366237323635, '20', '7cf89d9cc68df1ba63f746cf65f4b056', 1676874554235, 'img/20180414165827.jpg', '127.0.0.1', 'pc', 'cf8c7356-6bdf-48dd-b29f-2fd5ed7cf427', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x36346262353033382D623336302D343533632D396461652D343839346265636236623030, '6', '71a2652a01ac607de90b1481c8d4e92b', 1676874553944, 'img/20180414165827.jpg', '127.0.0.1', 'pc', 'd770b938-f9f8-42ee-b72b-3a4cd468c531', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x36616263616433632D363630302D346635352D626234632D613164303566343931353863, '18', '0002157a326c97c82bf1112b996f6420', 1676874554627, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '652fa800-48b5-4113-9b70-865cd3c563d6', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x36616532323365362D376135632D343164342D613631392D656236653965313964666664, '46', 'a786220552df10d6105c0670d5ce272a', 1676874554666, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '8e3dca3e-841c-456e-a714-dca212824d00', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x36646464616238392D323630392D343133382D623463392D353233333630653164326462, '19', 'a000ddabe7b90b5da4849ee6cb76b9b0', 1676874554246, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '16e11289-22ad-43a7-b95e-e3bb0176673d', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x37323936326266342D633039632D343664652D623630322D613532623362343461643439, '43', '0e440d8e4f81eb99f7ba38689e8c28a6', 1676874578851, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '96ea217e-b2c3-456b-a25b-5f310519f306', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x37356632613762342D663365652D343431342D386161382D643134396663366635346333, '0', '9f0de388c52d255622c4d4f4199bf761', 1676874553845, 'img/20180414165827.jpg', '127.0.0.1', 'pc', 'a0d17c60-9781-4cfb-9cf0-e70ede71eeb7', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x37366336373136352D383161302D343464352D396265372D353965313137626537343561, '11', '8e2d9d2a232d561164dc1363080730eb', 1676874553845, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '89092a74-57cd-438d-8588-61a8d0999878', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x37373830643063352D396561632D343964392D393965632D323862363162353333323663, '17', '56f46ed1f88021925181ee08d974792e', 1676874554460, 'img/20180414165827.jpg', '127.0.0.1', 'pc', 'a90078e8-a84c-4629-b4a4-ce919b2356a8', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x37626334396439382D373266312D343039662D613863612D663833373661356530313435, '22', '0ac44ccd8ba4e9ee54ca794998f03089', 1676874554242, 'img/20180414165827.jpg', '127.0.0.1', 'pc', 'a370e935-2ec7-48f8-9fed-bc36a163f49a', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x37626465393862662D663965642D346537352D383136302D616333633237346538623838, '23', '1f3a19738e145af5906a75fa132a465b', 1676874554703, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '89ce8bbd-7092-4073-9603-4963440c300c', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x38323432613531312D633763392D346636302D393463632D323932653336313165313764, '35', '55d91a30073d5897ab8c6550ac7124fa', 1676874554399, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '2a2885bd-0911-4f8a-ab94-35baf2fb2901', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x38346636353537612D333537662D346565382D383361372D633464366135376463303966, '12', '6b6f92ad5251c19cb216779eb2b6a88f', 1676874553849, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '9db9edc7-9c1b-41bc-81dc-d1ead9349add', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x38353639663934312D626532662D346130652D396537352D333230333431613437343363, '28', '7da525ca2717ee657f51aaa5bc015a0d', 1676874554226, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '9f558742-ed66-4b9e-b161-3c2d4634415e', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x38616534356238622D316463352D346564622D393931302D323933353939306664313362, '41', 'a8c84d45de15094206a9e07bdbf8b0c0', 1676874554226, 'img/20180414165827.jpg', '127.0.0.1', 'pc', 'f44380af-4bf5-4afa-9552-40d69576b301', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x39353230393030362D656436372D343064632D623637632D616561303863383365666563, '4', 'af6a963b02bdc1a6643532d6ccd81e5d', 1676874554639, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '2cc7bc56-5a74-444e-a8e2-56edfbc30a91', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x39353931353938302D613731652D346562652D386561312D333864633038323133343534, '36', '36434b48724897ccd4e56523f7b40542', 1676874554469, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '178bab84-8d5a-4f93-858f-b872f8287a4d', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x39393331626337362D393862392D343635652D613861392D643866646665633739653335, '8', '890df8cceab93d02c2a5760307cd33a2', 1676874553944, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '2f179d82-c195-47ec-aea7-9ce004ce865c', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x61323265316232342D373037342D346463382D393732382D616562393461616561316537, '9', 'b6fca9944dfda499c2719fd6b847a577', 1676874553848, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '475d1664-bc77-4f44-9dbe-36aa1c72ac75', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x61333632323061342D353362652D343539642D613762662D333764623762643038336330, '38', '9a2a3a514a3fe32bb321d37a5c14a583', 1676874554414, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '1e07358e-99b6-4736-9a75-c9bc1a09d48f', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x61386232623065302D396665322D346665302D383363642D623532333038346362343565, '27', '0e6f0cdf818c0bc4550595a8fc533d72', 1676874554427, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '0fa82fe0-de83-4407-acb8-9d44b375cefe', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x62386564376565622D323636322D343565382D383965642D313264363334346336653433, '13', '23751b5b94c6c09e088668aa059acaac', 1676874553845, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '4206a020-6abd-48ca-af39-362b06f7e760', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x62663331653862322D313838632D346635322D616539382D343933626363633637343436, '32', '95e54ae73849e8ffc778153c7f6c3349', 1676874554737, 'img/20180414165827.jpg', '127.0.0.1', 'pc', 'a6fcc286-3a68-4d82-9afd-f36c2eb315f0', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x64303639613933342D366538322D346262312D616663652D313236653131386137343238, '31', '0325a8845ca353273df17cd274fbeaec', 1676874554408, 'img/20180414165827.jpg', '127.0.0.1', 'pc', 'a6359de7-3195-4fc4-9146-ac5c9a146844', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x64326232636534352D383162342D346632362D393037612D383537666139653461333433, '16', 'dad68562e278c7a3f3ca25c9b8e40123', 1676874554469, 'img/20180414165827.jpg', '127.0.0.1', 'pc', 'cd8592ae-7b27-4634-b3df-0077c639ec1d', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x64353433383266312D616335322D343637612D393661392D646665613061346430376532, '7', 'ff71cb46c8b32164e008ddd4b66d8e30', 1676874554659, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '4150d4f5-a432-4f32-93a1-bc20a56c7e3a', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x64396332646362332D343030332D343964392D613161302D643736363730326433303333, '5', '020382da9468744e599644f367d8bf99', 1676874553931, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '41010402-a0cd-4ca3-b18e-c59a0f130349', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x64613365343330382D353765372D343934612D623366612D386433393464666135386434, '3', '1792c134bc41e4c336774ac46c260dcd', 1676874553940, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '8a9bf0c9-00d2-4b33-a98e-d7e25690ddd7', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x64653862336536632D336566332D346231392D616332642D623264353533303461373431, '39', 'c3b5b57b17331cf3cbf28bb67f6d354a', 1676874554414, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '67fb35c1-7ac7-414c-ad71-b7efac37eed9', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x65323539633739632D656130652D346264642D383163652D313534666433326235373938, '48', '31c2c6e94df1ba5a0d6ee814b7fab93d', 1676874578840, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '5affa10c-fc74-43ca-9308-72694a8906e7', 'user', '/tia-java');
INSERT INTO `db_user` VALUES (0x66623162323365362D396363612D343963332D616237612D303635646138653033646239, '33', 'b0527ad8c787bd48ee3a403df4ab3dbe', 1676874554459, 'img/20180414165827.jpg', '127.0.0.1', 'pc', '24b26427-b345-4a89-9f3a-4afe502ef1b2', 'user', '/tia-java');

SET FOREIGN_KEY_CHECKS = 1;
