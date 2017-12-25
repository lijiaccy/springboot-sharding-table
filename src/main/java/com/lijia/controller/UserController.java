package com.lijia.controller;

import com.lijia.bean.User;
import com.lijia.config.ShardingIDConfig;
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
        ShardingIDConfig shardingIDConfig = new ShardingIDConfig();
        user.setId(shardingIDConfig.generateKey().longValue());
        userService.create(user);
        return "success";
    }

    @RequestMapping("/max")
    @ResponseBody
    public User max(){
        return userService.getUserMax();
    }

    @RequestMapping("/trance")
    @ResponseBody
    public String trance(){
        userService.trance();
        return "test";
    }

    @RequestMapping("/trance1")
    @ResponseBody
    public String trance1(){
        try {
            userService.trance1();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "test";
    }
}
