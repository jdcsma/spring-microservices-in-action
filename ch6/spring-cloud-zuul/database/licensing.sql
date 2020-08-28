CREATE DATABASE IF NOT EXISTS licensing_default DEFAULT CHARACTER SET 'utf8' DEFAULT COLLATE 'utf8_unicode_ci';

USE licensing_default;

-- ----------------------------
-- Table structure for licenses
-- ----------------------------

DROP TABLE IF EXISTS Licenses;
CREATE TABLE Licenses
(
    LicenseId        VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL PRIMARY KEY,
    OrganizationId   VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
    ProductName      VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
    LicenseType      VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
    LicenseMax       INT                                  NOT NULL,
    LicenseAllocated INT,
    Comment          VARCHAR(255) COLLATE utf8_unicode_ci
) ENGINE = InnoDB;

INSERT INTO Licenses (LicenseId, OrganizationId, LicenseType, ProductName, LicenseMax, LicenseAllocated)
VALUES ('f3831f8c-c338-4ebe-a82a-e2fc1d1ff78a', 'e254f8c-c442-4ebe-a82a-e2fc1d1ff78a', 'user', 'CustomerPro', 100, 5);
INSERT INTO Licenses (LicenseId, OrganizationId, LicenseType, ProductName, LicenseMax, LicenseAllocated)
VALUES ('t9876f8c-c338-4abc-zf6a-ttt1', 'e254f8c-c442-4ebe-a82a-e2fc1d1ff78a', 'user', 'suitability-plus', 200, 189);
INSERT INTO Licenses (LicenseId, OrganizationId, LicenseType, ProductName, LicenseMax, LicenseAllocated)
VALUES ('38777179-7094-4200-9d61-edb101c6ea84', '442adb6e-fa58-47f3-9ca2-ed1fecdfe86c', 'user', 'HR-PowerSuite', 100, 4);
INSERT INTO Licenses (LicenseId, OrganizationId, LicenseType, ProductName, LicenseMax, LicenseAllocated)
VALUES ('08dbe05-606e-4dad-9d33-90ef10e334f9', '442adb6e-fa58-47f3-9ca2-ed1fecdfe86c', 'core-prod', 'WildCat Application Gateway', 16, 16);