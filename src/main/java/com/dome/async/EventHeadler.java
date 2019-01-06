package com.dome.async;

import java.util.List;

public interface EventHeadler {

    /**
     * 消费 Handle
     * @param model
     */
    void doHandle(EventModel model);

    /**
     * 获取事件类型
     * @return
     */
    List<EventType> getSupportEventTypes();
}
