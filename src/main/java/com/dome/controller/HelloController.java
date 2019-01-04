package com.dome.controller;

import com.dome.common.ServerResponse;
import com.dome.config.MyProps;
import com.dome.pojo.Test;
import com.dome.service.TestService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Random;
import java.util.UUID;

/**
 * @program: dome2
 * @description: test for spring boot
 * @author: Mr.Zhou
 * @create: 2018-12-13 20:42
 **/

@RestController
public class HelloController {
    @Resource
    private TestService testService;
    @Resource
    private MyProps myProps;
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 新增一条抽奖记录
     * @param test
     * @return
     */
    @RequestMapping("/addTestDome")
    public ServerResponse<String> addTestDome(@Valid Test test) {
        Random random = new Random(47);
        // 加密 MD5 + salt
        Md5Hash salt = new Md5Hash(UUID.randomUUID().toString().substring(0, 5 ));
        SimpleHash result = new SimpleHash("MD5", test.gettTel(), salt, 1024);
        test.settSalt(salt.toString());
        test.settTel(result.toString());
        System.out.println(salt);
        System.out.println(test.gettTel());
        return testService.addTestDome(test);
    }
    @RequestMapping("/getActivityTime")
    public ServerResponse<MyProps> getActivityTime () {
        return ServerResponse.createBySuccess(myProps);
    }

    @RequestMapping("/addTestDomeRedis")
    public ServerResponse<String> addTestRedis(@Valid Test test) throws InterruptedException {
        ValueOperations<String , Test> operations = redisTemplate.opsForValue();
        String uuid = UUID.randomUUID().toString();
        operations.set( "test", test);
        Thread.sleep(1000);
        //redisTemplate.delete("com.neo.f");
        boolean exists=redisTemplate.hasKey("test");
        if(exists){
            System.out.println("exists is true");
            Test test1 = (Test) redisTemplate.getKeySerializer();
            System.out.println(test1.gettName());
        }else{
            System.out.println("exists is false");
        }
        return ServerResponse.createBySuccessMessage("成功!");
    }
}