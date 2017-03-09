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

create table jpastart.membership_card (
  card_number varchar(16) not null primary key,
  user_email varchar(50),
  expiry_date date,
  enabled boolean,
  -- foreign key (user_email) references user (email),
  constraint unique key (user_email)
) engine innodb character set utf8;

create table jpastart.user_best_sight (
  email varchar(50) not null primary key,
  title varchar(255),
  description text
  -- foreign key (email) references user (email)
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
  grade varchar(255),
  zipcode varchar(5),
  address1 varchar(255),
  address2 varchar(255)
) engine innodb character set utf8;

create table jpastart.hotel_review (
  id int not null auto_increment primary key,
  hotel_id varchar(100) not null,
  rate int not null,
  comment varchar(255) not null,
  rating_date datetime
) engine innodb character set utf8;

create table jpastart.real_user_log (
  id int not null auto_increment primary key,
  review_id int,
  used_date datetime
) engine innodb character set utf8;

create table jpastart.id_gen (
  entity varchar(100) not null primary key,
  nextid int
) engine innodb character set utf8;

create table jpastart.city (
  id int not null primary key,
  name varchar(200),
  ct_phone varchar(255),
  ct_email varchar(255),
  ct_zip varchar(255),
  ct_addr1 varchar(255),
  ct_addr2 varchar(255)
) engine innodb character set utf8;

create table jpastart.sight (
  id int not null auto_increment primary key,
  name varchar(100) not null,
  zipcode varchar(5),
  address1 varchar(100),
  address2 varchar(100),
  eng_zipcode varchar(5),
  eng_addr1 varchar(100),
  eng_addr2 varchar(100)
) engine innodb character set utf8;

create table jpastart.sight_detail (
  sight_id int not null primary key,
  hours_op varchar(255),
  holidays varchar(255),
  facilities varchar(255)
) engine innodb character set utf8;


create table jpastart.hotel_review2 (
  id int not null auto_increment primary key,
  hotel_id varchar(100) not null,
  rate int not null,
  comment varchar(255) not null,
  rating_date datetime
) engine innodb character set utf8;

create table jpastart.real_user_log2 (
  review_id int,
  used_date datetime
) engine innodb character set utf8;