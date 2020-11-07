CREATE DATABASE IF NOT EXISTS special_routes_default DEFAULT CHARACTER SET 'utf8' DEFAULT COLLATE 'utf8_unicode_ci';

USE special_routes_default;

-- ----------------------------
-- Table structure for abtesting
-- ----------------------------

DROP TABLE IF EXISTS Abtesting;

CREATE TABLE Abtesting
(
    ServiceName VARCHAR(100) PRIMARY KEY NOT NULL,
    Active      VARCHAR(1)               NOT NULL,
    EndPoint    VARCHAR(100)             NOT NULL,
    Weight      INT
);


INSERT INTO Abtesting (ServiceName, Active, EndPoint, Weight)
VALUES ('organization-service', 'Y', 'http://organization-service-new:8084', 5);
