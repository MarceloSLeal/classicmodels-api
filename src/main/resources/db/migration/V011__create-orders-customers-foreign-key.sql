alter table orders add KEY `customer_number` (`customer_number`),
add CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`customer_number`) REFERENCES `customers` (`customer_number`)
on update CASCADE;