package com.harvey.module.user.controller;

import com.harvey.common.web.BaseRestSpringController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class SysUserController extends BaseRestSpringController {

    @RequestMapping(value = "/findUserByUserId",method = {RequestMethod.POST,RequestMethod.GET})
    public String findUserByUserId(ModelMap model){
        model.put("aaa","bbb");
        setSuccess(model);
        return "redirect:/template/hello.jsp";
    }
}
