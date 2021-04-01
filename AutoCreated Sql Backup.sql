-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.5.9-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for ecommerce
DROP DATABASE IF EXISTS `ecommerce`;
CREATE DATABASE IF NOT EXISTS `ecommerce` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `ecommerce`;

-- Dumping structure for table ecommerce.customer
DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer` (
  `id` bigint(20) NOT NULL,
  `delivery_address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sure_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_dwk6cx0afu8bs9o4t536v1j5v` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table ecommerce.customer: ~0 rows (approximately)
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT IGNORE INTO `customer` (`id`, `delivery_address`, `email`, `name`, `sure_name`) VALUES
	(1, 'Italy', 'behnam.lotfi@studenti.polito.it', 'Behnam', 'Lotfi');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;

-- Dumping structure for table ecommerce.hibernate_sequence
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table ecommerce.hibernate_sequence: ~1 rows (approximately)
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT IGNORE INTO `hibernate_sequence` (`next_val`) VALUES
	(1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;

-- Dumping structure for table ecommerce.transactions
DROP TABLE IF EXISTS `transactions`;
CREATE TABLE IF NOT EXISTS `transactions` (
  `id` bigint(20) NOT NULL,
  `amount_transfered` bigint(20) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `transaction_time` datetime DEFAULT NULL,
  `wallet_from_id` bigint(20) DEFAULT NULL,
  `wallet_to_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKearka5aaw19rglfafi64hva4j` (`wallet_from_id`),
  KEY `FKh18sakxp9u9pt1lxt5rte3pl4` (`wallet_to_id`),
  CONSTRAINT `FKearka5aaw19rglfafi64hva4j` FOREIGN KEY (`wallet_from_id`) REFERENCES `wallet` (`id`),
  CONSTRAINT `FKh18sakxp9u9pt1lxt5rte3pl4` FOREIGN KEY (`wallet_to_id`) REFERENCES `wallet` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table ecommerce.transactions: ~0 rows (approximately)
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;

-- Dumping structure for table ecommerce.wallet
DROP TABLE IF EXISTS `wallet`;
CREATE TABLE IF NOT EXISTS `wallet` (
  `id` bigint(20) NOT NULL,
  `amount` bigint(20) NOT NULL,
  `customer_id_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtdjkci50tdsrjjwg1ig7u3q1j` (`customer_id_id`),
  CONSTRAINT `FKtdjkci50tdsrjjwg1ig7u3q1j` FOREIGN KEY (`customer_id_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table ecommerce.wallet: ~0 rows (approximately)
/*!40000 ALTER TABLE `wallet` DISABLE KEYS */;
INSERT IGNORE INTO `wallet` (`id`, `amount`, `customer_id_id`) VALUES
	(1, 200, 1);
/*!40000 ALTER TABLE `wallet` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
