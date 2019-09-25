# Spring Boot开发

##  多数据源的配置

> 在实际的开发中，很有可能会需要配置多个数据源的情况，下面我们来看一下配置的步骤
>
> 1. 在 application.yml 文件中添加多个数据源的属性定义，如下 ：
>
>    ```yaml
>    spring:
>      # 多数据源配置
>      # 主数据库连接属性
>      datasource:
>        master:
>          driver-class-name: com.mysql.jdbc.Driver
>          jdbc-url: jdbc:mysql://localhost:3306/master?characterEncoding=UTF8&serverTimezone=UTC
>          username: root
>          password: root
>        slave:
>          driver-class-name: com.mysql.jdbc.Driver
>          jdbc-url: jdbc:mysql://localhost:3306/slave?characterEncoding=UTF8&serverTimezone=UTC
>          username: root
>          password: root
>    
>    ```
>
>    
>
> 2. 在config 包下面，开发一个 MasterDataSourceConfig 配置类，如下 ：
>
>    ```java
>    @Configuration //表示自己是一个配置类
>    @EnableTransactionManagement //启动事务管理器
>    public class MasterDataSourceConfig {
>    
>        @Bean("masterDS")
>        @ConfigurationProperties(prefix = "spring.datasource.master")
>        public DataSource masterDataSource() {
>            //设置 数据源属性
>            DataSource ds = DataSourceBuilder.create().build();
>            System.out.println("-- 创建主数据源："+ds);
>            //返回
>            return ds;
>        }
>    
>        @Bean(name = "masterSesssionFactory")
>        public SqlSessionFactory masterSessionFactory(
>                @Qualifier("masterDS") DataSource dataSource) throws Exception {
>            SqlSessionFactoryBean sqlSessionFactoryBean =
>                        new SqlSessionFactoryBean();
>            sqlSessionFactoryBean.setDataSource(dataSource);
>            //如果要指定 映射文件的位置
>            sqlSessionFactoryBean
>                .setTypeAliasesPackage("com.kclmedu.springboot.entity");
>            //
>            return sqlSessionFactoryBean.getObject();
>        }
>    
>        @Bean(name="masterTxManager")
>        public PlatformTransactionManager masterTxManager(
>                        @Qualifier("masterDS") DataSource dataSource) {
>            DataSourceTransactionManager dstm = new DataSourceTransactionManager();
>            dstm.setDataSource(dataSource);
>            //
>            return dstm;
>        }
>    }
>    ```
>
>    
>
> 3. 在 config  包下面，开发一个 SlaveDataSourceConfig 配置类，功能同上
>
>    代码略....
>
>    注意每个Bean的名字要与上面的不同
>
> 4. 在业务层中，添加事务注解，指定事务管理器
>
>    ```java
>    @Service
>    @Transactional(transactionManager="masterTxManager")
>    public class CustomerServiceImpl implements ICustomerService {
>    
>        @Autowired
>        private ICustomerDao customerDao;
>    
>        @Override
>        public void save(Customer c) {
>            customerDao.save(c);
>        }
>    
>        @Override
>        public Customer findById(Long id) {
>            return customerDao.findById(id);
>        }
>    
>        @Override
>        public List<Customer> findAll() {
>            return customerDao.findAll();
>        }
>    }
>    ```
>
>    

## Spring Boot中添加拦截器

> 本质上与 Spring MVC中使用是一样的，唯一不同的时，我们无需手动指定去扫描包了，步骤如下
>
> 1. 自定义一个拦截器类，继承 **HandlerInterceptorAdapter** 类，并重写你想要的方法，如下：
>
>    ```java
>    public class LogInterceptor extends HandlerInterceptorAdapter {
>    
>        @Override
>        public boolean preHandle(HttpServletRequest request,
>                                 HttpServletResponse response, Object handler)
>                                    throws Exception {
>                //通过request参数来获取数据
>            String remoteAddr = request.getRemoteAddr();
>            String url = request.getRequestURL().toString();
>            String uri = request.getRequestURI();
>            //还可以获取所有的请求参数
>            Map<String, String[]> parameterMap = request.getParameterMap();
>            //
>            System.out.println("-- 请求IP:"+remoteAddr);
>            System.out.println("-- 请求URL: "+url);
>            System.out.println("请求URI："+uri);
>            //
>            return true;
>        }
>    }
>    ```
>
>    
>
> 2. 在 config 包中，添加一个 WebMvcConfig 类，用来注册我们的拦截器类，如下：
>
>    ```java
>    @Configuration
>    public class WebMvcConfig implements WebMvcConfigurer {
>    
>    //    注册拦截器
>        @Override
>        public void addInterceptors(InterceptorRegistry registry) {
>            System.out.println("--  注册拦截器");
>            //创建我们自定义的拦截器
>            LogInterceptor logInterceptor = new LogInterceptor();
>            //
>            registry.addInterceptor(logInterceptor).addPathPatterns("/**");
>        }
>    }
>    ```
>
>    

思考一下，我们还可以在这种类中，添加哪些配置？



## 有关YAML格式

> spring boot 项目中，配置文件支持两种数据格式，、
>
> * .properties 属性文件，默认文件名: application.properties
> * .yml 文件，它是YAML格式， 默认文件名：application.yml 

YAML 是一种新型的标记语言格式，它相比 properties 而言，具有：

1. 有良好的层次结构来表达数据， 这样可以大大减小KEY的冗余。
2. 它充份利用了缩进，使用缩进来表达层次【注：不能使用TAB，而是采用空格，每个层次比上层要多2个以上的空格】



如：

abc.properties

```prop
spring.datasource.url=xxxxxx
spring.datasource.driver-class-name=xxxx
spring.datasource.username=xxxx
```

如果改成 abc.yml 

```yaml
spring: 
    datasource:
        url: xxxx
        driver-class-name: xxxx
        username: xxxx
```



## 如何使用Spring Boot 把项目发布成传统的war部署格式

> 在默认情况下，spring boot项目打包的格式是 jar, 它是基于 main 方法来执行的，如下：
>
> ```java
> package com.kclmedu.springboot;
> 
> import org.mybatis.spring.annotation.MapperScan;
> import org.springframework.boot.SpringApplication;
> import org.springframework.boot.autoconfigure.SpringBootApplication;
> import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
> 
> @SpringBootApplication
> public class HelloSpringboot03Application {
> 
>     public static void main(String[] args) {
>         SpringApplication.run(HelloSpringboot03Application.class, args);
>         System.out.println("--- SpringBootApplication run success....");
>     }
> }
> ```
>
> 打包成 jar 格式后，我们可以使用：
>
> ```cmd
> java -jar xxxx.jar    //就可以执行这个 spring boot 项目
> ```

要把 spring boot 打包成 war 格式，这个很简单，只需要在 pom.xml 中把 <packaging>这个标签的值设为 war 即可。如下：

```xml
<packaging>war</packaging>
```

但是，光改变这个，只是输出的格式变化了，当我们把此 war 文件放入到 WEB服务器中，如何去引导执行这个war包就成了一问题。

实际上，我们的WEB 服务器加载 部署的应用时，有两种方式

1. 基于传统的 web.xml 方式进行加载

2. 纯代码的方式来加载应用， 本质上是利用 **WebApplicationInitializer** , 如下：

   ```java
   package com.kclmedu.springboot;
   
   import org.springframework.boot.builder.SpringApplicationBuilder;
   import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
   
   public class HelloApplicationInitializer extends SpringBootServletInitializer {
   
       @Override
       protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
           //
           System.out.println("--- 基于web 方式启动SpringBoot应用....");
           //
           return builder.sources(HelloSpringboot03Application.class);
       }
   }
   ```

   



## Spring、Spring MVC 、Spring Boot小结

> Spring Framework 是包含 Spring MVC的，Spring 框架是一个大家族，里面包含很多子框架，如：
>
> ​	IOC
>
> ​	AOP
>
> ​	DT[tx]
>
> ​	SPRING MVC [是MVC思想的一种实现]
>
> ​	...
>
> Spring Boot 是与Spring Framework “平级”的一个项目，它是项目开发、部署、组织的一种新型方式，有可以有效地减化我们开发项目时的配置

### Spring Boot + Spring MVC 时为我们减化了哪些操作？

首先，来看一下 Spring mvc 的项目配置清单

1. web.xml  [或者是纯代码]
2. web环境的配置类，一般命名为： **WebMvcConfig.java** , 在此类中，会进行如下配置
   * 静态资源的访问配置  【一般要有】
   * 注册拦截器   【可选】
   * 视图解析器的配置 【可选】
     *  JSP
     *  Thymeleaf
     *  Beetl
     *  Velocity
     * ...
   * 文件上传的配置 【可选】
   * 表单数据的后端验证 【可选】
   * 国际化 【可选】
   * 注册全局异常处理器 【可选】
   * ...
3.  持久层和业务层的配置类，一般命名为： **AppConfig.java** , 此类中，会进行如下配置
   * 数据源的配置  【必选】
   * 根据不同的持久层实现技术，来配置不同的东西  【必选】
     *  如果是JDBC技术，则 配置 JdbcTemplate 模板类
     * 如果是 mybatis框架， 则配置 SqlSessionFactoryBean 
     *  如果是 Hibernate 框架， 则配置 SessionFactoryBean 
     * ....
   * 配置事务管理器  【如果开启DT的话，则必配】
4.  pom.xml 的配置   【有时会出现版本冲突】

**如果引入了 Spring Boot，则配置清单如下：**

1.  web.xml 没了

2. WebMvcConfig.java 配置类不需要

3. AppConfig.java 不需要

4. pom.xml  更加精减了，而且不会再有 版本冲突的问题

**那么，增加了哪些？**

1. 所有的配置信息，都可以定义在 **application.properties 或 application.yml** 文件中
2. springboot会根据导入的依赖【也就是classpath下的jar包】自动为我们进行配置【也就是自动化配置】





   







