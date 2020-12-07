CREATE DATABASE IF NOT EXISTS auth_user_default DEFAULT CHARACTER SET 'utf8' DEFAULT COLLATE 'utf8_unicode_ci';

USE auth_user_default;

-- ----------------------------
-- Table structure for user_principal
-- ----------------------------

DROP TABLE IF EXISTS user_principal;
CREATE TABLE user_principal
(
    user_id                 BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username                VARCHAR(64)     NOT NULL,
    hashed_password         BINARY(60)      NOT NULL,
    account_non_expired     BOOLEAN         NOT NULL,
    account_non_locked      BOOLEAN         NOT NULL,
    credentials_non_expired BOOLEAN         NOT NULL,
    enabled                 BOOLEAN         NOT NULL,
    UNIQUE KEY uk_username (username)
) ENGINE = InnoDB;

-- ----------------------------
-- Table structure for user_principal
-- ----------------------------

DROP TABLE IF EXISTS user_authority;
CREATE TABLE user_authority
(
    user_id   BIGINT UNSIGNED NOT NULL,
    authority VARCHAR(255)    NOT NULL,
    UNIQUE KEY uk_user_authority (user_id, authority),
    CONSTRAINT fk_userId FOREIGN KEY (user_id)
        REFERENCES user_principal (user_id) ON DELETE CASCADE
) ENGINE = InnoDB;

# INSERT INTO user_principal (username, hashed_password, account_non_expired,
#                             account_non_locked, credentials_non_expired, enabled)
# VALUES ('john.carnell', '$2a$10$r9BL/rId0TistJYhSfdDxOSgWE7t/uV3foMmBKNWa3sfzpkH/DZ3e', TRUE, TRUE, TRUE, TRUE);
#
# INSERT INTO user_authority (user_id, authority)
# VALUES (1, 'USER');
#
# INSERT INTO user_principal (username, hashed_password, account_non_expired,
#                             account_non_locked, credentials_non_expired, enabled)
# VALUES ('william.woodward', '$2a$10$r9BL/rId0TistJYhSfdDxOSgWE7t/uV3foMmBKNWa3sfzpkH/DZ3e', TRUE, TRUE, TRUE, TRUE);
#
# INSERT INTO user_authority (user_id, authority)
# VALUES (2, 'USER'),
#        (2, 'ADMIN');
