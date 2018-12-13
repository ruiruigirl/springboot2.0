package com.dome.pojo;

import org.apache.ibatis.annotations.Mapper;

/**
 * @program: dome2
 * @description: user
 * @author: Mr.Zhou
 * @create: 2018-12-13 21:31
 **/

@Mapper
public class User {

    private Long id;
    private String name;
    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    // 省略setter和getter

}