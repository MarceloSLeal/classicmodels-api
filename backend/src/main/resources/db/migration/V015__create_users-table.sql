CREATE TABLE `users` (
	`id` varchar(50) NOT NULL UNIQUE,
    `email` varchar(50) NOT NULL UNIQUE,
    `password` varchar(255) NOT NULL,
    `role` varchar(15) NOT NULL,
    PRIMARY KEY (`id`)
    );