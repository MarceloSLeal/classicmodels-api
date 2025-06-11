CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `required_date` datetime NOT NULL,
  `shipped_date` datetime DEFAULT NULL,
  `status` varchar(15) NOT NULL,
  `comments` text,
  `customer_id` int NOT NULL,
  PRIMARY KEY (`id`));