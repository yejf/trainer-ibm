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



## 删除表结构

## 插入数据

## 更新数据

## 删除数据

## 查询数据





