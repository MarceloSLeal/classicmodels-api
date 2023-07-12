Alter table customers ADD KEY `employee_id` (`employee_id`),
ADD CONSTRAINT `customers_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`)
on update CASCADE;