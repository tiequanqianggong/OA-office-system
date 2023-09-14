package com.ruoyi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.mapper.ProjectMapper;
import com.ruoyi.pojo.Project;
import com.ruoyi.pojo.dto.ProjectPageQueryDTO;
import com.ruoyi.pojo.dto.ProjectUpdateDTO;
import com.ruoyi.service.IProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.ruoyi.common.utils.PageUtils.startPage;
import static com.ruoyi.constant.RedisConstants.*;
import static java.util.concurrent.TimeUnit.MINUTES;

@Slf4j
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project>implements IProjectService {
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private RedisCache redisCache;

    /**
     * 可用常量 Key
     */
    String projectKey=PROJECT_MANAGE_KEY;
    String projectNullKey=PROJECT_MANAGE_NULL_KEY;
    /**
     * 项目查询，（可选）项目id、项目状态、项目名称、项目所属类别、项目负责人、项目修改人、项目所属部门        且可分页
     * @param projectPageQueryDTO
     * @return
     */
    @Override
    @Transactional
    public List<Project> QueryProject(ProjectPageQueryDTO projectPageQueryDTO) {


        PageDomain pageDomain = TableSupport.buildPageRequestNoDefault();
        if(pageDomain.getPageNum()!=null&& pageDomain.getPageSize()!=null){
            //若依分页
            startPage();
            //如果有分页参数，则走数据库进行查询
            return projectMapper.pageQuery(projectPageQueryDTO);
        }
        //     如果缓存不为空              且不是条件查询
        //     从缓存中获取数据
        if (redisCache.hasKey(projectKey)&& projectPageQueryDTO.equals(null)){

               return  redisCache.getCacheList(projectKey);

        }
        //从数据库查询
        List<Project> projects = projectMapper.pageQuery(projectPageQueryDTO);

        if (extracted(projectPageQueryDTO, projectKey, projectNullKey, projects))
            return redisCache.getCacheList(projectNullKey);
        //返回数据库数据
        return  projects;
    }


    /**
     * 查询数据库中该条数据是否被逻辑删除
     * @param projectPageQueryDTO
     * @return
     */
    @Override
    @Transactional
    public Project selectDelFlag(ProjectPageQueryDTO projectPageQueryDTO) {
        return projectMapper.selectDelFlag(projectPageQueryDTO);
    }


    /**
     * 项目删除  逻辑删除 将字段 project_del_flag设为1     0存在   1不存在
      * @param projectId
     * @return
     */
    @Override
    public int deleteProject(Long projectId) {
        //如果缓存存在，删除缓存
        if(redisCache.hasKey(projectKey)){
           redisCache.deleteObject(projectKey);
        }
        return projectMapper.delFlag(projectId);
    }

    /**
     * 项目修改
     * @param projectUpdateDTO
     * @return
     */
    @Override
    @Transactional
    public int UpdateProject(ProjectUpdateDTO projectUpdateDTO) {
        Project project=new Project();
        BeanUtils.copyProperties(projectUpdateDTO,project);
        //先修改数据库
        project.setProjectUpdateTime(LocalDate.now());
        int num=projectMapper.UpdateProject(project);
        //如果缓存存在，删除缓存
        if (redisCache.hasKey(projectKey)){
            redisCache.deleteObject(projectKey);
        }
        return num;
    }

    @Override
    public int AddProject(Project project) {
        int rows=projectMapper.AddProject(project);
        return rows;
    }


    /**
     * 解决缓存穿透
     * @param projectPageQueryDTO
     * @param projectKey
     * @param projectNullKey
     * @param projects
     * @return
     */
    private boolean extracted(ProjectPageQueryDTO projectPageQueryDTO, String projectKey, String projectNullKey, List<Project> projects) {
        //如果数据库也为空   往Redis中写入空数据，
        if (projects.isEmpty()){
            projects.add(
                    Project.builder()
                            .projectName("空项目")
                            .build()
            );
            //如果Redis中没有写入过Null数据，则写入
            if(!redisCache.hasKey(projectNullKey)) {
                redisCache.setCacheList(projectNullKey, projects);
                redisCache.expire(projectNullKey, RedisTTL, MINUTES);
                return true;
            }
            //Redis中写入过Null数据，则返回
            return true;
        }
        //当Redis中没有写入过数据，且不是条件查询时写入Redis，
        if (!redisCache.hasKey(projectKey)&& projectPageQueryDTO.equals(null)) {
            redisCache.setCacheList(projectKey, projects);
            redisCache.expire(projectKey, RedisTTL, MINUTES);
        }
        return false;
    }
}
