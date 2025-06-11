CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(70) NOT NULL,
  `product_line` varchar(50) NOT NULL,
  `scale` varchar(10) NOT NULL,
  `vendor` varchar(50) NOT NULL,
  `description` text NOT NULL,
  `quantity_in_stock` smallint NOT NULL,
  `buy_price` decimal(10,2) NOT NULL,
  `msrp` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`));