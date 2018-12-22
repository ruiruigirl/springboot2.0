package com.dome.controller;

import com.dome.common.ServerResponse;
import com.dome.config.MyProps;
import com.dome.pojo.Test;
import com.dome.service.TestService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
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

}