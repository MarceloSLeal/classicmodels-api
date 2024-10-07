CREATE TABLE `payments` (
  `order_id` int NOT NULL,
  `check_number` varchar(36) default (uuid()),
  `payment_date` datetime NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  PRIMARY KEY (`check_number`));