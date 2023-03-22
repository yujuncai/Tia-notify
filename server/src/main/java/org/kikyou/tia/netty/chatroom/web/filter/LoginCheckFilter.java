package org.kikyou.tia.netty.chatroom.web.filter;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.chatroom.models.User;
import org.kikyou.tia.netty.chatroom.web.utils.ResultVoUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //获取本次请求地址
        String requestURI = request.getRequestURI();
        log.info("拦截到路径{}",request);

        //配置不需要拦截的地址
        String[] url = new String[]{
                "/web/login",
                "/static/**"
        };
        //比较
        boolean check = this.check(url, requestURI);
        if(check){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }


        response.getWriter().write(JSONUtil.toJsonStr(ResultVoUtil.error("NOTLOGIN")));
        return;
    }

    public boolean check(String[] urls,String requestURI){
        for (String url : urls) {
            if(PATH_MATCHER.match(url,requestURI)){
                return true;
            }
        }
        return false;
    }}