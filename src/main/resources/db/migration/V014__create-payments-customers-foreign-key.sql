alter table payments add CONSTRAINT `payments_ibfk_1`
FOREIGN KEY (`customer_number`) REFERENCES `customers` (`customer_number`)
on update CASCADE;