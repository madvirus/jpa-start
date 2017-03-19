create table jpastart.account (
  account_num varchar(20) not null primary key,
  balance integer
) engine innodb character set utf8;

create table jpastart.customer (
  id varchar(20) not null primary key,
  ver integer,
  secret_code varchar(10)
) engine innodb character set utf8;
