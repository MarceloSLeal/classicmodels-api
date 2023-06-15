CREATE TABLE `offices` (
  `office_code` int NOT NULL AUTO_INCREMENT,
  `city` varchar(50) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `address_line1` varchar(50) NOT NULL,
  `address_line2` varchar(50) DEFAULT NULL,
  `state` varchar(50) DEFAULT NULL,
  `country` varchar(50) NOT NULL,
  `postal_code` varchar(15) NOT NULL,
  `territory` varchar(10) NOT NULL,
  PRIMARY KEY (`office_code`)
);