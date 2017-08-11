
INSERT INTO LANGUAGE_CODE (code, cre_date, mod_date, name) VALUES ('it-IT', current_timestamp(), current_timestamp(), 'Italian');
INSERT INTO LANGUAGE_CODE (code, cre_date, mod_date, name) VALUES ('en-UK', current_timestamp(), current_timestamp(), 'English (UK)');
INSERT INTO LANGUAGE_CODE (code, cre_date, mod_date, name) VALUES ('en-US', current_timestamp(), current_timestamp(), 'English (US)');

INSERT INTO COUNTRY_CODE (code, cre_date, mod_date, name) VALUES ('IT', current_timestamp(), current_timestamp(), 'Italy');
INSERT INTO COUNTRY_CODE (code, cre_date, mod_date, name) VALUES ('UK', current_timestamp(), current_timestamp(), 'United Kingdom');
INSERT INTO COUNTRY_CODE (code, cre_date, mod_date, name) VALUES ('US', current_timestamp(), current_timestamp(), 'United States');

INSERT INTO CURRENCY_CODE (code, cre_date, mod_date, name, symbol) VALUES ('EUR', current_timestamp(), current_timestamp(), 'Euro', '&euro;');
INSERT INTO CURRENCY_CODE (code, cre_date, mod_date, name, symbol) VALUES ('GBP', current_timestamp(), current_timestamp(), 'Great Britain Pound', '&pound;');
INSERT INTO CURRENCY_CODE (code, cre_date, mod_date, name, symbol) VALUES ('USD', current_timestamp(), current_timestamp(), 'US Dollar', '&#36;');

INSERT INTO RESTAURANT_STATUS (code, cre_date, mod_date, description) VALUES ('CREATED', current_timestamp(), current_timestamp(), 'Restaurant has been created and is visible to the admin and restaurant owner.');
INSERT INTO RESTAURANT_STATUS (code, cre_date, mod_date, description) VALUES ('VISIBLE', current_timestamp(), current_timestamp(), 'Restaurant is currently online and visible to the public.');
INSERT INTO RESTAURANT_STATUS (code, cre_date, mod_date, description) VALUES ('DELETED', current_timestamp(), current_timestamp(), 'Restaurant has been removed and cannot be accessed by the owner. It can only be visible by the admin in the admin console.');

INSERT INTO RESTAURANTS (cre_date, mod_date, name, tables, places, language_code, country_code, currency_code, display_city, display_email, login, password)
VALUES (current_timestamp(), current_timestamp(), 'Avocado Restaurant', 10, 70, 'it_IT', 'IT', 'EUR', 'Milan', 'info@avocado.it', '$2a$10$6NZnC6xeVl2einvrpZEay.BAxscztPwiz.nHGnr9qC.jZant3skQi', '$2a$10$eFrnhYicQ.fmfopfZP7lnuA5sW5RRWtOAR9jWqbwrz55VjTIbmDA6');