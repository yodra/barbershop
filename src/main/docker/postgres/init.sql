--DROP DATABASE barbershop;
--DROP USER IF EXISTS barbershop;

-- /*CREATE USER barbershop
-- WITH CREATEDB
--   LOGIN
--   PASSWORD '12345';
--
-- CREATE DATABASE barbershop
-- OWNER barbershop
-- ENCODING 'utf-8';
-- */
-- Start from here if you use Docker or Heroku database
-- Heroku database uses another username and database name

DROP TABLE IF EXISTS establishments;
CREATE TABLE establishments
(
  ID        SERIAL PRIMARY KEY NOT NULL,
  NAME      VARCHAR(200)       NOT NULL,
  EXTRADATA TEXT               NULL
);


DROP TABLE IF EXISTS owners;
CREATE TABLE owners
(
  ID           SERIAL PRIMARY KEY NOT NULL,
  USERNAME     VARCHAR(200)       NOT NULL,
  PASSWORDHASH VARCHAR(200)       NOT NULL,
  EXTRADATA    TEXT               NULL
);

DROP TABLE IF EXISTS establishments_owners;
CREATE TABLE establishments_owners
(
  ID        SERIAL PRIMARY KEY NOT NULL,
  ESTABLISHMENT INTEGER,
  OWNER INTEGER
);

DROP TABLE IF EXISTS appointments;
CREATE TABLE appointments (
  ID SERIAL NOT NULL PRIMARY KEY,
  TIME INT NOT NULL,
  OWNER_ID INT NOT NULL
);



INSERT INTO establishments(name)
VALUES ('Baberia Paco'),
  ('Tim Barbers'),
  ('Superbarbers');

INSERT INTO owners(username, passwordhash)
VALUES ('barbershop@leanmind.es', '$2a$12$5KiR0C/FopFFHk3m39xf6eQyiiBbHUSrYVnhbsFXX87Sg6zUGXue2');

INSERT INTO establishments_owners(establishment, owner)
VALUES (1, 1),
  (2, 1),
  (3, 1);
