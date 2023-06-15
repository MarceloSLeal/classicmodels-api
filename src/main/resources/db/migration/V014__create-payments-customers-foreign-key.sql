alter table payments add CONSTRAINT `payments_ibfk_1`
FOREIGN KEY (`customerNumber`) REFERENCES `customers` (`customerNumber`)
on update CASCADE;