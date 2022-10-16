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

create table users (
  id serial primary key,
  fname varchar (255),
  email text unique,
  password text
);
