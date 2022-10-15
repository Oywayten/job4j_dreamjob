create table post (
   id serial primary key,
   fname text,
   description text,
   created timestamp,
   visible bool,
   city_id int
);

create table candidate (
   id serial primary key,
   fname text,
   description text,
   created timestamp,
   file bytea
);

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  fname varchar (255),
  email TEXT unique,
  password TEXT
);
