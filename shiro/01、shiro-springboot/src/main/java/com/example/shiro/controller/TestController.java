package com.example.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class TestController {
    @RequestMapping({"/","index"})
    public String toIndex(Model model){
        model.addAttribute("msg","hello,shiro");
        return "index";
    }

    @GetMapping("/login")
    public String toLogin(){
        return "login";
    }

    @PostMapping("/login")
    public String login(String username,String password,Model model,@RequestParam(defaultValue =
            "false") boolean rememberMe, HttpSession session){
        //获取当前用户的 Subject 对象，该对象用于执行身份验证和授权操作。
        Subject subject = SecurityUtils.getSubject();
        //封装用户登录数据
        AuthenticationToken token = new UsernamePasswordToken(username, password, rememberMe);
        //执行登录，如果没有异常就说明ok
        try {
            subject.login(token);
//            session.setAttribute("user", token.getPrincipal().toString());
            return "redirect:index";
        } catch (UnknownAccountException e) {
            model.addAttribute("msg","用户名错误");
            return "login";
        } catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return "login";
        }
    }


    @GetMapping("/401")
    @ResponseBody
    public String Unauthorized(){
        return "你没有权限访问！";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String testAdmin(){
        return "您是管理员";
    }

    @GetMapping("/add")
    @ResponseBody
    public String add(){
        return "add";
    }

    @GetMapping("/update")
    @ResponseBody
    public String update(){
        return "update";
    }

    @GetMapping("/select")
    @ResponseBody
    public String select(){
        return "select";
    }

    @GetMapping("/delete")
    @ResponseBody
    public String delete(){
        return "delete";
    }




}
