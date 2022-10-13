create table post (
   id serial primary key,
   fname text,
   description text,
   created timestamp,
   visible bool,
   city_id int
);
