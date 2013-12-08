# Host: localhost  (Version: 5.5.28)
# Date: 2013-12-05 17:08:26
# Generator: MySQL-Front 5.3  (Build 4.43)

/*!40101 SET NAMES utf8 */;

#
# Structure for table "ss_menu"
#

DROP TABLE IF EXISTS `ss_menu`;
CREATE TABLE `ss_menu` (
  `id` varchar(32) NOT NULL,
  `code` varchar(100) NOT NULL DEFAULT '',
  `name` varchar(200) DEFAULT NULL,
  `sort_num` int(11) DEFAULT NULL,
  `status` varchar(32) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `icon` varchar(50) DEFAULT NULL,
  `fk_parent_menu_id` varchar(255) DEFAULT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `parent_name` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "ss_menu"
#

INSERT INTO `ss_menu` VALUES ('2c9f84db42c1486d0142c15f29c00000','ceshicaidan01','测试菜单',8,'enable','#','menu','icon-apple',NULL,'test','2013-12-05 14:08:28','test','2013-12-05 14:08:28',NULL),('402882e84241a9dc014241bb67420000','new','new',2,'enable','#','menu','icon-bell','654321','admin','2013-11-10 19:17:49','admin','2013-11-10 19:17:57','654321Name'),('402882e84241a9dc0142428cc3ed0001','new2','ne',3,'enable','#','menu','icon-legal','402882e84241a9dc014241bb67420000','admin','2013-11-10 23:06:30','admin','2013-12-04 21:23:49','new'),('402882ea425c2ca601425c2ed2f20000','2332','b',4,'enable','#','menu','icon-frown','402882e84241a9dc0142428cc3ed0001','admin','2013-11-15 22:34:01','admin','2013-12-03 19:31:50','ne'),('402882f042517a630142517db03a0000','cc','cc',32,'enable','#','menu','icon-bell-alt','402882e84241a9dc0142428cc3ed0001','admin','2013-11-13 20:44:20','admin','2013-12-03 14:47:39','ne'),('402882f042517a6301425181635f0001','aa','aaa',5,'enable','#','menu','icon-tablet','402882e84241a9dc0142428cc3ed0001','admin','2013-11-13 20:48:23','admin','2013-12-03 14:47:39','ne'),('654321','654321','654321Name',7,'enable','#',NULL,'icon-male',NULL,NULL,NULL,'admin','2013-12-04 20:58:51',NULL),('ff80808142546b3e01425534200d0000','ceshicaidan','测试菜单',1,'enable','#','menu','icon-cny',NULL,'admin','2013-11-14 14:02:28','admin','2013-12-04 20:58:40',NULL);

#
# Structure for table "ss_organization"
#

DROP TABLE IF EXISTS `ss_organization`;
CREATE TABLE `ss_organization` (
  `Id` varchar(32) NOT NULL DEFAULT '',
  `code` varchar(100) NOT NULL DEFAULT '',
  `name` varchar(200) DEFAULT NULL,
  `short_name` varchar(150) DEFAULT NULL,
  `sort_num` int(11) DEFAULT NULL,
  `manager` varchar(50) DEFAULT NULL COMMENT '负责人',
  `assistant_manager` varchar(50) DEFAULT NULL COMMENT '副负责人',
  `phone` varchar(20) DEFAULT NULL,
  `fax` varchar(20) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `is_leaf` int(11) DEFAULT NULL,
  `fk_parent_org_id` varchar(32) DEFAULT NULL,
  `parent_name` varchar(255) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `Inner_Phone` varchar(50) DEFAULT NULL COMMENT '内线',
  `postal_code` varchar(50) DEFAULT NULL COMMENT '邮编',
  `address` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL COMMENT '网址',
  PRIMARY KEY (`Id`),
  KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织机构表';

#
# Data for table "ss_organization"
#

INSERT INTO `ss_organization` VALUES ('2c9f84db426eb16801426ee6796b0000','jituan','集团','集团',0,'负责人',NULL,'010-6554444','','corporation','enable',NULL,NULL,NULL,'','admin','2013-11-19 13:47:47','admin','2013-11-19 13:47:47','内线',NULL,'',''),('2c9f84db426eb16801426ee78d0e0001','jituan.01','测试区域','测试区域',0,'测试区域',NULL,'','','region','enable',NULL,'2c9f84db426eb16801426ee6796b0000','集团','','admin','2013-11-19 13:48:57','admin','2013-11-19 13:52:26','',NULL,'',''),('2c9f84db426eb16801426ee83a9a0002','jituan.01.01','上海分公司','上海分公司',0,'',NULL,'','','company','enable',NULL,'2c9f84db426eb16801426ee78d0e0001','测试区域','','admin','2013-11-19 13:49:42','admin','2013-12-03 20:53:59','',NULL,'',''),('2c9f84db426eb16801426ee91c4f0003','jituan.01.02','湖南分公司','湖南分公司',1,'',NULL,'','','company','enable',NULL,'2c9f84db426eb16801426ee78d0e0001','测试区域','','admin','2013-11-19 13:50:40','admin','2013-12-03 20:53:59','',NULL,'','');

#
# Structure for table "ss_permission"
#

DROP TABLE IF EXISTS `ss_permission`;
CREATE TABLE `ss_permission` (
  `id` varchar(32) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `perms` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "ss_permission"
#


#
# Structure for table "ss_role"
#

DROP TABLE IF EXISTS `ss_role`;
CREATE TABLE `ss_role` (
  `id` varchar(32) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `group_id` varchar(32) DEFAULT NULL,
  `group_name` varchar(150) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `sort_Num` int(11) DEFAULT '0',
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "ss_role"
#

INSERT INTO `ss_role` VALUES ('4028519542553944014255815c720001','nn','nn','enable','business',NULL,NULL,'   nn',NULL,'admin','2013-11-14 15:26:50','admin','2013-11-17 21:48:42'),('402882ed428823e101428832ce270000','11','112','enable','system','2c9f84db426eb16801426ee83a9a0002','上海分公司','  22',NULL,'admin','2013-11-24 11:41:40','admin','2013-11-24 11:54:18'),('402882ed428823e101428832f0bf0001','tt','tt','enable','system',NULL,NULL,'    tt',NULL,'admin','2013-11-24 11:41:49','admin','2013-11-24 12:14:32'),('402882ee42b34ff50142b358b8030000','测试菜单关联','sh01','enable','system','2c9f84db426eb16801426ee83a9a0002','上海分公司','   ',NULL,'admin','2013-12-02 20:46:45','test','2013-12-05 15:09:53'),('402882ee42b34ff50142b358eeba0001','测试角色-上海分公司01','测试角色-上海分公司02','enable','system','2c9f84db426eb16801426ee83a9a0002','上海分公司',' ',NULL,'admin','2013-12-02 20:46:59','admin','2013-12-02 20:46:59'),('402882ee42b34ff50142b3590a6c0002','测试角色-上海分公司01','测试角色-上海分公司03','enable','system','2c9f84db426eb16801426ee83a9a0002','上海分公司',' ',NULL,'admin','2013-12-02 20:47:06','admin','2013-12-02 20:47:06'),('402882ee42b34ff50142b3593b030003','测试角色-上海分公司04','04','enable','system','2c9f84db426eb16801426ee83a9a0002','上海分公司','  ',NULL,'admin','2013-12-02 20:47:18','test','2013-12-05 15:09:00'),('4028e61c4269ceec01426a2b93c60000','role','role','disable','system',NULL,NULL,'  role\r\n ',NULL,'admin','2013-11-18 15:45:10','admin','2013-11-24 12:02:50'),('4028e61c4269ceec01426a3ceef80001','1','1','enable','system',NULL,NULL,'   1',NULL,'admin','2013-11-18 16:04:07','admin','2013-11-24 12:02:31'),('4028e61c4269ceec01426a3d03040002','2','2','enable','system',NULL,NULL,'     2',NULL,'admin','2013-11-18 16:04:12','admin','2013-11-26 14:24:08'),('4028e61c4269ceec01426a3d3a810004','4','4','enable','system',NULL,NULL,'   ',NULL,'admin','2013-11-18 16:04:26','admin','2013-11-27 17:06:31'),('4028e61c4269ceec01426a3d80fc0006','9','9','enable','system',NULL,NULL,'  9',NULL,'admin','2013-11-18 16:04:44','admin','2013-11-24 12:14:53'),('4028e61c4269ceec01426a3db1450007','0','00','enable','system',NULL,NULL,'     ',NULL,'admin','2013-11-18 16:04:57','admin','2013-11-30 22:24:46'),('4028e61c4269ceec01426a3e436f0009','aa','aa','enable','system',NULL,NULL,' ',NULL,'admin','2013-11-18 16:05:34','admin','2013-11-18 16:05:34');

#
# Structure for table "ss_role_menu"
#

DROP TABLE IF EXISTS `ss_role_menu`;
CREATE TABLE `ss_role_menu` (
  `fk_role_id` varchar(32) DEFAULT NULL,
  `fk_menu_id` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "ss_role_menu"
#

INSERT INTO `ss_role_menu` VALUES ('402882ed428823e101428832ce270000','402882e84241a9dc014241bb67420000'),('402882ed428823e101428832ce270000','402882e84241a9dc0142428cc3ed0001'),('402882ed428823e101428832ce270000','402882ea425c2ca601425c2ed2f20000'),('402882ed428823e101428832ce270000','ff80808142546b3e01425534200d0000'),('402882ee42b34ff50142b3590a6c0002','ff80808142546b3e01425534200d0000'),('402882ee42b34ff50142b358b8030000','2c9f84db42c1486d0142c15f29c00000'),('402882ee42b34ff50142b358b8030000','402882e84241a9dc014241bb67420000'),('402882ee42b34ff50142b358b8030000','402882e84241a9dc0142428cc3ed0001'),('402882ee42b34ff50142b358b8030000','402882ea425c2ca601425c2ed2f20000'),('402882ee42b34ff50142b358b8030000','402882f042517a630142517db03a0000'),('402882ee42b34ff50142b358b8030000','402882f042517a6301425181635f0001'),('402882ee42b34ff50142b358b8030000','654321'),('402882ee42b34ff50142b358b8030000','ff80808142546b3e01425534200d0000');

#
# Structure for table "ss_role_permission"
#

DROP TABLE IF EXISTS `ss_role_permission`;
CREATE TABLE `ss_role_permission` (
  `fk_role_id` varchar(32) DEFAULT NULL,
  `fk_permission_id` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "ss_role_permission"
#


#
# Structure for table "ss_user"
#

DROP TABLE IF EXISTS `ss_user`;
CREATE TABLE `ss_user` (
  `id` varchar(32) NOT NULL,
  `login_name` varchar(80) NOT NULL DEFAULT '',
  `nice_name` varchar(80) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `salt` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `user_registered` datetime DEFAULT NULL,
  `user_activation_key` varchar(100) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `telphone` varchar(50) DEFAULT NULL,
  `mobile` varchar(50) DEFAULT NULL,
  `fk_org_id` varchar(32) DEFAULT NULL,
  `org_name` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `login_name` (`login_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "ss_user"
#

INSERT INTO `ss_user` VALUES ('2c9f84e04100f741014100f7451e0000','admin','Gongle','管理员','ac0d9e488d22b3964afa9b61607cdb55a8bc2e81','2c237d8bdd81c02b','skygongle@gmail.com','2013-09-09 12:25:07',NULL,'           ','enable',NULL,'SYSTEM','2013-09-09 12:25:07','admin','2013-11-23 21:13:34',NULL,NULL,NULL,NULL),('2c9f84e04101431a01410143476e0000','test0','','测试用户长','09cba20b32dba38b2418b26f100f4aac6b167244','8308e70ec59ba549','2@q.com','2013-09-09 13:48:08',NULL,'                ','enable',NULL,'SYSTEM','2013-09-09 13:48:08','admin','2013-12-04 15:23:29',NULL,NULL,NULL,NULL),('2c9f84e041015af801410165e6fe0005','test0_4735199067607019',NULL,'测试用户8','d37fc264bf092ef814e8b24dd5a996c20c778497','ce0747a2ab7c8036','admin@q.com','2013-09-09 14:25:57',NULL,'      ','enable',NULL,'SYSTEM','2013-09-09 14:25:57','admin','2013-12-03 19:30:03',NULL,NULL,NULL,NULL),('2c9f84e041015af80141018921ab0006','test',NULL,'测试用户roles','10d7a765e4f54fa8d91fbecace8b2300ec1f5091','265b10381b912bfb','2@3.com','2013-09-09 15:04:26',NULL,'           ','enable',NULL,'SYSTEM','2013-09-09 15:04:26','admin','2013-12-04 20:56:22',NULL,'','2c9f84db426eb16801426ee83a9a0002','上海分公司');

#
# Structure for table "ss_user_role"
#

DROP TABLE IF EXISTS `ss_user_role`;
CREATE TABLE `ss_user_role` (
  `fk_user_id` varchar(32) DEFAULT NULL,
  `fk_role_id` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "ss_user_role"
#

INSERT INTO `ss_user_role` VALUES ('2c9f84e04100f741014100f7451e0000','4028e61c4269ceec01426a3d5b5b0005'),('2c9f84e041015af80141018921ab0006','402882ed428823e101428832ce270000'),('2c9f84e041015af80141018921ab0006','402882ee42b34ff50142b3593b030003'),('2c9f84e041015af80141018921ab0006','402882ee42b34ff50142b358b8030000');

#
# Structure for table "tb_dictionary_category"
#

DROP TABLE IF EXISTS `tb_dictionary_category`;
CREATE TABLE `tb_dictionary_category` (
  `id` char(32) NOT NULL,
  `code` varchar(128) NOT NULL,
  `name` varchar(256) NOT NULL,
  `remark` text,
  `fk_parent_id` char(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `FK181DDF36F99D2701` (`fk_parent_id`),
  CONSTRAINT `FK181DDF36F99D2701` FOREIGN KEY (`fk_parent_id`) REFERENCES `tb_dictionary_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "tb_dictionary_category"
#

INSERT INTO `tb_dictionary_category` VALUES ('402881e437d467d80137d46fc0e50001','state','状态',NULL,NULL),('402881e437d467d80137d4709b9c0002','resource-type','资源类型',NULL,NULL),('402881e437d467d80137d4712ca70003','group-type','组类型',NULL,NULL),('402881e437d47b250137d485274b0004','value-type','值类型',NULL,NULL);

#
# Structure for table "tb_data_dictionary"
#

DROP TABLE IF EXISTS `tb_data_dictionary`;
CREATE TABLE `tb_data_dictionary` (
  `id` char(32) NOT NULL,
  `name` varchar(512) NOT NULL,
  `pin_yin_code` varchar(512) DEFAULT NULL,
  `remark` text,
  `type` varchar(1) NOT NULL,
  `value` varchar(64) NOT NULL,
  `wubi_code` varchar(512) DEFAULT NULL,
  `fk_category_id` char(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKB034107A704DF8AD` (`fk_category_id`),
  CONSTRAINT `FKB034107A704DF8AD` FOREIGN KEY (`fk_category_id`) REFERENCES `tb_dictionary_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "tb_data_dictionary"
#

INSERT INTO `tb_data_dictionary` VALUES ('402881e437d47b250137d481b6920001','启用','QY',NULL,'I','1','YE','402881e437d467d80137d46fc0e50001'),('402881e437d47b250137d481dda30002','禁用','JY',NULL,'I','2','SE','402881e437d467d80137d46fc0e50001'),('402881e437d47b250137d481f23a0003','删除','SC',NULL,'I','3','MB','402881e437d467d80137d46fc0e50001'),('402881e437d47b250137d4870b230005','String','STRING',NULL,'S','S','STRING','402881e437d47b250137d485274b0004'),('402881e437d47b250137d487328e0006','Integer','INTEGER',NULL,'S','I','INTEGER','402881e437d47b250137d485274b0004'),('402881e437d47b250137d487a3af0007','Long','LONG',NULL,'S','L','LONG','402881e437d47b250137d485274b0004'),('402881e437d47b250137d487e23a0008','Double','DOUBLE',NULL,'S','N','DOUBLE','402881e437d47b250137d485274b0004'),('402881e437d47b250137d488416d0009','Date','DATE',NULL,'S','D','DATE','402881e437d47b250137d485274b0004'),('402881e437d47b250137d4885686000a','Boolean','BOOLEAN',NULL,'S','B','BOOLEAN','402881e437d47b250137d485274b0004'),('402881e437d49e430137d4a5e8570003','菜单类型','CDLX','llllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllv','S','01','AUOG','402881e437d467d80137d4709b9c0002'),('402881e437d49e430137d4a61cec0004','资源类型','AQLX',NULL,'S','02','PWOG','402881e437d467d80137d4709b9c0002'),('402881e437d49e430137d4a6f1aa0005','部门','BM',NULL,'S','02','UU','402881e437d467d80137d4712ca70003'),('402881e437d49e430137d4a7783d0006','机构','JG',NULL,'S','01','SS','402881e437d467d80137d4712ca70003'),('402881e437d49e430137d4a7ba1a0007','权限组','JSZ','1','S','03','QQX','402881e437d467d80137d4712ca70003');

#
# Structure for table "tb_group"
#

DROP TABLE IF EXISTS `tb_group`;
CREATE TABLE `tb_group` (
  `id` char(32) NOT NULL,
  `name` varchar(64) NOT NULL,
  `remark` text,
  `state` int(11) NOT NULL,
  `type` varchar(2) NOT NULL,
  `fk_parent_id` char(32) DEFAULT NULL,
  `role` varchar(64) DEFAULT NULL,
  `value` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `role` (`role`) USING BTREE,
  KEY `FKFA285D6EE76F3D70` (`fk_parent_id`),
  CONSTRAINT `FKFA285D6EE76F3D70` FOREIGN KEY (`fk_parent_id`) REFERENCES `tb_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "tb_group"
#

INSERT INTO `tb_group` VALUES ('SJDK3849CKMS3849DJCK2039ZMSK0002','超级管理员',NULL,1,'03',NULL,'','');

#
# Structure for table "tb_resource"
#

DROP TABLE IF EXISTS `tb_resource`;
CREATE TABLE `tb_resource` (
  `id` char(32) NOT NULL,
  `permission` varchar(64) DEFAULT NULL,
  `remark` text,
  `sort` int(11) NOT NULL,
  `name` varchar(64) NOT NULL,
  `type` varchar(2) NOT NULL,
  `value` varchar(512) DEFAULT NULL,
  `fk_parent_id` char(32) DEFAULT NULL,
  `icon` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `text` (`name`),
  UNIQUE KEY `permission` (`permission`),
  KEY `FKECCF42BF7BAAAEE9` (`fk_parent_id`),
  CONSTRAINT `FKECCF42BF7BAAAEE9` FOREIGN KEY (`fk_parent_id`) REFERENCES `tb_resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "tb_resource"
#

INSERT INTO `tb_resource` VALUES ('SJDK3849CKMS3849DJCK2039ZMSK0003',NULL,NULL,1,'权限管理','01','#',NULL,'security32_icon'),('SJDK3849CKMS3849DJCK2039ZMSK0004','perms[user:view]',NULL,2,'用户管理','01','/account/user/view/**','SJDK3849CKMS3849DJCK2039ZMSK0003','user24_icon'),('SJDK3849CKMS3849DJCK2039ZMSK0005','perms[user:create]',NULL,3,'创建用户','02','/account/user/save/**','SJDK3849CKMS3849DJCK2039ZMSK0004',NULL),('SJDK3849CKMS3849DJCK2039ZMSK0006','perms[user:update]',NULL,4,'修改用户','02','/account/user/update/**','SJDK3849CKMS3849DJCK2039ZMSK0004',NULL),('SJDK3849CKMS3849DJCK2039ZMSK0007','perms[user:delete]',NULL,5,'删除用户','02','/account/user/delete/**','SJDK3849CKMS3849DJCK2039ZMSK0004',NULL),('SJDK3849CKMS3849DJCK2039ZMSK0008','perms[user:read]',NULL,6,'查看用户','02','/account/user/read/**','SJDK3849CKMS3849DJCK2039ZMSK0004',NULL),('SJDK3849CKMS3849DJCK2039ZMSK0009','perms[group:view]',NULL,7,'组管理','01','/account/group/view/**','SJDK3849CKMS3849DJCK2039ZMSK0003','group24_icon'),('SJDK3849CKMS3849DJCK2039ZMSK0010','perms[resource:view]',NULL,8,'资源管理','01','/account/resource/view/**','SJDK3849CKMS3849DJCK2039ZMSK0003','resource24_icon'),('SJDK3849CKMS3849DJCK2039ZMSK0011','perms[group:save]',NULL,9,'创建和编辑组','02','/account/group/save/**','SJDK3849CKMS3849DJCK2039ZMSK0009',NULL),('SJDK3849CKMS3849DJCK2039ZMSK0012','perms[group:read]',NULL,10,'查看组','02','/account/group/read/**','SJDK3849CKMS3849DJCK2039ZMSK0009',NULL),('SJDK3849CKMS3849DJCK2039ZMSK0013','perms[group:delete]',NULL,11,'删除组','02','/account/group/delete/**','SJDK3849CKMS3849DJCK2039ZMSK0009',NULL),('SJDK3849CKMS3849DJCK2039ZMSK0014','perms[resource:save]',NULL,12,'创建和编辑资源','02','/account/resource/save/**','SJDK3849CKMS3849DJCK2039ZMSK0010',NULL),('SJDK3849CKMS3849DJCK2039ZMSK0015','perms[resource:read]',NULL,13,'查看资源','02','/account/resource/read/**','SJDK3849CKMS3849DJCK2039ZMSK0010',NULL),('SJDK3849CKMS3849DJCK2039ZMSK0016','perms[resource:delete]',NULL,14,'删除资源','02','/account/resource/delete/**','SJDK3849CKMS3849DJCK2039ZMSK0010',NULL),('SJDK3849CKMS3849DJCK2039ZMSK0017',NULL,NULL,15,'系统管理','01','#',NULL,'system32_icon'),('SJDK3849CKMS3849DJCK2039ZMSK0018','perms[data-dictionary:view]','',22,'数据字典关联','01','/foundation/data-dictionary/view/**','SJDK3849CKMS3849DJCK2039ZMSK0017','dictionary24_icon'),('SJDK3849CKMS3849DJCK2039ZMSK0019','perms[dictionary-category:view]',NULL,17,'字典类别管理','01','/foundation/dictionary-category/view/**','SJDK3849CKMS3849DJCK2039ZMSK0017','category24_icon'),('SJDK3849CKMS3849DJCK2039ZMSK0020','perms[dictionary-category:save]',NULL,18,'创建和编辑字典类别','02','/foundation/dictionary-category/save/**','SJDK3849CKMS3849DJCK2039ZMSK0019',NULL),('SJDK3849CKMS3849DJCK2039ZMSK0021','perms[dictionary-category:delete]',NULL,19,'删除字典类别','02','/foundation/dictionary-category/delete/**','SJDK3849CKMS3849DJCK2039ZMSK0019',NULL),('SJDK3849CKMS3849DJCK2039ZMSK0022','perms[data-dictionary:save]',NULL,20,'创建和编辑数据字典','02','/foundation/data-dictionary/save/**','SJDK3849CKMS3849DJCK2039ZMSK0018',NULL),('SJDK3849CKMS3849DJCK2039ZMSK0023','perms[data-dictionary:delete]',NULL,21,'删除数据字典','02','/foundation/data-dictionary/delete/**','SJDK3849CKMS3849DJCK2039ZMSK0018',NULL),('SJDK3849CKMS3849DJCK2039ZMSK0024','perms[data-dictionary:read]',NULL,22,'查看数据字典','02','/foundation/data-dictionary/read/**','SJDK3849CKMS3849DJCK2039ZMSK0018',NULL),('SJDK3849CKMS3849DJCK2039ZMSK0025','perms[dictionary-category:read]','',24,'查看字典类别','02','/foundation/dictionary-category/read/**','SJDK3849CKMS3849DJCK2039ZMSK0019','');

#
# Structure for table "tb_group_resource"
#

DROP TABLE IF EXISTS `tb_group_resource`;
CREATE TABLE `tb_group_resource` (
  `fk_resource_id` char(32) NOT NULL,
  `fk_group_id` char(32) NOT NULL,
  KEY `FK898FD3BF9CE20AF` (`fk_group_id`),
  KEY `FK898FD3BFE0485F85` (`fk_resource_id`),
  CONSTRAINT `FK898FD3BF9CE20AF` FOREIGN KEY (`fk_group_id`) REFERENCES `tb_group` (`id`),
  CONSTRAINT `FK898FD3BFE0485F85` FOREIGN KEY (`fk_resource_id`) REFERENCES `tb_resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "tb_group_resource"
#

INSERT INTO `tb_group_resource` VALUES ('SJDK3849CKMS3849DJCK2039ZMSK0003','SJDK3849CKMS3849DJCK2039ZMSK0002'),('SJDK3849CKMS3849DJCK2039ZMSK0004','SJDK3849CKMS3849DJCK2039ZMSK0002'),('SJDK3849CKMS3849DJCK2039ZMSK0005','SJDK3849CKMS3849DJCK2039ZMSK0002'),('SJDK3849CKMS3849DJCK2039ZMSK0006','SJDK3849CKMS3849DJCK2039ZMSK0002'),('SJDK3849CKMS3849DJCK2039ZMSK0007','SJDK3849CKMS3849DJCK2039ZMSK0002'),('SJDK3849CKMS3849DJCK2039ZMSK0008','SJDK3849CKMS3849DJCK2039ZMSK0002'),('SJDK3849CKMS3849DJCK2039ZMSK0009','SJDK3849CKMS3849DJCK2039ZMSK0002'),('SJDK3849CKMS3849DJCK2039ZMSK0010','SJDK3849CKMS3849DJCK2039ZMSK0002'),('SJDK3849CKMS3849DJCK2039ZMSK0011','SJDK3849CKMS3849DJCK2039ZMSK0002'),('SJDK3849CKMS3849DJCK2039ZMSK0012','SJDK3849CKMS3849DJCK2039ZMSK0002'),('SJDK3849CKMS3849DJCK2039ZMSK0013','SJDK3849CKMS3849DJCK2039ZMSK0002'),('SJDK3849CKMS3849DJCK2039ZMSK0014','SJDK3849CKMS3849DJCK2039ZMSK0002'),('SJDK3849CKMS3849DJCK2039ZMSK0015','SJDK3849CKMS3849DJCK2039ZMSK0002'),('SJDK3849CKMS3849DJCK2039ZMSK0016','SJDK3849CKMS3849DJCK2039ZMSK0002'),('SJDK3849CKMS3849DJCK2039ZMSK0017','SJDK3849CKMS3849DJCK2039ZMSK0002'),('SJDK3849CKMS3849DJCK2039ZMSK0018','SJDK3849CKMS3849DJCK2039ZMSK0002'),('SJDK3849CKMS3849DJCK2039ZMSK0019','SJDK3849CKMS3849DJCK2039ZMSK0002'),('SJDK3849CKMS3849DJCK2039ZMSK0020','SJDK3849CKMS3849DJCK2039ZMSK0002'),('SJDK3849CKMS3849DJCK2039ZMSK0021','SJDK3849CKMS3849DJCK2039ZMSK0002'),('SJDK3849CKMS3849DJCK2039ZMSK0022','SJDK3849CKMS3849DJCK2039ZMSK0002'),('SJDK3849CKMS3849DJCK2039ZMSK0023','SJDK3849CKMS3849DJCK2039ZMSK0002'),('SJDK3849CKMS3849DJCK2039ZMSK0024','SJDK3849CKMS3849DJCK2039ZMSK0002'),('SJDK3849CKMS3849DJCK2039ZMSK0025','SJDK3849CKMS3849DJCK2039ZMSK0002');

#
# Structure for table "tb_user"
#

DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` char(32) NOT NULL,
  `email` varchar(256) DEFAULT NULL,
  `password` char(32) NOT NULL,
  `realname` varchar(128) NOT NULL,
  `state` int(11) NOT NULL,
  `username` varchar(64) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "tb_user"
#

INSERT INTO `tb_user` VALUES ('SJDK3849CKMS3849DJCK2039ZMSK0001','27637461@qq.com.cn','21232f297a57a5a743894a0e4a801fc3','vincent.chen',1,'admin');

#
# Structure for table "tb_group_user"
#

DROP TABLE IF EXISTS `tb_group_user`;
CREATE TABLE `tb_group_user` (
  `fk_group_id` char(32) NOT NULL,
  `fk_user_id` char(32) NOT NULL,
  KEY `FK92B07BFC9CE20AF` (`fk_group_id`),
  KEY `FK92B07BFCE614E7E5` (`fk_user_id`),
  CONSTRAINT `FK92B07BFC9CE20AF` FOREIGN KEY (`fk_group_id`) REFERENCES `tb_group` (`id`),
  CONSTRAINT `FK92B07BFCE614E7E5` FOREIGN KEY (`fk_user_id`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "tb_group_user"
#

INSERT INTO `tb_group_user` VALUES ('SJDK3849CKMS3849DJCK2039ZMSK0002','SJDK3849CKMS3849DJCK2039ZMSK0001');
