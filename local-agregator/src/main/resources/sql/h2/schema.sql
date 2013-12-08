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
  `fk_role_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) 