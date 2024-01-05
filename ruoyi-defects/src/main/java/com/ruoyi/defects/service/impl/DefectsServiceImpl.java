package com.ruoyi.defects.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.defects.aspectj.CachingAspect;
import com.ruoyi.defects.domain.Export;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.defects.mapper.DefectsMapper;
import com.ruoyi.defects.domain.Defects;
import com.ruoyi.defects.service.IDefectsService;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;

import static com.ruoyi.defects.constants.CacheConstants.*;


/**
 * 缺陷管理Service业务层处理
 * 
 * @author hh
 * @date 2023-09-11
 */
@Service
public class DefectsServiceImpl implements IDefectsService 
{
    @Autowired
    private DefectsMapper defectsMapper;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private CachingAspect cachingAspect;



    /**
     * 根据id查询
     * 
     * @param defectId 缺陷管理主键
     * @return 缺陷管理
     */

    @Override
    public Defects selectDefectsByDefectId(Long defectId)
    {
        //使用AOP切面根据缺陷id获取缓存键,调用getDefects方法查询数据库（使用AOP做缓存）
        return getDefects(cachingAspect.buildCacheKey(defectId));
    }

    /**
     * 动态查询
     * 
     * @param defects 缺陷管理
     * @return 缺陷管理
     */

    @Override
    public List<Defects> selectDefectsList(Defects defects)
    {
        return getDefects(defects);
    }

    /**
     * 添加缺陷管理
     * 
     * @param defects 缺陷管理
     * @return 结果
     */
    @Override
    public int insertDefects(Defects defects)
    {
        List<Object> cacheList = redisCache.getCacheList(REDIS_KEY_LIST_BEFORE);
        // 遍历缓存列表，检查是否存在相同的 defects_id
        if (cacheList != null) {
            for (int i = 0; i < cacheList.size(); i++) {
                Defects cachedDefect = (Defects) cacheList.get(i);
                // 这里假设 defects_id 是 Long 类型，根据实际情况修改条件
                if (Objects.equals(cachedDefect.getCaseId(), defects.getCaseId())) {
                    throw new RuntimeException("缺陷信息已存在");
                }
            }
        }
        int i = defectsMapper.insertDefects(defects);
        redisCache.deleteObject(REDIS_KEY_LIST_BEFORE);
        return i;
    }

    /**
     * 修改缺陷管理
     * 
     * @param defects 缺陷管理
     * @return 结果
     */
    @Transactional
    @Override
    public int updateDefects(Defects defects)
    {
        String key = REDIS_KEY_BEFORE + defects.getDefectId();
//        修改数据库中的缺陷信息
        int i = defectsMapper.updateDefects(defects);
//        删除缓存
        redisCache.deleteObject(REDIS_KEY_BEFORE+REDIS_KEY_BEFORE_BY_ID+key);
        redisCache.deleteObject(REDIS_KEY_LIST_BEFORE);
        return i;
    }

    /**
     * 批量删除缺陷管理
     * 
     * @param defectIds 需要删除的缺陷管理主键
     * @return 结果
     */
    @Override
    public int deleteDefectsByDefectIds(Long[] defectIds)
    {
        int i = defectsMapper.deleteDefectsByDefectIds(defectIds);
        redisCache.deleteObject(REDIS_KEY_LIST_BEFORE);
        return i;
    }

    /**
     * 删除缺陷管理信息
     * 
     * @param defectId 缺陷管理主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteDefectsByDefectId(Long defectId)
    {
        String key = REDIS_KEY_BEFORE + defectId;
//        删除数据库中的缺陷信息
        int i = defectsMapper.deleteDefectsByDefectId(defectId);
//        删除缓存信息
        boolean b = redisCache.deleteObject(REDIS_KEY_BEFORE+REDIS_KEY_BEFORE_BY_ID+key);
        boolean b1 = redisCache.deleteObject(REDIS_KEY_LIST_BEFORE);
        return i;
    }
    //查询最大id
    @Override
    public long selectMaxId() {
        return defectsMapper.selectMaxId();
    }

    @Override
    public List<Export> exportDefectsList() {
        List<Export> exportList = defectsMapper.exportDefectsLis();
        return exportList;
    }


    /*/**
     * TODO     查询数据库并写入redis，最后返回
     * Created on 2023/9/15 9:10
     *@author:
     *@param:  * @param
     * @param key   根据缺陷id构建的缓存键key
     *@return: {@link com.ruoyi.defects.domain.Defects}
     *@throws:
     */
    Defects getDefects(String key) {
        return defectsMapper.selectDefectsByDefectId(extractDefectIdFromKey(key));
    }

    /*/**
     * TODO     查询数据库并写入redis，最后返回列表
     * Created on 2023/9/15 9:12
     *@author:
     *@param:  * @param defects     查询对象
     *@return: {@link java.util.List<com.ruoyi.defects.domain.Defects>}
     *@throws:
     */

    List<Defects> getDefects(Defects defects) {
        return defectsMapper.selectDefectsList(defects);
    }
    /*/**
     * TODO     从缓存键中提取缺陷id
     * Created on 2024/1/3 15:32
     *@author:
     *@param:  * @param key
     *@return: {@link java.lang.Long}
     *@throws:
     */
    public Long extractDefectIdFromKey(String key) {
        if (key != null && key.startsWith(REDIS_KEY_BEFORE)) {
            try {
                // 从缓存键中提取缺陷id部分
                String idString = key.substring(REDIS_KEY_BEFORE.length());
                idString = idString.replace(":", "");
                return Long.valueOf(idString);
            } catch (NumberFormatException e) {
                // 处理转换异常，如果转换失败则返回 null 或者抛出适当的异常
                // 这里简单返回 null
                return null;
            }
        }
        // 如果 key 不是以 REDIS_KEY_BEFORE 开头，则返回 null 或者抛出适当的异常
        // 这里简单返回 null
        return null;
    }

}
