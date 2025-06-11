alter table payments add CONSTRAINT `payments_ibfk_1`
FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
on update CASCADE;