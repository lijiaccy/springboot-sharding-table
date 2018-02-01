package com.lijia.test;

import com.lijia.App;
import com.lijia.service.RestUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class RestUserServiceTest {
    @Autowired
    private RestUserService restUserService;

    @Test
    public void test(){
        restUserService.findNearByPeople("1",116.322006,39.866726);
    }
}
