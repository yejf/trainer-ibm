# Spring Data JPA & Hibernate 

## 1. JPA规范中的注解

> 软件包：  javax.persistence
>
> 常用的注解：
>
> * @Entity(name="")    表示实体的名字，默认就是被修饰的实体的非全限定名【也就是simple class name】
>
> * @Table(name="")   表示实体映射的表名，这个表名一般以 T或TBL 为前缀
>
> * @Id  表示映射的主键，一般来说，做为主键列的属性，最好是 Integer, Long或String 三种类型之一，在没有使用@Column修饰时，表中的主键列名与@Id修饰的属性名同名。
>
>   > 一般都是自动生成的，它有很多的生成策略，其中，JPA规范中定义的策略不多，但是，HIbernate本身提供了很多的主键生成策略。
>   >
>   > JPA规范中定义的4种生成策略：
>   >
>   > * AUTO       自动，由底层的数据库产品来决定使用哪种策略
>   > * IDENTITY， 支持Identify column的数据库
>   > * SEQUENCE， 支持序列的数据库
>   > * TABLE， 使用表来做为主键生成策略
>   >
>   > Hibernate框架实现了JPA规范，所以，它除了支持上面4种方式外，它还支持更多的主键生成策略，比如：
>   >
>   > * HiLo 高低位算法的主键生成策略
>   > * UUID 生成唯一性字符串做为主键
>   > * ...
>
> * @Column(name="", unique=false, insertable=true, updatable=true, columnDefinition="check约束")   用来修饰普通属性，把普通属性映射成列名。
>
> * @ManyToOne   表示多对一，也就是在多的那边映射时使用，一般要配合 @JoinColumn 
>
> * @JoinColumn 用来在多的那边定义一个外键列
>
> * @OneToMany  表示一对多，定义在一的那边
>
> * @ManyToMany  表示多对多， 由于在DB关系列中，不能表达多对多，所以，它会拆分成2个一对多。【基于中间表】
>
> * @OneToOne  表示一对一， 本质就是特殊的一对多。【也就是 外键+唯一性约束 的情况】
>
> *  ....



### @RepositoryDefinition 注解的使用

> 这个注解也是用来避免我们定义的Repository接口方法暴露的，使用它来标记的类型是不需要继承任何父接口的。我们的方法按照规则定义即可。



## 2. JPQL的使用



## 3. 关联映射

### 一对多

> 主要就是使用 
>
> @OneToMany
>
> @ManyToOne + @JoinColumn  [多的那边负责维护外键]

### 多对多

> 注意：数据库中不支持多对多，要把它折分2个一对多关系，使用的注解：
>
> @ManyToMany
>
> 另一边就是
>
> @ManyToMany + @JoinTable

### 延迟加载

> 关联时，针对多的那一边默认会采用延迟加载策略，可以通过设置 fetch属性的值来改变这个默认值。
>
> fetch=FetchType.EAGER
>
> fetch=FetchType.LAZY 

### 级联操作

> 关联时，操作一个实体对象时，是否要级联操作关联的另一边，默认情况下是不操作的，可以通过修改 cascade属性的值来改变这个默认值。
>
> cascade=CascadeType.ALL
>
> ...

### no Session 问题的解决

> 默认情况下，hibernate的session会被 Spring Boot 托管，当我们调用的Repository方法结束后，hibernate框架的session就会关闭，而此时再去调用实体加载关联的另一边时，就会出现加载异常[no session]，我们可以通过在 application.yml 中添加如下配置来解决：

```yaml
spring:
  jpa:
    generate-ddl: true
    open-in-view: true     # 添加此项
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.MySQL8Dialect
        enable_lazy_load_no_trans: true     # 添加此项
```



## 待讲知识点：

1. 限定查询结果的行数

   first

   top

   distinct

2. 使用 @Query 注解来自定义查询

   ```java
   @Query("from User u where u.name = ?1")
   User getByName(String name);
   ```

3. 使用命名参数，注解@Param

   ```java
   @Query("from User u where u.name = :username or u.status = :status")
   User getByUsernameOrStatus(@Param("username") String username, @Param("status") int status);
   ```

4. 关联查询

   1. 隐式关联，适合于关联的另一边是一的情况，如下：

      ```java
      @Query("from Address a where a.user.name = :uname")
      List<Address> findByUserName(@Param("uname") String userName);
      ```

      

   2. 显示关联，适合于关联的另一边是多的情况

      ```java
      @Query("select u from User u join u.addressList a where a.province = :province")
      List<User> findByProvince(@Param("province") String province);
      ```

      

5. 使用 SpEL Expression 语法

   > 注： 需要在 @Entity注解上通过 name来指定实体的名字，然后才可以用如下的语法去引用

   ```java
   @Query("select u from #{#entityName} u where u.name = ?1")
   User getByName(String name);
   ```

6. 投影查询 [projection]

   > 利用 select 关键字来查询你想要的列.

   语法：

   select 类别名.属性名, 类别名.属性名, ... FROM 类名 类别名 WHERE 条件...

   如果要把这些列“封装”成一个对象，我们可以开发这类对象【叫 值对象，Value Object, VO]

   上面的语法就可以改成：

   select new xx.xx.xxVO(类别名.属性名, 类别名.属性名, ...)  FROM 类名 类别名 WHERE 条件...

7. 使用原生查询 nativeQuery

   > 写原生态的sql 语句， 在@Query 注解中，使用 nativeQuery=true 属性

8. 分页查询及排序

   > 方法的设计上，参数加上 Pageable ， 返回值以 Page<T> 做为类型

   

9. 自定义修改和删除 操作  @Modifying 注解的应用

   > 在@Query注解上面再加上一个注解： @Modifying 
   >
   > 就可以支持如下语法：
   >
   > 1. UPDATE 实体名 别名 Set 别名.属性名 = :参数 ....
   >
   > 2. DELETE FROM  实体名 别名 WHERE  别名.属性名 = :参数 ....
   >
   >    

10. 多表查询

    > 本质上是关联

