create table jpastart.issue (
  id int not null auto_increment primary key,
  issue_type varchar(2) not null,
  issue_date datetime,
  customer_name varchar(50),
  customer_cp varchar(20),
  content text,
  closed boolean,
  assignee_emp_id varchar(20),
  schedule_date datetime,
  response text
) engine innodb character set utf8;

create table jpastart.attach_file (
  id varchar(255) primary key,
  name varchar(255) not null,
  upload_date datetime not null
) engine innodb character set utf8;

create table jpastart.local_file (
  id varchar(255) primary key,
  path varchar(255)
) engine innodb character set utf8;

create table jpastart.cloud_file (
  id varchar(255) primary key,
  provider varchar(255),
  url varchar(255)
) engine innodb character set utf8;

create table jpastart.ent_member (
  id varchar(100) not null primary key,
  name varchar(100),
  comp_id varchar(100)
) engine innodb character set utf8;

create table jpastart.personal_member (
  id varchar(100) not null primary key,
  name varchar(100),
  email varchar(100)
) engine innodb character set utf8;

create table jpastart.temp_member (
  id varchar(100) not null primary key,
  name varchar(100),
  email varchar(100),
  expire_date datetime
) engine innodb character set utf8;

create table jpastart.auth_log (
  id int auto_increment PRIMARY KEY,
  userid varchar(100),
  ipaddress varchar(100),
  timestamp datetime,
  success boolean
) engine innodb character set utf8;

create table jpastart.item (
  id varchar(100) PRIMARY KEY,
  name varchar(100),
  price varchar(100)
) engine innodb character set utf8;

create table jpastart.category (
  id varchar(100) PRIMARY KEY,
  name varchar(100),
  crt_dtm datetime,
  crt_empid varchar(20),
  creation_ip varchar(16)
) engine innodb character set utf8;

create table jpastart.seller (
  id varchar(100) PRIMARY KEY,
  code varchar(100),
  crt_dtm datetime,
  crt_empid varchar(20),
  crt_ip varchar(16)
) engine innodb character set utf8;

