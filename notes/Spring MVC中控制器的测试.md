# Spring MVC中控制器的测试

> 目的是在不启动WEB容器的情况下，对我们开发的控制器进行单行测试。

Spring测试框架提供了 MockMvc 类，这个类是Spring mvc框架对控制器做测试的主要入口。



## 步骤

1. 编写一个测试类，打上 @ContextConfiguration注解和@RunWith注解，另外再加一个 @WebAppCofigruation注解

   ```java
   @ContextConfiguration(classes = WebMvcConfig.class)
   @RunWith(SpringJUnit4ClassRunner.class)
   @WebAppConfiguration  //是为了让spring测试框架注入 WebApplicationContext对象
   public class BaseControllerTests {
       //...
   }
   ```

   

2. 在测试类中，构建 MockMvc 对象

   ```java
   @ContextConfiguration(classes = WebMvcConfig.class)
   @RunWith(SpringJUnit4ClassRunner.class)
   @WebAppConfiguration  //是为了让spring测试框架注入 WebApplicationContext对象
   public class BaseControllerTests {
   
       @Autowired
       private WebApplicationContext wac;
   
       protected MockMvc mockMvc;
   
       @Before
       public void initMockMvc() {
           //初始化MockMvc
           this.mockMvc = webAppContextSetup(wac).build();
           //如果针对单个控制器的测试
           /*EmployeeController ec = new EmployeeController();
           this.mockMvc = standaloneSetup(ec).build();*/
       }
   }
   ```

   

3. 利用MockMvc执行各种不同的请求【GET, POST, DELETE,  PUT, PATCH， ...】

   ```java
   public class UserRestControllerTest extends BaseControllerTests {
   
       @Test
       public void testgetUsers() throws Exception {
           MvcResult mvcResult = mockMvc.perform(get("/v1/api/users"))
                   .andExpect(status().is(200))
                   .andReturn();
           System.out.println(mvcResult.getResponse().getContentAsString());
       }
   }
   ```

   

4.  根据响应进行断言

   ```java
   @Test
       public void testgetEmployee() throws Exception {
           //
           MvcResult mvcResult = mockMvc.perform(post("/emp/2"))
                                       .andExpect(status().is(200))
                                       .andExpect(content().contentType("application/json;charset=UTF-8"))
                                       .andExpect(content().json("{\"id\":2,\"name\":\"员工二\",\"start_date\":1567699200000,\"salary\":12098.0,\"title\":\"Java开发工程师\"}"))
                                       .andReturn();
           //通过 mvcResult来获取数据
           System.out.printf("本次请求响应的数据模型：%s",mvcResult.getResponse().getContentAsString());
   
       }
   ```

注: 如果要对 json 数据进行断言，需要加入 jsonassert 依赖，如下 ：

```xml
<dependency>
    <groupId>org.skyscreamer</groupId>
    <artifactId>jsonassert</artifactId>
    <version>1.5.0</version>
</dependency>
```



## 所涉及到的API

* ```
  MockMvcBuilder    // 用来构建 MockMvc 对象
  ```

* ```
  MockMvcBuilders    // 用来构建 MockMvcBuilder 对象
  ```

* ```
  MockMvcRequestBuilders  //用来构建各种不同类型的请求
  ```

* ```
  MockMvcResultHandlers    //针对响应结果的处理，主要是 log， print 操作
  ```

* ```
  MockMvcResultMatchers  // 针对响应结果的匹配
  ```

