package com.dome.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: dome2
 * @description: test for spring boot
 * @author: Mr.Zhou
 * @create: 2018-12-13 20:42
 **/

@RestController

public class HelloController {

    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }
}
