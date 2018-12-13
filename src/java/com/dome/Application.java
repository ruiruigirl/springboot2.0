package com.dome;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: dome2
 * @description: main
 * @author: Mr.Zhou
 * @create: 2018-12-13 20:38
 **/

@SpringBootApplication
@MapperScan("com.dome.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
