# Spring Framework

## IOC

> XML 配置
>
> Annotation 配置

Spring框架的环境

1. 使用的开发工具： IDEA
2. 项目构建工具: Maven
3. 导入 spring相关的依赖



### IOC 开发使用的步骤

1. 编写注解配置类, 一般命名为 AppConfig

   ```java
   @Configuration   //表明这是一个Spring 的配置类
   @ComponentScan(value = "com.hc.service.impl")
   public class AppConfig {
   
       @Bean   //告诉spring IOC容器，把此方法的返回值当做一个bean
               //由容器管理起来
       public List<Employee> initList() {
           System.out.println("--- invoke initList method...");
           return new ArrayList<>();
       }
   }
   ```

   其它的注解：

   @Import   注解，用来导入另外一个配置类

   @ImportResource  注解， 用来导入 xml 配置

   @PropertySource   注解，用来导入 properties 文件

   

2. 在需要被IOC容器管理的类上面，打上相应的注解

   * @Service        //更加语义化的 Component  ， 修饰业务层
   * @Repository  //更加语义化的 Component  , 修饰持久层
   * @Controller   //更加语义化的 Component  , 修饰 控制层
   * @Component  // 通用型注解，相当于
   * @Bean   //只能修饰方法，不能修饰类型

3. 在测试类，可以使用 junit和testng 来进行测试

   * 方式一：
     * 纯注解的方式
     * 注解+继承方式
   * 方式二：
     * 手写代码，自已初始化 ACAC【AnnotationConfigApplicationContext】



### spring IOC 的核心依赖

 * Spring-Beans

 * Spring-Context

   

## AOP

