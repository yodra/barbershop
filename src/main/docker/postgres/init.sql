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
