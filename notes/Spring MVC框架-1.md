# Spring MVC框架

> Spring Framework 是包含了 IOC, AOP, DT, Spring Test, Spring MVC 的。

M, 指的是 Model, 模型，数据的处理层，包含 Entity/pojo/bean,  dao, servcie

V，指的是 View，视图， 做数据呈现，包含：HTML/CSS/JS, AJAX, JSP, Freemaker, Velocity, Thymeleaf, ...

C，指的是 Controller, 控制器，做请求和响应的处理，包含： Servlet,  Controller, Action, ...

## Spring MVC框架的功能

1. 基于Spring Framework 的生态，完美与Spring IOC, AOP , DT ,Spring-test 集成
2. 提供了非侵入式的控制器，它通过配置来完成
3. 提供了视图解析器，使得视图技术与解析技术分离，这样，SPRING MVC可以支持多种不同的视图技术，如：JSP, FreeMarker, Velocity, thymeleaf
4. 提供了ModelAndView, 可以把视图与模型整合在一起，交给核心控制器进行解析。
5. 提供了异常处理机制。
6. 提供了表单数据的后端验证
7. 支持国际化[i18n]
8. ....

## 开发Spring MVC项目的步骤

1. 准备maven项目

2. 在pom.xml 中导入依赖 **spring-webmvc**  

   ```xml
   <!-- springmvc 依赖-->
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-webmvc</artifactId>
               <version>${spring.version}</version>
           </dependency>
   ```

   

3. 在web.xml中要配置Spring mvc框架的入口【要启动Spring mvc】，也就是配置前置控制器 **DispatherServlet**

   ```xml
   <!-- spring mvc 主控制器 -->
       <servlet>
           <servlet-name>DispatcherServlet</servlet-name>
           <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
   
           <init-param>
               <param-name>contextClass</param-name>
               <param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext</param-value>
           </init-param>
           <init-param>
               <param-name>contextConfigLocation</param-name>
               <param-value>com.hc.config.WebMvcConfig</param-value>
           </init-param>
           <load-on-startup>1</load-on-startup>
       </servlet>
   
       <servlet-mapping>
           <servlet-name>DispatcherServlet</servlet-name>
           <url-pattern>/</url-pattern>
       </servlet-mapping>
   ```

   

4. 编写基于注解的 Web配置类，名叫：WebMvcConfig.java , 在此类中 启动MVC模式，扫描 controller 包等

   ```java
   package com.hc.config;
   
   import org.springframework.context.annotation.ComponentScan;
   import org.springframework.context.annotation.Configuration;
   import org.springframework.context.annotation.Import;
   import org.springframework.web.servlet.config.annotation.EnableWebMvc;
   import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
   
   /*************
    * 代表的 ACWAC，也就是WEB应用相关的上下文
    */
   @Configuration   //表示这是一个Spring的注解配置类
   @ComponentScan({"com.hc.controller"})  //扫描控制层的包
   @EnableWebMvc   //启动 WebMvc
   //@Import(value = {AppConfig.class})  //加载IOC容器应用上下文
   //这就表明，此应用将有两个ApplicationContext
   public class WebMvcConfig implements WebMvcConfigurer {
   
       //nothing!
   }
   
   ```

   

5. 开发你自己的控制器【想象成 Servlet 】

   

6. 做测试

## Spring MVC框架下控制器的开发

> 框架本身提供了一个前置控制器，也就是 DispatcherServlet,  我们也把它叫 大C， 而我们自己开发的控制器叫小C， 大C接收客户端的请求后，负责把这个请求分发给目标小C， 它是如何确定分发给哪一个小C呢？

### 小C的开发

> 在Spring MVC框架下，我们开发小C，不需要实现任何的接口或继承任何抽象类，就是一个普通的JAVABEAN，所以说，SPRNIG MVC框架是非侵入式的。