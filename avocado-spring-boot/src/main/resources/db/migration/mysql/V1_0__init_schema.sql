/*
set global innodb_file_format = BARRACUDA;
set global innodb_large_prefix = ON;
set global innodb_file_per_table = true;
*/

/* ========== INTERNATIONALIZATION ========== */

CREATE TABLE IF NOT EXISTS CONFIGURATION (
  code     VARCHAR(128)  NOT NULL PRIMARY KEY,
  cre_date TIMESTAMP     NOT NULL,
  mod_date TIMESTAMP     NOT NULL,
  value    VARCHAR(4000) NOT NULL
);

CREATE TABLE IF NOT EXISTS LANGUAGE_CODE (
  code        VARCHAR(5) PRIMARY KEY,
  name        VARCHAR(100) NOT NULL,
  name_native VARCHAR(100)
);

/*
 * Type of the translation (Menu, Ingredient, Dish, Custom)
 */
CREATE TABLE IF NOT EXISTS TRANSLATION_TYPE (
  name        VARCHAR(50) PRIMARY KEY,
  description VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS TRANSLATION (
  id   BIGINT PRIMARY KEY AUTO_INCREMENT,
  type VARCHAR(50)  NOT NULL
  COMMENT 'Type of the translation (Menu, Ingredient, Dish, Custom)',
  name VARCHAR(100) NOT NULL
  COMMENT 'English name of the translation'
);

CREATE TABLE IF NOT EXISTS TRANSLATION_ENTRY (
  id            BIGINT,
  language_code VARCHAR(5),
  text          VARCHAR(4000)
);

CREATE TABLE IF NOT EXISTS COUNTRY_CODE (
  code        VARCHAR(2) PRIMARY KEY,
  name        VARCHAR(100) NOT NULL,
  description BIGINT COMMENT 'Country translation name'
);

CREATE TABLE IF NOT EXISTS CURRENCY_CODE (
  code        VARCHAR(3) PRIMARY KEY,
  symbol      VARCHAR(10)  NOT NULL,
  name        VARCHAR(100) NOT NULL,
  description BIGINT COMMENT 'Currency translation name'
);

/* ========== RESTAURANTS ========== */

/**
 * Statuses to manage the Restaurant visibility.
 */
CREATE TABLE IF NOT EXISTS RESTAURANT_STATUS (
  code        VARCHAR(50) PRIMARY KEY,
  description VARCHAR(255)
);

/**
 * Restaurants table with login information, display and search information.
 */
CREATE TABLE IF NOT EXISTS RESTAURANTS (
  id               BIGINT PRIMARY KEY       AUTO_INCREMENT,
  cre_date         TIMESTAMP    NOT NULL,
  mod_date         TIMESTAMP    NOT NULL,
  status           VARCHAR(50)              DEFAULT 'CREATED',
  name             VARCHAR(255) NOT NULL,
  country_code     VARCHAR(2)   NOT NULL,
  language_code    VARCHAR(5)   NOT NULL,
  currency_code    VARCHAR(3)   NOT NULL,
  latitude         FLOAT(10, 6),
  longitude        FLOAT(10, 6),
  display_address  VARCHAR(255),
  display_zip      VARCHAR(10),
  display_city     VARCHAR(255) NOT NULL,
  display_province VARCHAR(255),
  display_email    VARCHAR(255) NOT NULL,
  display_phone    VARCHAR(50),
  website          VARCHAR(255),
  tables           SMALLINT COMMENT 'Number of tables',
  places           SMALLINT COMMENT 'Number of places',
  open_hours       VARCHAR(255),
  description      VARCHAR(255),
  last_login_date  TIMESTAMP,
  login            VARCHAR(255) NOT NULL,
  password         VARCHAR(255) NOT NULL
);

/* ========== MENUS ========== */

/**
 * Statuses to manage the Menu & Item visibility.
 */
CREATE TABLE IF NOT EXISTS MENU_STATUS (
  code        VARCHAR(50) PRIMARY KEY,
  description VARCHAR(255)
);

/**
 * Menu list of the Restaurants.
 */
CREATE TABLE IF NOT EXISTS MENUS (
  id            BIGINT PRIMARY KEY       AUTO_INCREMENT,
  restaurant_id BIGINT,
  order_id      TINYINT   NOT NULL
  COMMENT 'Sorting number for the menu',
  cre_date      TIMESTAMP NOT NULL,
  mod_date      TIMESTAMP NOT NULL,
  title         BIGINT
  COMMENT 'Menu title ref. translation id',
  status        VARCHAR(50)              DEFAULT 'CREATED'
);

/* ========== CONSTRAINTS ========== */

ALTER TABLE TRANSLATION
  ADD CONSTRAINT FK_TRA_TYPE FOREIGN KEY (type) REFERENCES TRANSLATION_TYPE (name);

ALTER TABLE TRANSLATION_ENTRY
  ADD CONSTRAINT PK_TRE_ID_LAN_CODE PRIMARY KEY (id, language_code),
  ADD CONSTRAINT FK_TRE_ID_TRN_ID FOREIGN KEY (id) REFERENCES TRANSLATION (id),
  ADD CONSTRAINT FK_TRE_ID_LAN_CODE FOREIGN KEY (language_code) REFERENCES LANGUAGE_CODE (code);

ALTER TABLE COUNTRY_CODE
  ADD CONSTRAINT FK_CCD_DESC FOREIGN KEY (description) REFERENCES TRANSLATION (id);

ALTER TABLE CURRENCY_CODE
  ADD CONSTRAINT FK_CUR_DESC FOREIGN KEY (description) REFERENCES TRANSLATION (id);

ALTER TABLE RESTAURANTS
  ADD CONSTRAINT FK_RST_LOCALE FOREIGN KEY (language_code) REFERENCES LANGUAGE_CODE (code),
  ADD CONSTRAINT FK_RST_COUNTRY FOREIGN KEY (country_code) REFERENCES COUNTRY_CODE (code),
  ADD CONSTRAINT FK_RST_CURRENCY FOREIGN KEY (currency_code) REFERENCES CURRENCY_CODE (code),
  ADD CONSTRAINT FK_RST_STATUS FOREIGN KEY (status) REFERENCES RESTAURANT_STATUS (code);
CREATE UNIQUE INDEX IDX_RST_LOGIN
  ON RESTAURANTS (login);
CREATE INDEX IDX_RST_NAME
  ON RESTAURANTS (name);
CREATE INDEX IDX_RST_LAT
  ON RESTAURANTS (latitude);
CREATE INDEX IDX_RST_LONG
  ON RESTAURANTS (longitude);
CREATE INDEX IDX_RST_CITY
  ON RESTAURANTS (display_city);

ALTER TABLE MENUS
  ADD CONSTRAINT FK_MNU_TITLE FOREIGN KEY (title) REFERENCES TRANSLATION (id),
  ADD CONSTRAINT FK_MNU_STATUS FOREIGN KEY (status) REFERENCES MENU_STATUS (code);
