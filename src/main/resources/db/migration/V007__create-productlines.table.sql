CREATE TABLE `productlines` (
  `product_line` varchar(50) NOT NULL,
  `text_description` varchar(4000) DEFAULT NULL,
  `html_description` mediumtext,
  `image` mediumblob,
  PRIMARY KEY (`product_line`));