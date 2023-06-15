CREATE TABLE `products` (
  `product_code` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(70) NOT NULL,
  `product_line` varchar(50) NOT NULL,
  `product_scale` varchar(10) NOT NULL,
  `product_vendor` varchar(50) NOT NULL,
  `product_description` text NOT NULL,
  `quantity_in_stock` smallint NOT NULL,
  `buy_price` decimal(10,2) NOT NULL,
  `msrp` decimal(10,2) NOT NULL,
  PRIMARY KEY (`product_code`));