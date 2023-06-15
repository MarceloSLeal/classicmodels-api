CREATE TABLE `employees` (
  `employee_number` int NOT NULL AUTO_INCREMENT,
  `last_name` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `extension` varchar(10) NOT NULL,
  `email` varchar(100) NOT NULL,
  `office_code` int NOT NULL,
  `reports_to` int DEFAULT NULL,
  `job_title` varchar(50) NOT NULL,
  PRIMARY KEY (`employee_number`),
  KEY `reportsTo` (`reports_to`),
  CONSTRAINT `employees_ibfk_1` FOREIGN KEY (`reports_to`) REFERENCES `employees` (`employee_number`)
  on update CASCADE);