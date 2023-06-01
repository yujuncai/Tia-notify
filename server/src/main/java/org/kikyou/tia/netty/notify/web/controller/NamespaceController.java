package org.kikyou.tia.netty.notify.web.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.notify.models.MainBody;
import org.kikyou.tia.netty.notify.models.User;
import org.kikyou.tia.netty.notify.web.config.Auth;
import org.kikyou.tia.netty.notify.web.service.WebService;
import org.kikyou.tia.netty.notify.web.utils.EntityBeanUtil;
import org.kikyou.tia.netty.notify.web.utils.HttpServletUtil;
import org.kikyou.tia.netty.notify.web.utils.ResultVoUtil;
import org.kikyou.tia.netty.notify.web.vo.ResultVo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
public class NamespaceController {

    private final WebService namespaceService;

    @Auth
    @GetMapping("/main/namespace/index")
    public String index(Model model, User user) {

        // 获取用户列表
        String username = HttpServletUtil.getParameter("username");
        Page<MainBody> list = namespaceService.getNameSpacePageList(username);
        model.addAttribute("list", list.getContent());
        model.addAttribute("page", list);
        return "/system/namespace/index";
    }


    @Auth
    @RequestMapping("/namespace/status/delete")
    @ResponseBody
    public ResultVo delete(
            @RequestParam(value = "ids", required = false) List<String> ids) {
        // 更新状态
        if (namespaceService.delNameSpace( ids)) {
            return ResultVoUtil.success("成功");
        } else {
            return ResultVoUtil.error("失败，请重新操作");
        }
    }

    /**
     * 跳转到添加页面
     */
    @Auth
    @GetMapping({"/namespace/add"})
    public String toAdd( Model model) {

        return "/system/namespace/add";
    }
    /**
     * 跳转到编辑页面
     */
    @Auth
    @GetMapping({"/namespace/edit"})
    public String toEdit(MainBody mainBody, Model model) {
        mainBody = namespaceService.getNameSpaceById(mainBody.getId());
        model.addAttribute("mainBody", mainBody);
        return "/system/namespace/add";
    }

    @Auth
    @PostMapping("/namespace/save")
    @ResponseBody
    public ResultVo save( MainBody mainBody) {

        List<MainBody> list=  new ArrayList<>();
        // 编辑
        if (mainBody.getId() != null) {
            MainBody beMainBody = namespaceService.getNameSpaceById(mainBody.getId());
            EntityBeanUtil.copyProperties(beMainBody, mainBody);
            list.add(mainBody);
            namespaceService.updateNameSapce(list);
        }else{
            list.add(mainBody);
            // 新增
            namespaceService.saveNameSapce(list);
        }
        return ResultVoUtil.SAVE_SUCCESS;
    }

}
