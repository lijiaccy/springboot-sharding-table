package com.lijia.service;

import com.lijia.bean.User;
import com.lijia.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
