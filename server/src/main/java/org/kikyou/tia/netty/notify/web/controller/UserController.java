package org.kikyou.tia.netty.notify.web.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.notify.models.User;
import org.kikyou.tia.netty.notify.web.config.Auth;
import org.kikyou.tia.netty.notify.web.service.WebService;
import org.kikyou.tia.netty.notify.web.utils.HttpServletUtil;
import org.kikyou.tia.netty.notify.web.utils.ResultVoUtil;
import org.kikyou.tia.netty.notify.web.vo.ResultVo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final WebService userService;

    @Auth
    @GetMapping("/main/user/index")
    public String index(Model model, User user) {

        // 获取用户列表
        String username = HttpServletUtil.getParameter("username");
        Page<User> list = userService.getUserPageList(username);
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/system/user/index";
    }


    @Auth
    @RequestMapping("/user/status/delete")
    @ResponseBody
    public ResultVo delete(
            @RequestParam(value = "ids", required = false) List<String> ids) {
        // 更新状态

        if (userService.delUser(ids)) {
            return ResultVoUtil.success("成功");
        } else {
            return ResultVoUtil.error("失败，请重新操作");
        }
    }


}
