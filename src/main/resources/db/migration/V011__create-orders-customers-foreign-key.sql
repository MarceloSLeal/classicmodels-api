alter table orders add KEY `customer_id` (`customer_id`),
add CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
on update CASCADE;