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

