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
