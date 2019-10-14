# Mysql数据库

> 基于GPL协议开源的一个RDBMS【关系型数据库管理系统】

## 基本概念

> 数据库, DataBase, 数据集的集合体
>
> 数据库管理系统， DBMS,  一套软件，包含客户端、服务端、后台服务进程等组成
>
> 关系型数据库管理系统， RDBMS， 一套RDBMS，可以管理多个数据库
>
> schema,  简单地理解为数据库对象的命名空间
>
> 用户， 数据库对使用者的一个身份认证
>
> 客户端， RDBMS都是基于C/S架构的软件，这个客户端就是与用户交互的软件
>
> 服务端，也就是可以响应/解释 客户端发送过来的命令/指令，并返回结果给客户端。
>
> 

## 安装

> Windows:   一步 下一步 即可安装完成

## 配置

> 在mysql 8.0 之后，需要手动创建一个 my.ini 的配置文件， 手动创建一个这个文件，如下：
>
> ```prop
> [mysqld]
> # 设置3306端口
> port=3306
> 
> # 自定义设置mysql的安装目录，即解压mysql压缩包的目录
> basedir=D:\database\mysql-8.0.17-winx64
> 
> # 自定义设置mysql数据库的数据存放目录
> datadir=D:\database\mysqlDATA
> 
> # 允许最大连接数
> max_connections=200
> 
> # 允许连接失败的次数，这是为了防止有人从该主机试图攻击数据库系统
> max_connect_errors=10
> 
> # 服务端使用的字符集默认为UTF8
> character-set-server=utf8
> 
> # 创建新表时将使用的默认存储引擎
> default-storage-engine=INNODB
> 
> # 默认使用“mysql_native_password”插件认证
> default_authentication_plugin=mysql_native_password
> 
> [mysql]
> # 设置mysql客户端默认字符集
> default-character-set=utf8
> 
> [client]
> # 设置mysql客户端连接服务端时默认使用的端口和默认字符集
> port=3306
> default-character-set=utf8
> ```

另外，需要配置一下 mysql

mysqld --initialize

### 启动mysql

> 1. 先把mysql添加到服务中 ，   mysqld install  [服务名]
>
> 2. 通过 net start mysql 来启动 mysql
>
> 3. 通过 net stop mysql 来停止 mysql
>
>    

## 用户管理

> 默认情况下，mysql会有一个 root 用户，从 8.0开始，在配置完成后，系统会自动打印出 root 用户的随机密码，通过这个随机密码登录后，要你修改这个密码，以后，就通过这个修改后的密码进行登录。
>
> 创建，通过 root 用户登录后，我们可以添加/创建  新的用户，可以使用如下命令：
>
> 1.  往 mysql.user 表中添加一条记录来添加一个新用户
> 2.  通过命令来创建用户
>
> 

## 客户端连接

1. 通过命令行去连接 MYSQL 数据库

   ```xml
   mysql -u root -p
   提示你输入 root 用户的密码
   
   ```

2.  连接成功后，命令行的提示符将变成  mysql> 

3. 我们可以在命令行中，输入 mysql客户端相关的命令以及SQL命令

   > \h  命令，查看帮助文档
   >
   > \s 命令，查看服端的状态信息
   >
   > use 命令， 使用指定 的数据库

4.  常用的客户端命令

   > 1. show databases   查看当前RDBMS中所管理的所有数据仓库
   > 2. use databaseName  使用/进入 指定的数据库
   > 3.  show tables  查看当前数据仓库中的所有表结构

## 创建和删除数据库

1.创建数据库语法

> create database [IF NOT EXISTS] databaseName;
>
> 注：
>
> 如果没有添加 IF NOT EXISTS 选项的话，则当系统中存在相同名字的数据库时，会报错。
>
> 如果加了 IF NOT EXISTS 选项的话，则不会报错。


2. 删除数据库

   > drop database databaseName;

   

## 选择数据库

> 我们都知道，一套RDBMS中可以管理多个 DATABASE,  所以，我们要先 选项使用哪个 DATABASE， 使用 USE  命令。如下：
>
> ```xml
> mysql> use databaseName
> ```
>
> 注：databaseName 就是RDBMS中已有的数据库
>
> 

## 数据类型

也就是表中列的数据类型，MYSQL中主要有三大数据类型

###  数值型

> 主要是表达数字相关的数据，有：
>
> 1. 整数类
>    * tinyint   【相当于byte,单字节】
>    * smallint 【相当于 short, 双字节】
>    * int   【4字节】
>    * bigint  【相当于 long, 8字节】
> 2. 浮点数类
>    * float
>    * double
>    * decimal

### 日期/时间型

1. Date     只含 年、月、日
2. Time    只含 时、分、秒
3. DateTime  包含年、月、日、时、分、秒
4. Timestamp  与 datatime 类似
5. Year   就是年份

### 字符型

字符：

1. 定长字符  char[length],  length 的最大值是 4000

2. 可变长字符  varchar[length]  ,  length 的最大值是 4000

   

**字节字符：**

1.  binary ，相当于 char

2.  varbinary, 相当于 varchar

3.  tinytext   短文本二进制字符

4.  mediumtext,  ...

5.  longtext  极大文本数据，4G

6. Blob  二进制形式的长文本字符

7. longBlog  极大的， 4G

   

## 创建表结构

> 语法：
>
> CREATE TABLE [IF NOT EXISTS] table_name(
>
> ​	column_name DATA_TYPE [DEFAULT value] [约束信息],
>
> ​	column_name DATA_TYPE [DEFAULT value] [约束信息],
>
> ​	...
>
> ​	column_name DATA_TYPE [DEFAULT value] [约束信息]
>
> )engine=INNODB charset=utf-8;
>
> 注： 
>
> 列名 不能使用关键字

如：

```sql
create table if not exists TBL_USER(
	id bigint auto_increment,
    name varchar(255) not null,
    email varchar(255) not null unique,
    status tinyint default 0,
    enroll_date datetime,
    -- 添加约束
    primary key(id)
);
```

-- 查询表结构：

```sql
desc 表名;
或者：
show columns from 表名;
```



## 约束定义

> 约束，Constraints, 是一种数据库对象，用来对表中的列值或表之间的关系进行限定。一般来讲，主要有5种约束
>
> 1. 主键约束  [Primary key], 唯一且非空，一个表中最多只有一个主键约束。【但主键约束可以跨列，形成联合主键】
> 2. 非空约束  【NOT NULL】
> 3.  唯一性约束  【UNIQUE】， 唯一性
> 4.  自定义约束  【CHECK】
> 5.  外键约束  【FOREIGN KEY】，它主要是用来表达表之间的关系。

如何来约表中的列添加约束？

1. 列级语法

   > 指的是在列定义结束之前添加约束

   案例：

   ```sql
   create table tbl_demo(
   	id bigint auto_increment PRIMARY KEY,
       name varchar(255) NOT NULL,
       email varchar(255) UNIQUE,
       age tinyint check(age > 0 and age < 127) DEFAULT 18
   );
   ```

   

2. 表级语法

   > 指的是在列定义完成之后再来添加约束

   案例：

   ```sql
   create table tbl_demo(
   	id bigint auto_increment,
       name varchar(255) NOT NULL,
       email varchar(255),
       age tinyint DEFAULT 18,
       -- 再来添加约束
       primary key(id),
       UNIQUE(email),
       check(age > 0 and age < 127)
   );
   ```

   

## 删除表结构

> 删除数据库对象的命令：
>
> DROP TABLE  table_name [CASCADE CONSTRAINTS];



## SQL命令的分类

1. DDL 语句， Data Definition Language, 数据定义语句，主要包含：

   create 命令   -- 创建数据库对象

   drop 命令    -- 删除数据库对象

   ALTER 命令     -- 修改数据库对象

   comment 命令

   truncate 命令

   rename 命令

   ....

2.  DML 语句， Data Manipulation Language, 数据操作语句， 主要包含：

   INSERT 命令

   DELETE 命令

   UPDATE 命令

3.  DQL 语句， Data Query Language, 数据查询语句，主要包含：

   SELECT 命令

4. DCL 语句

   grant

   revoke

5. DTL 语句

   begin | start transaction

   commit

   rollback

   savepoint 

   release savepoint 

6. ....

## 插入数据

> 语法：
>
> INSERT INTO  table_name(column_name[,column_name,..... ])  VALUES(v1[,v2,.....]);

注：如果我们省略 table_name后面的列名，则表示所有列都要插入，有默认值的都无效。

如：

```sql
insert into tbl_demo(name,email,age) values('jack','jack@gmail.com',19);
或
insert into tbl_demo(name,email) values('smith','smith@163.com');
```

不带列名：

```sql
insert into tbl_demo values(3,'ann','ann@gmail.com',20);
```



## 更新数据

> 语法：
>
> UPDATE table_name SET column_name=value1[,column_name=value2, .... ]
>
> [WHERE 子句]
>
> 注：
>
> 如果没有WHERE条件的话，则表示更新表中的所有行的列值。

如：

```sql
UPDATE tbl_demo SET age = age + 5;
或
UPDATE tbl_demo SET age = age + 5 where id >= 2;
```



## 删除数据

> 语法
>
> DELETE FROM table_name [WHERE 子句]

备注：

如果没有条件的删除，则表示删除表中的所有行记录。

如：

```sql
delete from tbl_demo where id < 2;
```



## 事务操作

> 事务，Transaction, 一组相关的操作，它是不可再分的，要么一起成功，要么一起失败。
>
> 数据库事务，一组相关的SQL操作。这组SQL命令不分再分，一起成功或一起失败。
>
> 数据库事务有以下的4种特性：
>
> 1. 原子性, Atomic, 它不可再分。
> 2. 一致性，Consistency, 当事结束后，内存数据与底层磁盘数据是一致的。
> 3. 隔离性，Isolation,  多个事务之间互相隔离。
> 4. 持久层，Durability, 当事务提交成功后，数据保证不会丢失。

注：

mysql数据库本身支持多种数据存储引擎，有些是支持事务的，如：InnoDB引擎。 有些是不支持事务的，如: MyISAM 引擎。

所以，我们在创建表结构时，要指定存储的引擎，不过在 mysql8.0之后，我们在启动数据库之前，通过在my.ini 配置文件中进行配置，可以指定默认的数据存储引擎。

-- 创建表结构时指定引擎的语法：

CREATE TABLE table_name(

​	column_name DATATYPE [约束信息] [DEFAULT 默认值],

​	column_name DATATYPE [约束信息] [DEFAULT 默认值],

​	....

​	...

) engine=引擎类型



### 事务操作的基本命令

> 注： mysql 默认情况下，事务是自动提交的。

所以，我们要自已控制事务的话，则需要关闭自动提交。如何关闭？有以下方法

1. 在我们执行目标SQL之前，先执行 BEGIN 命令 或  START TRANSACTION
2. 在会话中，执行 set auto commit = 1



事务命令

commit  提交事务

rollback  回滚事务

savepoint 保存事务点



事务的边界：从事务的开始到事务的结束。

什么是事务开始？

​	执行 begin 命令

什么是事务的结束？

​	以commit命令或rollback命令为界限，当执行到 commit或rollback时，就表示一个事务结束。



注：

DDL 命令是自动提交事务的，

而DML 命令我们是可以手动控制事务的。



##  用户管理

> mysql数据库是一种用表来管理表的数据库。
>
> 而所有的MYSQL用户记录都是存储在 mysql.user 这个表中。
>
> 所以，添加一个mysql用户，本质上就是往 mysql.user 表中插入一条记录。 但是，在mysql8.0之后，好像是不允许这么做了，而推荐使用 CREATE USER 命令来创建，这个就与 ORACLE 的用户管理命令是一样的。
>
> 语法：
>
> CREATE USER 'user_name'@'host' IDENTIFIED BY '密码'

给用户权限

> 语法：
>
> GRANT 权限1,权限2[,权限3,....] | all privileges ON database_name.table_name TO 'user_name'@'host'
>
> 注：
>
> database_name.table_name 经常写成 \*\.\*
>
> 第一个 \* 代表 数据库， 第二个\* 代表 表

从用户外收回权限

> 语法：
>
> REVOKE 权限1,权限2[,权限3,....] | all privileges ON database_name.table_name FROM 'user_name'@'host'

注：

做完授权后，一定要执行：

```sql
flush privileges;
```

## 查询数据

> 语法：
>
> SELECT [表别名.]column_name [列别名] [,column2_name 列别名] | *  FROM  table1_name [表别名]  
>
> [left | right [outer] JOIN table2_name [表别名] ON 关联条件
>
> [WHERE 子句]
>
> [GROUP BY 子句]
>
> [HAVING 子句]
>
> [ORDER BY 子句]
>
> 



