-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               11.3.2-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for aiondbls_gs
CREATE DATABASE IF NOT EXISTS `aiondbls_gs` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `aiondbls_gs`;

-- Dumping structure for table aiondbls_gs.abyss_rank
DROP TABLE IF EXISTS `abyss_rank`;
CREATE TABLE IF NOT EXISTS `abyss_rank` (
  `player_id` int(11) NOT NULL,
  `daily_ap` int(11) NOT NULL,
  `weekly_ap` int(11) NOT NULL,
  `ap` int(11) NOT NULL,
  `rank` int(2) NOT NULL DEFAULT 1,
  `top_ranking` int(4) NOT NULL,
  `daily_kill` int(5) NOT NULL,
  `weekly_kill` int(5) NOT NULL,
  `all_kill` int(4) NOT NULL DEFAULT 0,
  `max_rank` int(2) NOT NULL DEFAULT 1,
  `last_kill` int(5) NOT NULL,
  `last_ap` int(11) NOT NULL,
  `last_update` decimal(20,0) NOT NULL,
  `rank_pos` int(11) NOT NULL DEFAULT 0,
  `old_rank_pos` int(11) NOT NULL DEFAULT 0,
  `rank_ap` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`player_id`),
  CONSTRAINT `abyss_rank_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.accounts_siel
DROP TABLE IF EXISTS `accounts_siel`;
CREATE TABLE IF NOT EXISTS `accounts_siel` (
  `account_id` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  `init` timestamp NULL DEFAULT NULL,
  `end` timestamp NULL DEFAULT NULL,
  `remain` bigint(20) DEFAULT 0,
  PRIMARY KEY (`account_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.account_blackcloud
DROP TABLE IF EXISTS `account_blackcloud`;
CREATE TABLE IF NOT EXISTS `account_blackcloud` (
  `id` int(11) NOT NULL,
  `owner` int(11) DEFAULT NULL,
  `read` tinyint(1) DEFAULT 0,
  `title` varchar(500) DEFAULT NULL,
  `message` varchar(500) DEFAULT NULL,
  `received_date` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `owner` (`owner`),
  CONSTRAINT `FK__account_data` FOREIGN KEY (`owner`) REFERENCES `account_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.account_data
DROP TABLE IF EXISTS `account_data`;
CREATE TABLE IF NOT EXISTS `account_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `password` varchar(65) NOT NULL,
  `activated` tinyint(1) NOT NULL DEFAULT 1,
  `access_level` tinyint(3) NOT NULL DEFAULT 0,
  `membership` tinyint(3) NOT NULL DEFAULT 0,
  `old_membership` tinyint(3) NOT NULL DEFAULT 0,
  `last_server` tinyint(3) NOT NULL DEFAULT -1,
  `last_ip` varchar(20) DEFAULT NULL,
  `ip_force` varchar(20) DEFAULT NULL,
  `reward_points` int(15) DEFAULT 0,
  `expire` date DEFAULT NULL,
  `toll` int(11) NOT NULL DEFAULT 0,
  `email` varchar(60) DEFAULT NULL,
  `address` varchar(20) DEFAULT NULL,
  `last_mac` varchar(20) NOT NULL DEFAULT 'xx-xx-xx-xx-xx-xx',
  `balance` float NOT NULL DEFAULT 0,
  `vote` int(11) NOT NULL DEFAULT 0,
  `remember_token` varchar(100) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `rewarded_status` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=11048 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.account_rewards
DROP TABLE IF EXISTS `account_rewards`;
CREATE TABLE IF NOT EXISTS `account_rewards` (
  `uniqId` int(11) NOT NULL AUTO_INCREMENT,
  `accountId` int(11) NOT NULL,
  `added` varchar(70) NOT NULL DEFAULT '',
  `points` decimal(20,0) NOT NULL DEFAULT 0,
  `received` varchar(70) NOT NULL DEFAULT '0',
  `rewarded` tinyint(1) NOT NULL DEFAULT 0,
  `luna` decimal(20,0) NOT NULL DEFAULT 0,
  PRIMARY KEY (`uniqId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.account_time
DROP TABLE IF EXISTS `account_time`;
CREATE TABLE IF NOT EXISTS `account_time` (
  `account_id` int(11) NOT NULL,
  `last_active` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `expiration_time` timestamp NULL DEFAULT NULL,
  `session_duration` int(10) DEFAULT 0,
  `accumulated_online` int(10) DEFAULT 0,
  `accumulated_rest` int(10) DEFAULT 0,
  `penalty_end` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.announcements
DROP TABLE IF EXISTS `announcements`;
CREATE TABLE IF NOT EXISTS `announcements` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `announce` text NOT NULL,
  `faction` enum('ALL','ASMODIANS','ELYOS') NOT NULL DEFAULT 'ALL',
  `type` enum('SHOUT','ORANGE','YELLOW','WHITE','SYSTEM') NOT NULL DEFAULT 'SYSTEM',
  `delay` int(4) NOT NULL DEFAULT 1800,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.banned_ip
DROP TABLE IF EXISTS `banned_ip`;
CREATE TABLE IF NOT EXISTS `banned_ip` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mask` varchar(45) NOT NULL,
  `time_end` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mask` (`mask`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.banned_mac
DROP TABLE IF EXISTS `banned_mac`;
CREATE TABLE IF NOT EXISTS `banned_mac` (
  `uniId` int(10) NOT NULL AUTO_INCREMENT,
  `address` varchar(20) NOT NULL,
  `time` timestamp NOT NULL DEFAULT '2010-01-01 06:00:00' ON UPDATE current_timestamp(),
  `details` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`uniId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.blackcloud_item
DROP TABLE IF EXISTS `blackcloud_item`;
CREATE TABLE IF NOT EXISTS `blackcloud_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `blackcloud_id` int(11) NOT NULL DEFAULT 0,
  `item_id` int(11) NOT NULL DEFAULT 0,
  `item_count` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `blackcloud_id` (`blackcloud_id`),
  CONSTRAINT `FK__account_blackcloud` FOREIGN KEY (`blackcloud_id`) REFERENCES `account_blackcloud` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.blocks
DROP TABLE IF EXISTS `blocks`;
CREATE TABLE IF NOT EXISTS `blocks` (
  `player` int(11) NOT NULL,
  `blocked_player` int(11) NOT NULL,
  `reason` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`player`,`blocked_player`),
  KEY `blocked_player` (`blocked_player`),
  CONSTRAINT `blocks_ibfk_1` FOREIGN KEY (`player`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `blocks_ibfk_2` FOREIGN KEY (`blocked_player`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.bookmark
DROP TABLE IF EXISTS `bookmark`;
CREATE TABLE IF NOT EXISTS `bookmark` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `char_id` int(11) NOT NULL,
  `x` float NOT NULL,
  `y` float NOT NULL,
  `z` float NOT NULL,
  `world_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.broker
DROP TABLE IF EXISTS `broker`;
CREATE TABLE IF NOT EXISTS `broker` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_pointer` int(11) NOT NULL DEFAULT 0,
  `item_id` int(11) NOT NULL,
  `item_count` bigint(20) NOT NULL,
  `item_creator` varchar(50) DEFAULT NULL,
  `seller` varchar(50) DEFAULT NULL,
  `price` bigint(20) NOT NULL DEFAULT 0,
  `broker_race` enum('ELYOS','ASMODIAN') NOT NULL,
  `expire_time` timestamp NOT NULL DEFAULT '2010-01-01 06:00:00',
  `settle_time` timestamp NOT NULL DEFAULT '2010-01-01 06:00:00',
  `seller_id` int(11) NOT NULL,
  `is_sold` tinyint(1) NOT NULL,
  `is_settled` tinyint(1) NOT NULL,
  `is_splitsell` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `seller_id` (`seller_id`),
  CONSTRAINT `broker_ibfk_1` FOREIGN KEY (`seller_id`) REFERENCES `players` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.craft_cooldowns
DROP TABLE IF EXISTS `craft_cooldowns`;
CREATE TABLE IF NOT EXISTS `craft_cooldowns` (
  `player_id` int(11) NOT NULL,
  `delay_id` int(11) unsigned NOT NULL,
  `reuse_time` bigint(13) unsigned NOT NULL,
  PRIMARY KEY (`player_id`,`delay_id`),
  CONSTRAINT `craft_cooldowns_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.event_items
DROP TABLE IF EXISTS `event_items`;
CREATE TABLE IF NOT EXISTS `event_items` (
  `player_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `counts` int(10) unsigned NOT NULL,
  PRIMARY KEY (`player_id`,`item_id`),
  CONSTRAINT `event_items_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=COMPACT;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.friends
DROP TABLE IF EXISTS `friends`;
CREATE TABLE IF NOT EXISTS `friends` (
  `player` int(11) NOT NULL,
  `friend` int(11) NOT NULL,
  `note` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`player`,`friend`),
  KEY `friend` (`friend`),
  CONSTRAINT `friends_ibfk_1` FOREIGN KEY (`player`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `friends_ibfk_2` FOREIGN KEY (`friend`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.gameservers
DROP TABLE IF EXISTS `gameservers`;
CREATE TABLE IF NOT EXISTS `gameservers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mask` varchar(45) NOT NULL,
  `password` varchar(65) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.guides
DROP TABLE IF EXISTS `guides`;
CREATE TABLE IF NOT EXISTS `guides` (
  `guide_id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) NOT NULL,
  `title` varchar(80) NOT NULL,
  PRIMARY KEY (`guide_id`),
  KEY `player_id` (`player_id`),
  CONSTRAINT `guides_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=542778 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.ingameshop
DROP TABLE IF EXISTS `ingameshop`;
CREATE TABLE IF NOT EXISTS `ingameshop` (
  `object_id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) NOT NULL,
  `item_count` bigint(13) NOT NULL DEFAULT 0,
  `item_price` bigint(13) NOT NULL DEFAULT 0,
  `category` tinyint(1) NOT NULL DEFAULT 0,
  `sub_category` tinyint(1) NOT NULL DEFAULT 0,
  `list` int(11) NOT NULL DEFAULT 0,
  `sales_ranking` int(11) NOT NULL DEFAULT 0,
  `item_type` tinyint(1) NOT NULL DEFAULT 0,
  `gift` tinyint(1) NOT NULL DEFAULT 0,
  `title_description` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`object_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.inventory
DROP TABLE IF EXISTS `inventory`;
CREATE TABLE IF NOT EXISTS `inventory` (
  `item_unique_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `item_count` bigint(20) NOT NULL DEFAULT 0,
  `item_color` int(11) NOT NULL DEFAULT 0,
  `color_expires` int(11) DEFAULT NULL,
  `item_owner` int(11) NOT NULL,
  `item_creator` varchar(50) DEFAULT NULL,
  `itemCreationTime` timestamp NOT NULL DEFAULT '2013-01-01 04:00:01',
  `expire_time` int(11) NOT NULL DEFAULT 0,
  `is_equiped` tinyint(1) NOT NULL DEFAULT 0,
  `is_soul_bound` tinyint(1) NOT NULL DEFAULT 0,
  `slot` bigint(20) NOT NULL DEFAULT 0,
  `item_location` tinyint(1) DEFAULT 0,
  `enchant` tinyint(1) DEFAULT 0,
  `item_skin` int(11) NOT NULL DEFAULT 0,
  `fusioned_item` int(11) NOT NULL DEFAULT 0,
  `optional_socket` int(1) NOT NULL DEFAULT 0,
  `optional_fusion_socket` int(1) NOT NULL DEFAULT 0,
  `activation_count` int(11) NOT NULL DEFAULT 0,
  `charge` mediumint(9) NOT NULL DEFAULT 0,
  PRIMARY KEY (`item_unique_id`),
  KEY `item_owner` (`item_owner`),
  KEY `item_location` (`item_location`),
  KEY `is_equiped` (`is_equiped`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.item
DROP TABLE IF EXISTS `item`;
CREATE TABLE IF NOT EXISTS `item` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `code` int(255) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `image` varchar(255) NOT NULL,
  `quantite` int(255) NOT NULL,
  `prix` int(255) NOT NULL,
  `id_categorie` int(255) NOT NULL,
  `qualite` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `createur` varchar(255) NOT NULL,
  `date_creation` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.item_cooldowns
DROP TABLE IF EXISTS `item_cooldowns`;
CREATE TABLE IF NOT EXISTS `item_cooldowns` (
  `player_id` int(11) NOT NULL,
  `delay_id` int(11) NOT NULL,
  `use_delay` smallint(5) unsigned NOT NULL,
  `reuse_time` bigint(13) NOT NULL,
  PRIMARY KEY (`player_id`,`delay_id`),
  CONSTRAINT `item_cooldowns_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.item_stones
DROP TABLE IF EXISTS `item_stones`;
CREATE TABLE IF NOT EXISTS `item_stones` (
  `item_unique_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `slot` int(2) NOT NULL,
  `category` int(2) NOT NULL DEFAULT 0,
  PRIMARY KEY (`item_unique_id`,`slot`,`category`),
  CONSTRAINT `item_stones_ibfk_1` FOREIGN KEY (`item_unique_id`) REFERENCES `inventory` (`item_unique_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.legions
DROP TABLE IF EXISTS `legions`;
CREATE TABLE IF NOT EXISTS `legions` (
  `id` int(11) NOT NULL,
  `name` varchar(32) NOT NULL,
  `level` int(1) NOT NULL DEFAULT 1,
  `contribution_points` bigint(20) NOT NULL DEFAULT 0,
  `deputy_permission` int(11) NOT NULL DEFAULT 7692,
  `centurion_permission` int(11) NOT NULL DEFAULT 7176,
  `legionary_permission` int(11) NOT NULL DEFAULT 6144,
  `volunteer_permission` int(11) NOT NULL DEFAULT 2048,
  `disband_time` int(11) NOT NULL DEFAULT 0,
  `rank_cp` int(11) NOT NULL DEFAULT 0,
  `rank_pos` int(11) NOT NULL DEFAULT 0,
  `old_rank_pos` int(11) NOT NULL DEFAULT 0,
  `description` varchar(255) DEFAULT NULL,
  `joinType` int(1) DEFAULT 0,
  `minJoinLevel` int(3) DEFAULT 0,
  `mainActivity` int(3) DEFAULT 0,
  `growthLevel` int(11) DEFAULT 1,
  `growthExp` bigint(20) DEFAULT 0,
  `essenceCoin` bigint(20) DEFAULT 0,
  `essenceFragment` bigint(20) DEFAULT 0,
  `productivityStep` int(11) DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_unique` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.legion_announcement_list
DROP TABLE IF EXISTS `legion_announcement_list`;
CREATE TABLE IF NOT EXISTS `legion_announcement_list` (
  `legion_id` int(11) NOT NULL,
  `announcement` varchar(256) NOT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp(),
  KEY `legion_id` (`legion_id`),
  CONSTRAINT `legion_announcement_list_ibfk_1` FOREIGN KEY (`legion_id`) REFERENCES `legions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.legion_emblems
DROP TABLE IF EXISTS `legion_emblems`;
CREATE TABLE IF NOT EXISTS `legion_emblems` (
  `legion_id` int(11) NOT NULL,
  `emblem_id` int(1) NOT NULL DEFAULT 0,
  `color_r` int(3) NOT NULL DEFAULT 0,
  `color_g` int(3) NOT NULL DEFAULT 0,
  `color_b` int(3) NOT NULL DEFAULT 0,
  `emblem_type` enum('DEFAULT','CUSTOM') NOT NULL DEFAULT 'DEFAULT',
  `emblem_data` longblob DEFAULT NULL,
  PRIMARY KEY (`legion_id`),
  CONSTRAINT `legion_emblems_ibfk_1` FOREIGN KEY (`legion_id`) REFERENCES `legions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.legion_history
DROP TABLE IF EXISTS `legion_history`;
CREATE TABLE IF NOT EXISTS `legion_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `legion_id` int(11) NOT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp(),
  `history_type` enum('CREATE','JOIN','KICK','APPOINTED','EMBLEM_REGISTER','EMBLEM_MODIFIED','ITEM_DEPOSIT','ITEM_WITHDRAW','KINAH_DEPOSIT','KINAH_WITHDRAW','LEVEL_UP') NOT NULL,
  `name` varchar(50) NOT NULL,
  `tab_id` smallint(3) NOT NULL DEFAULT 0,
  `description` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `legion_id` (`legion_id`),
  CONSTRAINT `legion_history_ibfk_1` FOREIGN KEY (`legion_id`) REFERENCES `legions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=25435 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.legion_join_requests
DROP TABLE IF EXISTS `legion_join_requests`;
CREATE TABLE IF NOT EXISTS `legion_join_requests` (
  `legionId` int(11) NOT NULL DEFAULT 0,
  `playerId` int(11) NOT NULL DEFAULT 0,
  `playerName` varchar(64) NOT NULL DEFAULT '',
  `playerClassId` int(2) NOT NULL DEFAULT 0,
  `playerRaceId` int(2) NOT NULL DEFAULT 0,
  `playerLevel` int(4) NOT NULL DEFAULT 0,
  `playerGenderId` int(2) NOT NULL DEFAULT 0,
  `joinRequestMsg` varchar(40) NOT NULL DEFAULT '',
  `date` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`legionId`,`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.legion_members
DROP TABLE IF EXISTS `legion_members`;
CREATE TABLE IF NOT EXISTS `legion_members` (
  `legion_id` int(11) NOT NULL,
  `player_id` int(11) NOT NULL,
  `nickname` varchar(10) NOT NULL DEFAULT '',
  `rank` enum('BRIGADE_GENERAL','CENTURION','LEGIONARY','DEPUTY','VOLUNTEER') NOT NULL DEFAULT 'VOLUNTEER',
  `selfintro` varchar(32) DEFAULT '',
  PRIMARY KEY (`player_id`),
  KEY `player_id` (`player_id`),
  KEY `legion_id` (`legion_id`),
  CONSTRAINT `legion_members_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `legion_members_ibfk_2` FOREIGN KEY (`legion_id`) REFERENCES `legions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.mail
DROP TABLE IF EXISTS `mail`;
CREATE TABLE IF NOT EXISTS `mail` (
  `mail_unique_id` int(11) NOT NULL,
  `mail_recipient_id` int(11) NOT NULL,
  `sender_name` varchar(26) NOT NULL,
  `mail_title` varchar(20) NOT NULL,
  `mail_message` varchar(1000) NOT NULL,
  `unread` tinyint(4) NOT NULL DEFAULT 1,
  `attached_item_id` int(11) NOT NULL,
  `attached_kinah_count` bigint(20) NOT NULL,
  `express` tinyint(4) NOT NULL DEFAULT 0,
  `recieved_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`mail_unique_id`),
  KEY `mail_recipient_id` (`mail_recipient_id`),
  CONSTRAINT `FK_mail` FOREIGN KEY (`mail_recipient_id`) REFERENCES `players` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.old_names
DROP TABLE IF EXISTS `old_names`;
CREATE TABLE IF NOT EXISTS `old_names` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `player_id` int(11) NOT NULL,
  `old_name` varchar(50) NOT NULL,
  `new_name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `player_id` (`player_id`),
  CONSTRAINT `old_names_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.petitions
DROP TABLE IF EXISTS `petitions`;
CREATE TABLE IF NOT EXISTS `petitions` (
  `id` bigint(11) NOT NULL,
  `player_id` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `message` text NOT NULL,
  `add_data` varchar(255) DEFAULT NULL,
  `time` bigint(11) NOT NULL DEFAULT 0,
  `status` enum('PENDING','IN_PROGRESS','REPLIED') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.players
DROP TABLE IF EXISTS `players`;
CREATE TABLE IF NOT EXISTS `players` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `account_id` int(11) NOT NULL,
  `account_name` varchar(50) NOT NULL,
  `exp` bigint(20) NOT NULL DEFAULT 0,
  `recoverexp` bigint(20) NOT NULL DEFAULT 0,
  `x` float NOT NULL,
  `y` float NOT NULL,
  `z` float NOT NULL,
  `heading` int(11) NOT NULL,
  `world_id` int(11) NOT NULL,
  `gender` enum('MALE','FEMALE') NOT NULL,
  `race` enum('ASMODIANS','ELYOS') NOT NULL,
  `player_class` enum('WARRIOR','GLADIATOR','TEMPLAR','SCOUT','ASSASSIN','RANGER','MAGE','SORCERER','SPIRIT_MASTER','PRIEST','CLERIC','CHANTER','MONK','THUNDERER') NOT NULL,
  `creation_date` timestamp NULL DEFAULT NULL,
  `deletion_date` timestamp NULL DEFAULT NULL,
  `last_online` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp(),
  `quest_expands` tinyint(1) NOT NULL DEFAULT 0,
  `advenced_stigma_slot_size` tinyint(1) NOT NULL DEFAULT 0,
  `warehouse_size` tinyint(1) NOT NULL DEFAULT 0,
  `mailbox_letters` tinyint(4) NOT NULL DEFAULT 0,
  `bind_point` int(11) NOT NULL DEFAULT 0,
  `title_id` int(3) NOT NULL DEFAULT -1,
  `online` tinyint(1) NOT NULL DEFAULT 0,
  `note` text DEFAULT NULL,
  `npc_expands` tinyint(1) NOT NULL DEFAULT 0,
  `world_owner` int(11) NOT NULL DEFAULT 0,
  `dp` int(3) NOT NULL DEFAULT 0,
  `soul_sickness` tinyint(1) unsigned NOT NULL DEFAULT 0,
  `reposte_energy` bigint(20) NOT NULL DEFAULT 0,
  `mentor_flag_time` int(11) NOT NULL DEFAULT 0,
  `last_transfer_time` decimal(20,0) NOT NULL DEFAULT 0,
  `wardrobe_size` int(11) DEFAULT 1,
  `frenzy_points` int(4) DEFAULT 0,
  `frenzy_count` int(1) DEFAULT 0,
  `join_legion_id` int(11) DEFAULT 0,
  `join_state` enum('NONE','DENIED','ACCEPTED') DEFAULT 'NONE',
  `guildCoins` int(11) DEFAULT 0,
  `sp` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_unique` (`name`),
  KEY `account_id` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.player_appearance
DROP TABLE IF EXISTS `player_appearance`;
CREATE TABLE IF NOT EXISTS `player_appearance` (
  `player_id` int(11) NOT NULL,
  `voice` int(11) NOT NULL,
  `skin_rgb` int(11) NOT NULL,
  `hair_rgb` int(11) NOT NULL,
  `eye_rgb` int(11) NOT NULL,
  `lip_rgb` int(11) NOT NULL,
  `face` int(11) NOT NULL,
  `hair` int(11) NOT NULL,
  `deco` int(11) NOT NULL,
  `tattoo` int(11) NOT NULL,
  `face_contour` int(11) NOT NULL,
  `expression` int(11) NOT NULL,
  `jaw_line` int(11) NOT NULL,
  `forehead` int(11) NOT NULL,
  `eye_height` int(11) NOT NULL,
  `eye_space` int(11) NOT NULL,
  `eye_width` int(11) NOT NULL,
  `eye_size` int(11) NOT NULL,
  `eye_shape` int(11) NOT NULL,
  `eye_angle` int(11) NOT NULL,
  `brow_height` int(11) NOT NULL,
  `brow_angle` int(11) NOT NULL,
  `brow_shape` int(11) NOT NULL,
  `nose` int(11) NOT NULL,
  `nose_bridge` int(11) NOT NULL,
  `nose_width` int(11) NOT NULL,
  `nose_tip` int(11) NOT NULL,
  `cheek` int(11) NOT NULL,
  `lip_height` int(11) NOT NULL,
  `mouth_size` int(11) NOT NULL,
  `lip_size` int(11) NOT NULL,
  `smile` int(11) NOT NULL,
  `lip_shape` int(11) NOT NULL,
  `jaw_height` int(11) NOT NULL,
  `chin_jut` int(11) NOT NULL,
  `ear_shape` int(11) NOT NULL,
  `head_size` int(11) NOT NULL,
  `neck` int(11) NOT NULL,
  `neck_length` int(11) NOT NULL,
  `shoulder_size` int(11) NOT NULL,
  `torso` int(11) NOT NULL,
  `chest` int(11) NOT NULL,
  `waist` int(11) NOT NULL,
  `hips` int(11) NOT NULL,
  `arm_thickness` int(11) NOT NULL,
  `hand_size` int(11) NOT NULL,
  `leg_thickness` int(11) NOT NULL,
  `facial_rate` int(11) NOT NULL,
  `foot_size` int(11) NOT NULL,
  `arm_length` int(11) NOT NULL,
  `leg_length` int(11) NOT NULL,
  `shoulders` int(11) NOT NULL,
  `face_shape` int(11) NOT NULL,
  `height` float NOT NULL,
  PRIMARY KEY (`player_id`),
  CONSTRAINT `player_id_fk` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.player_battle_pass
DROP TABLE IF EXISTS `player_battle_pass`;
CREATE TABLE IF NOT EXISTS `player_battle_pass` (
  `id` int(11) NOT NULL,
  `player_id` int(11) NOT NULL,
  `level` int(11) NOT NULL DEFAULT 1,
  `exp` int(20) NOT NULL DEFAULT 0
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.player_battle_pass_quest
DROP TABLE IF EXISTS `player_battle_pass_quest`;
CREATE TABLE IF NOT EXISTS `player_battle_pass_quest` (
  `object_id` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  `player_id` int(11) NOT NULL,
  `state` tinyint(1) NOT NULL,
  `step` int(11) NOT NULL DEFAULT 0,
  `start_date` timestamp NULL DEFAULT NULL,
  `end_date` timestamp NULL DEFAULT NULL,
  `type` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.player_battle_pass_reward
DROP TABLE IF EXISTS `player_battle_pass_reward`;
CREATE TABLE IF NOT EXISTS `player_battle_pass_reward` (
  `id` int(11) NOT NULL,
  `player_id` int(11) NOT NULL,
  `season_id` int(11) NOT NULL,
  `rewarded` tinyint(1) NOT NULL DEFAULT 0,
  `unlockReward` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.player_bind_point
DROP TABLE IF EXISTS `player_bind_point`;
CREATE TABLE IF NOT EXISTS `player_bind_point` (
  `player_id` int(11) NOT NULL,
  `map_id` int(11) NOT NULL,
  `x` float NOT NULL,
  `y` float NOT NULL,
  `z` float NOT NULL,
  `heading` int(3) NOT NULL,
  PRIMARY KEY (`player_id`),
  CONSTRAINT `player_bind_point_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.player_cooldowns
DROP TABLE IF EXISTS `player_cooldowns`;
CREATE TABLE IF NOT EXISTS `player_cooldowns` (
  `player_id` int(11) NOT NULL,
  `cooldown_id` int(6) NOT NULL,
  `reuse_delay` bigint(13) NOT NULL,
  PRIMARY KEY (`player_id`,`cooldown_id`),
  CONSTRAINT `player_cooldowns_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.player_effects
DROP TABLE IF EXISTS `player_effects`;
CREATE TABLE IF NOT EXISTS `player_effects` (
  `player_id` int(11) NOT NULL,
  `skill_id` int(11) NOT NULL,
  `skill_lvl` tinyint(4) NOT NULL,
  `current_time` int(11) NOT NULL,
  `end_time` bigint(13) NOT NULL,
  PRIMARY KEY (`player_id`,`skill_id`),
  CONSTRAINT `player_effects_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.player_emotions
DROP TABLE IF EXISTS `player_emotions`;
CREATE TABLE IF NOT EXISTS `player_emotions` (
  `player_id` int(11) NOT NULL,
  `emotion` int(11) NOT NULL,
  `remaining` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`player_id`,`emotion`),
  CONSTRAINT `player_emotions_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.player_equipment_setting
DROP TABLE IF EXISTS `player_equipment_setting`;
CREATE TABLE IF NOT EXISTS `player_equipment_setting` (
  `player_id` int(11) NOT NULL,
  `slot` int(11) NOT NULL,
  `display` tinyint(2) NOT NULL DEFAULT 0,
  `m_hand` int(21) NOT NULL DEFAULT 0,
  `s_hand` int(21) NOT NULL DEFAULT 0,
  `helmet` int(21) NOT NULL DEFAULT 0,
  `torso` int(21) NOT NULL DEFAULT 0,
  `glove` int(21) NOT NULL DEFAULT 0,
  `boots` int(21) NOT NULL DEFAULT 0,
  `earrings_left` int(21) NOT NULL DEFAULT 0,
  `earrings_right` int(21) NOT NULL DEFAULT 0,
  `ring_left` int(21) NOT NULL DEFAULT 0,
  `ring_right` int(21) NOT NULL DEFAULT 0,
  `necklace` int(21) NOT NULL DEFAULT 0,
  `shoulder` int(21) NOT NULL DEFAULT 0,
  `pants` int(21) NOT NULL DEFAULT 0,
  `powershard_left` int(21) NOT NULL DEFAULT 0,
  `powershard_right` int(21) NOT NULL DEFAULT 0,
  `wings` int(21) NOT NULL DEFAULT 0,
  `waist` int(21) NOT NULL DEFAULT 0,
  `m_off_hand` int(21) NOT NULL DEFAULT 0,
  `s_off_hand` int(21) NOT NULL DEFAULT 0,
  PRIMARY KEY (`player_id`,`slot`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.player_events_window
DROP TABLE IF EXISTS `player_events_window`;
CREATE TABLE IF NOT EXISTS `player_events_window` (
  `account_id` int(50) NOT NULL,
  `event_id` int(11) DEFAULT NULL,
  `last_stamp` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `elapsed` int(11) NOT NULL DEFAULT 0,
  `reward_recived_count` int(11) NOT NULL DEFAULT 0,
  KEY `account_id` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.player_life_stats
DROP TABLE IF EXISTS `player_life_stats`;
CREATE TABLE IF NOT EXISTS `player_life_stats` (
  `player_id` int(11) NOT NULL,
  `hp` int(11) NOT NULL DEFAULT 1,
  `mp` int(11) NOT NULL DEFAULT 1,
  `fp` int(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`player_id`),
  CONSTRAINT `FK_player_life_stats` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.player_macrosses
DROP TABLE IF EXISTS `player_macrosses`;
CREATE TABLE IF NOT EXISTS `player_macrosses` (
  `player_id` int(11) NOT NULL,
  `order` int(3) NOT NULL,
  `macro` text NOT NULL,
  UNIQUE KEY `main` (`player_id`,`order`),
  CONSTRAINT `player_macrosses_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.player_motions
DROP TABLE IF EXISTS `player_motions`;
CREATE TABLE IF NOT EXISTS `player_motions` (
  `player_id` int(11) NOT NULL,
  `motion_id` int(3) NOT NULL,
  `time` int(11) NOT NULL DEFAULT 0,
  `active` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`player_id`,`motion_id`) USING BTREE,
  CONSTRAINT `motions_player_id_fk` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.player_npc_factions
DROP TABLE IF EXISTS `player_npc_factions`;
CREATE TABLE IF NOT EXISTS `player_npc_factions` (
  `player_id` int(11) NOT NULL,
  `faction_id` int(2) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `time` int(11) NOT NULL,
  `state` enum('NOTING','START','COMPLETE') NOT NULL DEFAULT 'NOTING',
  `quest_id` int(6) NOT NULL DEFAULT 0,
  PRIMARY KEY (`player_id`,`faction_id`),
  CONSTRAINT `player_npc_factions_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.player_passkey
DROP TABLE IF EXISTS `player_passkey`;
CREATE TABLE IF NOT EXISTS `player_passkey` (
  `account_id` int(11) NOT NULL,
  `passkey` varchar(65) NOT NULL,
  PRIMARY KEY (`account_id`,`passkey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.player_pets
DROP TABLE IF EXISTS `player_pets`;
CREATE TABLE IF NOT EXISTS `player_pets` (
  `player_id` int(11) NOT NULL,
  `pet_id` int(11) NOT NULL,
  `decoration` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `hungry_level` tinyint(4) NOT NULL DEFAULT 0,
  `feed_progress` int(11) NOT NULL DEFAULT 0,
  `reuse_time` bigint(20) NOT NULL DEFAULT 0,
  `birthday` timestamp NOT NULL DEFAULT current_timestamp(),
  `mood_started` bigint(20) NOT NULL DEFAULT 0,
  `counter` int(11) NOT NULL DEFAULT 0,
  `mood_cd_started` bigint(20) NOT NULL DEFAULT 0,
  `gift_cd_started` bigint(20) NOT NULL DEFAULT 0,
  `dopings` varchar(80) CHARACTER SET ascii COLLATE ascii_general_ci DEFAULT NULL,
  `despawn_time` timestamp NULL DEFAULT NULL,
  `expire_time` int(11) NOT NULL,
  PRIMARY KEY (`player_id`,`pet_id`),
  CONSTRAINT `FK_player_pets` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.player_punishments
DROP TABLE IF EXISTS `player_punishments`;
CREATE TABLE IF NOT EXISTS `player_punishments` (
  `player_id` int(11) NOT NULL,
  `punishment_type` enum('PRISON','GATHER','CHARBAN') NOT NULL,
  `start_time` int(10) unsigned DEFAULT 0,
  `duration` int(10) unsigned DEFAULT 0,
  `reason` text DEFAULT NULL,
  PRIMARY KEY (`player_id`,`punishment_type`),
  CONSTRAINT `player_punishments_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.player_quests
DROP TABLE IF EXISTS `player_quests`;
CREATE TABLE IF NOT EXISTS `player_quests` (
  `player_id` int(11) NOT NULL,
  `quest_id` int(10) unsigned NOT NULL DEFAULT 0,
  `status` varchar(10) NOT NULL DEFAULT 'NONE',
  `quest_vars` int(10) unsigned NOT NULL DEFAULT 0,
  `complete_count` int(3) unsigned NOT NULL DEFAULT 0,
  `complete_time` timestamp NULL DEFAULT NULL,
  `next_repeat_time` timestamp NULL DEFAULT NULL,
  `reward` smallint(3) DEFAULT NULL,
  PRIMARY KEY (`player_id`,`quest_id`),
  CONSTRAINT `player_quests_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.player_recipes
DROP TABLE IF EXISTS `player_recipes`;
CREATE TABLE IF NOT EXISTS `player_recipes` (
  `player_id` int(11) NOT NULL,
  `recipe_id` int(11) NOT NULL,
  PRIMARY KEY (`player_id`,`recipe_id`),
  CONSTRAINT `player_recipes_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.player_settings
DROP TABLE IF EXISTS `player_settings`;
CREATE TABLE IF NOT EXISTS `player_settings` (
  `player_id` int(11) NOT NULL,
  `settings_type` tinyint(1) NOT NULL,
  `settings` blob NOT NULL,
  PRIMARY KEY (`player_id`,`settings_type`),
  CONSTRAINT `ps_pl_fk` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.player_skills
DROP TABLE IF EXISTS `player_skills`;
CREATE TABLE IF NOT EXISTS `player_skills` (
  `player_id` int(11) NOT NULL,
  `skill_id` int(11) NOT NULL,
  `skill_level` int(3) NOT NULL DEFAULT 1,
  PRIMARY KEY (`player_id`,`skill_id`),
  CONSTRAINT `player_skills_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.player_titles
DROP TABLE IF EXISTS `player_titles`;
CREATE TABLE IF NOT EXISTS `player_titles` (
  `player_id` int(11) NOT NULL,
  `title_id` int(11) NOT NULL,
  `remaining` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`player_id`,`title_id`),
  CONSTRAINT `player_titles_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.player_upgrade_arcade
DROP TABLE IF EXISTS `player_upgrade_arcade`;
CREATE TABLE IF NOT EXISTS `player_upgrade_arcade` (
  `player_id` int(11) NOT NULL,
  `frenzy_meter` int(11) NOT NULL,
  `upgrade_lvl` int(11) NOT NULL,
  PRIMARY KEY (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.player_vars
DROP TABLE IF EXISTS `player_vars`;
CREATE TABLE IF NOT EXISTS `player_vars` (
  `player_id` int(11) NOT NULL,
  `param` varchar(255) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  `time` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`player_id`,`param`),
  CONSTRAINT `player_vars_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.player_wardrobe
DROP TABLE IF EXISTS `player_wardrobe`;
CREATE TABLE IF NOT EXISTS `player_wardrobe` (
  `player_id` int(11) DEFAULT NULL,
  `object_id` int(11) NOT NULL,
  `item_id` int(11) DEFAULT NULL,
  `color` int(11) DEFAULT 0,
  `like` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`object_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.portal_cooldowns
DROP TABLE IF EXISTS `portal_cooldowns`;
CREATE TABLE IF NOT EXISTS `portal_cooldowns` (
  `player_id` int(11) NOT NULL,
  `world_id` int(11) NOT NULL,
  `reuse_time` bigint(13) NOT NULL,
  `entry_count` int(2) NOT NULL DEFAULT 0,
  PRIMARY KEY (`player_id`,`world_id`),
  CONSTRAINT `portal_cooldowns_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.realms
DROP TABLE IF EXISTS `realms`;
CREATE TABLE IF NOT EXISTS `realms` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `sqlhost` varchar(32) DEFAULT NULL,
  `sqluser` varchar(32) DEFAULT NULL,
  `sqlpass` varchar(32) DEFAULT NULL,
  `chardb` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.server_variables
DROP TABLE IF EXISTS `server_variables`;
CREATE TABLE IF NOT EXISTS `server_variables` (
  `key` varchar(30) NOT NULL,
  `value` varchar(30) NOT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.siege_locations
DROP TABLE IF EXISTS `siege_locations`;
CREATE TABLE IF NOT EXISTS `siege_locations` (
  `id` int(11) NOT NULL,
  `race` enum('ELYOS','ASMODIANS','BALAUR') NOT NULL,
  `legion_id` int(11) NOT NULL,
  `occupy_count` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.skill_motions
DROP TABLE IF EXISTS `skill_motions`;
CREATE TABLE IF NOT EXISTS `skill_motions` (
  `motion_name` varchar(255) NOT NULL DEFAULT '',
  `skill_id` int(11) NOT NULL,
  `attack_speed` int(11) NOT NULL,
  `weapon_type` varchar(255) NOT NULL,
  `off_weapon_type` varchar(255) NOT NULL,
  `race` varchar(255) NOT NULL,
  `gender` varchar(255) NOT NULL,
  `time` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`motion_name`,`skill_id`,`attack_speed`,`weapon_type`,`off_weapon_type`,`gender`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.surveys
DROP TABLE IF EXISTS `surveys`;
CREATE TABLE IF NOT EXISTS `surveys` (
  `unique_id` int(11) NOT NULL AUTO_INCREMENT,
  `owner_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `item_count` decimal(20,0) NOT NULL DEFAULT 1,
  `html_text` text NOT NULL,
  `html_radio` varchar(100) NOT NULL DEFAULT 'accept',
  `used` tinyint(1) NOT NULL DEFAULT 0,
  `used_time` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`unique_id`),
  KEY `owner_id` (`owner_id`),
  CONSTRAINT `surveys_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `players` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=199 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.tasks
DROP TABLE IF EXISTS `tasks`;
CREATE TABLE IF NOT EXISTS `tasks` (
  `id` int(5) NOT NULL,
  `task` varchar(50) NOT NULL,
  `type` enum('FIXED_IN_TIME') NOT NULL,
  `last_activation` timestamp NOT NULL DEFAULT '2010-01-01 04:00:00',
  `start_time` varchar(8) NOT NULL,
  `delay` int(10) NOT NULL,
  `param` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.users
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(30) DEFAULT NULL,
  `ip` varchar(30) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.vote
DROP TABLE IF EXISTS `vote`;
CREATE TABLE IF NOT EXISTS `vote` (
  `idvote` int(255) NOT NULL AUTO_INCREMENT,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`idvote`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table aiondbls_gs.web_shop
DROP TABLE IF EXISTS `web_shop`;
CREATE TABLE IF NOT EXISTS `web_shop` (
  `unique` int(11) NOT NULL AUTO_INCREMENT,
  `item_owner` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `item_count` int(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`unique`),
  KEY `item_owner` (`item_owner`),
  CONSTRAINT `FK_web_shop_account_data` FOREIGN KEY (`item_owner`) REFERENCES `account_data` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
