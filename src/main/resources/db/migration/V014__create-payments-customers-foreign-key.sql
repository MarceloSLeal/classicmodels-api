alter table payments add CONSTRAINT `payments_ibfk_1`
FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
on update CASCADE;