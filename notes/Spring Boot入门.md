# Spring Boot

> 它的目的就是为了减化基于Spring 框架生态开发项目的配置，它是COC【Convention Over Configuration, 约定优于配置】理念具体的实现。

## 特点

- Create stand-alone Spring applications
- Embed Tomcat, Jetty or Undertow directly (no need to deploy WAR files)
- Provide opinionated 'starter' dependencies to simplify your build configuration
- Automatically configure Spring and 3rd party libraries whenever possible
- Provide production-ready features such as metrics, health checks and externalized configuration
- Absolutely no code generation and no requirement for XML configuration



## 开发步骤 

1. 利用 Initializr 脚手架来生成 Spring Boot 项目结构 【IDEA中自带此功能，建议直接使用IDEA来创建】

   ```http
   https://start.spring.io
   ```

   

2. 输入相关的项目信息，主要就是 groupId, artifactId, version, name, description, url 等pom元信息

3. 选择项目需要依赖的springboot组件，一般 spring web 是要选的，其它的可以根据情况

4. 生成的项目中，主要就是  pom.xml 和 src目录下的 xxxxApplication.java 文件，这个java文件就是springboot的启动类。

5. 开发一个控制器

6. 直接启动 xxxApplication 类，然后，打开浏览器，访问 控制器的 uri 即可以看结果。

## Spring Boot 常用注解

1. @SpringBootApplication    //由以下三个注解合成
   1.  @SpringBootConfiguration       //代表这是一个配置类， 相当于 @Configuration
   2.  @EnableAutoConfiguration      //启动自动化配置，会根据项目导入的依赖来自动化创建一些实例来为项目服务
   3.  @ComponentScan       //描扫指定包及其子包下的所有组件
2.  


## Spring boot 项目的 pom.xml 文件

> 最关键的是一段 parent 的引入，如下 ：
>
> ```xml
> <!-- 引用spring-boot-starter 配置 -->
>     <parent>
>         <groupId>org.springframework.boot</groupId>
>         <artifactId>spring-boot-starter-parent</artifactId>
>         <version>2.1.8.RELEASE</version>
>         <relativePath/> <!-- lookup parent from repository -->
>     </parent>
> ```
>
> 注：此处引用的 spring boot 的版本是 2.1.8

## Spring Boot项目的配置文件

> 支持两种格式，一种是 .properties 属性文件， 二种是 .yml 文件， 默认文件名如下：
>
> 1. application.properties 文件 [默认]
>
>    属性文件的配置缺少层次表达， 都是扁平的
>
>    冗余较高
>
>    优点是 简单
>
> 2. application.yml 文件
>
>    YAML是一全新数据表达式语法，它支持层次结构，可以有效减少KEY值的冗余

### YAML 语法规则  [待讲]

> 先来对比一下
>
> ```proper
> #properties(示例来源于Springboot User guide):
> 
> environments.dev.url=http://dev.bar.com
> environments.dev.name=Developer Setup
> environments.prod.url=http://foo.bar.com
> environments.prod.name=My Cool App
> my.servers[0]=dev.bar.com
> my.servers[1]=foo.bar.com
> ```
>
> YAML 格式
>
> ```yaml
> #YAML格式
> environments:
>     dev:
>         url: http://dev.bar.com
>         name: Developer Setup
>     prod:
>         url: http://foo.bar.com
>         name: My Cool App
> my:
>     servers:
>         - dev.bar.com
>         - foo.bar.com
> ```
>
> YAML使用冒号加缩进的方式代表层级（属性）关系，使用短横杠(-)代表数组元素。 

#### 基本格式要求

 1，YAML大小写敏感；
 2，使用缩进代表层级关系；
 3，缩进只能使用空格，不能使用TAB，不要求空格个数，只需要相同层级左对齐（一般2个或4个空格）

## SpringBoot 整合JDBC 【了解一下】

1. 在 pom.xml 中导入 spring-boot-starter-data-jdbc  或  spring-boot-starter-jdbc  依赖

   ```xml
   <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-jdbc</artifactId>
           </dependency>
   ```

   

2. 有了上面的依赖，就可以直接在你的 DAO 接口中注入  JdbcTemplate 对象

   ```java
   @Repository   //当然，也可以使用 @Component
   public class StudentDaoImpl implements IStudentDao {
   
       @Autowired
       private JdbcTemplate template;
       
       //....
   }
   ```

   上面2步，就可以完成整合。

### Spring Boot 整合 mybatis

1. 在pom.xml中导入 mybatis-spring-boot-starter 依赖，这个依赖中会间接依赖 spring-boot-starter-jdbc

   ```xml
   <dependency>
               <groupId>org.mybatis.spring.boot</groupId>
               <artifactId>mybatis-spring-boot-starter</artifactId>
               <version>2.1.0</version>
           </dependency>
   ```

   

2. 我们需要在 application.yml 配置文件中加入 对 mybatis的配置，如下

   ```yam
   # mybatis config
   mybatis:
     type-aliases-package: com.hc.springboot.entity
     # 如果使用注解的话，则无需指定映射文件的位置
     mapper-locations: classpath*:mapper/**/*.xml 
   ```

   

3. 编写 mybatis的接口, 并且要打上 **@Mapper** 注解

   ```java
   package com.hc.springboot.mapper;
   
   import com.hc.springboot.entity.Account;
   import org.apache.ibatis.annotations.Mapper;
   
   import java.util.List;
   
   /*****
    * DAO接口，也就是数据持久层接口，本例采用mybatis来实现
    */
   @Mapper   //如果没有这个注解，则此类的实例不会被IOC容器管理
   public interface IAccountMapper {
   
       void save(Account account);
   
       void delete(Integer id);
   
       void update(Account account);
   
       Account findById(Integer id);
   
       Account findByAccountNo(String no);
   
       List<Account> findAll();
   
   }
   
   ```

   

4. 编写 mybatis的映射文件, 一般是存放在 resources 目录下，在上面第2步中，我们指定了 mapper-locations ,我们课堂上讲的是纯注解实现的，下面是 xml 配置，可以了解一下.

   ```java
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE mapper
    PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <mapper namespace="com.hc.springboot.mapper.IAccountMapper">
   
       <insert id="save" parameterType="account">
           insert into tbl_account(accountNo,idcard,balance,pinCode)
           values(#{accountNo},#{idcard},#{balance},#{pinCode})
       </insert>
   
       <delete id="delete" parameterType="int">
           delete from tbl_account where id = #{value}
       </delete>
   
       <update id="update" parameterType="account">
           update tbl_account set
               accountNo=#{accountNo},
               idcard=#{idcard},
               balance=#{balance},
               pinCode=#{pinCode}
               where id = #{id}
       </update>
   
       <select id="findById" parameterType="int" resultType="account">
           select * from tbl_account where id = #{value}
       </select>
   
       <select id="findByAccountNo" parameterType="string" resultType="account">
            select * from tbl_account where accountNo = #{value}
       </select>
   
       <select id="findAll" resultType="account">
           select * from tbl_account
       </select>
   </mapper>
   ```

## Spring Boot 与视图技术整合

### 与JSP的整合

> 注：由于 Spring Boot 对JSP的支持很不友好，所以，如果一定要整合JSP做为视图技术的话，则需要做如下操作
>
> 1. 在 pom.xml 中，导入如下依赖
>
>    ```xml
>    <dependency>
>       <groupId>org.apache.tomcat.embed</groupId>
>       <artifactId>tomcat-embed-jasper</artifactId>
>       <scope>provided</scope>
>    </dependency>
>    <!-- 如果需要JSTL，则加入JSTL的依赖 -->
>    <dependency>
>       <groupId>javax.servlet</groupId>
>       <artifactId>jstl</artifactId>
>    </dependency>
>    ```
>
> 2.  由于 spring Boot项目是没有 webapp目录的，而且打包也是 jar  包，所以，还需要手动创建 webapp/WEB-INF/jsp 目录，把你的jsp 文件放在此目录下
>
> 3.  在 application.yml 文件中，添加 JSP 视图解析配置，如下：
>
>    ```yaml
>    spring.mvc.view.prefix=/WEB-INF/jsp/
>    spring.mvc.view.suffix=.jsp
>    ```
>
> 4.  编写控制器如下
>
>    ```java
>    @RequestMapping(value = "/issue/list")
>        public String listIssue(Model model) {
>            List<Issue> issues = issueJpa.findAll();
>            //
>            model.addAttribute("ISSUE_LIST", issues);
>            //
>            return "issue/list"; // /WEB-INF/jsp/ issue/list  .jsp
>        }
>    ```
>
>    > 以上就是在 Spring Boot中整合JSP的方式，非常不推荐，如果一定要用JSP为视图，那也没有办法，可以参考直接使用 Spring MVC +  JSP



### 与Thymeleaf的整合

> Spring Boot 对 Thymeleaf 页面模板技术的支持是很好的，所以，也是推荐大家使用 thymeleaf 技术，整合步骤如下：
>
> 1. 在 pom.xml 中，导入 thymeleaf 依赖，如下：
>
>    ```xml
>    <dependency>
>        <groupId>org.springframework.boot</groupId>
>        <artifactId>spring-boot-starter-thymeleaf</artifactId>
>    </dependency>
>    ```
>
> 2.  在 application.yml 中，添加如下配置
>
>    ```yaml
>    spring:   
>      thymeleaf:
>        cache: false         # 开发模式下，关闭缓存
>        encoding: UTF-8      # 编码，默认也是 UTF-8
>        mode: HTML           # 显示模式
>        prefix: classpath:/templates/thymeleaf/ # 自定义的thymeleaf页面模板位置，要加classpath
>        suffix: .html      # 页面以 .html 结尾
>        servlet:
>          content-type: text/html    # 内容类型
>    ```
>
> 3.  开发控制器，如下：
>
>    ```java
>    	@RequestMapping(value = "/issue/list")
>        public String listIssue(Model model) {
>            List<Issue> issues = issueJpa.findAll();
>            //
>            model.addAttribute("ISSUE_LIST", issues);
>            //
>            return "issue/list"; // /templates/thymeleaf/ issue/list  .html
>        }
>    ```

### 静态资源的访问

> 在 application.yml 中，直接添加如下配置即可
>
> ```yaml
> spring:   
>   mvc:
>     static-path-pattern: /static/**     # ** 表示多级子目录
> ```



## Thymeleaf 页面模板技术的语法

1. 在 你的HTML页面中，加入如下的命名空间

   ```html
   <html lang="en" xmlns:th="http://www.thymeleaf.org">
   ```

2. 有关 thymeleaf的语法知识，请自行学习



### 附录：

**application.properties** 文件

```properties
server.port=9090
server.servlet.context-path=/

spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/springbootdemo?characterEncoding=UTF8&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
# 默认数据源
spring.datasource.type=com.zaxxer.hikari.HikariDataSource 

# mybatis的配置
mybatis.type-aliases-package=com.kclmedu.springboot.entity

# 告诉大C，不要处理 /static 开头的请求URL
spring.mvc.static-path-pattern=/static/**
# 告诉 spring Boot,资源所在的位置, 默认指定 /templates/, /static/
# spring.resources.static-locations=classpath:/templates/,classpath:/static/

# 与thymeleaf的整合配置
spring.thymeleaf.cache=false
spring.thymeleaf.encoding=UTF-8
spring.thymeleaf.mode=HTML
spring.thymeleaf.prefix=classpath:/templates/thymeleaf/
spring.thymeleaf.suffix=.html
spring.thymeleaf.servlet.content-type=text/html
```
