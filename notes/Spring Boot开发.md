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

