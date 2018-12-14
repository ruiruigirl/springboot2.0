package com.dome;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @program: dome2
 * @description: main
 * @author: Mr.Zhou
 * @create: 2018-12-13 20:38
 **/

@SpringBootApplication
@EnableScheduling // 允许定时任务
@MapperScan("com.dome.mapper")
@EnableAspectJAutoProxy
public class Application {
    public static void main(String[] args)throws Exception {
        SpringApplication.run(Application.class);
    }
}
