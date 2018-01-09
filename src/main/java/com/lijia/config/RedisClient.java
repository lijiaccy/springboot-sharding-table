package com.lijia.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisClient {

    private RedisTemplate redisTemplate;

    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }

    //set值
    public void set(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key,value);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
      //get值
    public String get(String key) {

        try {
            return ""+redisTemplate.opsForValue().get(key);
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
   //自增1
    public void incr(String key) {
        int num = Integer.parseInt(""+redisTemplate.opsForValue().get(key));
        num=num+1;
        redisTemplate.opsForValue().set(key,num);
    }
   //到期时间
    public boolean expire(String key, int second) {
        boolean result = redisTemplate.expire(key,second, TimeUnit.SECONDS);
        return result;
    }

   //删除
    public void del(String key) {
        redisTemplate.delete(key);
    }
   //是否存在key
    public boolean hasKey(String s) {
        Boolean flag =  redisTemplate.hasKey(s);
        return flag;

    }
   //左push
    public boolean lpush(String key, String value){
        boolean flag =false;
        try{
            redisTemplate.boundListOps(key).leftPush(value);
            flag =true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;

    }
    //右push
    public boolean rpush(String key, String value){
        boolean flag =false;
        try{
            redisTemplate.boundListOps(key).rightPush(value);
            flag =true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;

    }

    public void watch(String key){
        try{
            redisTemplate.watch(key);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
   //添加附近的人
    public boolean geoadd(String key,double longitude,double latitude){
        boolean flag = false;
        try{
            redisTemplate.boundGeoOps("nearPeople").geoAdd(new Point(longitude,latitude),key);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return flag;
    }
   //查询附近的人
    public List geoQuery(double longitude,double latitude) {
        List  list = null;
        try{
            list = redisTemplate.opsForGeo().geoRadius("nearPeople", new Circle(new Point(longitude, latitude),
                    new Distance(500,Metrics.KILOMETERS)),
                    RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().includeCoordinates()).getContent();

        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }
}