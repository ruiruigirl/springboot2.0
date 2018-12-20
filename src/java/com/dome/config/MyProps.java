package com.dome.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: dome2
 * @description: 读取yml配置信息类
 * @author: Mr.Zhou
 * @create: 2018-12-19 20:44
 **/

@Component
@ConfigurationProperties(prefix = "config") //接收application.yml中的config下面的属性
public class MyProps {
    int year;
    int month;
    int day;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}

