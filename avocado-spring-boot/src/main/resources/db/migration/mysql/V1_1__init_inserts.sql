
INSERT INTO LANGUAGE_CODE (code, cre_date, mod_date, name) VALUES ('it-IT', current_timestamp(), current_timestamp(), 'Italian');
INSERT INTO LANGUAGE_CODE (code, cre_date, mod_date, name) VALUES ('fr-BE', current_timestamp(), current_timestamp(), 'French (Belgium)');
INSERT INTO LANGUAGE_CODE (code, cre_date, mod_date, name) VALUES ('en-UK', current_timestamp(), current_timestamp(), 'English (UK)');
INSERT INTO LANGUAGE_CODE (code, cre_date, mod_date, name) VALUES ('en-US', current_timestamp(), current_timestamp(), 'English (US)');

INSERT INTO COUNTRY_CODE (code, cre_date, mod_date, name) VALUES ('IT', current_timestamp(), current_timestamp(), 'Italy');
INSERT INTO COUNTRY_CODE (code, cre_date, mod_date, name) VALUES ('BE', current_timestamp(), current_timestamp(), 'Belgium');
INSERT INTO COUNTRY_CODE (code, cre_date, mod_date, name) VALUES ('UK', current_timestamp(), current_timestamp(), 'United Kingdom');
INSERT INTO COUNTRY_CODE (code, cre_date, mod_date, name) VALUES ('US', current_timestamp(), current_timestamp(), 'United States');

INSERT INTO CURRENCY_CODE (code, cre_date, mod_date, name, symbol) VALUES ('EUR', current_timestamp(), current_timestamp(), 'Euro', '&euro;');
INSERT INTO CURRENCY_CODE (code, cre_date, mod_date, name, symbol) VALUES ('GBP', current_timestamp(), current_timestamp(), 'Great Britain Pound', '&pound;');
INSERT INTO CURRENCY_CODE (code, cre_date, mod_date, name, symbol) VALUES ('USD', current_timestamp(), current_timestamp(), 'US Dollar', '&#36;');

INSERT INTO RESTAURANT_STATUS (code, cre_date, mod_date, description) VALUES ('CREATED', current_timestamp(), current_timestamp(), 'Restaurant has been created and is visible to the admin and restaurant owner.');
INSERT INTO RESTAURANT_STATUS (code, cre_date, mod_date, description) VALUES ('VISIBLE', current_timestamp(), current_timestamp(), 'Restaurant is currently online and visible to the public.');
INSERT INTO RESTAURANT_STATUS (code, cre_date, mod_date, description) VALUES ('DELETED', current_timestamp(), current_timestamp(), 'Restaurant has been removed and cannot be accessed by the owner. It can only be visible by the admin in the admin console.');

INSERT INTO RESTAURANTS (cre_date, mod_date, name, country_code, language_code, currency_code, latitude, longitude, display_city, display_email, display_phone, website, tables, places, description, login, password)
VALUES (current_timestamp(), current_timestamp(), 'Il Pasticcio', 'BE', 'fr-BE', 'EUR', '50.838944', '4.370902', 'Brussels', 'info@ilpasticcio.be', 'www.ilpasticcio.be', '+32 2 512.62.52', 25, 60, 'Italian Restaurant', '$2a$10$6NZnC6xeVl2einvrpZEay.BAxscztPwiz.nHGnr9qC.jZant3skQi', '$2a$10$eFrnhYicQ.fmfopfZP7lnuA5sW5RRWtOAR9jWqbwrz55VjTIbmDA6');

INSERT INTO TRANSLATION (id) VALUES (1);

INSERT INTO TRANSLATION_ENTRY (id, language_code, cre_date, mod_date, name, text) VALUES (1, 'it-IT', current_timestamp(), current_timestamp(), 'Starter', 'Antipasti');
INSERT INTO TRANSLATION_ENTRY (id, language_code, cre_date, mod_date, name, text) VALUES (1, 'fr-BE', current_timestamp(), current_timestamp(), 'Starter', 'Entrée');
INSERT INTO TRANSLATION_ENTRY (id, language_code, cre_date, mod_date, name, text) VALUES (1, 'en-UK', current_timestamp(), current_timestamp(), 'Starter', 'Starter');
INSERT INTO TRANSLATION_ENTRY (id, language_code, cre_date, mod_date, name, text) VALUES (1, 'en-US', current_timestamp(), current_timestamp(), 'Starter', 'Starter');

INSERT INTO MENU_STATUS (code, cre_date, mod_date, description) VALUES ('CREATED', current_timestamp(), current_timestamp(), 'Menu has been created and is visible to the admin and restaurant owner.');
INSERT INTO MENU_STATUS (code, cre_date, mod_date, description) VALUES ('VISIBLE', current_timestamp(), current_timestamp(), 'Menu is currently online and visible to the public.');
INSERT INTO MENU_STATUS (code, cre_date, mod_date, description) VALUES ('INVISIBLE', current_timestamp(), current_timestamp(), 'Menu is invisible to the public but still visible to the owner.');
INSERT INTO MENU_STATUS (code, cre_date, mod_date, description) VALUES ('DELETED', current_timestamp(), current_timestamp(), 'Menu has been removed and cannot be accessed by the owner. It can only be visible by the admin in the admin console.');

INSERT INTO MENUS (restaurant_id, order_id, cre_date, mod_date, title, status) VALUES (1, 1, current_timestamp(), current_timestamp(), 1, 'CREATED');
