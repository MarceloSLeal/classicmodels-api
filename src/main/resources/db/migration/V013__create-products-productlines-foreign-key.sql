alter table products add KEY `productLine` (`productLine`),
add CONSTRAINT `products_ibfk_1` FOREIGN KEY (`productLine`) REFERENCES `productlines` (`productLine`);