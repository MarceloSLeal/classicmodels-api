alter table employees add KEY `office_code` (`office_code`),
add CONSTRAINT `employees_ibfk_2` FOREIGN KEY (`office_code`) REFERENCES `offices` (`office_code`)
on update CASCADE;