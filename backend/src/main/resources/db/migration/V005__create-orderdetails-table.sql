CREATE TABLE `orderdetails` (
  `order_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity_ordered` int NOT NULL,
  `price_each` decimal(10,2) NOT NULL,
  `order_line_number` smallint NOT NULL,
  PRIMARY KEY (`order_id`,`product_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `orderdetails_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
  on update CASCADE);