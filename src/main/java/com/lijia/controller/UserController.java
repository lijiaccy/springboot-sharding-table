package com.lijia.controller;

import com.lijia.bean.User;
import com.lijia.check.LijiaCheck;
import com.lijia.config.ShardingIDConfig;
import com.lijia.service.UserService1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService1 userService;

    @RequestMapping("/{id}")
    @ResponseBody
    public User getUser(@PathVariable("id") Long id){
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
        userService.testRedis();
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


    @LijiaCheck(count=5,time=50000)
    @RequestMapping("/urltest")
    @ResponseBody
    public String test(HttpServletRequest request, ModelMap modelMap) {
        return "aaa";
    }
}
