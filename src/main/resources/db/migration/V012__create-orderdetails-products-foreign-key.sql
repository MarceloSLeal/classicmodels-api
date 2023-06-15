alter table orderdetails add CONSTRAINT `orderdetails_ibfk_2`
FOREIGN KEY (`productCode`) REFERENCES `products` (`productCode`)
on update CASCADE;