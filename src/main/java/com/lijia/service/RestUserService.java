package com.lijia.service;

import com.alibaba.fastjson.JSON;
import com.lijia.bean.User;
import com.lijia.config.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class RestUserService {
    @Autowired
    private RedisClient redisClient;

    public void findNearByPeople(String clientUserId,double longitude, double latitude) {



        //把位置坐标放入set
        boolean flag1 = redisClient.geoadd(clientUserId, longitude, latitude);

        if (!flag1){
            System.out.println("存入地址错误");
        }

        List<HashMap> list1 = new ArrayList<>();

        List list = redisClient.geoQuery(longitude, latitude);

        if (list.size()>0){
            for (int i = 0; i <list.size() ; i++) {
                HashMap map = new HashMap();
                String clientUserid1 = (String) ((RedisGeoCommands.GeoLocation)(((GeoResult)list.get(i)).getContent())).getName();
                if (clientUserId.equals(clientUserid1)){
                    continue;
                }
                Distance distance = ((GeoResult) list.get(i)).getDistance();
                double longitude1 = ((RedisGeoCommands.GeoLocation)(((GeoResult)list.get(i)).getContent())).getPoint().getX();
                double latitude1 = ((RedisGeoCommands.GeoLocation)(((GeoResult)list.get(i)).getContent())).getPoint().getY();
                map.put("distance",distance.getValue());
                map.put("clientId",clientUserid1);
                map.put("longitude",longitude1);
                map.put("latitude",latitude1);
                list1.add(map);

            }
        }

        System.out.println(list1.toArray());

    }
}
