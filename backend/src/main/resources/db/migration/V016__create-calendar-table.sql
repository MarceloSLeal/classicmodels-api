CREATE TABLE `calendar` (
	`id` varchar(36) NOT NULL UNIQUE,
    `title` varchar(50) NOT NULL,
    `start` datetime NOT NULL,
    `end` datetime NOT NULL,
    `allday` tinyint(1) NOT NULL,
    PRIMARY KEY (`id`));