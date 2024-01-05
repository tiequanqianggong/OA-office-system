package com.ruoyi.lcp.config;


import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@Slf4j
public class  RedissonConfig {

    private static final Logger logger = LoggerFactory.getLogger(RedissonConfig.class);

    public RedissonConfig() {
        // 在构造函数中添加日志输出
        logger.info("Initializing RedissonConfig...");
        // 其他初始化逻辑...
    }


    @Bean
    public static RedissonClient redissonClient() throws IOException {
        try {
            // 配置
            Config config = new Config();
            config.useSingleServer().setAddress("redis://10.25.18.103:6379").setPassword("lxrzh535179");
            logger.info("Creating RedissonClient with config: {}", config.toJSON());
//             创建RedissonClient对象
            return Redisson.create(config);
        } catch (Exception e) {
            logger.error("Error creating RedissonClient", e);
//             可以选择抛出异常或者返回一个默认的 RedissonClient
            throw e;


        }
    }
}