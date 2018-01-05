package com.lijia.test;

import com.lijia.App;
import com.lijia.service.UserService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class Test {
    @Autowired
    private UserService userService;

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
