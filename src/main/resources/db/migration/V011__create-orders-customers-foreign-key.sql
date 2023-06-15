alter table orders add KEY `customerNumber` (`customerNumber`),
add CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`customerNumber`) REFERENCES `customers` (`customerNumber`)
on update CASCADE;