CREATE DATABASE IF NOT EXISTS auth_client_default DEFAULT CHARACTER SET 'utf8' DEFAULT COLLATE 'utf8_unicode_ci';

USE auth_client_default;

-- ----------------------------
-- Table structure for client_principal
-- ----------------------------

DROP TABLE IF EXISTS client_principal;
CREATE TABLE client_principal
(
    client_id              VARCHAR(255) NOT NULL PRIMARY KEY,
    hashed_secret          VARCHAR(60)  NOT NULL,
    scope                  VARCHAR(255) NOT NULL,
    authorized_grant_types VARCHAR(255) NOT NULL
) ENGINE = InnoDB;

# INSERT INTO client_principal (client_id, hashed_secret, scope, authorized_grant_types)
# VALUES ('eagleeye', 'thisissecret', 'webclient,mobileclient', 'refresh_token,password,client_credentials');

