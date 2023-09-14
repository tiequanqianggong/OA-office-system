package com.ruoyi.service.impl;



import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.core.redis.RedisCache;

import com.ruoyi.mapper.ProjectTeamMapper;
import com.ruoyi.pojo.ProjectTeam;
import com.ruoyi.pojo.dto.ProjectTeamAddDTO;
import com.ruoyi.pojo.dto.ProjectTeamDTO;
import com.ruoyi.pojo.dto.ProjectTeamUpdateDTO;
import com.ruoyi.service.IProjectTeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.ruoyi.common.utils.PageUtils.startPage;
import static com.ruoyi.constant.RedisConstants.*;
import static java.util.concurrent.TimeUnit.MINUTES;

@Slf4j
@Service
public  class ProjectTeamServiceImpl implements IProjectTeamService {
    @Autowired
    private ProjectTeamMapper projectTeamMapper;

    @Autowired
    private RedisCache redisCache;
    /**
     * 可用常量Key
     */
    String projectTeamKey=PROJECT_MANAGE_TEAM_KEY;
    String projectTeamNullKey=PROJECT_MANAGE_TEAM_NULL_KEY;
    /**
     * 项目成员管理分页查询             采用随机TTL解决缓存雪崩
     * @return
     */
    @Override
    @Transactional
    public List<ProjectTeam> QueryProjectTeam(ProjectTeamDTO projectTeamDTO) {

        PageDomain pageDomain = TableSupport.buildPageRequestNoDefault();
        if(pageDomain.getPageNum()!=null&& pageDomain.getPageSize()!=null){
            //若依分页
            startPage();
            //如果有分页参数，则走数据库进行查询
            return projectTeamMapper.pageQuery(projectTeamDTO);
        }
        //     如果缓存不为空              且不是条件查询
        //     从缓存中获取数据
        if (redisCache.hasKey(projectTeamKey)&& projectTeamDTO.equals(null)){

              return redisCache.getCacheList(projectTeamKey);

        }


        //Redis为空查询数据库
        List<ProjectTeam> projectTeams=projectTeamMapper.pageQuery(projectTeamDTO);


        if (extracted(projectTeamDTO, projectTeamKey, projectTeamNullKey, projectTeams))
            return redisCache.getCacheList(projectTeamNullKey);
        return projectTeams ;
    }


    /**
     * 根据成员teamid和姓名查询数据库中是否存在该项目成员
     * @return
     */
    @Override
    public ProjectTeam selectTeamByTeamIdAndName(ProjectTeamAddDTO projectTeamAddDTO) {
        return projectTeamMapper.selectTeamByTeamIdAndName(projectTeamAddDTO);
    }


    /**
     * 根据teamId删除
     * @param teamId
     * @return
     */
    @Override
    @Transactional
    public int deleteByTeamId(Long teamId) {
        //如果缓存存在，删除缓存
        if(redisCache.hasKey(projectTeamKey)){
            redisCache.deleteObject(projectTeamKey);
        }

        return projectTeamMapper.deleteByTeamId(teamId);
    }

    /**
     * 项目成员修改 根据teamId
     * @param projectTeamUpdateDTO
     * @return
     */
    @Override
    @Transactional
    public int UpdateProjectTeam(ProjectTeamUpdateDTO projectTeamUpdateDTO) {
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
    }

    /**
     * 新增项目成员
     * @param projectTeam
     * @return
     */
    @Override
    public int AddProjectTeam(ProjectTeam projectTeam) {
        int rows=projectTeamMapper.AddProjectTeam(projectTeam);
        return rows;
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
                redisCache.expire(projectTeamNullKey,RedisTTL,MINUTES);
                return true;
            }
            //Redis中写入过Null数据，则返回
            return true;
        }

        //此处为：数据库不为空则 将数据写入Redis   返回数据库数据
        if (!redisCache.hasKey(projectTeamKey)&& projectTeamDTO.equals(null)) {
            redisCache.setCacheList(projectTeamKey, projectTeams);
            redisCache.expire(projectTeamKey,RedisTTL,MINUTES);
        }
        return false;
    }
}
