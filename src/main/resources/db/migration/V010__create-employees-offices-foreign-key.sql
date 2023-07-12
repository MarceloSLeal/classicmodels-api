alter table employees add KEY `office_id` (`office_id`),
add CONSTRAINT `employees_ibfk_2` FOREIGN KEY (`office_id`) REFERENCES `offices` (`id`)
on update CASCADE;