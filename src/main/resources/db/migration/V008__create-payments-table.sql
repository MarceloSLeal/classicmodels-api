CREATE TABLE `payments` (
  `customer_number` int NOT NULL,
  `check_number` varchar(50) NOT NULL,
  `payment_date` date NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  PRIMARY KEY (`customer_number`,`check_number`));