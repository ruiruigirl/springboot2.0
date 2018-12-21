package com.dome.scheduled;

import ch.qos.logback.classic.Logger;
import com.dome.aspect.LogAspect;
import com.dome.dao.TestMapper;
import com.dome.pojo.Test;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @program: dome2
 * @description:
 * @author: Mr.Zhou
 * @create: 2018-12-14 09:33
 **/

@Component
public class ScheduledTasks {
    public static Logger logger = (Logger) LoggerFactory.getLogger(LogAspect.class);

    @Resource
    private TestMapper testMapper;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled( cron = "0 32 11 21 12 *") // 定时任务 当前系统扩展性稍微差了点 有点懒 如果需要提供系统灵活性可以cron时间配置在yml或者数据库中
    public void reportCurrentTime() {
            doAction();
    }

    /**
     * 得到数据库中所有数据并随机抽取一条数据
     * mysql 版本
     */
    private void doAction() {
        StringBuilder sb = new StringBuilder();
        Date date = new Date();
        Format format = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        sb.append("Start time:"+ format.format(date).toString());
        List<Test> tests = testMapper.selectByRand(1);// 当数据量较小的时候容易发生搜索出的结果小于limits
        sb.append("    LuckOne is :" + tests.get(0).toString());
        logger.info(sb.toString());
    }

}
