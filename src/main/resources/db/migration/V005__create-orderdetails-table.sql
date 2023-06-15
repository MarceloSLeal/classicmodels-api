CREATE TABLE `orderdetails` (
  `order_number` int NOT NULL AUTO_INCREMENT,
  `product_code` int NOT NULL,
  `quantity_ordered` int NOT NULL,
  `price_each` decimal(10,2) NOT NULL,
  `order_line_number` smallint NOT NULL,
  PRIMARY KEY (`order_number`,`product_code`),
  KEY `product_code` (`product_code`),
  CONSTRAINT `orderdetails_ibfk_1` FOREIGN KEY (`order_number`) REFERENCES `orders` (`order_number`)
  on update CASCADE);