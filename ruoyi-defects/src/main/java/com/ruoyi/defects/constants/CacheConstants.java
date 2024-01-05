package com.ruoyi.defects.constants;

/**
 * TODO
 * Created on $ $
 *
 * @author $
 * @version V1.0
 */
public class CacheConstants {
//    Redis key 过期时间 TTL
    public static final int REDIS_KEY_TTL = 10;
//    Redis key 分层前缀
    public static final String REDIS_KEY_BEFORE = "defect:";
//    Redis key 分层前缀(根据id查询)
    public static final String REDIS_KEY_BEFORE_BY_ID = "id:";
//    Redis key 分层前缀 list
    public static final String REDIS_KEY_LIST_BEFORE = "defect:all:";

//    Redis解决缓存雪崩 随机TTL
    public static final int REDIS_RANDOM_KEY_TTL = 100;
}
