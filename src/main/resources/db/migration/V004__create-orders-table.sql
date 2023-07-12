CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `required_date` date NOT NULL,
  `shipped_date` date DEFAULT NULL,
  `status` varchar(15) NOT NULL,
  `comments` text,
  `customer_id` int NOT NULL,
  PRIMARY KEY (`id`));