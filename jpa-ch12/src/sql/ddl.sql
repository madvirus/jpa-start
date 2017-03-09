create database jpastart CHARACTER SET utf8;

CREATE USER 'jpauser'@'localhost' IDENTIFIED BY 'jpapass';
CREATE USER 'jpauser'@'%' IDENTIFIED BY 'jpapass';

GRANT ALL PRIVILEGES ON jpastart.* TO 'jpauser'@'localhost';
GRANT ALL PRIVILEGES ON jpastart.* TO 'jpauser'@'%';

create table jpastart.performance (
  id varchar(50) not null primary key,
  name varchar(50)
) engine innodb character set utf8;

create table jpastart.person (
  id varchar(50) not null primary key,
  name varchar(50)
) engine innodb character set utf8;

create table jpastart.perf_person(
  performance_id varchar(50) not null,
  person_id varchar(50) not null,
  UNIQUE (performance_id, person_id)
) engine innodb character set utf8;