package com.ruoyi.lcp.config;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class  RedissonConfig {

    @Bean
    public RedissonClient redissonClient(){
        // 配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://59.110.32.219:6379").setPassword("lxrzh535179");
        // 创建RedissonClient对象
        return Redisson.create(config);
    }
}
