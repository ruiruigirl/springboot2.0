package com.dome.async;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: dome2
 * @description: 事件模型
 * @author: Mr.Zhou
 * @create: 2019-01-05 14:02
 **/

public class EventModel {
    private EventType type;  // 事件类型
    private int actorId;      // 事件产生人
    private int entityId;     // 事件影响实体
    private int entityType;   // 事件影响类型
    private int entityOwnerId;// 影响事件的id

    private Map<String , String> exts = new HashMap<>();

    public EventType getType() {
        return type;
    }

    /**
     * 所有set retrun this； 采用链式调用模型 当一个对象需要多个set是 可以一条语句搞定
     * @param type
     * @return
     */
    public EventModel setType(EventType type) {
        this.type = type;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }

    /**
     * 方便起见 设置一个单一参数的set 和get
     * @return
     */
    public EventModel setExts(String key , String value) {
        exts.put(key , value);
        return this;
    }

}
