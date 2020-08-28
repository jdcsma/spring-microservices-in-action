CREATE DATABASE IF NOT EXISTS organization_default DEFAULT CHARACTER SET 'utf8' DEFAULT COLLATE 'utf8_unicode_ci';

USE organization_default;

-- ----------------------------
-- Table structure for organizations
-- ----------------------------
DROP TABLE IF EXISTS Organizations;
CREATE TABLE Organizations
(
    OrganizationId VARCHAR(127) COLLATE utf8_unicode_ci NOT NULL PRIMARY KEY,
    Name           VARCHAR(127) COLLATE utf8_unicode_ci NOT NULL,
    ContactName    VARCHAR(127) COLLATE utf8_unicode_ci NOT NULL,
    ContactEmail   VARCHAR(127) COLLATE utf8_unicode_ci NOT NULL,
    ContactPhone   VARCHAR(127) COLLATE utf8_unicode_ci NOT NULL
) ENGINE = InnoDB;

INSERT INTO Organizations (OrganizationId, Name, ContactName, ContactEmail, ContactPhone)
VALUES ('e254f8c-c442-4ebe-a82a-e2fc1d1ff78a', 'customer-crm-co', 'Mark Balster', 'mark.balster@custcrmco.com', '823-555-1212');

INSERT INTO Organizations (OrganizationId, Name, ContactName, ContactEmail, ContactPhone)
VALUES ('442adb6e-fa58-47f3-9ca2-ed1fecdfe86c', 'HR-PowerSuite', 'Doug Drewry','doug.drewry@hr.com', '920-555-1212');
