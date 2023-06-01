package org.kikyou.tia.netty.notify.web.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.notify.web.config.Auth;
import org.kikyou.tia.netty.notify.web.service.WebService;
import org.kikyou.tia.netty.notify.web.utils.EntityBeanUtil;
import org.kikyou.tia.netty.notify.web.utils.HttpServletUtil;
import org.kikyou.tia.netty.notify.web.utils.ResultVoUtil;
import org.kikyou.tia.netty.notify.web.vo.Menu;
import org.kikyou.tia.netty.notify.web.vo.ResultVo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Slf4j
@Controller
@RequiredArgsConstructor
public class MenuController {
    private final StringRedisTemplate stringRedisTemplate;

    private final RedisTemplate<String, Object> redisTemplate;
    private final WebService menuService;

    @Auth
    @GetMapping("/main/menu/index")
    public String index(Model model) {
        String search = HttpServletUtil.getRequest().getQueryString();
        model.addAttribute("search", search);
        return "/system/menu/index";
    }

    /**
     * 菜单数据列表
     */
    @Auth
    @GetMapping("/menu/list")
    @ResponseBody
    public ResultVo list(Menu menu) {


        List<Menu> list = menuService.getMenuListBySortOk();

        list.forEach(editMenu -> {
            String type = String.valueOf(editMenu.getType());
            switch (type) {
                case "1":
                    editMenu.setRemark("目录");
                    break;
                case "2":
                    editMenu.setRemark("菜单");
                    break;
                case "3":
                    editMenu.setRemark("按钮");
                    break;
            }
        });
        return ResultVoUtil.success(list);
    }


    /**
     * 跳转到添加页面
     */
    @Auth
    @GetMapping({"/menu/add"})
    public String toAdd( Model model) {

        return "/system/menu/add";
    }

    /**
     * 跳转到编辑页面
     */
    @Auth
    @GetMapping({"/menu/edit"})
    public String toEdit( Menu menu, Model model) {
        Menu pMenu = menuService.getMenuById(menu.getPid());
        menu = menuService.getMenuById(menu.getId());
        model.addAttribute("menu", menu);
        model.addAttribute("pMenu", pMenu);
        return "/system/menu/add";
    }

    @Auth
    @PostMapping("/menu/save")
    @ResponseBody
    public ResultVo save( Menu menu) {

      List<Menu> list=  new ArrayList<>();
        // 编辑
        if (menu.getId() != null) {
            Menu beMenu = menuService.getMenuById(menu.getId());
            EntityBeanUtil.copyProperties(beMenu, menu);
            list.add(menu);

            menuService.updateMenu(list);
        }else{
            list.add(menu);
            // 新增
            menuService.saveMenu(list);
        }

        return ResultVoUtil.SAVE_SUCCESS;
    }


    /**
     * 设置一条或者多条数据的状态
     */
    @Auth
    @RequestMapping("/menu/status/delete")
    @ResponseBody
    public ResultVo delete(
            @RequestParam(value = "ids", required = false) List<Long> ids) {
        // 更新状态

        if (menuService.delMenu( ids)) {
            return ResultVoUtil.success("成功");
        } else {
            return ResultVoUtil.error("失败，请重新操作");
        }
    }

}
