# Spring Data JPA

## 思考：Spring 框架中直接使用 Spring Data JPA ?

> 当然是可以使用的，但是，需要做一些额外的配置【相对于SpringBoot而言的】, 其中，最主要的就是： @EnableJpaRepositories 注解
>
> 如果在 Web项目使用 DATA JPA，则还需要在WEB配置类中添加另一个注解： @EnableSpringDataWebSupport 注解

配置如下：

1. 在 **AppConfig.java**配置类中，添加对 Spring DATA JPA的支持

   ```java
   @Configuration
   // repositoryImplementationPostfix默认就是Impl
   // entityManagerFactoryRef默认就是entityManagerFactory
   // transactionManagerRef默认就是transactionManager
   @EnableJpaRepositories(basePackages = {"com.example.repository"},
           repositoryImplementationPostfix = "Impl",
           entityManagerFactoryRef = "entityManagerFactory",
           transactionManagerRef = "transactionManager")
   @EnableTransactionManagement    // 启用事务管理器
   public class SpringDataJpaConfig {
   
       @Bean
       public DataSource dataSource() {
   
          //....
           
       }
       
       // 配置jpa厂商适配器（参见spring实战p320）
       @Bean
       public JpaVendorAdapter jpaVendorAdapter() {
           HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
           // 设置数据库类型（可使用org.springframework.orm.jpa.vendor包下的Database枚举类）
           jpaVendorAdapter.setDatabase(Database.MYSQL);
           // 设置打印sql语句
           jpaVendorAdapter.setShowSql(true);
           // 设置不生成ddl语句
           jpaVendorAdapter.setGenerateDdl(false);
           // 设置hibernate方言
           jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
           return jpaVendorAdapter;
       }
   
       // 配置实体管理器工厂
       @Bean
       public LocalContainerEntityManagerFactoryBean entityManagerFactory(
               DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
           LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
           // 注入数据源
           emfb.setDataSource(dataSource);
           // 注入jpa厂商适配器
           emfb.setJpaVendorAdapter(jpaVendorAdapter);
           // 设置扫描基本包
           emfb.setPackagesToScan("com.example.entity");
           return emfb;
       }
   
       // 配置jpa事务管理器
       @Bean
       public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
           JpaTransactionManager transactionManager = new JpaTransactionManager();
           // 配置实体管理器工厂
           transactionManager.setEntityManagerFactory(emf);
           return transactionManager;
       }
   
   }
   ```

   

2. 如果是WEB 项目，则在 **WebMvcConfig.java** 中，也需要启动一个注解来表示对 Spring DATA JPA的支持

   ```java
   @Configuration
   @ComponentScan(basePackages = {"cn.fulgens.controller"})
   @EnableWebMvc   // 启用spring mvc
   @EnableSpringDataWebSupport     // 启用springmvc对spring data的支持
   public class WebMvcConfig extends WebMvcConfigurerAdapter {
   	//...
   }
   ```

> 可以看出，程序员需要做一些配置才能在 spring framework或 spring mvc中使用 spring data jpa，来做开发项目的持久层。
>
> 如果是在 spring boot中，以上的配置都可以不需要。

## 在 Spring Boot中 整合 Spring Data JPA

> 1. 只需要导入 相关的依赖
>
>    ```xml
>    <dependency>
>        <groupId>org.springframework.boot</groupId>
>        <artifactId>spring-boot-starter-data-jpa</artifactId>
>    </dependency>
>    ```
>
>    
>
> 2. 在 application.yml 中配置一下即可\
>
>    ```yaml
>    # jpa config
>    spring:
>      jpa:
>        generate-ddl: true
>        hibernate:
>          ddl-auto: update
>        show-sql: true
>        properties:
>          hibernate:
>            format_sql: true
>            dialect: org.hibernate.dialect.MySQL8Dialect
>    ```

## Spring Data JPA 的核心API

​	Repository<T, ID>

​		\\- CrudRepository<T, ID>

​			\\-  PagingAndSortingRepository<T, ID>

​					\\- JpaRepository<T, ID>

​							\\-  程序员的开发的持久层接口

​	如下图：

​	![springdatajpa-classdiagram](D:\学习工作区\1.codeArea\3.JavaEE_Workspace\0.SpringEcoSystem\trainer-ibm\notes\images\springdatajpa-classdiagram.png)



## 自定义查询方法

> 在SQL语句中，有很多运算符来组织查询的条件，如：
>
> 比较运算符,  >,  <,  >=,  <=, ==, <>,  !=
>
> NULL判断，  is null, is not null
>
> in, not in
>
> between and
>
> or
>
> ...

SPRING DATA JPA SQL转换引擎在translate我们的方法名时，会根据关键字来翻译，常用的关键字有：

| Keyword             | Sample                                                       | JPQL snippet                                                 |
| ------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| `And`               | `findByLastnameAndFirstname`                                 | `… where x.lastname = ?1 and x.firstname = ?2`               |
| `Or`                | `findByLastnameOrFirstname`                                  | `… where x.lastname = ?1 or x.firstname = ?2`                |
| `Is,Equals`         | `findByFirstname`,`findByFirstnameIs`,`findByFirstnameEquals` | `… where x.firstname = ?1`                                   |
| `Between`           | `findByStartDateBetween`                                     | `… where x.startDate between ?1 and ?2`                      |
| `LessThan`          | `findByAgeLessThan`                                          | `… where x.age < ?1`                                         |
| `LessThanEqual`     | `findByAgeLessThanEqual`                                     | `… where x.age <= ?1`                                        |
| `GreaterThan`       | `findByAgeGreaterThan`                                       | `… where x.age > ?1`                                         |
| `GreaterThanEqual`  | `findByAgeGreaterThanEqual`                                  | `… where x.age >= ?1`                                        |
| `After`             | `findByStartDateAfter`                                       | `… where x.startDate > ?1`                                   |
| `Before`            | `findByStartDateBefore`                                      | `… where x.startDate < ?1`                                   |
| `IsNull`            | `findByAgeIsNull`                                            | `… where x.age is null`                                      |
| `IsNotNull,NotNull` | `findByAge(Is)NotNull`                                       | `… where x.age not null`                                     |
| `Like`              | `findByFirstnameLike`                                        | `… where x.firstname like ?1`                                |
| `NotLike`           | `findByFirstnameNotLike`                                     | `… where x.firstname not like ?1`                            |
| `StartingWith`      | `findByFirstnameStartingWith`                                | `… where x.firstname like ?1` (parameter bound with appended `%`) |
| `EndingWith`        | `findByFirstnameEndingWith`                                  | `… where x.firstname like ?1` (parameter bound with prepended `%`) |
| `Containing`        | `findByFirstnameContaining`                                  | `… where x.firstname like ?1` (parameter bound wrapped in `%`) |
| `OrderBy`           | `findByAgeOrderByLastnameDesc`                               | `… where x.age = ?1 order by x.lastname desc`                |
| `Not`               | `findByLastnameNot`                                          | `… where x.lastname <> ?1`                                   |
| `In`                | `findByAgeIn(Collection<Age> ages)`                          | `… where x.age in ?1`                                        |
| `NotIn`             | `findByAgeNotIn(Collection<Age> ages)`                       | `… where x.age not in ?1`                                    |
| `True`              | `findByActiveTrue()`                                         | `… where x.active = true`                                    |
| `False`             | `findByActiveFalse()`                                        | `… where x.active = false`                                   |
| `IgnoreCase`        | `findByFirstnameIgnoreCase`                                  | `… where UPPER(x.firstame) = UPPER(?1)`                      |

参考： [官方文档](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.java-config)

## JPQL

> 全称： Java Persistence Query Language,  Java持久化查询语言， 类似于 HQL[Hibernate Query Language]
>
> SQL语法：
>
> SELECT [表别名.] 列名 [列别名],列名[列别名] | *  FROM  表名 [表别名] 
>
> WHERE 条件
>
> GROUP BY 分组
>
> HAVING 分组过滤
>
> ORDER BY 列名|表达式|列序号  [ASC | DESC]
>
> ----------------------------------------
>
> JPQL: 可以简单地理解为在 SQL的基础上，把所有写列名的位置改成 属性名，把表名的位置 改成 实体类名 即可。



## 如何去避免暴露不必要的父接口中的方法

> 我们开发的Repository接口是继承于 JpaRepository接口的，而这个父接口中有些方法我们是不需要的，如果不做处理，则会暴露给业务层，造成调用者不必要的“麻烦”

处理式方式一：

> 1. 我们可以自定义一个接口，直接从 Repository<T, ID> 接口处继承， 并打上 @NoRepositoryBean 注解。然后在此方法定义你想要暴露的方法出来。
> 2. 我们开发的Repository接口从我们自定义的  **@NoRepositoryBean** 接口中派生出来，这样就可以达到目的。



