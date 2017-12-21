package com.lijia.controller;

import com.lijia.bean.User;
import com.lijia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/{id}")
    @ResponseBody
    public User getUser(@PathVariable("id") Integer id){
        return userService.getUser(id);
    }

    @RequestMapping("/create")
    @ResponseBody
    public Object create(User user){
        userService.create(user);
        return "success";
    }

    @RequestMapping("/max")
    @ResponseBody
    public User max(){
        return userService.getUserMax();
    }
}
