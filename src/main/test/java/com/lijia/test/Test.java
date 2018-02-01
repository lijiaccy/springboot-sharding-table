package com.lijia.test;

import com.lijia.App;
import com.lijia.service.UserService1;
import org.junit.runner.RunWith;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class Test {
    @Autowired
    private UserService1 userService;

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
