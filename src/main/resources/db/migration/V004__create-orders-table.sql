CREATE TABLE `orders` (
  `order_number` int NOT NULL AUTO_INCREMENT,
  `order_date` date NOT NULL,
  `required_date` date NOT NULL,
  `shipped_date` date DEFAULT NULL,
  `status` varchar(15) NOT NULL,
  `comments` text,
  `customer_number` int NOT NULL,
  PRIMARY KEY (`order_number`));