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
    public void trance(){
        userService.trance();
    }

}
