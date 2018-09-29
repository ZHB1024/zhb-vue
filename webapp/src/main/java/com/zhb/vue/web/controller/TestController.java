package com.zhb.vue.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhb.forever.redis.client.RedisClient;
import com.zhb.forever.redis.client.RedisClientFactory;

@Controller
@RequestMapping("/htgl/testcontroller")
public class TestController {
    
    private Logger logger = LoggerFactory.getLogger(TestController.class);
    
    
    private RedisClient redisClient = RedisClientFactory.getRedisClientBean();
    
    @RequestMapping(value = "/testredis")
    @Transactional
    public void testRedis(HttpServletRequest request,HttpServletResponse response) {
        /*List<String> countries = new ArrayList<String>();
        countries.add("China");
        countries.add("America");
        
        redisClient.set("hello", "hello world!123012");
        Object value =redisClient.get("hello");
        if (null != value) {
            logger.info(value.toString());
        }
        
        redisClient.set("hello", "hellosss");
        value =redisClient.get("hello");
        if (null != value) {
            logger.info(value.toString());
        }*/
        
        Set<?> setTemps2 = redisClient.getSet("number");
        if (null != setTemps2) {
            for (Object object : setTemps2) {
                logger.info(object.toString());
            }
        }
        
        
        /*redisClient.addList("zhb-vue-list", countries);
        List<?> result = redisClient.getList("zhb-vue-list");
        if (null != result) {
            for (Object object : result) {
                logger.info(object.toString());
            }
        }
        
        Set<String> sets = new TreeSet<String>();
        sets.add("11");
        sets.add("33");
        sets.add("22");
        sets.add("22");
        redisClient.addSet("number", sets);
        */
    }


}
