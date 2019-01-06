package com.dome.unti;

import ch.qos.logback.classic.Logger;
import com.dome.pojo.User;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @program: dome2
 * @description: jedis
 * @author: Mr.Zhou
 * @create: 2019-01-04 20:25
 **/

@Component
public class JedisAdapter implements InitializingBean {
    public static Logger logger = (Logger) LoggerFactory.getLogger(JedisAdapter.class);

    private JedisPool pool;

    /**
     * 测试方法
     *
     * @param index
     * @param obj
     */
    public static void print(int index, Object obj) {
        System.out.println(String.format("%d , %s", index, obj.toString()));
    }

    /**
     * jredis 测试
     * @param args
     */
    public static void main(String[] args) {
        Jedis jedis = new Jedis("redis://192.168.30.164:6379/0");
        jedis.auth("foobared");
        jedis.flushDB();

        // key value 单一数值， 验证码 ， pv ， 缓存 , 浏览数
        jedis.set("hello", "world");
        print(1, jedis.get("hello"));

        jedis.rename("hello", "newhello");
        print(1, jedis.get("newhello"));
//        jedis.setex("hello2" , 15 , "world");
        jedis.set("pv", "100");
        jedis.incr("pv");
        print(2, jedis.get("pv"));
        print(3, jedis.keys("*"));

        // list 双向列表， 适用于最新列表， 关注列表
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

        // hash  对象属性不定 对象属性，不定长属性数
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

        // set 点赞 去重 适合于无顺序的集合，点赞点踩 ， 抽奖 ， 已读 ， 共同好友
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

        // 堆 sortedset 排行榜 ，优先队列

        String rankKey  = "rankKey";
        jedis.zadd(rankKey , 15 , "jim");
        jedis.zadd(rankKey , 60 , "ben");
        jedis.zadd(rankKey , 90 , "lee");
        jedis.zadd(rankKey , 75 , "lucy");
        jedis.zadd(rankKey , 85 , "mie");
        print(25 , jedis.zcard(rankKey));
        print(25 , jedis.zcount(rankKey , 60 , 100));
        print(25 , jedis.zscore(rankKey , "jim"));
        jedis.zincrby(rankKey , 2 ,"jim");
        print(25 , jedis.zscore(rankKey , "jim"));
        print(25 , jedis.zrange(rankKey , 0 , 5)); // 默认从小到大排序

        // 连接池
        JedisPool pool = new JedisPool(); // 默认8个线程
        for (int i = 0; i < 100; i++) {
            Jedis j = pool.getResource();
            print(43,j.get("pv"));
            j.close();
        }

        // redis缓存

        User user = new User();
        user.setAge(16);
        user.setName("jim");
        user.setId(1l);
        // 将对象转为json序列化 存入redis缓存
//        jedis.set("user1" , );
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("redis://192.168.30.164:6379/0" );
    }

    public long sadd(String key , String  value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.sadd(key , value);
        } catch (Exception e){
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
        return 0;
    }

    public long scard(String key){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        }catch (Exception e){
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
        return 0;
    }
    public long lpush(String key, String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lpush(key , value);
        }catch (Exception e){
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
        return 0;
    }
    public List<String> brpop(int key, String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.brpop(key , value);
        }catch (Exception e){
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
        return null;
    }
}
