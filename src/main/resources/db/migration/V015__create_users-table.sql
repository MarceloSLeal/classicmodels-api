CREATE TABLE `users` (
	`id` varchar(50) NOT NULL UNIQUE,
    `login` varchar(50) NOT NULL UNIQUE,
    `password` varchar(50) NOT NULL,
    `role` varchar(15) NOT NULL,
    PRIMARY KEY (`id`)
    );