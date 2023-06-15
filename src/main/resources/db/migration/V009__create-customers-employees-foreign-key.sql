Alter table customers ADD `salesRepEmployeeNumber` int DEFAULT NULL,
ADD KEY `salesRepEmployeeNumber` (`salesRepEmployeeNumber`),
ADD CONSTRAINT `customers_ibfk_1` FOREIGN KEY (`salesRepEmployeeNumber`) REFERENCES `employees` (`employeeNumber`)
on update CASCADE;