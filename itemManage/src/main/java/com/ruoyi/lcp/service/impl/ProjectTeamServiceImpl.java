package com.ruoyi.lcp.service.impl;



import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.lcp.constant.RedisConstants;
import com.ruoyi.lcp.mapper.ProjectTeamMapper;
import com.ruoyi.lcp.pojo.Project;
import com.ruoyi.lcp.pojo.dto.ProjectTeamAddDTO;
import com.ruoyi.lcp.pojo.dto.ProjectTeamDTO;
import com.ruoyi.lcp.pojo.dto.ProjectTeamUpdateDTO;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.core.redis.RedisCache;

import com.ruoyi.lcp.pojo.ProjectTeam;
import com.ruoyi.lcp.service.IProjectTeamService;
import com.ruoyi.lcp.util.PageUtil;
import com.ruoyi.lcp.util.RedisU;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ruoyi.common.utils.PageUtils.startPage;
import static com.ruoyi.lcp.constant.RedissonConstants.ReadLock;
import static java.util.concurrent.TimeUnit.MINUTES;

@Slf4j
@Service
public  class ProjectTeamServiceImpl implements IProjectTeamService {
    @Autowired
    private ProjectTeamMapper projectTeamMapper;

    @Autowired
    private RedisCache redisCache;

    @Resource
    private RedissonClient redissonClient;
    @Resource
    private RedisU redisU;


    /**
     * 可用常量Key
     */
    String projectTeamKey= RedisConstants.PROJECT_MANAGE_TEAM_KEY;
    String projectTeamNullKey= RedisConstants.PROJECT_MANAGE_TEAM_NULL_KEY;
    /**
     * 项目成员管理分页查询             采用随机TTL解决缓存雪崩
     * @return
     */
    @Override
    @Transactional
    public List<ProjectTeam> QueryProjectTeam(ProjectTeamDTO projectTeamDTO) {
        RReadWriteLock rReadWriteLock = redissonClient.getReadWriteLock( ReadLock+ SecurityUtils.getUserId());
        RLock readLock=rReadWriteLock.readLock();
        try {
            readLock.lock();

            PageDomain pageDomain = TableSupport.buildPageRequestNoDefault();
            Integer pageNum=pageDomain.getPageNum();
            Integer pageSize=pageDomain.getPageSize();
//            if(pageDomain.getPageNum()!=null&& pageDomain.getPageSize()!=null){
//                //若依分页
//                startPage();
//                //如果有分页参数，则走数据库进行查询
//                return projectTeamMapper.pageQuery(projectTeamDTO);
//            }

            //     如果缓存不为空              且不是条件查询
            //     从缓存中获取数据
            if (redisCache.hasKey(projectTeamKey)&& projectTeamDTO==null&&pageNum!=null&&pageSize!=null){

                /**
                 * 通过读取若依获取的分页参数， 根据分页算法获取redis中的数据，     不完善：
                 */

                // 获取符合模式为 "project_manage:CACHE_Project*" 的哈希表
                Map<String, String> hashData = redisCache.getCacheMap(projectTeamKey);
                List<ProjectTeam> projectTeamList=redisU.getDataFromRedis(pageNum,pageSize,projectTeamKey,hashData,ProjectTeam.class);
                return projectTeamList;
            }


            //Redis为空查询数据库
            List<ProjectTeam> projectTeams=projectTeamMapper.pageQuery(projectTeamDTO);


            if (extracted(projectTeamDTO, projectTeamKey, projectTeamNullKey, projectTeams)) {
                return redisCache.getCacheList(projectTeamNullKey);
            }

            //如果不是条件查询          通过分页工具将集合进行分页
            if (projectTeamDTO==null) {
                PageUtil<ProjectTeam> pageUtil=new PageUtil<>(pageNum,pageSize,projectTeams);
                return  pageUtil.getPageList();
            }
            return projectTeams ;
        }finally {
            readLock.unlock();
        }

    }


    /**
     * 根据成员teamid和姓名查询数据库中是否存在该项目成员
     * @return
     */
    @Override
    public ProjectTeam selectTeamByTeamIdAndName(ProjectTeamAddDTO projectTeamAddDTO) {
        RReadWriteLock rReadWriteLock = redissonClient.getReadWriteLock( ReadLock+ SecurityUtils.getUserId());
        RLock readLock=rReadWriteLock.readLock();
        try {
            readLock.lock();
        return projectTeamMapper.selectTeamByTeamIdAndName(projectTeamAddDTO);
        }finally {
            readLock.unlock();
        }
    }


    /**
     * 根据teamId删除
     * @param teamId
     * @return
     */
    @Override
    @Transactional
    public int deleteByTeamId(Long teamId) {
        RReadWriteLock rReadWriteLock = redissonClient.getReadWriteLock( ReadLock+ SecurityUtils.getUserId());
        RLock readLock=rReadWriteLock.readLock();
        try {
        //如果缓存存在，删除缓存
           if(redisCache.hasKey(projectTeamKey)){
            redisCache.deleteObject(projectTeamKey);
           }

        return projectTeamMapper.deleteByTeamId(teamId);
        }finally {
            readLock.unlock();
        }
    }

    /**
     * 项目成员修改 根据teamId
     * @param projectTeamUpdateDTO
     * @return
     */
    @Override
    @Transactional
    public int UpdateProjectTeam(ProjectTeamUpdateDTO projectTeamUpdateDTO) {
        RReadWriteLock rReadWriteLock = redissonClient.getReadWriteLock( ReadLock+ SecurityUtils.getUserId());
        RLock writeLock=rReadWriteLock.writeLock();
        try {
            ProjectTeam projectTeam=new ProjectTeam();
            BeanUtils.copyProperties(projectTeamUpdateDTO,projectTeam);
            //先修改数据库
            projectTeam.setTeamUpdateTime(LocalDate.now());
            int num=projectTeamMapper.UpdateProjectTeam(projectTeam);
            //如果缓存存在，删除缓存
            if (redisCache.hasKey(projectTeamKey)){
                redisCache.deleteObject(projectTeamKey);
            }
            return num;
        }finally {
            writeLock.unlock();
        }

    }

    /**
     * 新增项目成员
     * @param projectTeam
     * @return
     */
    @Override
    public int AddProjectTeam(ProjectTeam projectTeam) {
        RReadWriteLock rReadWriteLock = redissonClient.getReadWriteLock( ReadLock+ SecurityUtils.getUserId());
        RLock writeLock=rReadWriteLock.writeLock();
        try {
            writeLock.lock();
            //设置项目成员创建时间
            projectTeam.setTeamCreateTime(LocalDate.now());
            projectTeamMapper.update();
            int rows=projectTeamMapper.AddProjectTeam(projectTeam);
            if (redisCache.hasKey(projectTeamKey)){
                redisCache.deleteObject(projectTeamKey);
            }
            return rows;
        }finally {
            writeLock.unlock();
        }

    }




    /**
     * 解决缓存穿透  缓存空数据到redis
     * @param projectTeamDTO
     * @param projectTeamKey
     * @param projectTeamNullKey
     * @param projectTeams
     * @return
     */
    private boolean extracted(ProjectTeamDTO projectTeamDTO, String projectTeamKey, String projectTeamNullKey, List<ProjectTeam> projectTeams) {
        //数据库为空   往Redis中写入空数据，
        if (projectTeams.isEmpty()){
            projectTeams.add(
                    ProjectTeam.builder()
                            .teamName("没有这个项目成员")
                            .build()
            );
            //如果Redis中没有写入过Null数据，则写入
            if(!redisCache.hasKey(projectTeamNullKey)) {
                redisCache.setCacheList(projectTeamNullKey, projectTeams);
                redisCache.expire(projectTeamNullKey, RedisConstants.RedisTTL,MINUTES);
                return true;
            }
            //Redis中写入过Null数据，则返回
            return true;
        }

        //此处为：数据库不为空则 将数据写入Redis   返回数据库数据
        if (!redisCache.hasKey(projectTeamKey)&& ObjectUtils.isEmpty(projectTeamDTO)) {
            for (ProjectTeam projectTeam:projectTeams) {
                /**
                 * 采用Redis Hash存储 List<ProjectTeam> 对象，根据自增id作为hashkey存入Redis
                 */
                redisCache.setCacheMapValue(projectTeamKey,projectTeam.getTeamId().toString(),projectTeam);
            }
            redisCache.expire(projectTeamKey, RedisConstants.RedisTTL, MINUTES);
        }
        return false;
    }


    /**
     * 计算ProjectTeam表有几个数据
     * @return
     */
    @Override
    public List<Long> countAllProjectTeam() {
        return projectTeamMapper.countAllProjectTeam();
    }
}
