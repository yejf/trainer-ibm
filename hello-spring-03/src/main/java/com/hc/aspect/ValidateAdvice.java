package com.hc.aspect;

import com.hc.entity.Employee;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(1)
public class ValidateAdvice {

    @Around(value = "execution(public * com.hc.service.impl.EmployeeServiceImpl.add(..))")
    public void validate(ProceedingJoinPoint pjp) {
        //通过 ProceedingJoinPoint 参数来获取一些数据
        String mName = pjp.getSignature().getName(); //
        String cName = pjp.getTarget().getClass().getSimpleName();
        System.out.printf("-- before: 调用了类[%s]中的[%s]方法\n",cName,mName);
        Object[] args = pjp.getArgs(); //目标方法的参数
        if(args[0] != null) { //说明参不为nul
            //
            if(args[0] instanceof Employee) {
                // 直接放行
                Object result = null;
                try {
                    result = pjp.proceed();
                    System.out.println("-- after: 正常返回");
                } catch (Throwable throwable) {
                    System.out.println("-- afterThrowing: "+throwable.getMessage());
//                    throwable.printStackTrace();
                } finally {
                    System.out.println("-- afterReturning: 执行的结果："+result);
                }
            }
        } else {
            //
            System.out.println("参数为null值，不调用目标方法...");
        }
    }
}
