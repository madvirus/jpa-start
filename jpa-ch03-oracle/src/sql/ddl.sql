create user jpastart identified by jpapass;

grant resource, create session to jpastart;

create table jpastart.users (
  email varchar2(50) not null,
  name varchar2(50),
  create_date date
);
alter table jpastart.users add primary key (email);

create table jpastart.room_info (
  room_number varchar2(50) not null,
  name varchar2(50),
  description varchar2(255),
  createtime date
);
alter table jpastart.room_info add primary key (room_number);

create table jpastart.hotel (
  id varchar(100) not null,
  name varchar(50),
  grade varchar(255)
);
alter table jpastart.hotel add primary key (id);

create sequence jpastart.hotel_review_seq
start with 1 increment by 1 nomaxvalue;

create table jpastart.hotel_review (
  id int not null,
  hotel_id varchar2(100) not null,
  rate int not null,
  review_comment varchar2(255) not null,
  rating_date date
);
alter table jpastart.hotel_review add primary key (id);

create table jpastart.id_gen (
  entity varchar(100) not null primary key,
  nextid int
);

create table jpastart.city (
  id int not null primary key,
  name varchar(200)
);