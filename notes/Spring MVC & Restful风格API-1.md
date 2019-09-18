# Spring MVC常用的功能

## 1. 文件上传下载

### A.必要条件

1. 在WebMvcConfig.java中，添加处理 Multipart-formdata 的功能@Bean， 如下：

   ```java
   @Bean
   public CommonsMultipartResolver multipartResolver() {
           CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
           //做一些设置
           multipartResolver.setDefaultEncoding("UTF-8");
           multipartResolver.setMaxUploadSize(20*1024*1024);
           multipartResolver.setMaxUploadSizePerFile(2*1024*1024); //单个文件不能超过2M
           return multipartResolver;
   }
   ```

### B.文件上传操作

1.  在前台的页面表单上，需要保证 form 表单的 method=post 以及 enctype=multipart/form-data

   ```jsp
   <form action="${pageContext.request.contextPath}/upload" method="post"
                   enctype="multipart/form-data" name="uploadForm">
       选择一个文件：<input type="file" name="uploadFile" accept="*/*">
       <input type="submit" value="上传">
   </form>
   ```

2.  在后台的控制器中，需要在参数中加上 MultipartFile 对象，并且参数名要与前台表单中<input type="file" name="与方法参数一样">  name属性值保持一致

   ```java
   @RequestMapping(value = "/upload")
       public String upload(MultipartFile uploadFile, Model model,
                               HttpSession session)
                                   throws IOException {
           log.debug("-- 上传的文件名："+uploadFile.getOriginalFilename());
           log.debug("-- 文件大小： "+uploadFile.getSize());
           //
           String originalFilename = uploadFile.getOriginalFilename();
           int pos = originalFilename.lastIndexOf(".");
           //原文件后缀名
           String suffix = originalFilename.substring(pos);
           //确定好服务端保存文件的具体位置
           ServletContext context = session.getServletContext();
           String realpath = context.getRealPath(UPLOAD_DIR);
           //产生一个uuid式的随机文件名
           String uuid = UUID.randomUUID().toString();
           //构建目标的完整文件名
           String fullname = realpath+ File.separator+uuid+suffix;
           log.debug("** 目标文件名"+fullname);
           //利用IO流进行读写，此处可以利用 commons-io中的工具类来快速完成
           FileUtils.copyInputStreamToFile(uploadFile.getInputStream(), new File(fullname));
           log.debug("-- 把上传的源文件COPY到目标文件完成....");
           //把一个信息绑定到model中，以便在前端可以显示
           model.addAttribute("originalFilename",originalFilename);
           model.addAttribute("server_file_name",fullname);
           model.addAttribute("filesize", uploadFile.getSize());
           //
           return "forward:/common/upload.jsp"; //不经过视图解析器
       }
   ```
### C.文件下载

> 需要注意的是设置响应内容类型和响应头，代码片断如下：

```java
@RequestMapping(value = "/download", method = RequestMethod.GET)
    public void downloadFile(String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取应用上下文
        ServletContext context = request.getServletContext();
        String realpath = context.getRealPath(UPLOAD_DIR);
        log.debug("要下载的文件名："+id);
        //找到指定的文件
        File parent = new File(realpath);
        File target = new File(parent, id);
        //
        if(target.isFile()) {
            //根据文件的类型不同，设置不同的响应内容类型
            if(id.endsWith(".zip")) {
                log.debug("-- 文件内容是 zip 格式，应响头为：application/zip");
                response.setContentType("application/zip");
            } else if(id.endsWith(".jpg") || id.endsWith(".jpeg")) {
                log.debug("-- 文件内容是图片格式，应响头为：image/jpeg");
                response.setContentType("image/jpeg");
            } else {
                log.debug("-- 设置成通用的格式：application/octet-stream ");
                response.setContentType("application/octet-stream");
            }
            //这个头可以让浏览器弹出保存框
            response.setHeader("Content-disposition","attachment;filename=\""+id+"\"");
            //            //把此文件以流的方式输出到客户端
            try(FileInputStream fis = new FileInputStream(target);) {
                //利用commons-io的工具类来完成读写的COPY
                IOUtils.copy(fis, response.getOutputStream());
                //刷新缓存
                response.flushBuffer();
                log.debug("下载完成");
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.debug("下载的文件："+id);
    }
```



## 2.Bean Validation[后补充的笔记]

### 传统的写法
写起来代码量很大,且效率低下.
```java
String name = user.getName();
        if(name == null || name.trim().equals("") ){
            errors.put("name", "name不能为空!");
        }else{
            if(name.trim().length()>20 ){
                errors.put("name", "name长度不能超过20!");
            }else{
                if(name 不满足某个格式){
                    errors.put("name", "name格式不正确!");
                }else{
                    if(userService.getByName(name.trim()) != null){
                        errors.put("name", "用户名已被使用!");
                    }
                }
            }
        }
```

* JSR-303 是JAVA EE 6 中的一项子规范，称为Bean Validation，官方参考实现是Hibernate Validator.
* 此实现与Hibernate ORM 没有任何关系， JSR303 用于对Java Bean 的属性值值进行验证。
* Spring MVC 支持 JSR-303，可以在控制器中对表单提交的数据方便地验证。

### 使用步骤

#### 配置 pom.xml
```xml
<!--JSR303-->
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>5.3.0.Final</version>
</dependency>
```

#### 配置 MessageSource

```java
@Configuration
@ComponentScan
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public MessageSource validationMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        //字符编码
        messageSource.setDefaultEncoding("UTF-8");
        //Set an array of basenames
        messageSource.setBasenames("validationMessages");
        //whether to use the message code as default message
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean lvfb = new LocalValidatorFactoryBean();
        lvfb.setValidationMessageSource(validationMessageSource());
        return lvfb;
    }

}
```

#### 准备校验逻辑
* 彻底理解需求
* 整理请求参数列表
* 整理校验逻辑

### 准备资源文件
基于 i18n 国际化技术，需要提供多语言环境下的各种资源文件

##### validationMessages_en.properties
```properties
user.name.invalid=Username must be between 6 and 12
user.password.invalid=The password must be between 6 and 12
user.age.invalid=Age must be between 18 and 80.
```

##### validationMessages_zh.properties
```properties
user.name.invalid=用户名必须在6到12之间
user.password.invalid=密码必须在6到12之间
user.age.invalid=年龄必须在18到200之间
```

### 定义校验封装类

```java
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.GroupSequence;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * User Data Transfer Object
 */
public class UserDto {

    @NotNull
    @Size(min = 6, max = 12, message = "{user.name.invalid}")
    private String name;

    @NotNull
    @Size(min = 6, max = 12, message = "{user.password.invalid}")
    private String password;

    @Min(value = 18, message = "{user.age.invalid}")
    @Max(value = 80, message = "{user.age.invalid}")
    private int age;

    public UserDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
```

### 控制器
```java
@RequestMapping(value = "add", method = RequestMethod.POST)
public String test(Model model, @Valid UserDto user, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
        model.addAttribute("user", user);
        model.addAttribute("errors", getErrorMap(bindingResult));
        return "userAdd";
    }
    model.addAttribute("user", user);
    return "result";
}
```

##### 可自定义提供转换方法:

```java
/**
 * 将参数错误信息封装到 map
 */
private Object getErrorMap(BindingResult bindingResult) {
    Map<String, String> errorMap = new HashMap<>();
    for (ObjectError objectError : bindingResult.getAllErrors()) {
        FieldError fieldError = (FieldError) objectError;
        String field = fieldError.getField();
        String error = fieldError.getDefaultMessage();
        errorMap.put(field, error);
    }
    return errorMap;
}
```

#### 页面
```jsp
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>Bean Validation (JSR303)</title>
</head>
<body>
<h1>Bean Validation (JSR303)</h1>
<hr/>
<p>演示提交一个User对象</p>

<form id="user" action="${path}/demo/add" method="post">
    <table width="30%">
        <tr>
            <th>Name:</th>
            <td>
                <input id="name" name="name" type="text" value="${user.name}"/>
            </td>
            <td>
                <span>${errors.name}</span>
            </td>
        </tr>
        <tr>
            <th>Password:</th>
            <td>
                <input id="password" name="password" type="password" value="${user.password}"/>
            </td>
            <td>
                <span>${errors.password}</span>
            </td>
        </tr>
        <tr>
            <th>Age:</th>
            <td>
                <input id="age" name="age" type="text" value="${user.age}"/>
            </td>
            <td>
                <span>${errors.age}</span>
            </td>
        </tr>
        <tr>
            <td style="text-align: center">
                <input type="submit" value="提交">
            </td>
        </tr>
    </table>
</form>

</body>
</html>
```
###  附录
#### JSR303提供
注解类 | 说明
-----  | -----
@Null | 必须为 null
@NotNull | 必须不为 null
@AssertTrue | 必须为 true
@AssertFalse | 必须为 false
@Min(value) | 必须是一个数字，其值必须大于等于指定的最小值
@Max(value) | 必须是一个数字，其值必须小于等于指定的最大值
@DecimalMin(value) | 必须是一个数字，其值必须大于等于指定的最小值
@DecimalMax(value) | 必须是一个数字，其值必须小于等于指定的最大值
@Size(max, min) | 大小必须在指定的范围内
@Digits (integer, fraction) | 必须是一个数字，其值必须在可接受的范围内
@Past | 必须是一个过去的日期
@Future | 必须是一个将来的日期
@Pattern(value) | 必须符合指定的正则表达式

#### Hibernate Validator提供
注解类 | 说明
-----  | -----
@Email | 必须是电子邮箱地址
@Length | 字符串的长度必须在指定的范围内
@NotEmpty |必须非空(有空格就不算Empty)
@NotBlank | 字符串的必须非空(单纯空格算Blank)
@Range | 必须在指定范围内

#### Spring MVC提供
注解类 | 说明
-----  | -----
@Valid | 数据需要校验

## 3.拦截器 【Interceptor】

> 主要就是要弄清楚  Filter、 Interceptor、 AOP三者的区别，以及各自的应用场景。

> 1. **Filter:** **过滤器**，主要是针对**请求** 的过滤，在容器层次上进行，保证**“取你所需”**
> 2. **Interceptor:**  拦截器, 是过滤器的延伸，除了可以对请求操作外，还可以对 视图和模型进行拦截，是 “拒你所拒”
> 3. **AOP:**  面向切面编程， 主要针对 业务层和持久层的代码织入，它是降低代码耦合度的一种很好的方式，基于动态代理

![interceptor](D:\学习工作区\1.codeArea\3.JavaEE_Workspace\0.SpringEcoSystem\trainer-ibm\notes\images\interceptor.png)

# Restful  风构架构

> Spring mvc 框架是完全支持 Restful 风格的，我们采用 / 匹配模式可以完美地实现Restful 操作



## REST 定义

> Representation State Transfer, 表述性状态转移， 后端的任何资源都可以采用 统一资源定位符[URI] 来定位，配合上请求的动作，可以完整地表达出请求的含义。 根据数据的常用操作，把它与不同的请求类型相匹配，主要有：
>
> C操作，Create, 创建数据,  可以使用  POST 请求
>
> R操作，Retrieve, 获取数据，可以使用 GET 请求
>
> U操作，Update， 更新数据，可以使用  PUT 请求
>
> D操作，Delete， 删除数据，可以使用  DELETE 请求

在控制器的方法中， @RequestMapping中有一个 method属性，它可以指定此方法只接收哪种请求类型。

## 如何开发 REST风格的控制器

> 与开发普通的控制器相比，需要使用 @ResponseBody 来表明以方法返回值做为响应体，基本上都是以JSON做数据交换的格式。

1. 我们的控制器可以使用 @Controller + @ResponseBody 组合注解，也可以使用 @RestController 【推荐这个】
2. 在方法上面，使用 @RequestMapping 时，要指定 method 以及 produces
3. 由于在Rest风格上，可以通过 路径参数来传递请求参数，所以需要用到 @PathVariable 注解以及 @RequestBody 注解

## Rest风格与普通的URI对比

1. 获取id为1的Employee实例或获取所有的Employee实例

   非REST风格URI:  localhost:9090/emp/get?id=1   和   localhost:9090/emp/list

    Rest风格URI:   GET localhost:9090/emp/1         和    GET localhost:9090/emp

2.  删除ID为2的Employee 和  删除所有的 Employee

   非REST风格URI:  localhost:9090/emp/delete?id=1   和   localhost:9090/emp/deleteAll

    Rest风格URI:  DELETE localhost:9090/emp/2         和    DELETE localhost:9090/emp

综上所述，REST风格的URI 充份利用了请求的类型这个动词，所以，不需要在URI层面再去说明不同的操作【相当于“动词”前移】，在REST风格的URI中，它的格式：

​	 请求动作  +  URI名词

