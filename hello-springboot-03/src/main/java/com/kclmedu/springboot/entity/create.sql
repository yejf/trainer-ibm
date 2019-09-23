create table tbl_customer (
    id integer primary key auto_increment,
    name varchar (255),
    phone varchar (32) not null unique,
    c_level varchar (25),
    age smallint,
    gender varchar (8)
) engine=InnoDB charset=utf8;