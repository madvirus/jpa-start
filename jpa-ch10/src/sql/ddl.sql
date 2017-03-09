create database jpastart CHARACTER SET utf8;

CREATE USER 'jpauser'@'localhost' IDENTIFIED BY 'jpapass';
CREATE USER 'jpauser'@'%' IDENTIFIED BY 'jpapass';

GRANT ALL PRIVILEGES ON jpastart.* TO 'jpauser'@'localhost';
GRANT ALL PRIVILEGES ON jpastart.* TO 'jpauser'@'%';

create table jpastart.team (
  id varchar(50) not null primary key,
  name varchar(50)
) engine innodb character set utf8;

create table jpastart.player (
  player_id varchar(50) not null primary key,
  name varchar(50),
  salary int,
  team_id varchar(50),
  UNIQUE (player_id, team_id)
) engine innodb character set utf8;

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

create table jpastart.location (
  id varchar(50) not null primary key,
  name varchar(50) not null
) engine innodb character set utf8;

create table jpastart.loc_eng (
  location_id varchar(50) not null,
  list_idx int,
  engineer_id varchar(50) not null,
  index(location_id, list_idx, engineer_id)
) engine innodb character set utf8;

create table jpastart.engineer (
  id varchar(50) not null primary key,
  name varchar(50) not null
) engine innodb character set utf8;

create table jpastart.location3 (
  id varchar(50) not null primary key,
  name varchar(50) not null
) engine innodb character set utf8;

create table jpastart.loc_eng3 (
  location_id varchar(50) not null,
  map_key varchar(50),
  engineer_id varchar(50) not null,
  index(location_id, map_key, engineer_id)
) engine innodb character set utf8;

create table jpastart.engineer3 (
  id varchar(50) not null primary key,
  name varchar(50) not null
) engine innodb character set utf8;


create table jpastart.location2 (
  id varchar(50) not null primary key,
  name varchar(50) not null
) engine innodb character set utf8;

create table jpastart.engineer2 (
  id varchar(50) not null primary key,
  name varchar(50) not null,
  location_id varchar(50),
  list_idx int,
  index(location_id, list_idx, id)
) engine innodb character set utf8;
