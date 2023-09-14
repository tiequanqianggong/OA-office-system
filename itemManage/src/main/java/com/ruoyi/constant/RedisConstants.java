package com.ruoyi.constant;

import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.ThreadLocalRandom;

public class RedisConstants {
    public static final  Long RedisTTL=getRandomLongInRange(3000L,6000L);

    public static final  String PROJECT_MANAGE_KEY="project_manage:CACHE_Project";//项目key
    public static final String PROJECT_MANAGE_TEAM_KEY="project_manage:CACHE_ProjectTeam";//项目团队key
    public static final String PROJECT_MANAGE_NULL_KEY="project_manage:CACHE_NullProject";//项目空值key
    public static final String PROJECT_MANAGE_TEAM_NULL_KEY="project_manage:CACHE_NullProjectTeam";//项目团队空值key

    public static Long getRandomLongInRange(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max);
    }
}
