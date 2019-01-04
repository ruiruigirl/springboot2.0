package com.dome.unti;

import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;

/**
 * @program: dome2
 * @description: jedis
 * @author: Mr.Zhou
 * @create: 2019-01-04 20:25
 **/

public class JedisAdapter {

    /**
     * 测试方法
     *
     * @param index
     * @param obj
     */
    public static void print(int index, Object obj) {
        System.out.println(String.format("%d , %s", index, obj.toString()));
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis("redis://192.168.30.164:6379/0");
        jedis.auth("foobared");
        jedis.flushDB();

        // key value
        jedis.set("hello", "world");
        print(1, jedis.get("hello"));

        jedis.rename("hello", "newhello");
        print(1, jedis.get("newhello"));
//        jedis.setex("hello2" , 15 , "world");
        jedis.set("pv", "100");
        jedis.incr("pv");
        print(2, jedis.get("pv"));
        print(3, jedis.keys("*"));

        // list
        String listName = "list";
        jedis.del(listName);
        for (int i = 0; i < 10; i++) {
            jedis.lpush(listName, "a" + String.valueOf(i));
        }
        print(4, jedis.lrange(listName, 0, 3));
        print(5, jedis.lrange(listName, 0, 12));
        print(6, jedis.llen(listName));
        print(7, jedis.lpop(listName));
        print(8, jedis.llen(listName));
        // 先进后出
        print(9, jedis.linsert(listName, BinaryClient.LIST_POSITION.BEFORE, "a4", "xx"));
        print(10, jedis.linsert(listName, BinaryClient.LIST_POSITION.AFTER, "a4", "xx"));
        print(11, jedis.lrange(listName, 0, 12));

        // hash  对象属性不定
        String userKey = "userXXX";

        jedis.hset(userKey, "name", "jim");
        jedis.hset(userKey, "age", "16");
        jedis.hset(userKey, "phone", "151898636563");

        print(12, jedis.hget(userKey, "name"));
        print(13, jedis.hgetAll(userKey));

        jedis.hdel(userKey, "phone");
        print(14, jedis.hgetAll(userKey));
        print(15, jedis.hexists(userKey, "age"));
        print(16, jedis.hkeys(userKey));
        print(17, jedis.hvals(userKey));
        jedis.hsetnx(userKey, "age", "17");
        jedis.hsetnx(userKey, "sex", "nan");
        print(18, jedis.hgetAll(userKey));

        // set 点赞 去重
        String likeKey1 = "commentLike1";
        String likeKey2 = "commentLike2";
        for (int i = 0; i < 10; i++) {
            jedis.sadd(likeKey1, String.valueOf(i));
            jedis.sadd(likeKey1, String.valueOf(i * i));
        }
        print(19, jedis.smembers(likeKey1));
        print(20, jedis.smembers(likeKey2));
        print(21, jedis.sunion(likeKey1, likeKey2));
        print(21, jedis.sdiff(likeKey1, likeKey2));
        print(22, jedis.sinter(likeKey1, likeKey2));
        print(23, jedis.sismember(likeKey1, "12"));
        print(24, jedis.sismember(likeKey2, "16"));
    }
}
