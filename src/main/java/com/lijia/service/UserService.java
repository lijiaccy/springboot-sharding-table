package com.lijia.service;

import com.lijia.bean.User;
import com.lijia.config.RedisClient;
import com.lijia.config.ShardingIDConfig;
import com.lijia.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisClient redisClient;

    /**这里的单引号不能少，否则会报错，被识别是一个对象;
     *
     */
    public static final String CACHE_KEY = "'user'";

    public static final String USER_CACHE_NAME = "usercache";

    @Autowired
    private UserMapper userMapper;


    @Cacheable(value=USER_CACHE_NAME,key="'user_'+#id")
    public User getUser(long id) {
        logger.info("没有走缓存");
        return  userMapper.getUser(id);

    }

    public void create(User user) {
        userMapper.create(user);
    }

    @CacheEvict(value = USER_CACHE_NAME,key="'user_'+#id")
    public void deleteUser(long id){

        logger.error("error");
        logger.info("info");
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


    @Transactional(propagation = Propagation.REQUIRED,timeout = 1,rollbackFor = Exception.class)
    public void trance1() throws Exception{
        Thread.sleep(1200);
        User user = new User();
        ShardingIDConfig shardingIDConfig = new ShardingIDConfig();
        user.setId(shardingIDConfig.generateKey().longValue());
        user.setName("trance1");
        user.setSex(1);
        userMapper.create(user);
        System.out.println(user.getId());
    }


    public void testRedis() {
        redisClient.set("test","test");
        System.out.println(redisClient.get("test"));
    }
}
