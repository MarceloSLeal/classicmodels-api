alter table orderdetails add CONSTRAINT `orderdetails_ibfk_2`
FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
on update CASCADE;