# Spring Data JPA & Hibernate

## 什么是 ORM

> ORM, Object Relationship Mapping, 对象关系映射，表达的是实体类与数据库表之间的映射关系，核心思想是如下规则：
>
> * 实体类映射成表名
> * 对象标识映射成主键列
> * 属性映射成列名
> * 对象关系映射成外键
>
> ORM有很多已经实现了的框架，比如：
>
> ​	Hibernate框架
>
> ​	MyBatis框架、
>
> ​	...

## Hibernate 框架

> 它是一款非常优秀的ORM框架，它可以自动创建并执行DDL语句、DML语句、事务语句等操作，程序员使用HIBERNATE后无需写原生态的SQL命名，通过面向对象的操作就可以完成CRUD操作。

## JPA 又是什么？

> JPA 是ORACLE公司制定的数据持久层的规范，它的全称：Java Persistence API, 它的目的是整合市面上所有的数据持久层框架，如：Hibernate, mybatis, ...

那么这些数据持久层框架要遵守 JPA规范，Hibernate框架是最早实现了JPA规范，它还得到了JPA的兼容性认证。



使用JPA规范去开发数据持久层的好处是：

1. 将来可以很方便地从 HIBERNATE 切换到 mybatis或是 toplink.  或者是反过来。
2.  由官方提供后续的版本升级

![jpa-spec](D:\学习工作区\1.codeArea\3.JavaEE_Workspace\0.SpringEcoSystem\trainer-ibm\notes\images\jpa-spec.png)

> 所以，我们可以理解为：
>
> Hibernate框架是JPA规范的实现者之一，当然，是目前JPA实现最好的框架

## Spring DATA JPA 又是什么？

> Spring Data 是Spring 技术生态下的一个“顶级”项目，与Spring Framework, Spring Boot “平级”， 它也是想一统数据持久层的技术，它当然是基于 Spring 生态， 而Spring Data jpa 是  Spring Data 框架下的一个组成部份，专注于 JPA 规范的实现，由于 Hibernate框架本身是得到了JPA官方的兼容性认证的，所以，Spring DATA JPA并没有重新去实现JPA规范，则是基于 Hibernate 框架做了改良和辅助性功能的提供，使之更好地融入到Spring技术生态中去。
>
> 如：

![spring-data-jpa](D:\学习工作区\1.codeArea\3.JavaEE_Workspace\0.SpringEcoSystem\trainer-ibm\notes\images\spring-data-jpa.png)

所以，我们可以这么理解，Spring Data JPA 是更高层次的API抽象【相当于具体的ORM框架而言的】



## Spring boot 中整合 Spring Data JPA

1. 在pom.xml导入 spring data jpa的依赖

   ```xml
   <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-data-jpa</artifactId>
           </dependency>
   ```

   

2. 在 application.yml 中配置 spring data jpa

   ```yaml
   # jpa config
   spring:
     jpa:
       generate-ddl: true
       hibernate:
         ddl-auto: update
       show-sql: true
       properties:
         hibernate:
           format_sql: true
           dialect: org.hibernate.dialect.MySQL8Dialect
   
     datasource:
       driver-class-name: com.mysql.jdbc.Driver
       url: jdbc:mysql://localhost:3306/master?characterEncoding=UTF8&serverTimezone=UTC
       username: root
       password: 123456
   ```

   

3. 实体类使用JPA规范中的注解来与表进行映射

   ```java
   package com.kclmedu.datajpa.entity;
   
   import lombok.Data;
   
   import javax.persistence.*;
   import java.util.Date;
   
   @Data
   @Entity
   @Table(name = "TBL_LOG_RECORD")
   public class LogRecord {
   
       @Id  //表明是主键
       @GeneratedValue
       private Long id;
   
       @Column
       private String title;
   
       @Column
       private String content;
   
       @Column(name = "record_time")
       private Date recordTime;
   
       @Column
       private String user;
   
   }
   ```

   

4. 写一个类继承 JpaRepository 类

   ```java
   package com.kclmedu.datajpa.dao;
   
   import com.kclmedu.datajpa.entity.LogRecord;
   import org.springframework.data.jpa.repository.JpaRepository;
   import org.springframework.stereotype.Repository;
   
   @Repository  //可选
   public interface LogRecordRepository extends JpaRepository<LogRecord, Long> {
   
       //nothing!
   
   }
   
   ```

   

5.  编写测试类

   ```java
   package com.kclmedu.datajpa.dao;
   
   import com.kclmedu.datajpa.entity.LogRecord;
   import org.junit.Test;
   import org.junit.runner.RunWith;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.boot.test.context.SpringBootTest;
   import org.springframework.test.context.junit4.SpringRunner;
   
   import java.util.Date;
   
   @SpringBootTest
   @RunWith(SpringRunner.class)
   public class LogRecordRepositoryTests {
   
       @Autowired
       private LogRecordRepository logRecordRepository;
   
       @Test
       public void testSave() {
           LogRecord record = new LogRecord();
           record.setTitle("添加操作");
           record.setContent("添加了一条日志记录");
           record.setRecordTime(new Date());
           record.setUser("admin");
           //
           logRecordRepository.save(record);
       }
   }
   ```

   