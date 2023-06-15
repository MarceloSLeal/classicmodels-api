Alter table customers ADD KEY `sales_rep_employee_number` (`sales_rep_employee_number`),
ADD CONSTRAINT `customers_ibfk_1` FOREIGN KEY (`sales_rep_employee_number`) REFERENCES `employees` (`employee_number`)
on update CASCADE;