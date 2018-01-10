package com.lijia.test;

import com.lijia.App;
import com.lijia.service.UserService;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class Test {
    @Autowired
    private UserService userService;

    @org.junit.Test
    public void getUser(){
//        System.out.println(userService.getUser(1L).getSex());
        System.out.println(userService.getOthers());
    }

    @org.junit.Test
    public void trance() throws Exception {
        userService.trance1();
    }

    @org.junit.Test
    public void testa() throws Exception {
        System.out.println(userService.getUser(1L).getId());
        userService.deleteUser(1L);
        System.out.println(userService.getUser(1L).getId());
    }

    @org.junit.Test
    public void testRedis(){
        userService.testRedis();
    }

}
