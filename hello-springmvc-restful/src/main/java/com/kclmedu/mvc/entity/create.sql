create table tbl_emp(
    id integer primary key auto_increment,
    name varchar(255) not null,
    start_date date,
    salary double,
    title varchar(255)
)engine=InnoDB charset=utf8