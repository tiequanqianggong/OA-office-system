package com.ruoyi.defects.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
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





    /**
     * 根据id查询
     * 
     * @param defectId 缺陷管理主键
     * @return 缺陷管理
     */

    @Override
    public Defects selectDefectsByDefectId(Long defectId)
    {
        String key = REDIS_KEY_BEFORE + defectId;
//        根据id从redis查询缺陷管理
        Defects cacheObject = redisCache.getCacheObject(key);
//        判断是否存在
        if(StringUtils.isNotNull(cacheObject)){
//        存在直接返回
//            cacheObject.setStatus("0");
            return cacheObject;
        }
//        缓存不存在 查询数据库并将数据存入缓存
        return getDefects(defectId, key);
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
//        从redis查询缺陷列表
        List<Defects> cacheList = redisCache.getCacheList(REDIS_KEY_LIST_BEFORE);
//        判断是否存在
        if(StringUtils.isNotEmpty(cacheList)){
            List<Defects> defects1 = getDefects(defects);
//        存在直接返回
            return defects1;
        }
//        不存在 查询数据库并将数据存入缓存
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
        redisCache.deleteObject(key);
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
        boolean b = redisCache.deleteObject(key);
        boolean b1 = redisCache.deleteObject(REDIS_KEY_LIST_BEFORE);
        return i;
    }
    //查询最大id
    @Override
    public long selectMaxId() {
        return defectsMapper.selectMaxId();
    }


    /*/**
     * TODO     查询数据库并写入redis，最后返回
     * Created on 2023/9/15 9:10
     *@author:
     *@param:  * @param defectId    查询对象ID
     * @param key   存入redis的key
     *@return: {@link com.ruoyi.defects.domain.Defects}
     *@throws:
     */
    @Transactional
    Defects getDefects(Long defectId, String key) {
        Defects defects = defectsMapper.selectDefectsByDefectId(defectId);
        if(StringUtils.isNotNull(defects)){
//        数据库存在 写入redis 然后返回
            redisCache.setCacheObject(key,defects,REDIS_KEY_TTL + new Random().nextInt(REDIS_RANDOM_KEY_TTL), TimeUnit.MINUTES);
            return defects;
        }
//        不存在则向redis缓存空值，防止缓存穿透
        redisCache.setCacheObject(key,new Defects(),REDIS_KEY_TTL + new Random().nextInt(REDIS_RANDOM_KEY_TTL),TimeUnit.MINUTES);
        return null;
    }

    /*/**
     * TODO     查询数据库并写入redis，最后返回列表
     * Created on 2023/9/15 9:12
     *@author:
     *@param:  * @param defects     查询对象
     *@return: {@link java.util.List<com.ruoyi.defects.domain.Defects>}
     *@throws:
     */
    @Transactional
    List<Defects> getDefects(Defects defects) {
        //        不存在 查询数据库
        List<Defects> defects1 = defectsMapper.selectDefectsList(defects);
        if(StringUtils.isNotEmpty(defects1)){
//        数据库存在 写入redis 然后返回
            redisCache.setCacheList(REDIS_KEY_LIST_BEFORE,defects1);
            redisCache.expire(REDIS_KEY_LIST_BEFORE,REDIS_KEY_TTL + new Random().nextInt(REDIS_RANDOM_KEY_TTL),TimeUnit.MINUTES);
            return defects1;
        }
//        不存在则报错
        return new ArrayList<Defects>();
    }
}
