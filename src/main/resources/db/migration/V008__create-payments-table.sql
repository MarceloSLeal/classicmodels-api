CREATE TABLE `payments` (
  `customer_id` int NOT NULL,
  `check_number` varchar(50) NOT NULL,
  `payment_date` datetime NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  PRIMARY KEY (`customer_id`,`check_number`));