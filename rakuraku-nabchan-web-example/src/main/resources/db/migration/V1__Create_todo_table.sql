create table todo (
  id identity ,
  title varchar (512),
  primary key (id)
);

insert into todo (title) values ('これ');
insert into todo (title) values ('あれ');
