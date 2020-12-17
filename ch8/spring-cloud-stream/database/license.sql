CREATE DATABASE IF NOT EXISTS license_default DEFAULT CHARACTER SET 'utf8' DEFAULT COLLATE 'utf8_unicode_ci';

USE license_default;

-- ----------------------------
-- Table structure for licensing
-- ----------------------------

DROP TABLE IF EXISTS license;
CREATE TABLE license
(
    license_id        VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL PRIMARY KEY,
    organization_id   VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
    product_name      VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
    license_type      VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
    license_max       INT                                  NOT NULL,
    license_allocated INT,
    comment           VARCHAR(255) COLLATE utf8_unicode_ci
) ENGINE = InnoDB;

INSERT INTO license (license_id, organization_id, license_type, product_name, license_max, license_allocated)
VALUES ('f3831f8c-c338-4ebe-a82a-e2fc1d1ff78a', 'e254f8c-c442-4ebe-a82a-e2fc1d1ff78a', 'user', 'CustomerPro', 100, 5);
INSERT INTO license (license_id, organization_id, license_type, product_name, license_max, license_allocated)
VALUES ('t9876f8c-c338-4abc-zf6a-ttt1', 'e254f8c-c442-4ebe-a82a-e2fc1d1ff78a', 'user', 'suitability-plus', 200, 189);
INSERT INTO license (license_id, organization_id, license_type, product_name, license_max, license_allocated)
VALUES ('38777179-7094-4200-9d61-edb101c6ea84', '442adb6e-fa58-47f3-9ca2-ed1fecdfe86c', 'user', 'HR-PowerSuite', 100,
        4);
INSERT INTO license (license_id, organization_id, license_type, product_name, license_max, license_allocated)
VALUES ('08dbe05-606e-4dad-9d33-90ef10e334f9', '442adb6e-fa58-47f3-9ca2-ed1fecdfe86c', 'core-prod',
        'WildCat Application Gateway', 16, 16);
