package com.dome.async;

import com.dome.unti.JedisAdapter;
import com.dome.unti.RedisKeyUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: dome2
 * @description: （队列）事件插入者 发送headler
 * @author: Mr.Zhou
 * @create: 2019-01-05 14:31
 **/
@Service
public class EventProducer {
    @Autowired
    private JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel eventModel){
        try {
            /**
             *  两种方法实现 一 java 自带的 BlockingDeque
             *              二 redis
             */
//            BlockingDeque<EventProducer> q = new LinkedBlockingDeque<>();
            // redis 实现
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(eventModel);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key ,json);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
