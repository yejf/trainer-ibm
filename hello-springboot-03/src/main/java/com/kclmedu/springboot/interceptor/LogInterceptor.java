package com.kclmedu.springboot.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class LogInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
                                throws Exception {
            //通过request参数来获取数据
        String remoteAddr = request.getRemoteAddr();
        String url = request.getRequestURL().toString();
        String uri = request.getRequestURI();
        //还可以获取所有的请求参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        //
        System.out.println("-- 请求IP:"+remoteAddr);
        System.out.println("-- 请求URL: "+url);
        System.out.println("请求URI："+uri);
        //
        return true;
    }
}
