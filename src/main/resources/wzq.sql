/*
 Navicat Premium Data Transfer

 Source Server         : wzq
 Source Server Type    : MySQL
 Source Server Version : 80029 (8.0.29)
 Source Host           : localhost:3306
 Source Schema         : wzq

 Target Server Type    : MySQL
 Target Server Version : 80029 (8.0.29)
 File Encoding         : 65001

 Date: 28/03/2023 23:35:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for wzq_user
-- ----------------------------
DROP TABLE IF EXISTS `wzq_user`;
CREATE TABLE `wzq_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_nick` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
  `user_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户密码',
  `user_integral` int NOT NULL DEFAULT 0 COMMENT '用户积分',
  `user_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户Token',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10065 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wzq_user
-- ----------------------------
INSERT INTO `wzq_user` VALUES (10041, '啊手动阀', 'fffffff', 0, '');
INSERT INTO `wzq_user` VALUES (10042, '啊手动阀', 'fffffff', 0, '');
INSERT INTO `wzq_user` VALUES (10043, '啊手动阀', 'fffffff', 0, '');
INSERT INTO `wzq_user` VALUES (10044, '啊手动阀', 'fffffff', 0, 'sdfgsd');
INSERT INTO `wzq_user` VALUES (10045, '啊手动阀', 'fffffff', 0, '');
INSERT INTO `wzq_user` VALUES (10046, '啊手动阀', 'fffffff', 0, '');
INSERT INTO `wzq_user` VALUES (10047, '啊手动阀', 'fffffff', 0, '');
INSERT INTO `wzq_user` VALUES (10048, '啊手动阀', 'fffffff', 0, '');
INSERT INTO `wzq_user` VALUES (10049, '啊手动阀', 'fffffff', 0, 'eyJhbGciOiJIUzI1NiJ9.eyJzYWx0IjoiczE2akN2TFNjVVJNMnRhOTZnIiwiZXhwIjoxNzExNTQ4OTA3LCJ1c2VySWQiOjEwMDQ5fQ.vSJDTF-rDy1-OOUSJoahPOcizjS7Moo2ReGLJwcqD38');
INSERT INTO `wzq_user` VALUES (10050, '啊手动阀', 'fffffff', 0, 'eyJhbGciOiJIUzI1NiJ9.eyJzYWx0IjoiQzBWaXFGSjMzQ1B0Tm56VVR2IiwiZXhwIjoxNzExNTQ4OTA5LCJ1c2VySWQiOjEwMDUwfQ.SzRRMC9j91LYPAKjZue4FYc-7JFlEFcZw8Wwfi-WWPs');
INSERT INTO `wzq_user` VALUES (10051, '啊手动阀', 'fffffff', 0, 'eyJhbGciOiJIUzI1NiJ9.eyJzYWx0IjoidDBVWklwOVdhTWt1V0Jpa2R3IiwiZXhwIjoxNzExNTQ4OTEwLCJ1c2VySWQiOjEwMDUxfQ.EWp2KMEZ7BVcBK7NyzIQrGhoGNXAywfTmyv8qRZaajw');
INSERT INTO `wzq_user` VALUES (10052, '啊手动阀', 'fffffff', 0, 'eyJhbGciOiJIUzI1NiJ9.eyJzYWx0IjoibnBZZXB6cGpLVFpWZ09pZlNCIiwiZXhwIjoxNzExNTQ4OTExLCJ1c2VySWQiOjEwMDUyfQ.CGy1NdlO91UOz9JKlS7UDYIq4TtMe5yaHshagS__bGE');
INSERT INTO `wzq_user` VALUES (10053, '啊手动阀', 'fffffff', 0, 'eyJhbGciOiJIUzI1NiJ9.eyJzYWx0IjoiMTJmNm9XZ2I2RkxvRGE4aXkyIiwiZXhwIjoxNzExNTQ4OTExLCJ1c2VySWQiOjEwMDUzfQ.oBV7He6-7t8OnbDxYc8wvc7Oul5tgnbaIokYjeXwJlo');
INSERT INTO `wzq_user` VALUES (10054, '啊手动阀', 'fffffff', 0, 'eyJhbGciOiJIUzI1NiJ9.eyJzYWx0IjoicHB4YnZGNHZ0dndYODlrcnFCIiwiZXhwIjoxNzExNTQ4OTEyLCJ1c2VySWQiOjEwMDU0fQ.if5BEH1We_xYyHe5PBcJvcBOpsSyrxADBtl9v18bkZA');
INSERT INTO `wzq_user` VALUES (10055, '啊手动阀', 'fffffff', 0, 'eyJhbGciOiJIUzI1NiJ9.eyJzYWx0IjoiUm5CZ2tBeXRLaGltUHFTTWVuIiwiZXhwIjoxNzExNTQ4OTEyLCJ1c2VySWQiOjEwMDU1fQ.GbyGw6IkiJygDMf6t_KKwhZnui-phnubOcePOh7G4oQ');
INSERT INTO `wzq_user` VALUES (10056, '啊手动阀', 'fffffff', 0, 'eyJhbGciOiJIUzI1NiJ9.eyJzYWx0IjoiQnc2NHA0N09uNkhNUVBZaEphIiwiZXhwIjoxNzExNTQ4OTE1LCJ1c2VySWQiOjEwMDU2fQ.lQvdCIai6jzQvfyjQbPGo7Gv95R2jOnhhhTmhtBo-A0');
INSERT INTO `wzq_user` VALUES (10057, '啊手动阀', 'fffffff ', 0, 'eyJhbGciOiJIUzI1NiJ9.eyJzYWx0IjoiS3hxdGdydGk1QTNFaHdVUlBrIiwiZXhwIjoxNzExNTQ4OTE2LCJ1c2VySWQiOjEwMDU3fQ.fvibPozsr5aLFAsNtb6sCJlSnlxcZeeXuayN4RLv0o0');
INSERT INTO `wzq_user` VALUES (10058, '啊手动阀', '8088581a15d3bcf4ffc5458478ac69a2753c92c9', 0, 'eyJhbGciOiJIUzI1NiJ9.eyJzYWx0IjoiNnVNZE9OcUNEdUdhN2FZMmI4IiwiZXhwIjoxNzExNTQ5NDA3LCJ1c2VySWQiOjEwMDU4fQ.7GoSz-kwWL12kh9DBm3QxWav7cNzWHVFv36IsI21Xac');
INSERT INTO `wzq_user` VALUES (10059, '啊手动阀', '8088581a15d3bcf4ffc5458478ac69a2753c92c9', 0, 'eyJhbGciOiJIUzI1NiJ9.eyJzYWx0IjoiUVdac2hjZGNVZnRVOG9nSEc1IiwiZXhwIjoxNzExNTQ5ODc3LCJ1c2VySWQiOjEwMDU5fQ.Cs_DzPi5K10UmNflHOm1IyY5O9fPBeM_WeOWAioKtB0');
INSERT INTO `wzq_user` VALUES (10060, '啊手动阀', '8088581a15d3bcf4ffc5458478ac69a2753c92c9', 0, 'eyJhbGciOiJIUzI1NiJ9.eyJzYWx0IjoiTmtXM2JaaXJhY254OGFib1ZmIiwiZXhwIjoxNzExNTQ5ODgwLCJ1c2VySWQiOjEwMDYwfQ.5BoLwLfzEg8Dm_OhJem13YTeaf2PL5XcV3O079qwqSg');
INSERT INTO `wzq_user` VALUES (10061, '啊手动阀2', '8088581a15d3bcf4ffc5458478ac69a2753c92c9', 0, 'eyJhbGciOiJIUzI1NiJ9.eyJzYWx0IjoieDNydWpONGdQeU82cDU0eG05IiwiZXhwIjoxNzExNTQ5OTM5LCJ1c2VySWQiOjEwMDYxfQ.DG5JBJimlqojvS4TbVNlt-kmu7yLHjxjyQkQMtZMko0');
INSERT INTO `wzq_user` VALUES (10062, '啊手动阀2', '8088581a15d3bcf4ffc5458478ac69a2753c92c9', 0, 'eyJhbGciOiJIUzI1NiJ9.eyJzYWx0IjoiTlBLU20zN0RVQWJWSlZCRHlRIiwiZXhwIjoxNzExNTQ5OTQzLCJ1c2VySWQiOjEwMDYyfQ.qDYkzWFtdjh9VI2dkl-w_CeHvkaJClJR90e1Rhd9J9E');
INSERT INTO `wzq_user` VALUES (10063, '啊手动阀2', '8088581a15d3bcf4ffc5458478ac69a2753c92c9', 0, 'eyJhbGciOiJIUzI1NiJ9.eyJzYWx0IjoiQ21sUXh5eGwyT2prQVVEdmtZIiwiZXhwIjoxNzExNTQ5OTQ3LCJ1c2VySWQiOjEwMDYzfQ.WTkxYiLQgk8OliBtfmlDoG_Czd9amn0qHrUWRAKEBuc');
INSERT INTO `wzq_user` VALUES (10064, '啊手', '8088581a15d3bcf4ffc5458478ac69a2753c92c9', 0, 'eyJhbGciOiJIUzI1NiJ9.eyJzYWx0IjoiNEY4cFNPYlpKYU1iVlVVQ0tmIiwiZXhwIjoxNzExNTQ5OTU2LCJ1c2VySWQiOjEwMDY0fQ.yILW7wLoGMYqhsj0PInRVY2RD9ibLnY6sv2wZX4QHjk');

SET FOREIGN_KEY_CHECKS = 1;
