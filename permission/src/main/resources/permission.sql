/*
Navicat MySQL Data Transfer

Source Server         : ljh
Source Server Version : 50714
Source Host           : localhost:3306
Source Database       : permission

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2019-07-22 19:36:03
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `sys_acl`
-- ----------------------------
DROP TABLE IF EXISTS `sys_acl`;
CREATE TABLE `sys_acl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `code` varchar(20) NOT NULL DEFAULT '' COMMENT '权限码',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '权限名称',
  `acl_module_id` int(11) NOT NULL DEFAULT '0' COMMENT '权限所在的权限模块id',
  `url` varchar(100) NOT NULL DEFAULT '' COMMENT '请求的url, 可以填正则表达式',
  `type` int(11) NOT NULL DEFAULT '3' COMMENT '类型，1：菜单，2：按钮，3：其他',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，1：正常，0：冻结',
  `seq` int(11) NOT NULL DEFAULT '0' COMMENT '权限在当前模块下的顺序，由小到大',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `operator` varchar(20) NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `operate_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一个更新者的ip地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_acl
-- ----------------------------
INSERT INTO `sys_acl` VALUES ('1', '20190718103315_15', '进入产品管理页面', '1', '/sys/product/product.page', '1', '1', '1', '', 'Admin', '2019-07-18 10:33:16', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl` VALUES ('2', '20190718103329_65', '查询产品列表', '1', '/sys/product/page.json', '2', '1', '2', '', 'Admin', '2019-07-18 10:33:29', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl` VALUES ('3', '20190718103410_17', '产品上架', '1', '/sys/product/online.json', '2', '1', '3', '', 'Admin', '2019-07-18 10:34:11', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl` VALUES ('4', '20190718103427_41', '产品下架', '1', '/sys/product/offline.json', '2', '1', '4', '', 'Admin', '2019-07-18 10:44:53', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl` VALUES ('5', '20190718171615_85', '进入订单页', '2', '/sys/order/order.page', '1', '1', '1', '', 'Admin', '2019-07-18 17:17:11', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl` VALUES ('6', '20190718171656_90', '查询订单列表', '2', '/sys/order/list.json', '2', '1', '2', '', 'Admin', '2019-07-18 17:17:26', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl` VALUES ('7', '20190718171946_25', '进入权限管理页', '7', '/sys/aclModule/acl.page', '1', '1', '1', '', 'Admin', '2019-07-18 17:20:07', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl` VALUES ('8', '20190718172038_1', '进入角色管理页', '8', '/sys/role/role.page', '1', '1', '1', '', 'Admin', '2019-07-20 01:40:23', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl` VALUES ('9', '20190718172113_27', '进入用户管理页', '9', '/sys/dept/dept.page', '1', '1', '1', '', 'Admin', '2019-07-18 17:21:14', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl` VALUES ('10', '20190721180355_4', '进入权限更新记录页面', '11', '/sys/log/log.page', '1', '1', '1', '', 'Admin', '2019-07-21 18:03:55', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for `sys_acl_module`
-- ----------------------------
DROP TABLE IF EXISTS `sys_acl_module`;
CREATE TABLE `sys_acl_module` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限模块id',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '权限模块名称',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '上级权限模块id',
  `level` varchar(200) NOT NULL DEFAULT '' COMMENT '权限模块层级',
  `seq` int(11) NOT NULL DEFAULT '0' COMMENT '权限模块在当前层级下的顺序，由小到大',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，1：正常，0：冻结',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `operator` varchar(20) NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次操作时间',
  `operate_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一次更新操作者的ip地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_acl_module
-- ----------------------------
INSERT INTO `sys_acl_module` VALUES ('1', '产品管理', '0', '0', '1', '1', '', 'Admin', '2019-07-18 10:04:23', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl_module` VALUES ('2', '订单管理', '0', '0', '2', '1', '', 'Admin', '2019-07-18 10:04:29', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl_module` VALUES ('3', '公告管理', '0', '0', '3', '1', '', 'Admin', '2019-07-18 10:04:35', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl_module` VALUES ('4', '出售中产品管理', '1', '0.1', '1', '1', '', 'Admin', '2019-07-18 10:04:43', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl_module` VALUES ('5', '下架产品管理', '1', '0.1', '2', '1', '', 'Admin', '2019-07-18 10:04:55', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl_module` VALUES ('6', '权限管理', '0', '0', '4', '1', '', 'Admin', '2019-07-18 17:18:24', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl_module` VALUES ('7', '权限管理', '6', '0.6', '1', '1', '', 'Admin', '2019-07-18 17:18:38', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl_module` VALUES ('8', '角色管理', '6', '0.6', '2', '1', '', 'Admin', '2019-07-18 17:18:50', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl_module` VALUES ('9', '用户管理', '6', '0.6', '3', '1', '', 'Admin', '2019-07-18 17:19:01', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl_module` VALUES ('10', '运维管理', '0', '0', '6', '1', '', 'Admin', '2019-07-21 18:02:50', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_acl_module` VALUES ('11', '权限更新记录管理', '6', '0.6', '4', '1', '', 'Admin', '2019-07-21 18:03:09', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for `sys_dept`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '部门名称',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '上级部门id',
  `level` varchar(200) NOT NULL DEFAULT '' COMMENT '部门层级',
  `seq` int(11) NOT NULL DEFAULT '0' COMMENT '部门在当前层级下的顺序，由小到大',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `operator` varchar(20) NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次操作时间',
  `operate_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一次更新操作者的ip地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1', '技术部', '0', '0', '1', '技术部', 'system', '2019-07-15 22:57:26', '127.0.0.1');
INSERT INTO `sys_dept` VALUES ('2', '后端开发', '1', '0.1', '1', '后端', 'system-update', '2019-07-16 03:23:08', '127.0.0.1');
INSERT INTO `sys_dept` VALUES ('3', '前端开发', '1', '0.1', '2', '', 'system-update', '2019-07-17 02:59:46', '127.0.0.1');
INSERT INTO `sys_dept` VALUES ('4', 'UI设计', '1', '0.1', '3', '', 'system-update', '2019-07-17 02:59:36', '127.0.0.1');
INSERT INTO `sys_dept` VALUES ('5', '产品部', '0', '0', '2', '', 'Admin', '2019-07-21 17:25:01', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_dept` VALUES ('6', '客户部', '0', '0', '4', '', 'Admin', '2019-07-21 21:40:12', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for `sys_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '权限更新的类型，1：部门，2：用户，3：权限模块，4：权限，5：角色，6：角色用户关系，7：角色权限关系',
  `target_id` int(11) NOT NULL COMMENT '基于type后指定的对象id，比如用户、权限、角色等表的主键',
  `old_value` text COMMENT '旧值',
  `new_value` text COMMENT '新值',
  `operator` varchar(20) NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新的时间',
  `operate_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一次更新者的ip地址',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '当前是否复原过，0：没有，1：复原过',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('1', '1', '6', '', '{\"id\":6,\"name\":\"客户部\",\"parentId\":0,\"level\":\"0\",\"seq\":3,\"operator\":\"Admin\",\"operateTime\":1563702594286,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-07-21 17:49:54', '0:0:0:0:0:0:0:1', '1');
INSERT INTO `sys_log` VALUES ('2', '1', '6', '{\"id\":6,\"name\":\"客户部\",\"parentId\":0,\"level\":\"0\",\"seq\":3,\"operator\":\"Admin\",\"operateTime\":1563702594000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":6,\"name\":\"客户部\",\"parentId\":0,\"level\":\"0\",\"seq\":4,\"operator\":\"Admin\",\"operateTime\":1563702602177,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-07-21 17:50:02', '0:0:0:0:0:0:0:1', '1');
INSERT INTO `sys_log` VALUES ('3', '2', '4', '', '{\"id\":4,\"username\":\"Kate\",\"telephone\":\"13144445555\",\"mail\":\"Kate@qq.com\",\"password\":\"25D55AD283AA400AF464C76D713C07AD\",\"deptId\":1,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1563703329032,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-07-21 18:02:09', '0:0:0:0:0:0:0:1', '1');
INSERT INTO `sys_log` VALUES ('4', '2', '4', '{\"id\":4,\"username\":\"Kate\",\"telephone\":\"13144445555\",\"mail\":\"Kate@qq.com\",\"password\":\"25D55AD283AA400AF464C76D713C07AD\",\"deptId\":1,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1563703329000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":4,\"username\":\"Kate\",\"telephone\":\"13144445555\",\"mail\":\"Kate@qq.com\",\"deptId\":1,\"status\":1,\"remark\":\"sss\",\"operator\":\"Admin\",\"operateTime\":1563703341402,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-07-21 18:02:21', '0:0:0:0:0:0:0:1', '1');
INSERT INTO `sys_log` VALUES ('5', '3', '10', '', '{\"id\":10,\"name\":\"运维管理\",\"parentId\":0,\"level\":\"0\",\"seq\":5,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1563703366174,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-07-21 18:02:46', '0:0:0:0:0:0:0:1', '1');
INSERT INTO `sys_log` VALUES ('6', '3', '10', '{\"id\":10,\"name\":\"运维管理\",\"parentId\":0,\"level\":\"0\",\"seq\":5,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1563703366000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":10,\"name\":\"运维管理\",\"parentId\":0,\"level\":\"0\",\"seq\":6,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1563703370296,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-07-21 18:02:50', '0:0:0:0:0:0:0:1', '1');
INSERT INTO `sys_log` VALUES ('7', '3', '11', '', '{\"id\":11,\"name\":\"权限更新记录管理\",\"parentId\":6,\"level\":\"0.6\",\"seq\":4,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1563703389358,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-07-21 18:03:09', '0:0:0:0:0:0:0:1', '1');
INSERT INTO `sys_log` VALUES ('8', '4', '10', '', '{\"id\":10,\"code\":\"20190721180355_4\",\"name\":\"进入权限更新记录页面\",\"aclModuleId\":11,\"url\":\"/sys/log/log.page\",\"type\":1,\"status\":1,\"seq\":1,\"operator\":\"Admin\",\"operateTime\":1563703435469,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-07-21 18:03:55', '0:0:0:0:0:0:0:1', '1');
INSERT INTO `sys_log` VALUES ('9', '5', '5', '', '{\"id\":5,\"name\":\"运维管理员\",\"type\":1,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1563703468916,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-07-21 18:04:29', '0:0:0:0:0:0:0:1', '1');
INSERT INTO `sys_log` VALUES ('10', '5', '5', '{\"id\":5,\"name\":\"运维管理员\",\"type\":1,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1563703469000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":5,\"name\":\"运维管理员\",\"type\":1,\"status\":1,\"remark\":\"运维\",\"operator\":\"Admin\",\"operateTime\":1563703474847,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-07-21 18:04:35', '0:0:0:0:0:0:0:1', '1');
INSERT INTO `sys_log` VALUES ('11', '7', '4', '[1]', '[1,4]', 'Admin', '2019-07-21 20:42:35', '0:0:0:0:0:0:0:1', '1');
INSERT INTO `sys_log` VALUES ('12', '6', '4', '[7,8,9]', '[7,8,9,10]', 'Admin', '2019-07-21 20:43:20', '0:0:0:0:0:0:0:1', '1');
INSERT INTO `sys_log` VALUES ('13', '2', '5', '', '{\"id\":5,\"username\":\"服务员A\",\"telephone\":\"18677778888\",\"mail\":\"service@qq.com\",\"password\":\"25D55AD283AA400AF464C76D713C07AD\",\"deptId\":6,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1563716335335,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-07-21 21:38:55', '0:0:0:0:0:0:0:1', '1');
INSERT INTO `sys_log` VALUES ('14', '2', '5', '{\"id\":5,\"username\":\"服务员A\",\"telephone\":\"18677778888\",\"mail\":\"service@qq.com\",\"password\":\"25D55AD283AA400AF464C76D713C07AD\",\"deptId\":6,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1563716335000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":5,\"username\":\"服务员B\",\"telephone\":\"18677778888\",\"mail\":\"service@qq.com\",\"deptId\":6,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1563716351973,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-07-21 21:39:12', '0:0:0:0:0:0:0:1', '1');
INSERT INTO `sys_log` VALUES ('15', '2', '5', '{\"id\":5,\"username\":\"服务员B\",\"telephone\":\"18677778888\",\"mail\":\"service@qq.com\",\"password\":\"25D55AD283AA400AF464C76D713C07AD\",\"deptId\":6,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1563716352000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":5,\"username\":\"服务员A\",\"telephone\":\"18677778888\",\"mail\":\"service@qq.com\",\"password\":\"25D55AD283AA400AF464C76D713C07AD\",\"deptId\":6,\"status\":1,\"operator\":\"Admin\",\"operateTime\":1563716364572,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-07-21 21:39:25', '0:0:0:0:0:0:0:1', '1');
INSERT INTO `sys_log` VALUES ('16', '1', '6', '{\"id\":6,\"name\":\"客户部\",\"parentId\":0,\"level\":\"0\",\"seq\":4,\"operator\":\"Admin\",\"operateTime\":1563702602000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":6,\"name\":\"客户部A\",\"parentId\":0,\"level\":\"0\",\"seq\":4,\"operator\":\"Admin\",\"operateTime\":1563716402169,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-07-21 21:40:02', '0:0:0:0:0:0:0:1', '1');
INSERT INTO `sys_log` VALUES ('17', '1', '6', '{\"id\":6,\"name\":\"客户部A\",\"parentId\":0,\"level\":\"0\",\"seq\":4,\"operator\":\"Admin\",\"operateTime\":1563716402000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":6,\"name\":\"客户部\",\"parentId\":0,\"level\":\"0\",\"seq\":4,\"operator\":\"Admin\",\"operateTime\":1563716411638,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-07-21 21:40:12', '0:0:0:0:0:0:0:1', '1');
INSERT INTO `sys_log` VALUES ('18', '5', '5', '{\"id\":5,\"name\":\"运维管理员\",\"type\":1,\"status\":1,\"remark\":\"运维\",\"operator\":\"Admin\",\"operateTime\":1563703475000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":5,\"name\":\"运维管理员A\",\"type\":1,\"status\":1,\"remark\":\"运维\",\"operator\":\"Admin\",\"operateTime\":1563716436798,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-07-21 21:40:37', '0:0:0:0:0:0:0:1', '1');
INSERT INTO `sys_log` VALUES ('19', '5', '5', '{\"id\":5,\"name\":\"运维管理员A\",\"type\":1,\"status\":1,\"remark\":\"运维\",\"operator\":\"Admin\",\"operateTime\":1563716437000,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', '{\"id\":5,\"name\":\"运维管理员\",\"type\":1,\"status\":1,\"remark\":\"运维\",\"operator\":\"Admin\",\"operateTime\":1563716443732,\"operateIp\":\"0:0:0:0:0:0:0:1\"}', 'Admin', '2019-07-21 21:40:44', '0:0:0:0:0:0:0:1', '1');
INSERT INTO `sys_log` VALUES ('20', '7', '4', '[1,4]', '[1,4,2,3,5]', 'Admin', '2019-07-21 21:41:20', '0:0:0:0:0:0:0:1', '1');
INSERT INTO `sys_log` VALUES ('21', '7', '4', '[1,4,2,3,5]', '[1,4]', 'Admin', '2019-07-21 21:41:31', '0:0:0:0:0:0:0:1', '1');
INSERT INTO `sys_log` VALUES ('22', '6', '5', '[]', '[7,8,9,10]', 'Admin', '2019-07-21 21:42:03', '0:0:0:0:0:0:0:1', '1');
INSERT INTO `sys_log` VALUES ('23', '6', '5', '[7,8,9,10]', '[]', 'Admin', '2019-07-21 21:42:11', '0:0:0:0:0:0:0:1', '1');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(20) NOT NULL,
  `type` int(11) NOT NULL DEFAULT '1' COMMENT '角色的类型，1：管理员角色，2：其他',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，1：可用，0：冻结',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `operator` varchar(20) NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新的时间',
  `operate_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一次更新者的ip地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '产品管理员', '1', '1', '', 'Admin', '2019-07-18 11:39:36', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_role` VALUES ('2', '订单管理员', '1', '1', '', 'Admin', '2019-07-18 11:28:35', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_role` VALUES ('3', '公告管理员', '1', '1', '', 'Admin', '2019-07-18 11:28:41', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_role` VALUES ('4', '权限管理员', '1', '1', '', 'Admin', '2019-07-18 17:22:45', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_role` VALUES ('5', '运维管理员', '1', '1', '运维', 'Admin', '2019-07-21 21:40:44', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for `sys_role_acl`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_acl`;
CREATE TABLE `sys_role_acl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `acl_id` int(11) NOT NULL COMMENT '权限id',
  `operator` varchar(20) NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新的时间',
  `operate_ip` varchar(200) NOT NULL DEFAULT '' COMMENT '最后一次更新者的ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_role_acl
-- ----------------------------
INSERT INTO `sys_role_acl` VALUES ('9', '4', '7', 'Admin', '2019-07-21 20:43:20', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_role_acl` VALUES ('10', '4', '8', 'Admin', '2019-07-21 20:43:20', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_role_acl` VALUES ('11', '4', '9', 'Admin', '2019-07-21 20:43:20', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_role_acl` VALUES ('12', '4', '10', 'Admin', '2019-07-21 20:43:20', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for `sys_role_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `operator` varchar(20) NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新的时间',
  `operate_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一次更新者的ip地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES ('11', '4', '1', 'Admin', '2019-07-21 21:41:31', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_role_user` VALUES ('12', '4', '4', 'Admin', '2019-07-21 21:41:31', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名称',
  `telephone` varchar(13) NOT NULL DEFAULT '' COMMENT '手机号',
  `mail` varchar(20) NOT NULL DEFAULT '' COMMENT '邮箱',
  `password` varchar(40) NOT NULL DEFAULT '' COMMENT '加密后的密码',
  `dept_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户所在部门的id',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，1：正常，0：冻结状态，2：删除',
  `remark` varchar(200) DEFAULT '' COMMENT '备注',
  `operator` varchar(20) NOT NULL DEFAULT '' COMMENT '操作者',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `operate_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '最后一次更新者的ip地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'Admin', '18612344321', 'admin@qq.com', '25D55AD283AA400AF464C76D713C07AD', '1', '1', 'admin', 'system', '2019-07-17 02:28:26', '127.0.0.1');
INSERT INTO `sys_user` VALUES ('2', 'jimin', '13188889999', 'jimin@qq.com', '25D55AD283AA400AF464C76D713C07AD', '1', '1', 'jimin.zheng', 'Admin', '2019-07-18 09:31:12', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_user` VALUES ('3', 'jimmy', '13812344311', 'jimmy@qq.com', '25D55AD283AA400AF464C76D713C07AD', '2', '1', '', 'Admin', '2019-07-19 03:24:59', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_user` VALUES ('4', 'Kate', '13144445555', 'Kate@qq.com', '25D55AD283AA400AF464C76D713C07AD', '1', '1', 'sss', 'Admin', '2019-07-21 18:02:21', '0:0:0:0:0:0:0:1');
INSERT INTO `sys_user` VALUES ('5', '服务员A', '18677778888', 'service@qq.com', '25D55AD283AA400AF464C76D713C07AD', '6', '1', '', 'Admin', '2019-07-21 21:39:25', '0:0:0:0:0:0:0:1');
