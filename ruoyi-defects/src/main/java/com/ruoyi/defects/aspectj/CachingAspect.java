package com.ruoyi.defects.aspectj;

import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.defects.domain.Defects;
import com.ruoyi.defects.mapper.DefectsMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.ruoyi.defects.constants.CacheConstants.*;

/**
 * TODO     缓存切面
 * Created on $ $
 *
 * @author $
 * @version V1.0
 */
@Aspect
@Transactional
@Slf4j
@Component
public class CachingAspect {

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private DefectsMapper defectsMapper;

    /*/**
     * TODO     反射判断是否是空对象的辅助方法
     * Created on 2024/1/4 9:50
     *@author:
     *@param:  * @param obj
     *@return: {@link boolean}
     *@throws:
     */

    // 判断对象的所有属性是否都为 null
    private boolean areAllFieldsNull(Object obj) {
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.get(obj) != null) {
                    return false; // 只要有一个属性不为 null，就返回 false
                }
            } catch (IllegalAccessException e) {
                // 处理异常，例如记录日志或抛出运行时异常
                e.printStackTrace();
            }
        }
        return true; // 所有属性都为 null，返回 true
    }

    /*/**
     * TODO     根据传入的参数动态构建拼接缓存键
     * Created on 2024/1/3 15:03
     *@author:
     *@param:  * @param defectId
     *@return: {@link java.lang.String}
     *@throws:
     */
    public String buildCacheKey(Object... args) {
        // 构建缓存键，通过拼接参数值
        StringBuilder cacheKeyBuilder = new StringBuilder(REDIS_KEY_BEFORE);
        for (Object arg : args) {
            if (arg != null && !areAllFieldsNull(arg)){
                cacheKeyBuilder.append(arg);
            }
        }

        return cacheKeyBuilder + ":";
    }

    /*/**
     * TODO     不传参数返回默认缓存键         defect:all:
     * Created on 2024/1/4 9:57
     *@author:
     *@param:  * @param
     *@return: {@link java.lang.String}
     *@throws:
     */
    public String buildDefaultCacheKey(){
        return REDIS_KEY_BEFORE + "all:";
    }

    /*/**
     * TODO     模糊查询缺陷信息缓存切面
     * Created on 2024/1/3 14:50
     *@author:
     *@param:  * @param joinPoint
     *@return: {@link java.util.List<com.ruoyi.defects.domain.Defects>}
     *@throws:
     */
    @Around("execution(* com.ruoyi.defects.service.impl.DefectsServiceImpl.selectDefectsList(com.ruoyi.defects.domain.Defects))")
    public List<Defects> cacheSelectDefectsList(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("CachingAspect: Executing cacheSelectDefectsList method...");
            // 提取参数
            Object[] args = joinPoint.getArgs();
            String cacheKey;
            if (args == null || args.length == 0 || (args.length == 1 && areAllFieldsNull(args))) {
                // 如果没有传递参数，使用模糊查询的缓存键
                cacheKey = buildDefaultCacheKey();
            } else {
                // 如果有传递参数，使用参数动态拼接的缓存键
                cacheKey = buildCacheKey(args);
            }

            // 从缓存中获取数据
            List<Defects> cacheList = redisCache.getCacheList(cacheKey);

            if (cacheList != null && !cacheList.isEmpty()) {
                // 如果缓存中有数据，直接返回缓存的数据
                return cacheList;
            } else {
                // 如果缓存中没有数据，执行实际的方法获取数据
                List<Defects> result = (List<Defects>) joinPoint.proceed();

                // 如果数据库中有数据，则写入缓存并返回
                if (StringUtils.isNotEmpty(result)) {
                    redisCache.setCacheList(cacheKey, result);
                    redisCache.expire(cacheKey, REDIS_KEY_TTL + new Random().nextInt(REDIS_RANDOM_KEY_TTL), TimeUnit.MINUTES);
                    return result;
                } else {
                    // 如果数据库中没有数据，则报错或返回一个默认值，这里返回一个空列表
                    return new ArrayList<>();
                }

            }
        } catch (Exception ex) {
            log.error("Exception in cacheSelectDefectsList method: {}", ex.getMessage(), ex);
            throw ex;
        }
    }
    /*/**
     * TODO     根据id查询缺陷信息缓存切面
     * Created on 2024/1/3 14:50
     *@author:
     *@param:  * @param joinPoint
     *@return: {@link java.lang.Object}
     *@throws:
     */
    @Around("execution(* com.ruoyi.defects.service.impl.DefectsServiceImpl.selectDefectsByDefectId(Long))")
    public Object cacheGetDefects(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("CachingAspect: Executing cacheGetDefects method...");
            // 提取参数
            Object[] args = joinPoint.getArgs();
            Long defectId = (Long) args[0];
            String key = REDIS_KEY_BEFORE + defectId;

            // 检查缓存
            Defects cacheObject = redisCache.getCacheObject(key);

            if (StringUtils.isNotNull(cacheObject)) {
                // 如果缓存中有数据，直接返回
                return cacheObject;
            } else {
                // 如果缓存中没有数据，执行实际的方法获取数据
                Object result = joinPoint.proceed();

                // 缓存结果，处理事务
                if (result != null && result instanceof Defects) {
                    // 将数据写入缓存
                    redisCache.setCacheObject(key, (Defects) result, REDIS_KEY_TTL + new Random().nextInt(REDIS_RANDOM_KEY_TTL), TimeUnit.MINUTES);
                } else {
                    // 如果数据库中不存在数据，缓存空值，防止缓存穿透
                    redisCache.setCacheObject(key, new Defects(), REDIS_KEY_TTL + new Random().nextInt(REDIS_RANDOM_KEY_TTL), TimeUnit.MINUTES);
                }

                return result;
            }
        } catch (Throwable ex) {
            log.error("Exception in cacheSelectDefectsList method: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

}