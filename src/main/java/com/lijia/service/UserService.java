package com.lijia.service;

import com.lijia.bean.User;
import com.lijia.config.ShardingIDConfig;
import com.lijia.mapper.UserMapper;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUser(Integer id) {
        return  userMapper.getUser(id);

    }

    public void create(User user) {
        userMapper.create(user);
    }

    public User getUserMax() {
        return userMapper.getUserMax();
    }

    @Transactional(rollbackFor = Exception.class)
    public void trance() {
        try {
            ((UserService)AopContext.currentProxy()).trance1();
//            trance1();
        } catch (Exception e) {
            e.printStackTrace();
        }

            User user = new User();
            ShardingIDConfig shardingIDConfig = new ShardingIDConfig();
            user.setId(shardingIDConfig.generateKey().longValue());
            user.setName("trance");
            user.setSex(0);
            userMapper.create(user);

    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void trance1() throws Exception{
        User user = new User();
        ShardingIDConfig shardingIDConfig = new ShardingIDConfig();
        user.setId(shardingIDConfig.generateKey().longValue());
        user.setName("trance1");
        user.setSex(1);
        userMapper.create(user);
        System.out.println(user.getId());
        throw new RuntimeException();
    }
}
