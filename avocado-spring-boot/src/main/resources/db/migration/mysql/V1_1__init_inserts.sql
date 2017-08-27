# Code Tables
INSERT INTO LANGUAGE_CODE (code, name) VALUES ('it-IT', 'Italian');
INSERT INTO LANGUAGE_CODE (code, name) VALUES ('fr-BE', 'French (Belgium)');
INSERT INTO LANGUAGE_CODE (code, name) VALUES ('en-UK', 'English (UK)');
INSERT INTO LANGUAGE_CODE (code, name) VALUES ('en-US', 'English (US)');

INSERT INTO COUNTRY_CODE (code, name) VALUES ('IT', 'Italy');
INSERT INTO COUNTRY_CODE (code, name) VALUES ('BE', 'Belgium');
INSERT INTO COUNTRY_CODE (code, name) VALUES ('UK', 'United Kingdom');
INSERT INTO COUNTRY_CODE (code, name) VALUES ('US', 'United States');

INSERT INTO CURRENCY_CODE (code, name, symbol) VALUES ('EUR', 'Euro', '&euro;');
INSERT INTO CURRENCY_CODE (code, name, symbol) VALUES ('GBP', 'Great Britain Pound', '&pound;');
INSERT INTO CURRENCY_CODE (code, name, symbol) VALUES ('USD', 'US Dollar', '&#36;');

INSERT INTO USER_STATUS (code, description) VALUES ('REGISTERED', 'User has done registration waiting for confirmation to become active.');
INSERT INTO USER_STATUS (code, description) VALUES ('ACTIVE', 'User is active and can start to browse Menus and place Orders.');
INSERT INTO USER_STATUS (code, description) VALUES ('BLOCKED', 'User is blocked and cannot place orders and add comments, just browse the menu.');
INSERT INTO USER_STATUS (code, description) VALUES ('DELETED', 'User has been deleted and cannot access anymore.');

INSERT INTO USER_AUTHORITY (code, title, description) VALUES ('ROLE_ADMIN', 'Administrator', 'Has rights for CRUD actions on all entities.');
INSERT INTO USER_AUTHORITY (code, title, description) VALUES ('ROLE_MODERATOR', 'Moderator', 'Has rights to moderate comments and user visibility as well as disable user access.');
INSERT INTO USER_AUTHORITY (code, title, description) VALUES ('ROLE_RESTAURANT', 'Restaurant Manager', 'Has rights for CRUD actions on Restaurant entity.');
INSERT INTO USER_AUTHORITY (code, title, description) VALUES ('ROLE_USER', 'Client', 'Has normal rights to access, to manage Order entity and retrieve Restaurant, Menu entities information.');

INSERT INTO RESTAURANT_STATUS (code, description) VALUES ('CREATED', 'Restaurant has been created and is visible to the admin and restaurant owner.');
INSERT INTO RESTAURANT_STATUS (code, description) VALUES ('ONLINE', 'Restaurant is currently online and visible to the public.');
INSERT INTO RESTAURANT_STATUS (code, description) VALUES ('DELETED', 'Restaurant has been removed and cannot be accessed by the owner. It can only be visible by the admin in the admin console.');

INSERT INTO TRANSLATION_TYPE (name, description) VALUES ('MENU', 'Represents all the possible names of a menu');
INSERT INTO TRANSLATION (type, name) VALUES ('MENU', 'Starter');
INSERT INTO TRANSLATION_ENTRY (id, language_code, text) VALUES (1, 'it-IT', 'Antipasti');
INSERT INTO TRANSLATION_ENTRY (id, language_code, text) VALUES (1, 'fr-BE', 'Entrée');
INSERT INTO TRANSLATION_ENTRY (id, language_code, text) VALUES (1, 'en-UK', 'Starter');
INSERT INTO TRANSLATION_ENTRY (id, language_code, text) VALUES (1, 'en-US', 'Starter');

INSERT INTO MENU_STATUS (code, description) VALUES ('CREATED', 'Menu has been created and is visible to the admin and restaurant owner.');
INSERT INTO MENU_STATUS (code, description) VALUES ('ONLINE', 'Menu is currently online and visible to the public.');
INSERT INTO MENU_STATUS (code, description) VALUES ('HIDDEN', 'Menu is invisible to the public but still visible to the owner.');
INSERT INTO MENU_STATUS (code, description) VALUES ('DELETED', 'Menu has been removed and cannot be accessed by the owner. It can only be visible by the admin in the admin console.');

# Test data
INSERT INTO USERS (cre_date, mod_date, username, nickname, firstname, lastname, email, password, language_code, authority) # Password: admin
VALUES (current_timestamp(), current_timestamp(), 'admin', 'Admin', null, null, 'admin@davidea.eu', '$2a$10$6xFxTmE98R8o8YFpjyaBzeSfWck3bxScg.AMfrKUwl6sDfqSi7tC6', 'en-US', 'ROLE_ADMIN');
INSERT INTO USERS (cre_date, mod_date, username, nickname, firstname, lastname, email, password, language_code, authority) # Password: avocado_ce
VALUES (current_timestamp(), current_timestamp(), 'davideas', 'Davideas', 'David', 'Rock', 'davide@davidea.eu', '$2a$10$n7yElcquhL.cwgiV2..xw.F8UsHAB1FC/50o7VXlwT5VRuAN7IfSi', 'it-IT', 'ROLE_RESTAURANT');
INSERT INTO USERS (cre_date, mod_date, username, nickname, firstname, lastname, email, password, language_code, authority) # Password: avocado_ce
VALUES (current_timestamp(), current_timestamp(), 'jonnydoe', 'Jonny', 'John', 'Doe', 'jonny@doe.com', '$2a$10$eFrnhYicQ.fmfopfZP7lnuA5sW5RRWtOAR9jWqbwrz55VjTIbmDA6', 'en-US', 'ROLE_USER');

INSERT INTO RESTAURANTS (cre_date, mod_date, user_id, name, country_code, language_code, currency_code, latitude, longitude, display_city, display_email, display_phone, website, tables, places, description)
VALUES (current_timestamp(), current_timestamp(), 2, 'Il Pasticcio', 'BE', 'fr-BE', 'EUR', '50.838944', '4.370902', 'Brussels', 'info@ilpasticcio.be', 'www.ilpasticcio.be', '+32 2 512.62.52', 25, 60, 'Italian Restaurant');

INSERT INTO MENUS (restaurant_id, order_id, cre_date, mod_date, title_trans_id, status) VALUES (1, 1, current_timestamp(), current_timestamp(), 1, 'CREATED');
