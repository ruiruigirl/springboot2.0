package com.dome.scheduled;

import com.dome.dao.TestMapper;
import com.dome.pojo.Test;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @program: dome2
 * @description:
 * @author: Mr.Zhou
 * @create: 2018-12-14 09:33
 **/

@Component
public class ScheduledTasks {
    @Resource
    private TestMapper testMapper;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled( cron = "0 0 13 20 12 *") // 定时任务 当前系统扩展性稍微差了点 有点懒 如果需要提供系统灵活性可以cron时间配置在yml或者数据库中
    public void reportCurrentTime() {
            doAction();
    }

    /**
     * 得到数据库中所有数据并随机抽取一条数据
     */
    private void doAction() {
        List<Test> tests = testMapper.selectByRand(1);// 当数据量较小的时候容易发生搜索出的结果小于limits
        System.out.println(tests);
    }

}
