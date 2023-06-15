alter table orderdetails add CONSTRAINT `orderdetails_ibfk_2`
FOREIGN KEY (`product_code`) REFERENCES `products` (`product_code`)
on update CASCADE;