package com.example.demo.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

//拦截器
@Component
@Slf4j
public class DemoInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenDb tokenDb;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();
        log.info("=== preHandle ====");
        log.info("=== request.getRequestURI() ====" + requestUri);

        //拦截器放开登录、注册、swagger的token校验
        if(requestUri.equalsIgnoreCase("/hogwartsUser/login")
                || requestUri.equalsIgnoreCase("/hogwartsUser/register")
                || requestUri.contains("swagger")) {
            return true;
        }

        //从请求的header获取客户端附加Token
        String tokenStr = request.getHeader(UserBaseStr.LOGIN_TOKEN);

        if (Objects.isNull(tokenStr)){
            response.setStatus(401);
            ServiceException.throwEx("客户端未传token");
        }

        if (Objects.isNull(tokenDb.getUserInfo(tokenStr))){
            response.setStatus(401);
            ServiceException.throwEx("客户端未登录");
            return false;
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

        log.info("=== postHandle ====");
        log.info("=== request.getRequestURI() ====" + request.getRequestURI());

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

        log.info("=== afterCompletion ====");
        log.info("=== request.getRequestURI() ====" + request.getRequestURI());

    }

}

