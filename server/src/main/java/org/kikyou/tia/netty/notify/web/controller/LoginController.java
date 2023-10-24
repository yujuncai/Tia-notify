package org.kikyou.tia.netty.notify.web.controller;


import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.kikyou.tia.netty.notify.models.User;
import org.kikyou.tia.netty.notify.utils.MySecureUtil;
import org.kikyou.tia.netty.notify.web.config.AdminConfiguration;
import org.kikyou.tia.netty.notify.web.config.Auth;
import org.kikyou.tia.netty.notify.web.utils.CaptchaUtil;
import org.kikyou.tia.netty.notify.web.utils.ResultVoUtil;
import org.kikyou.tia.netty.notify.web.vo.ResultVo;
import org.kikyou.tia.netty.notify.web.vo.URL;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Controller
@RequiredArgsConstructor
public class LoginController implements ErrorController {

    private final RedisTemplate<String, Object> redisTemplate;

    private final AdminConfiguration adminConfiguration;

    /**
     * 跳转到登录页面
     */
    @GetMapping("/login")
    public String toLogin(Model model) {

        model.addAttribute("isCaptcha", true);
        String signature = MySecureUtil.aesEncrypt("jvZJhHtp3vOVmpool6QlMw==", "/tia-java");
        String s = "webDemo/index.html".concat("?").concat("appid=987654321").concat("&").concat("signature=" + signature).concat("&namespace=tia-java");
        model.addAttribute("url", s);
        return "/login";
    }

    /**
     * 实现登录
     */
    @PostMapping("/login")
    @ResponseBody
    public ResultVo login(String username, String password, String captcha, String rememberMe, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException {
        // 判断账号密码是否为空
        if (!StringUtils.hasLength(username) || !StringUtils.hasLength(password) || !StringUtils.hasLength(captcha)) {
            return ResultVoUtil.error("输入数据为空");
        }
        Optional<Cookie> code_cookie = Arrays.stream(request.getCookies()).filter(p -> p.getName().equals("code_token")).findFirst();
        String vale = code_cookie.get().getValue();
        String name = code_cookie.get().getName();
        String code = (String) redisTemplate.opsForValue().get("IMGCODE_".concat(vale));


        if (StrUtil.isEmpty(code) || !captcha.equalsIgnoreCase(code)) {
            return ResultVoUtil.error("验证码错误");
        }
        redisTemplate.delete("IMGCODE_".concat(name));

        if (username.equals(adminConfiguration.getUsername()) && password.equals(adminConfiguration.getPassword())) {

            String online_token = UUID.randomUUID().toString();
            Cookie online_cookie = new Cookie("online_token", online_token);
            online_cookie.setPath("/");
            response.addCookie(online_cookie);

            User u = new User();
            u.setName(username);
            redisTemplate.opsForValue().set("WEB_ONLINE_".concat(online_token), u, 3000, TimeUnit.SECONDS);
        } else {

            return ResultVoUtil.error("账号密码错误!");
        }
        return ResultVoUtil.success("登录成功", new URL("/main"));
    }

    /**
     * 验证码图片
     */
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置响应头信息，通知浏览器不要缓存
        response.setHeader("Expires", "-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "-1");
        response.setContentType("image/jpeg");

        // 获取验证码
        String code = CaptchaUtil.getRandomCode();

        String code_token = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("code_token", code_token);
        cookie.setPath("/");
        response.addCookie(cookie);
        redisTemplate.opsForValue().set("IMGCODE_".concat(code_token), code, 300, TimeUnit.SECONDS);
        // 输出到web页面
        ImageIO.write(CaptchaUtil.genCaptcha(code), "jpg", response.getOutputStream());
    }

    /**
     * 退出登录
     */
    @Auth
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        Optional<Cookie> cookie = Arrays.stream(request.getCookies()).filter(p -> p.getName().equals("online_token")).findFirst();
        redisTemplate.delete("WEB_ONLINE_".concat(cookie.get().getName()));
        return "redirect:/login";
    }

    /**
     * 权限不足页面
     */
    @GetMapping("/noAuth")
    public String noAuth() {
        return "/system/main/noAuth";
    }


}
