create database jpastart CHARACTER SET utf8;

CREATE USER 'jpauser'@'localhost' IDENTIFIED BY 'jpapass';
CREATE USER 'jpauser'@'%' IDENTIFIED BY 'jpapass';

GRANT ALL PRIVILEGES ON jpastart.* TO 'jpauser'@'localhost';
GRANT ALL PRIVILEGES ON jpastart.* TO 'jpauser'@'%';

create table jpastart.user (
  email varchar(50) not null primary key,
  name varchar(50),
  create_date datetime
) engine innodb character set utf8;

create table jpastart.room_info (
  id int not null auto_increment primary key,
  number varchar(50) not null,
  name varchar(50),
  description varchar(255),
  createtime datetime,
  unique key (number)
) engine innodb character set utf8;

create table jpastart.hotel (
  id varchar(100) not null primary key,
  name varchar(50),
  grade varchar(255)
) engine innodb character set utf8;

create table jpastart.hotel_review (
  id int not null auto_increment primary key,
  hotel_id varchar(100) not null,
  rate int not null,
  comment varchar(255) not null,
  rating_date datetime
) engine innodb character set utf8;

create table jpastart.id_gen (
  entity varchar(100) not null primary key,
  nextid int
) engine innodb character set utf8;

create table jpastart.city (
  id int not null primary key,
  name varchar(200)
) engine innodb character set utf8;