package org.kikyou.tia.netty.notify.web.config;

import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.kikyou.tia.netty.notify.models.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Aspect
@Component
public class LoginAspect {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Pointcut(value = "@annotation(org.kikyou.tia.netty.notify.web.config.Auth)")
    public void access() {
    }

    @Before("access()")
    public void before() {

    }

    @Around("@annotation(auth)")
    public Object around(ProceedingJoinPoint pj, Auth auth) {


        try {
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            Optional<Cookie> code_cookie = Arrays.stream(request.getCookies()).filter(p -> p.getName().equals("online_token")).findFirst();
            if (code_cookie.isEmpty()) {
                return "redirect:/login";
            }
            String vale = code_cookie.get().getValue();
            User u = (User) redisTemplate.opsForValue().get("WEB_ONLINE_".concat(vale));

            if (u == null) {
                return "redirect:/noAuth";
            }
            return pj.proceed();


        } catch (Throwable throwable) {
            return null;
        }
    }
}
