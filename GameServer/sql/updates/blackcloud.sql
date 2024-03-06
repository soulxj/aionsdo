
DROP TABLE IF EXISTS `web_shop`;
CREATE TABLE `web_shop` (
  `unique` int(11) NOT NULL AUTO_INCREMENT,
  `item_owner` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `item_count` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`unique`),
  KEY `item_owner` (`item_owner`),
  CONSTRAINT `FK_web_shop_account_data` FOREIGN KEY (`item_owner`) REFERENCES `account_data` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `account_blackcloud`;
CREATE TABLE `account_blackcloud` (
  `id` int(11) NOT NULL,
  `owner` int(11) DEFAULT NULL,
  `read` tinyint(1) DEFAULT '0',
  `title` varchar(500) DEFAULT NULL,
  `message` varchar(500) DEFAULT NULL,
  `received_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `owner` (`owner`),
  CONSTRAINT `FK__account_data` FOREIGN KEY (`owner`) REFERENCES `account_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de la table aion. blackcloud_item
DROP TABLE IF EXISTS `blackcloud_item`;
CREATE TABLE `blackcloud_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `blackcloud_id` int(11) NOT NULL DEFAULT '0',
  `item_id` int(11) NOT NULL DEFAULT '0',
  `item_count` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `blackcloud_id` (`blackcloud_id`),
  CONSTRAINT `FK__account_blackcloud` FOREIGN KEY (`blackcloud_id`) REFERENCES `account_blackcloud` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;