Alter table customers ADD KEY `employees_id` (`employees_id`),
ADD CONSTRAINT `customers_ibfk_1` FOREIGN KEY (`employees_id`) REFERENCES `employees` (`id`)
on update CASCADE;