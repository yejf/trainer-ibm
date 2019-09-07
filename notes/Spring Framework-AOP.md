# Spring 框架之 AOP

> AOP, Aspect Oriented Programming, 面向切面编程，是OOP的 一个延伸。所以，AOP是一种编程理念。
>
> AOP中所涉及到的概念有：
>
> 1. 你要执行的【增强】代码，如：日志、认证、授权、事务、异常处理等...
> 2. 你的【增强】代码织入[weave]的规则，就是指 这个代码要给哪个对象的哪个方法去【增强】
> 3. 在目标方法的调用时机上面再做可配置。

## AOP 的官方术语

### Aspect, 切面

> 把非核心逻辑抽取出来的代码和切入的规则的组合， 相当于 Advice + PointCut

### Advice, 增强

> 就是目标对象的方法要增强的代码，或者说是 非核心逻辑的代码体现

### PointCut, 切入点

> 是指【增强】代码按照哪种规则去匹配哪些目标对象的哪些方法。它一般采用正则进行匹配

### JoinPoint, 连接点

> 被匹配的目标方法中，织入的【增强】代码的执行时机，主要有：
>
> 1. 在目标方法执行之前
> 2. 在目标方法执行之后【返回之后】
> 3. 如果目标方法出现异常，在catch块中执行
> 4. 在finally 块中执行
> 5. 环绕， 相当于上面4种的综合体。

### Weave, 织入

> 它是指一个过程，就是【增强】代码被加入到目标方法的过程

### Introduction, 引入

> 表达被增强代码是如何加入到目标对象中。

## AOP 的原理

> 本质上就是代理， 分为两种，一种静态代理，一种动态代理， Spring中支持两种：
>
> 1. 基于JDK内置代理  [java.lang.relfect.Proxy 和 InvocationHandler ], 这种方式要求 目标对象一定要有实现的接口。
> 2. 基于CGLIB 代码实现， 功能比较强大，它是基于字节码的预处理来完成代理的，不要求目标对象实现任何接口。
>
> Spring AOP 会根据目标对象是否有实现接口来决定采用哪种方式生成代理对象。



##  开发AOP程序的 步骤 

1. 准备一个maven项目

2. 在 AppConfig中，启动AOP 

   ```java
   @Configuration
   @ComponentScan(value = {"com.hc.service.impl","com.hc.aspect"})
   @EnableAspectJAutoProxy  //表示启动AOP 机制
   public class AppConfig {
   
       @Bean
       public List<Employee> getList() {
           //
           return new ArrayList<>();
       }
   }
   ```

3. 开发一个 切面[aspect], 在切面通过 @PointCut注解来定义匹配规则 ，当然，也可以通地连接点来定义匹配规则。

   ```java
   @Component  //表示此类的实例由spring 容器管理
   @Aspect   //告诉spring 容器， 我是一个 切面
   public class LogAdvice {
   
       public LogAdvice() {
           System.out.println("----constructor() ...");
       }
   
       @Pointcut(value = "execution(public * com.hc.service.impl.EmployeeServiceImpl.add(..))")
       public void init() {
           //nothing!
       }
   
   //    @Before(value = "init()")
       @Before("@annotation(com.hc.annotations.Special)") //只有目标方法打上了指定的注解时才会匹配
       public void preLog(JoinPoint jp) {
           //通过 JointPoint 参数可以获取 目标对象、目标方法的签名【方法名、修饰符、返回类型】、方法参数列表
           String mName = jp.getSignature().getName();
           Object[] args = jp.getArgs();
           StringBuilder stringBuilder = new StringBuilder("--- 在目标方法[");
           stringBuilder.append(mName);
           if(args != null) {
               stringBuilder.append("(");
               for(int i = 0;i<args.length;i++) {
                   stringBuilder.append(args[i]);
                   if(i != args.length - 1) {
                       stringBuilder.append(",");
                   }
               }
               stringBuilder.append(")");
           }
           stringBuilder.append("]调用之前...");
           //
           System.out.println(stringBuilder.toString());
       }
   
       //@After(value = "execution(public * com.hc.service.impl.EmployeeServiceImpl.add(..))")
       @After(value = "init()")
       public void afterLog(JoinPoint jp) {
           System.out.printf("--- 在目标方法[%s]调用之后\n", jp.getSignature().getName());
       }
   }
   ```

4. 需要注意的事，切面也是一个 @Component, 同样需要被扫描。

5. 单元测试


### 使用到的注解

1. @Aspect
2. @Component    //不是 AOP的，它是 IOC的注解
3. @PointCut
4. 连接点相关的
   1. @Before   在目标方法调用之前
   2. @After   在目标方法调用之后
   3. @AfterReturning   在 finally 块中执行，也就是目标方法返回之后
   4. @AfterThrowing   在 catch 块中执行，也就是目标方法出现异常时
   5. @Around     【上面四种的综合体，使用较多】

