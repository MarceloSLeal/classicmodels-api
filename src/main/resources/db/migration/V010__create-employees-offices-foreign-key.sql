alter table employees add KEY `officeCode` (`officeCode`),
add CONSTRAINT `employees_ibfk_2` FOREIGN KEY (`officeCode`) REFERENCES `offices` (`officeCode`)
on update CASCADE;