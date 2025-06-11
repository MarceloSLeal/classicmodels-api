alter table products add KEY `productLine` (`product_line`),
add CONSTRAINT `products_ibfk_1` FOREIGN KEY (`product_line`) REFERENCES `productlines` (`product_line`)
on update CASCADE;