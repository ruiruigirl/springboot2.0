package com.dome.async;

import com.dome.unti.JedisAdapter;
import com.dome.unti.RedisKeyUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: dome2
 * @description: 消费headler
 * @author: Mr.Zhou
 * @create: 2019-01-05 16:32
 **/
@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);
    private Map<EventType, List<EventHeadler>> config = new HashMap<>();
    private  ApplicationContext applicationContext;
    @Autowired
    private JedisAdapter jedisAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, EventHeadler> beans = applicationContext.getBeansOfType(EventHeadler.class); //通过spring 上下文 获取当前所有实现EventHeader接口的类
        if (beans != null){
            for (Map.Entry<String, EventHeadler> entry : beans.entrySet()) {
                List<EventType> eventTypes = entry.getValue().getSupportEventTypes();

                for (EventType type : eventTypes) {
                    if (!config.containsKey(type)) {
                        config.put(type, new ArrayList<EventHeadler>());
                    }
                    config.get(type).add(entry.getValue());
                }
            }
        }
        /**
         * 消费的异步线程
         */
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String key = RedisKeyUtil.getEventQueueKey();
                    List<String> events = jedisAdapter.brpop(0, key);
                    for (String message: events
                         ) {
                        if (message.equals(key)) {
                            continue;
                        }
                        ObjectMapper objectMapper = new ObjectMapper();
                        EventModel model = null;
                        try {
                            model = objectMapper.readValue(message, EventModel.class);
                        } catch (IOException e) {
                            logger.error("序列化错误：" + e.getMessage());
                            continue;
                        }
                        if (!config.containsKey(model.getType())){
                            logger.error("不能识别的事件类型");
                            continue;
                        }
                        for (EventHeadler eventHeadler : config.get(model.getType())){
                                 eventHeadler.doHandle(model);
                        }
                    }

                }
            }
        });
        thread.start();
     }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
