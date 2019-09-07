package com.hc.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/****************
 * 日志增强代码，所以取名 XXXAdvice
 */
@Component  //表示此类的实例由spring 容器管理
@Aspect   //告诉spring 容器， 我是一个 切面
@Order(2)  //AOP 增强代码执行的顺序，此值越大，优先级越高
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
