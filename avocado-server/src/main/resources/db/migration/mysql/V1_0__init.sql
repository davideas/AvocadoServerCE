/*
set global innodb_file_format = BARRACUDA;
set global innodb_large_prefix = ON;
set global innodb_file_per_table = true;
*/

/* ========== INTERNATIONALIZATION ========== */

CREATE TABLE IF NOT EXISTS CONFIGURATION (
  code     VARCHAR(128)  NOT NULL PRIMARY KEY,
  cre_date TIMESTAMP     NOT NULL,
  mod_dt   TIMESTAMP     NOT NULL,
  value    VARCHAR(4000) NOT NULL
);

CREATE TABLE IF NOT EXISTS LANGUAGE_CODE (
  code        VARCHAR(5) PRIMARY KEY,
  cre_date    TIMESTAMP    NOT NULL,
  mod_date    TIMESTAMP    NOT NULL,
  name        VARCHAR(100) NOT NULL,
  name_native VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS TRANSLATION (
  id BIGINT PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS TRANSLATION_ENTRY (
  id             BIGINT,
  languange_code VARCHAR(5),
  cre_date       TIMESTAMP NOT NULL,
  mod_date       TIMESTAMP NOT NULL,
  name           VARCHAR(100) COMMENT 'English name',
  description    BLOB
);

CREATE TABLE IF NOT EXISTS COUNTRY_CODE (
  code        VARCHAR(2) PRIMARY KEY,
  cre_date    TIMESTAMP    NOT NULL,
  mod_date    TIMESTAMP    NOT NULL,
  name        VARCHAR(100) NOT NULL,
  description BIGINT COMMENT 'Country translation name'
);

CREATE TABLE IF NOT EXISTS CURRENCY_CODE (
  code        VARCHAR(3) PRIMARY KEY,
  cre_date    TIMESTAMP    NOT NULL,
  mod_date    TIMESTAMP    NOT NULL,
  symbol      VARCHAR(10)  NOT NULL,
  name        VARCHAR(100) NOT NULL,
  description BIGINT COMMENT 'Currency translation name'
);

/* ========== RESTAURANTS ========== */

/**
 * Restaurants table with login information, display and search information.
 */
CREATE TABLE IF NOT EXISTS RESTAURANTS (
  id               BIGINT PRIMARY KEY       AUTO_INCREMENT,
  cre_date         TIMESTAMP       NOT NULL,
  mod_date         TIMESTAMP       NOT NULL,
  login            VARCHAR(255)    NOT NULL,
  password         VARCHAR(255)    NOT NULL,
  last_login_date  TIMESTAMP,
  name             VARCHAR(255)    NOT NULL,
  tables           SMALLINT COMMENT 'Number of tables',
  places           SMALLINT COMMENT 'Number of places',
  language_code    VARCHAR(5)      NOT NULL,
  country_code     VARCHAR(2)      NOT NULL,
  currency_code    VARCHAR(3)      NOT NULL,
  latitude         FLOAT,
  longitude        FLOAT,
  display_address  VARCHAR(255),
  display_zip      VARCHAR(10),
  display_city     VARCHAR(255)    NOT NULL,
  display_province VARCHAR(255),
  display_email    VARCHAR(255)    NOT NULL,
  display_phone    VARCHAR(50),
  website          VARCHAR(255),
  open_hours       VARCHAR(255),
  description      VARCHAR(255),
  deleted          ENUM ('T', 'F') NOT NULL DEFAULT 'F'
);

/**
 * Multiple contacts information for internal usage.
 */
CREATE TABLE IF NOT EXISTS RESTAURANTS_CONTACT_INFO (
  id            BIGINT PRIMARY KEY AUTO_INCREMENT,
  restaurant_id BIGINT       NOT NULL,
  sort_nr       SMALLINT     NOT NULL,
  cre_date      TIMESTAMP    NOT NULL,
  mod_date      TIMESTAMP    NOT NULL,
  name          VARCHAR(255) NOT NULL,
  email         VARCHAR(255) NOT NULL,
  phone         VARCHAR(50),
  mobile_phone  VARCHAR(50),
  notes         VARCHAR(255)
);

/**
 * Legal information for internal usage.
 */
CREATE TABLE IF NOT EXISTS RESTAURANTS_LEGAL_INFO (
  id                   BIGINT PRIMARY KEY    AUTO_INCREMENT,
  restaurant_id        BIGINT       NOT NULL,
  cre_date             TIMESTAMP    NOT NULL,
  mod_date             TIMESTAMP    NOT NULL,
  legal_name           VARCHAR(255) NOT NULL,
  legal_company_id     VARCHAR(255) NOT NULL,
  legal_company_owner  VARCHAR(255) NOT NULL,
  legal_vat_number     VARCHAR(255) NOT NULL,
  legal_address        VARCHAR(255) NOT NULL,
  legal_zip            VARCHAR(10)  NOT NULL,
  legal_city           VARCHAR(255) NOT NULL,
  legal_province       VARCHAR(255),
  legal_country_code   VARCHAR(2)   NOT NULL,
  profile_type         VARCHAR(3)   NOT NULL DEFAULT 'P01',
  iban                 VARCHAR(30),
  swift                VARCHAR(11),
  bank_name            VARCHAR(255),
  contract             BLOB,
  contract_signed      BLOB,
  contract_signed_date DATE COMMENT 'Only date, no time'
);

/**
 * Profile type for Restaurants (Monthly invoice, Direct debit, etc...).
 * Internal usage.
 */
CREATE TABLE IF NOT EXISTS RESTAURANT_PROFILE_TYPE (
  code        VARCHAR(3) PRIMARY KEY,
  cre_date    TIMESTAMP   NOT NULL,
  mod_date    TIMESTAMP   NOT NULL,
  title       VARCHAR(50) NOT NULL,
  tariff      FLOAT DEFAULT 0,
  description VARCHAR(255)
);

/* ========== MENUS ========== */

/**
 * Food type (Dish, Drink, Service), internal usage.
 */
CREATE TABLE IF NOT EXISTS MENU_ITEM_TYPE (
  code        VARCHAR(3) PRIMARY KEY,
  cre_date    TIMESTAMP   NOT NULL,
  mod_date    TIMESTAMP   NOT NULL,
  title       VARCHAR(50) NOT NULL,
  description VARCHAR(255)
);

/**
 * Menu list of the Restaurants.
 */
CREATE TABLE IF NOT EXISTS MENUS (
  id            BIGINT PRIMARY KEY       AUTO_INCREMENT,
  restaurant_id BIGINT,
  order_id      SMALLINT        NOT NULL,
  cre_date      TIMESTAMP       NOT NULL,
  mod_date      TIMESTAMP       NOT NULL,
  title         BIGINT COMMENT 'Menu title translated',
  hidden        ENUM ('T', 'F') NOT NULL DEFAULT 'F',
  deleted       ENUM ('T', 'F') NOT NULL DEFAULT 'F'
);

/**
 * Menu Items.
 */
CREATE TABLE IF NOT EXISTS MENU_ITEMS (
  id                BIGINT PRIMARY KEY       AUTO_INCREMENT,
  menu_id           BIGINT,
  restaurant_id     BIGINT,
  cre_date          TIMESTAMP       NOT NULL,
  mod_date          TIMESTAMP       NOT NULL,
  title             BIGINT COMMENT 'Item title translated',
  description       BIGINT COMMENT 'Item description translated',
  type              VARCHAR(3)      NOT NULL,
  has_configuration ENUM ('T', 'F') NOT NULL DEFAULT 'F',
  price             FLOAT           NOT NULL,
  image             MEDIUMBLOB,
  image_path        VARCHAR(255),
  kcal              SMALLINT,
  total_chosen      SMALLINT COMMENT 'How many times this item was chosen',
  total_comments    SMALLINT COMMENT 'Comments count',
  total_votes       SMALLINT COMMENT 'Votes count. Rating is calculated by dividing the Sum of stars by the number of votes',
  total_likes       SMALLINT COMMENT 'Likes count',
  total_shares      SMALLINT COMMENT 'Shares count',
  sum_of_stars      FLOAT COMMENT 'Sum of all stars. Single star is between 1 and 5',
  hidden            ENUM ('T', 'F') NOT NULL DEFAULT 'F',
  deleted           ENUM ('T', 'F') NOT NULL DEFAULT 'F'
);

/**
 * Ingredients with translation
 */
CREATE TABLE IF NOT EXISTS INGREDIENTS (
  id       SMALLINT PRIMARY KEY AUTO_INCREMENT,
  cre_date TIMESTAMP NOT NULL,
  mod_date TIMESTAMP NOT NULL,
  name     BIGINT COMMENT 'Ingredient name translated'
);

/**
 * Many to many relation between Items and Ingredients.
 */
CREATE TABLE IF NOT EXISTS MENU_ITEMS_INGREDIENTS (
  item_id       BIGINT   NOT NULL,
  ingredient_id SMALLINT NOT NULL
);

/**
 * Tag names.
 */
CREATE TABLE IF NOT EXISTS TAGS (
  code     SMALLINT PRIMARY KEY,
  cre_date TIMESTAMP NOT NULL,
  mod_date TIMESTAMP NOT NULL,
  name     VARCHAR(100)
);

/**
 * Many to many relation between Items and Tags.
 */
CREATE TABLE IF NOT EXISTS MENU_ITEMS_TAGS (
  item_id BIGINT   NOT NULL,
  code    SMALLINT NOT NULL
);

/**
 * Comments for items
 */
CREATE TABLE IF NOT EXISTS MENU_ITEMS_COMMENTS (
  id        BIGINT PRIMARY KEY       AUTO_INCREMENT,
  item_id   BIGINT,
  cre_date  TIMESTAMP       NOT NULL,
  mod_date  TIMESTAMP       NOT NULL,
  moderated ENUM ('T', 'F') NOT NULL DEFAULT 'T',
  text      VARCHAR(1024)
);

/* ========== USERS ========== */

/**
 * Profile type with subscription plan.
 */
CREATE TABLE IF NOT EXISTS USER_PROFILE_TYPE (
  code        VARCHAR(5) PRIMARY KEY,
  cre_date    TIMESTAMP   NOT NULL,
  mod_date    TIMESTAMP   NOT NULL,
  title       VARCHAR(50) NOT NULL,
  description VARCHAR(255)
);

/**
 * User table with basic information.
 */
CREATE TABLE IF NOT EXISTS USERS (
  id              BIGINT PRIMARY KEY       AUTO_INCREMENT,
  cre_date        TIMESTAMP       NOT NULL,
  mod_date        TIMESTAMP       NOT NULL,
  login           VARCHAR(255)    NOT NULL,
  nickname        VARCHAR(50)     NOT NULL,
  password        VARCHAR(1024)   NOT NULL,
  email           VARCHAR(255)    NOT NULL,
  name            VARCHAR(50),
  surname         VARCHAR(50),
  language_code   VARCHAR(5) COMMENT 'Favourite language',
  avatar          BLOB,
  last_login_date TIMESTAMP,
  profile_type    VARCHAR(5)      NOT NULL,
  deleted         ENUM ('T', 'F') NOT NULL DEFAULT 'F'
);

/* ========== ORDERS ========== */

CREATE TABLE IF NOT EXISTS ORDER_STATUS (
  code        VARCHAR(10) PRIMARY KEY,
  cre_date    TIMESTAMP    NOT NULL,
  mod_date    TIMESTAMP    NOT NULL,
  name        VARCHAR(100) NOT NULL,
  description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS ORDER_PAY_MODE (
  code        VARCHAR(5) PRIMARY KEY,
  cre_date    TIMESTAMP   NOT NULL,
  mod_date    TIMESTAMP   NOT NULL,
  title       VARCHAR(50) NOT NULL,
  description VARCHAR(255)
);

/**
 * Order
*/
CREATE TABLE IF NOT EXISTS ORDERS (
  id             BIGINT PRIMARY KEY       AUTO_INCREMENT,
  cre_date       TIMESTAMP NOT NULL,
  mod_date       TIMESTAMP NOT NULL,
  restaurant_id  BIGINT,
  name           VARCHAR(50) COMMENT 'Nickname of the order',
  table_nr       SMALLINT,
  user_id        BIGINT COMMENT 'Creator/Admin of the order',
  customers_nr   BIGINT COMMENT 'Number of customers',
  total_amount   FLOAT,
  status         VARCHAR(10),
  submitted_date TIMESTAMP,
  confirmed_date TIMESTAMP,
  paid_date      TIMESTAMP,
  payment_mode   VARCHAR(5)
);

CREATE TABLE IF NOT EXISTS ORDER_ITEMS (
  order_id   BIGINT          NOT NULL,
  item_id    BIGINT          NOT NULL,
  user_id    BIGINT COMMENT 'Item owner',
  submitted  ENUM ('T', 'F') NOT NULL DEFAULT 'F',
  confirmed  ENUM ('T', 'F') NOT NULL DEFAULT 'F',
  orig_price FLOAT COMMENT 'Original price at the moment of the order'
);

/* ========== CONSTRAINTS ========== */

ALTER TABLE TRANSLATION_ENTRY
  ADD CONSTRAINT PK_TRE_ID_LAN_CODE PRIMARY KEY (id, languange_code),
  ADD CONSTRAINT FK_TRE_ID_TRN_ID FOREIGN KEY (id) REFERENCES TRANSLATION (id),
  ADD CONSTRAINT FK_TRE_ID_LAN_CODE FOREIGN KEY (languange_code) REFERENCES LANGUAGE_CODE (code);

ALTER TABLE COUNTRY_CODE
  ADD CONSTRAINT FK_CCD_DESC FOREIGN KEY (description) REFERENCES TRANSLATION (id);

ALTER TABLE CURRENCY_CODE
  ADD CONSTRAINT FK_CUR_DESC FOREIGN KEY (description) REFERENCES TRANSLATION (id);

ALTER TABLE RESTAURANTS
  ADD CONSTRAINT FK_RST_LOCALE FOREIGN KEY (language_code) REFERENCES LANGUAGE_CODE (code),
  ADD CONSTRAINT FK_RST_COUNTRY FOREIGN KEY (country_code) REFERENCES COUNTRY_CODE (code),
  ADD CONSTRAINT FK_RST_CURRENCY FOREIGN KEY (currency_code) REFERENCES CURRENCY_CODE (code);
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

ALTER TABLE RESTAURANTS_CONTACT_INFO
  ADD CONSTRAINT FK_RSC_RST_ID FOREIGN KEY (restaurant_id) REFERENCES RESTAURANTS (id);

ALTER TABLE RESTAURANTS_LEGAL_INFO
  ADD CONSTRAINT FK_RSL_RST_ID FOREIGN KEY (restaurant_id) REFERENCES RESTAURANTS (id),
  ADD CONSTRAINT FK_RSL_LEGAL_COUNTRY FOREIGN KEY (legal_country_code) REFERENCES COUNTRY_CODE (code),
  ADD CONSTRAINT FK_RSL_PROFILE_TYPE FOREIGN KEY (profile_type) REFERENCES RESTAURANT_PROFILE_TYPE (code);
CREATE INDEX IDX_RSL_LEGAL_NAME
  ON RESTAURANTS_LEGAL_INFO (legal_name);
CREATE INDEX IDX_RSL_LEGAL_VAT
  ON RESTAURANTS_LEGAL_INFO (legal_vat_number);

ALTER TABLE MENUS
  ADD CONSTRAINT FK_MNU_TITLE FOREIGN KEY (title) REFERENCES TRANSLATION (id);
CREATE UNIQUE INDEX IDX_MNU_RST_ID_ORDER_ID
  ON MENUS (restaurant_id, order_id);

ALTER TABLE MENU_ITEMS
  ADD CONSTRAINT FK_MNU_ITM_TITLE FOREIGN KEY (title) REFERENCES TRANSLATION (id),
  ADD CONSTRAINT FK_MNU_ITM_DESC FOREIGN KEY (description) REFERENCES TRANSLATION (id),
  ADD CONSTRAINT FK_MNU_ITM_TYPE FOREIGN KEY (type) REFERENCES MENU_ITEM_TYPE (CODE);
CREATE UNIQUE INDEX IDX_MNU_ITM
  ON MENU_ITEMS (id, menu_id, restaurant_id);
CREATE INDEX IDX_MNU_ITM_TITLE
  ON MENU_ITEMS (title);

ALTER TABLE INGREDIENTS
  ADD CONSTRAINT FK_ING_NAME FOREIGN KEY (name) REFERENCES TRANSLATION (id);

ALTER TABLE MENU_ITEMS_INGREDIENTS
  ADD CONSTRAINT PK_ING_ID_ITM_ID PRIMARY KEY (item_id, ingredient_id);

ALTER TABLE MENU_ITEMS_TAGS
  ADD CONSTRAINT PK_TAG_ITM_ID_CODE PRIMARY KEY (item_id, code),
  ADD CONSTRAINT FK_TAG_ITM_ID FOREIGN KEY (item_id) REFERENCES MENU_ITEMS (id),
  ADD CONSTRAINT FK_TAG_CODE FOREIGN KEY (code) REFERENCES TAGS (code);

ALTER TABLE MENU_ITEMS_COMMENTS
  ADD CONSTRAINT FK_COM_ITM_ID FOREIGN KEY (item_id) REFERENCES MENU_ITEMS (id);

ALTER TABLE USERS
  ADD CONSTRAINT FK_USR_PROFILE_TYPE FOREIGN KEY (profile_type) REFERENCES USER_PROFILE_TYPE (code),
  ADD CONSTRAINT FK_USR_FAV_LAN_CODE FOREIGN KEY (language_code) REFERENCES LANGUAGE_CODE (code);
CREATE UNIQUE INDEX IDX_USR_LOGIN
  ON USERS (login);

ALTER TABLE ORDERS
  ADD CONSTRAINT FK_ORD_RST_ID FOREIGN KEY (restaurant_id) REFERENCES RESTAURANTS (id),
  ADD CONSTRAINT FK_ORD_USR_ID FOREIGN KEY (user_id) REFERENCES USERS (id),
  ADD CONSTRAINT FK_ORD_STATUS FOREIGN KEY (status) REFERENCES ORDER_STATUS (code),
  ADD CONSTRAINT FK_ORD_PAY_MODE FOREIGN KEY (payment_mode) REFERENCES ORDER_PAY_MODE (code);

ALTER TABLE ORDER_ITEMS
  ADD CONSTRAINT PK_ORI_ORD_ID_ITM_IT PRIMARY KEY (order_id, item_id),
  ADD CONSTRAINT FK_ORI_ORD_ID FOREIGN KEY (order_id) REFERENCES ORDERS (id),
  ADD CONSTRAINT FK_ORI_USR_ID FOREIGN KEY (user_id) REFERENCES USERS (id);
