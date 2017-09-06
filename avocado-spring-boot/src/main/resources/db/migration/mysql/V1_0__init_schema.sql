/*
set global innodb_file_format = BARRACUDA;
set global innodb_large_prefix = ON;
set global innodb_file_per_table = true;
*/

/* ======== APPLICATION CONFIGURATION ======== */

CREATE TABLE IF NOT EXISTS CONFIGURATION (
  code  VARCHAR(128)  NOT NULL,
  type  VARCHAR(64) DEFAULT 'MAIN',
  value VARCHAR(4000) NOT NULL,
  CONSTRAINT PK_CONFIGURATION PRIMARY KEY (code, type)
);

/* ========== INTERNATIONALIZATION ========== */

CREATE TABLE IF NOT EXISTS LANGUAGE_CODE (
  code        VARCHAR(5)   NOT NULL,
  name        VARCHAR(100) NOT NULL,
  name_native VARCHAR(100),
  CONSTRAINT PK_LANGUAGE_CODE PRIMARY KEY (code)
);

/*
 * Group of the translation (MENU, INGREDIENT, DISH, CUSTOM)
 */
CREATE TABLE IF NOT EXISTS TRANSLATION_TYPE (
  name        VARCHAR(50) NOT NULL,
  description VARCHAR(100),
  CONSTRAINT PK_TRANSLATION_TYPE PRIMARY KEY (name)
);

/**
 * Associates the DB fields to all languages entries.
 * A translation has many entries.
 */
CREATE TABLE IF NOT EXISTS TRANSLATION (
  id   MEDIUMINT AUTO_INCREMENT,
  type VARCHAR(50) COMMENT 'Type of the translation'          NOT NULL,
  name VARCHAR(100) COMMENT 'English name of the translation' NOT NULL,
  CONSTRAINT PK_TRANSLATION PRIMARY KEY (id),
  CONSTRAINT FK_TRA_TRANSLATION_TYPE FOREIGN KEY (type) REFERENCES TRANSLATION_TYPE (name)
);

/**
 * Represent the entries in each registered language of a translation name.
 */
CREATE TABLE IF NOT EXISTS TRANSLATION_ENTRY (
  id            MEDIUMINT NOT NULL,
  language_code VARCHAR(5),
  text          VARCHAR(4000),
  CONSTRAINT PK_TRANSLATION_ENTRY PRIMARY KEY (id, language_code),
  CONSTRAINT FK_TRE_TRANSLATION_ID FOREIGN KEY (id) REFERENCES TRANSLATION (id),
  CONSTRAINT FK_TRE_LOCALE FOREIGN KEY (language_code) REFERENCES LANGUAGE_CODE (code)
);

/**
 * All countries of the world.
 */
CREATE TABLE IF NOT EXISTS COUNTRY_CODE (
  code        VARCHAR(2)   NOT NULL,
  name        VARCHAR(100) NOT NULL,
  name_native VARCHAR(100),
  CONSTRAINT PK_COUNTRY_CODE PRIMARY KEY (code)
);

/**
 * All the supported currency.
 */
CREATE TABLE IF NOT EXISTS CURRENCY_CODE (
  code   VARCHAR(3)   NOT NULL,
  symbol VARCHAR(10)  NOT NULL,
  name   VARCHAR(100) NOT NULL,
  CONSTRAINT PK_CURRENCY_CODE PRIMARY KEY (code)
);

/* ========== USERS ========== */

/**
 * Statuses to manage the User visibility and login access.
 * REGISTERED, ACTIVE, BLOCKED, DELETED.
 */
CREATE TABLE IF NOT EXISTS USER_STATUS (
  code        VARCHAR(20) NOT NULL,
  description VARCHAR(255),
  CONSTRAINT PK_USER_STATUS PRIMARY KEY (code)
);

/**
 * Roles of the users.
 * ROLE_[USER|RESTAURANT|ADMIN|MODERATOR]
 */
CREATE TABLE IF NOT EXISTS USER_AUTHORITY (
  code        VARCHAR(20) NOT NULL,
  title       VARCHAR(50) NOT NULL,
  description VARCHAR(255),
  CONSTRAINT PK_USER_AUTHORITY PRIMARY KEY (code)
);

/**
 * Central User table with basic account information.
 * Admins, Moderators, Clients and Restaurant owners has an account here.
 */
CREATE TABLE IF NOT EXISTS USERS (
  id                        BIGINT AUTO_INCREMENT                 NOT NULL,
  cre_date                  TIMESTAMP DEFAULT current_timestamp   NOT NULL,
  mod_date                  TIMESTAMP DEFAULT current_timestamp   NOT NULL,
  username                  VARCHAR(50)                           NOT NULL,
  nickname                  VARCHAR(50)                           NOT NULL,
  firstname                 VARCHAR(50),
  lastname                  VARCHAR(50),
  email                     VARCHAR(255)                          NOT NULL,
  password                  VARCHAR(1024) COMMENT 'Password hash' NOT NULL,
  language_code             VARCHAR(5) COMMENT 'Favourite language',
  terms_accepted            TINYINT(1) COMMENT 'If user has accepted latest Terms and Conditions' DEFAULT 0,
  avatar                    BLOB,
  authority                 VARCHAR(20)                           NOT NULL,
  status                    VARCHAR(20) DEFAULT 'REGISTERED'      NOT NULL,
  last_password_change_date TIMESTAMP                             NULL,
  CONSTRAINT PK_USERS PRIMARY KEY (id),
  CONSTRAINT FK_USR_FAV_LOCALE FOREIGN KEY (language_code) REFERENCES LANGUAGE_CODE (code),
  CONSTRAINT FK_USR_AUTHORITY FOREIGN KEY (authority) REFERENCES USER_AUTHORITY (code),
  CONSTRAINT FK_USR_STATUS FOREIGN KEY (status) REFERENCES USER_STATUS (code)
);

CREATE UNIQUE INDEX IDX_USR_USERNAME
  ON USERS (username);
CREATE UNIQUE INDEX IDX_USR_EMAIL
  ON USERS (email);

/**
 * Authentication token for each users.
 * Note: A user can login from multiple devices and tokens.
 */
CREATE TABLE IF NOT EXISTS USER_TOKENS (
  id              BIGINT AUTO_INCREMENT                           NOT NULL,
  user_id         BIGINT                                          NOT NULL,
  jti             VARCHAR(128)                                    NOT NULL,
  enabled         TINYINT(1) COMMENT 'If this token is enabled' DEFAULT 1,
  os_name         VARCHAR(64),
  os_version      VARCHAR(64),
  user_agent      VARCHAR(256),
  cre_date        TIMESTAMP DEFAULT current_timestamp             NOT NULL,
  exp_date        TIMESTAMP                                       NULL,
  last_login_date TIMESTAMP DEFAULT current_timestamp             NOT NULL,
  CONSTRAINT PK_USER_TOKENS PRIMARY KEY (id),
  CONSTRAINT FK_TKN_USER_ID FOREIGN KEY (user_id) REFERENCES USERS (id)
);

CREATE UNIQUE INDEX IDX_TKN_JTI
  ON USER_TOKENS (jti);

/* ========== RESTAURANTS ========== */

/**
 * Statuses to manage the Restaurant visibility.
 * CREATED, APPROVED, NOT_APPROVED, REVIEW, MODIFIED, VISIBLE, INVISIBLE, DELETED
 */
CREATE TABLE IF NOT EXISTS RESTAURANT_STATUS (
  code        VARCHAR(50) NOT NULL,
  description VARCHAR(255),
  CONSTRAINT PK_RESTAURANT_STATUS PRIMARY KEY (code)
);

/**
 * Restaurants table with login information, display and search information.
 * Location for searching by distance:
 * https://developers.google.com/maps/solutions/store-locator/clothing-store-locator
 */
CREATE TABLE IF NOT EXISTS RESTAURANTS (
  id               BIGINT AUTO_INCREMENT                         NOT NULL,
  cre_date         TIMESTAMP DEFAULT current_timestamp           NOT NULL,
  mod_date         TIMESTAMP DEFAULT current_timestamp           NOT NULL,
  user_id          BIGINT COMMENT 'Owner of the restaurant'      NOT NULL,
  name             VARCHAR(255) COMMENT 'Name of the restaurant' NOT NULL,
  status           VARCHAR(50) DEFAULT 'CREATED',
  country_code     VARCHAR(2)                                    NOT NULL,
  language_code    VARCHAR(5)                                    NOT NULL,
  currency_code    VARCHAR(3)                                    NOT NULL,
  latitude         FLOAT(10, 6),
  longitude        FLOAT(10, 6),
  display_address  VARCHAR(255),
  display_zip      VARCHAR(10),
  display_city     VARCHAR(255)                                  NOT NULL,
  display_province VARCHAR(255),
  display_email    VARCHAR(255)                                  NOT NULL,
  display_phone    VARCHAR(50),
  website          VARCHAR(255),
  tables           SMALLINT COMMENT 'Number of tables',
  places           SMALLINT COMMENT 'Number of places',
  description      VARCHAR(255),
  CONSTRAINT PK_RESTAURANT PRIMARY KEY (id),
  CONSTRAINT FK_RST_USER_ID FOREIGN KEY (user_id) REFERENCES USERS (id),
  CONSTRAINT FK_RST_COUNTRY FOREIGN KEY (country_code) REFERENCES COUNTRY_CODE (code),
  CONSTRAINT FK_RST_CURRENCY FOREIGN KEY (currency_code) REFERENCES CURRENCY_CODE (code),
  CONSTRAINT FK_RST_LOCALE FOREIGN KEY (language_code) REFERENCES LANGUAGE_CODE (code),
  CONSTRAINT FK_RST_STATUS FOREIGN KEY (status) REFERENCES RESTAURANT_STATUS (code)
);

CREATE INDEX IDX_RST_NAME
  ON RESTAURANTS (name);

/* ========== MENUS ========== */

/**
 * Statuses to manage the Menu & Item visibility.
 */
CREATE TABLE IF NOT EXISTS MENU_STATUS (
  code        VARCHAR(50) NOT NULL,
  description VARCHAR(255),
  CONSTRAINT PK_MENU_STATUS PRIMARY KEY (code)
);

/**
 * Menu names of the Restaurants.
 */
CREATE TABLE IF NOT EXISTS MENUS (
  id             BIGINT AUTO_INCREMENT                            NOT NULL,
  restaurant_id  BIGINT                                           NOT NULL,
  order_id       TINYINT(1) COMMENT 'Sorting number for the menu' NOT NULL,
  cre_date       TIMESTAMP DEFAULT current_timestamp              NOT NULL,
  mod_date       TIMESTAMP DEFAULT current_timestamp              NOT NULL,
  title_trans_id MEDIUMINT COMMENT 'Menu title ref. translation id',
  status         VARCHAR(50) DEFAULT 'CREATED',
  CONSTRAINT PK_MENU PRIMARY KEY (id),
  CONSTRAINT FK_MNU_RESTAURANT_ID FOREIGN KEY (restaurant_id) REFERENCES RESTAURANTS (id),
  CONSTRAINT FK_MNU_STATUS FOREIGN KEY (status) REFERENCES MENU_STATUS (code)
);
