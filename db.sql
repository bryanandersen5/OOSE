-- Database creation
CREATE DATABASE `Stock_Trading_Management_System`;

-- Select the database created
USE `Stock_Trading_Management_System`;


-- Create table for ROLE
CREATE TABLE `ROLE` (
	`id` INT AUTO_INCREMENT, /* STC! */
	`name` VARCHAR(60) NOT NULL DEFAULT 0,
	`permissions` TEXT NOT NULL /* Store as JSON array like '["read", "write"]' STC! */,
	PRIMARY KEY (`id`) 
);

-- Add sample data to ROLE table
INSERT INTO ROLE(name, permissions) 
VALUES('admin', '["read", "write", "execute"]');


-- Add sample data to ROLE table
INSERT INTO ROLE(name, permissions) 
VALUES('trader', '["read"]');

-- Show ROLE table
SELECT * FROM ROLE;


-- Create table for USER
CREATE TABLE `USER`  (
	`username` VARCHAR(60) NOT NULL DEFAULT '',
	`password` VARCHAR(60) NOT NULL DEFAULT'',
    `role_id` INT,
	PRIMARY KEY (`username`),
    FOREIGN KEY (role_id)
		REFERENCES ROLE (id)
		ON UPDATE CASCADE ON DELETE CASCADE
);

-- Add sample data to USER table
INSERT INTO USER (username,password,role_id)
VALUES
('JohnDoe','JohnInvest123','1'),
('KevinNguyen,','InvestD123','2');

-- Show USER table
SELECT * FROM TABLE;


-- Some useful SQL commands
SET SQL_SAFE_UPDATES = 0;

DELETE FROM TABLE;

DROP TABLE USER;
