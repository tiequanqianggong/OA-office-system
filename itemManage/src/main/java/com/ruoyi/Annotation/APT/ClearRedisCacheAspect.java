package com.ruoyi.Annotation.APT;

import com.ruoyi.Annotation.ClearRedisCache;
import com.ruoyi.common.core.redis.RedisCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ClearRedisCacheAspect {

    /**
     * 注解实现缓存删除
     */
    @Autowired
    private RedisCache redisCache;

    @AfterReturning(value = "@annotation(clearRedisCache)", returning = "returnValue")
    public void clearRedisCache(JoinPoint joinPoint, ClearRedisCache clearRedisCache, Object returnValue) {
        String cacheKey = clearRedisCache.cacheKey();
        redisCache.deleteObject(cacheKey);
    }
}