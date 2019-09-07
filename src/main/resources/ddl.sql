CREATE TABLE `tc_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_created` datetime DEFAULT current_timestamp(),
  `date_last_change` datetime DEFAULT NULL,
  `role_desc` varchar(100) DEFAULT NULL,
  `status_id` tinyint(4) NOT NULL DEFAULT 1,
  `role_code` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `tc_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_desc` varchar(150) NOT NULL,
  `date_created` datetime NOT NULL DEFAULT current_timestamp(),
  `date_last_change` datetime DEFAULT NULL,
  `icon` varchar(250) NOT NULL,
  `icon_color` varchar(50) NOT NULL,
  `label_color` varchar(50) NOT NULL,
  `menu_url` varchar(250) NOT NULL,
  `short_name` varchar(50) NOT NULL,
  `status_id` tinyint(4) NOT NULL,
  `show_tree` tinyint(4) NOT NULL DEFAULT 1 COMMENT '0 = No se muestra en el menu izquierdo, 1 = Se muestra en el arbol del menu izquierdo',
  `father_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`menu_id`),
  UNIQUE KEY `UK_2gj4jwsfadbnwbthx2tpqntng` (`menu_desc`),
  KEY `FKj02kuou5d1841pt387h9g327x` (`father_id`),
  CONSTRAINT `FKj02kuou5d1841pt387h9g327x` FOREIGN KEY (`father_id`) REFERENCES `tc_menu` (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `tc_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_created` datetime DEFAULT current_timestamp(),
  `email` varchar(50) DEFAULT NULL,
  `fullname` varchar(250) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `required_change_password` tinyint(4) NOT NULL,
  `status_id` tinyint(4) NOT NULL,
  `username` varchar(30) DEFAULT NULL,
  `role_id` bigint(20) NOT NULL,
  `photo` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UKkwks4pdoqyh1kugvpgnpv1kax` (`username`),
  UNIQUE KEY `UKf1ipof0s0n0eu4evc6gjls0f0` (`email`),
  KEY `FKyv533e8je81502i04qs35yt1` (`role_id`),
  CONSTRAINT `FK_tc_user_tc_role` FOREIGN KEY (`role_id`) REFERENCES `tc_role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `tc_role_menu` (
  `menu_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  `date_created` datetime NOT NULL DEFAULT current_timestamp(),
  `status_id` tinyint(4) NOT NULL DEFAULT 1,
  `role_menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`role_menu_id`),
  UNIQUE KEY `menu_id_role_id` (`menu_id`,`role_id`),
  KEY `FK_tc_role_menu_tc_role` (`role_id`),
  CONSTRAINT `FK_tc_role_menu_tc_menu` FOREIGN KEY (`menu_id`) REFERENCES `tc_menu` (`menu_id`),
  CONSTRAINT `FK_tc_role_menu_tc_role` FOREIGN KEY (`role_id`) REFERENCES `tc_role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;