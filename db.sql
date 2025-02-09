-- Database creation
CREATE DATABASE `Stock_Trading_Management_System`;

USE `Stock_Trading_Management_System`;

-- Table for ROLE STC!
CREATE TABLE `ROLE` (
	`id` INT AUTO_INCREMENT,
	`name` VARCHAR(60) NOT NULL DEFAULT 0,
	`permissions` TEXT NOT NULL /*Store as JSON array like '["read", "write"]'*/,
	PRIMARY KEY (`id`)
);

INSERT INTO ROLE(name, permissions) 
VALUES('admin', '["read", "write", "execute"]');

INSERT INTO ROLE(name, permissions) 
VALUES('trader', '["read"]');

SELECT * FROM ROLE;

-- Table for USER
CREATE TABLE `USER`  (
	`username` VARCHAR(60) NOT NULL DEFAULT '',
	`password` VARCHAR(255) NOT NULL DEFAULT'',
    `name` VARCHAR(60) NOT NULL DEFAULT'',
    `email` VARCHAR(60) NOT NULL DEFAULT'',
    `securityQuestion` VARCHAR(255) NOT NULL DEFAULT'',
    `securityAnswer` VARCHAR(255) NOT NULL DEFAULT'',
    `phoneNumber` VARCHAR(60) NOT NULL DEFAULT'',
    `role_id` INT,
	PRIMARY KEY (`username`),
    FOREIGN KEY (role_id)
		REFERENCES ROLE (id)
		ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO USER (username, password, name, email, securityQuestion, securityAnswer, phoneNumber)
VALUES
('JohnDoe','JohnInvest123','John Doe', 'JohnDoe@email.com', 'Who are you?','John','6041234567'),
('KevinNguyen,','InvestD123','Kevin Nguyen','KevinNguyen@email.com', 'Who are you?','Kevin','7789876543');

SELECT * FROM USER;

DELETE FROM USER;

SET SQL_SAFE_UPDATES = 0;

DROP TABLE USER;
