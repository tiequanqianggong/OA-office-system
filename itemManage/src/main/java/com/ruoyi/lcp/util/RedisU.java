package com.ruoyi.lcp.util;


import com.ruoyi.common.core.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.*;



@Component
public class RedisU {
    @Autowired
    private RedisCache redisCache;

    /**
     * 从Redis中取出分页数据
     * @param pageNum
     * @param pageSize
     * @param key
     * @param valueType
     * @return
     */
    public <T> List<T> getDataFromRedis(Integer pageNum, Integer pageSize, String key,Map<String, String> hashData, Class<T> valueType) {
        // 计算起始索引和结束索引
        Integer start = (pageNum - 1) * pageSize;
        Integer end = pageNum * pageSize - 1;



        // 获取指定分页范围内的数据
        List<T> data = new ArrayList<>();
        List<T>sortedList =new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, String> entry : hashData.entrySet()) {
            if (index >= start && index <= end) {

                T value = (T) entry.getValue();
                data.add(value);
            }


            index++;
        }

        return data;
    }

    /**
     * 获取Redis中Hash类型的所有数据
     * @param key
     * @param valueType
     * @return
     * @param <T>
     */
    public <T> List<T> getData(String key, Class<T> valueType) {
        // 获取符合模式为 "project_manage:CACHE_Project*" 的哈希表
        Map<String, String> hashData = redisCache.getCacheMap(key);

        List<T> data = new ArrayList<>();

        for ( Map.Entry<String, String> entry : hashData.entrySet()){
            T value = (T) entry.getValue();
            if (value != null) {
                data.add(value);
            }
        }

        return data;
    }

}