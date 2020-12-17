CREATE DATABASE IF NOT EXISTS organization_default DEFAULT CHARACTER SET 'utf8' DEFAULT COLLATE 'utf8_unicode_ci';

USE organization_default;

-- ----------------------------
-- Table structure for organization
-- ----------------------------
DROP TABLE IF EXISTS organization;
CREATE TABLE organization
(
    organization_id VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL PRIMARY KEY,
    name            VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
    contact_name    VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
    contact_email   VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
    contact_phone   VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE = InnoDB;

INSERT INTO organization (organization_id, name, contact_name, contact_email, contact_phone)
VALUES ('e254f8c-c442-4ebe-a82a-e2fc1d1ff78a', 'customer-crm-co', 'Mark Balster', 'mark.balster@custcrmco.com',
        '823-555-1212');

INSERT INTO organization (organization_id, name, contact_name, contact_email, contact_phone)
VALUES ('442adb6e-fa58-47f3-9ca2-ed1fecdfe86c', 'HR-PowerSuite', 'Doug Drewry', 'doug.drewry@hr.com', '920-555-1212');
