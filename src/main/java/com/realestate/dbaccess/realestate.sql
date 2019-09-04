create database realestate;
use realestate;
create table Flat (
  id int not null auto_increment primary key,
  district varchar(128) not null,
  street varchar(128) not null,
  space double not null,
  roomsnumber int not null,
  price double not null,
  phoneNumber varchar(32)
);