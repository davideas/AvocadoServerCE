
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

INSERT INTO RESTAURANT_STATUS (code, description) VALUES ('CREATED', 'Restaurant has been created and is visible to the admin and restaurant owner.');
INSERT INTO RESTAURANT_STATUS (code, description) VALUES ('VISIBLE', 'Restaurant is currently online and visible to the public.');
INSERT INTO RESTAURANT_STATUS (code, description) VALUES ('DELETED', 'Restaurant has been removed and cannot be accessed by the owner. It can only be visible by the admin in the admin console.');

INSERT INTO RESTAURANTS (cre_date, mod_date, name, country_code, language_code, currency_code, latitude, longitude, display_city, display_email, display_phone, website, tables, places, description, login, password)
VALUES (current_timestamp(), current_timestamp(), 'Il Pasticcio', 'BE', 'fr-BE', 'EUR', '50.838944', '4.370902', 'Brussels', 'info@ilpasticcio.be', 'www.ilpasticcio.be', '+32 2 512.62.52', 25, 60, 'Italian Restaurant', '$2a$10$6NZnC6xeVl2einvrpZEay.BAxscztPwiz.nHGnr9qC.jZant3skQi', '$2a$10$eFrnhYicQ.fmfopfZP7lnuA5sW5RRWtOAR9jWqbwrz55VjTIbmDA6');

INSERT INTO TRANSLATION_TYPE (name, description) VALUES ('MENU', 'Represents all the possible names of a menu');

INSERT INTO TRANSLATION (id, type, name) VALUES (1, 'MENU', 'Starter');

INSERT INTO TRANSLATION_ENTRY (id, language_code, text) VALUES (1, 'it-IT', 'Antipasti');
INSERT INTO TRANSLATION_ENTRY (id, language_code, text) VALUES (1, 'fr-BE', 'Entrée');
INSERT INTO TRANSLATION_ENTRY (id, language_code, text) VALUES (1, 'en-UK', 'Starter');
INSERT INTO TRANSLATION_ENTRY (id, language_code, text) VALUES (1, 'en-US', 'Starter');

INSERT INTO MENU_STATUS (code, description) VALUES ('CREATED', 'Menu has been created and is visible to the admin and restaurant owner.');
INSERT INTO MENU_STATUS (code, description) VALUES ('VISIBLE', 'Menu is currently online and visible to the public.');
INSERT INTO MENU_STATUS (code, description) VALUES ('INVISIBLE', 'Menu is invisible to the public but still visible to the owner.');
INSERT INTO MENU_STATUS (code, description) VALUES ('DELETED', 'Menu has been removed and cannot be accessed by the owner. It can only be visible by the admin in the admin console.');

INSERT INTO MENUS (restaurant_id, order_id, cre_date, mod_date, title, status) VALUES (1, 1, current_timestamp(), current_timestamp(), 1, 'CREATED');
