package com.ruoyi.plan.utils;

import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.Serializable;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author liupian
 * @description redisTemplate工具类
 * 注： 同一个键存再次进行存储就是修改操作
 * @date 2023/9/13 15:19:13
 */
public class RedisUtil implements Serializable {
    @Resource
    private RedisTemplate redisTemplate;

    private static RedisTemplate redisTem;

    @PostConstruct
    public void initRedisTem(){
        redisTem = redisTemplate;
    }

    /**
     * 判断redis中是否含有该键
     * @param key 键值
     * @return Boolean值 false 没有此键， true 含有此键
     */
    public static boolean hasKey(String key){
        //返回boolean值
        return redisTem.hasKey(key);
    }

    /**
     * 获取键的过期时间
     * @param key 键
     * @return 返回long类型的时间数值
     */
    public static long getExpire(String key){
        return redisTem.getExpire(key);
    }

    /**
     * 过期时间设置
     * @param key 键
     * @param expireMinutes 过期时间
     * @return 返回设置成功
     */
    public static boolean setExpire(String key, long expireMinutes){
        Boolean expire = redisTem.expire(key, Duration.ofMinutes(expireMinutes));
        return expire;
    }
    public static boolean setExpireByMillis(String key, long expireMillis){
        Boolean expire = redisTem.expire(key, Duration.ofMillis(expireMillis));
        return expire;
    }
    public static boolean setExpireBySecond(String key, long expireSeconds){
        Boolean expire = redisTem.expire(key, Duration.ofSeconds(expireSeconds));
        return expire;
    }
    public static boolean setExpireByHour(String key, long expireHours){
        Boolean expire = redisTem.expire(key, Duration.ofHours(expireHours));
        return expire;
    }
    public static boolean setExpireByDay(String key, long expireDays){
        Boolean expire = redisTem.expire(key, Duration.ofMinutes(expireDays));
        return expire;
    }



    /**
     * 删除键值
     * @param key 键
     * @return 返回删除结果
     */
    public static boolean delete(String key){
        Boolean delete = redisTem.delete(key);
        return delete;
    }

    /**
     * 通过集合中的所有key删除对应的所有值
     * @param keys 集合keys
     * @return 返回boolean值
     */
    public static boolean delete(Collections keys){
        Boolean delete = redisTem.delete(keys);
        return delete;
    }

    //-----------------------------对象键值存取---------------------------------------------------------------
    /**
     * 存值
     * @param key 键
     * @param value 值
     */
    public static void set(Object key, Object value){
        redisTem.opsForValue().set(key, value);
    }

    /**
     * 存值
     * @param key 键
     * @param value 值
     * @param offset 位置
     */
    public static void set(Object key, Object value, long offset){
        redisTem.opsForValue().set(key, value, offset);
    }

    /**
     * 存值
     * @param key 键
     * @param value 值
     * @param timeout 过期时间
     */
    public static void set(Object key, Object value, Duration timeout){
        redisTem.opsForValue().set(key, value, timeout);
    }

    /**
     * 存值
     * @param key 键
     * @param value 值
     * @param timeout 过期时间
     * @param timeUnit 时间单位
     */
    public static void set(Object key, Object value, long timeout, TimeUnit timeUnit){
        redisTem.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 获取键对应的值
     * @param key 键
     * @return 返回键对应的值
     */
    public static Object get(Object key){
        Object value = redisTem.opsForValue().get(key);
        return value;
    }

    /**
     * 获取键对应的值
     * @param key 键
     * @param start 开始位置
     * @param end 结束位置
     * @return 返回范围内的对应键的值
     */
    public static Object get(Object key, long start, long end){
        Object value = redisTem.opsForValue().get(key, start, end);
        return value;
    }

    /**
     * 获取键对应的值的大小
     * @param key 键
     * @return 大小
     */
    public static long getSize(Object key){
        Long size = redisTem.opsForValue().size(key);
        return size;
    }

    //-----------------------------String键值存取---------------------------------------------------------------
    /**
     * 存值
     * @param key 键
     * @param value 值
     */
    public static void set(String key, String value){
        redisTem.opsForValue().set(key, value);
    }

    /**
     * 存值
     * @param key 键
     * @param value 值
     * @param offset 位置
     */
    public static void setByOffset(String key, String value, long offset){
        redisTem.opsForValue().set(key, value, offset);
    }

    /**
     * 存值
     * @param key 键
     * @param value 值
     * @param timeout 过期时间 可以使用Duration来调用相关时间参数
     */
    public static void set(String key, String value, Duration timeout){
        redisTem.opsForValue().set(key, value, timeout);
    }

    /**
     * 存值（时间封装）
     * @param key 键
     * @param value 值
     * @param minutes 过期时间 分钟
     */
    public static void set(String key, String value, long minutes){
        redisTem.opsForValue().set(key, value, Duration.ofMinutes(minutes));
    }
    public static void setBySeconds(String key, String value, long seconds){
        redisTem.opsForValue().set(key, value, Duration.ofSeconds(seconds));
    }

    public static void setByHour(String key, String value, long hours){
        redisTem.opsForValue().set(key, value, Duration.ofHours(hours));
    }
    public static void setByDay(String key, String value, long days){
        redisTem.opsForValue().set(key, value, Duration.ofDays(days));
    }

    /**
     * 存值
     * @param key 键
     * @param value 值
     * @param timeout 过期时间
     * @param timeUnit 时间单位
     */
    public static void set(String key, String value, long timeout, TimeUnit timeUnit){
        redisTem.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 获取键对应的值
     * @param key 键
     * @return 返回键对应的值
     */
    public static Object get(String key){
        Object value = redisTem.opsForValue().get(key);
        return value;
    }

    /**
     * 获取键对应的值
     * @param key 键
     * @param start 开始位置
     * @param end 结束位置
     * @return 返回范围内的对应键的值
     */
    public static Object get(String key, long start, long end){
        Object value = redisTem.opsForValue().get(key, start, end);
        return value;
    }

    //-----------------------------List键值存取---------------------------------------------------------------

    /**
     * 根据key存储到list的指定位置
     * @param key 键
     * @param index list中指定索引
     * @param value 值
     */
    public static void lSet(Object key, long index, Object value){
        redisTem.opsForList().set(key, index, value);
    }

    /**
     * 存储到列表最左侧
     * @param key 键
     * @param value 值
     */
    public static void lSet(Object key, Object value){
        redisTem.opsForList().leftPush(key, value);
    }

    /**
     * 存储到列表最左
     * @param key 键
     * @param pivot
     * @param value 值
     */
    public static void lSet(Object key, Object pivot, Object value){
        redisTem.opsForList().leftPush(key, pivot, value);
    }

    /**
     * 存储到列表最右
     * @param key 键
     * @param value 值
     */
    public static void lSetR(Object key, Object value){
        redisTem.opsForList().rightPush(key, value);
    }

    /**
     * 存储到列表最右
     * @param key 键
     * @param pivot
     * @param value 值
     */
    public static void lSetR(Object key, Object pivot, Object value){
        redisTem.opsForList().rightPush(key, pivot, value);
    }

    /**
     * 获取对应key的list列表大小
     * @param key 键
     * @return size
     */
    public static long lGetSize(Object key){
        Long size = redisTem.opsForList().size(key);
        return size;
    }

    /**
     * 获取键对应的列表数据
     * @param key 键
     * @return key的值（列表）
     */
    public static List lGet(Object key){
        List list = redisTem.opsForList().range(key, 0, -1);
        return list;
    }

    /**
     * 获取键对应的列表数据
     * @param key 键
     * @param start 开始位置
     * @param end 结束位置
     * @return 返回key对应范围内的列表数据
     */
    public static List lGet(Object key, long start, long end){
        List list = redisTem.opsForList().range(key, start, end);
        return list;
    }

    //-----------------------------Set(无序)键值存取---------------------------------------------------------------

    /**
     * 存储set类型的数据
     * @param key 键
     * @param values 值，可以是多个
     */
    public static void sSet(Object key, Object... values){
        redisTem.opsForSet().add(key, values);
    }

    /**
     * 获取key对应set类型数据的大小
     * @param key 键
     */
    public static long sGetSize(Object key){
        Long size = redisTem.opsForSet().size(key);
        return size;
    }

    /**
     * 获取set类型的数据
     * @param key 键
     * @return 返回一个set集合
     */
    public static Set sGet(Object key){
        Set members = redisTem.opsForSet().members(key);
        return members;
    }

    //-----------------------------ZSet(有序)键值存取---------------------------------------------------------------

    /**
     * 存储有序集合
     * @param key 键
     * @param value 值
     * @param score 排序
     */
    public static void zSet(Object key, Object value, double score){
        redisTem.opsForZSet().add(key, value, score);
    }

    /**
     * 存储值
     * @param key 键
     * @param set 集合
     */
    public static void zSet(Object key, Set set){
        redisTem.opsForZSet().add(key, set);
    }

    /**
     * 获取key指定范围的值
     * @param key 键
     * @param start 开始位置
     * @param end 结束位置
     * @return 返回set
     */
    public static Set zGet(Object key, long start, long end){
        Set set = redisTem.opsForZSet().range(key, start, end);
        return set;
    }

    /**
     * 获取key对应的所有值
     * @param key 键
     * @return 返回set
     */
    public static Set zGet(Object key){
        Set set = redisTem.opsForZSet().range(key, 0, -1);
        return set;
    }

    /**
     * 获取对用数据的大小
     * @param key 键
     * @return 键值大小
     */
    public static long zGetSize(Object key){
        Long size = redisTem.opsForZSet().size(key);
        return size;
    }

    //-----------------------------HashMap键值存取---------------------------------------------------------------


    /**
     * 存储hashMap数据
     * @param key 键
     * @param hashKey map的id
     * @param value 值
     */
    public static void hSet(Object key, Object hashKey, Object value){
        redisTem.opsForHash().put(key, hashKey, value);
    }

    /**
     * 获取大小
     * @param key 键
     */
    public static void hGetSize(Object key){
        redisTem.opsForHash().size(key);
    }

    /**
     * 获取hashMap数据
     * @param key 键
     * @param hashKey map的id
     * @return 返回值
     */
    public static Object hGet(Object key, Object hashKey){
        Object o = redisTem.opsForHash().get(key, hashKey);
        return o;
    }

    /**
     * 删除数据
     * @param key 键
     * @param hashKeys map的id
     * @return 返回Boolean
     */
    public static Object hDel(Object key, Object... hashKeys){
        Long delete = redisTem.opsForHash().delete(key, hashKeys);
        return delete;
    }

}
