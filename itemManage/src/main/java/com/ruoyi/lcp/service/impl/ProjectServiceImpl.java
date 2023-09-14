package com.ruoyi.lcp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.lcp.constant.RedisConstants;
import com.ruoyi.lcp.mapper.ProjectMapper;
import com.ruoyi.lcp.pojo.Project;
import com.ruoyi.lcp.pojo.dto.ProjectPageQueryDTO;
import com.ruoyi.lcp.pojo.dto.ProjectUpdateDTO;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.lcp.pojo.vo.ProjectVO;
import com.ruoyi.lcp.service.IProjectService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

import static com.ruoyi.common.utils.PageUtils.startPage;
import static com.ruoyi.lcp.constant.RedissonConstants.ReadLock;
import static java.util.concurrent.TimeUnit.MINUTES;

@Slf4j
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project>implements IProjectService {
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private RedisCache redisCache;

    @Resource
    private RedissonClient redissonClient;

    /**
     * 可用常量 Key
     */
    String projectKey= RedisConstants.PROJECT_MANAGE_KEY;
    String projectNullKey= RedisConstants.PROJECT_MANAGE_NULL_KEY;
    /**
     * 项目查询，（可选）项目id、项目状态、项目名称、项目所属类别、项目负责人、项目修改人、项目所属部门        且可分页
     * @param projectPageQueryDTO
     * @return
     */
    @Override
    @Transactional
    public List<Project> QueryProject(ProjectPageQueryDTO projectPageQueryDTO) {
        RReadWriteLock rReadWriteLock = redissonClient.getReadWriteLock( ReadLock+ SecurityUtils.getUserId());
        RLock readLock=rReadWriteLock.readLock();
       try {
           readLock.lock();
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

       }finally {
           readLock.unlock();
       }

    }

    /**
     * 查询数据库中该条数据是否被逻辑删除
     * @param projectPageQueryDTO
     * @return
     */
    @Override
    @Transactional
    public Project selectDelFlag(ProjectPageQueryDTO projectPageQueryDTO) {
        RReadWriteLock rReadWriteLock = redissonClient.getReadWriteLock( ReadLock+ SecurityUtils.getUserId());
        RLock readLock=rReadWriteLock.readLock();
        try {
            readLock.lock();
            return projectMapper.selectDelFlag(projectPageQueryDTO);
        }finally {
            readLock.unlock();
        }

    }


    /**
     * 项目删除  逻辑删除 将字段 project_del_flag设为1     0存在   1不存在
      * @param projectId
     * @return
     */
    @Override
    @Transactional
    public int deleteProject(Long projectId) {
        RReadWriteLock rReadWriteLock = redissonClient.getReadWriteLock( ReadLock+ SecurityUtils.getUserId());
        RLock readLock=rReadWriteLock.readLock();
        try {
            readLock.lock();
            //如果缓存存在，删除缓存
            if(redisCache.hasKey(projectKey)){
                redisCache.deleteObject(projectKey);
            }
            return projectMapper.delFlag(projectId);
        }finally {
            readLock.unlock();
        }
    }

    /**
     * 项目修改
     * @param projectUpdateDTO
     * @return
     */
    @Override
    @Transactional
    public int UpdateProject(ProjectUpdateDTO projectUpdateDTO) {
        RReadWriteLock rReadWriteLock = redissonClient.getReadWriteLock( ReadLock+ SecurityUtils.getUserId());
        RLock writeLock=rReadWriteLock.writeLock();
        try {
            writeLock.lock();
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

        }finally {
            writeLock.unlock();
        }

    }

    @Override
    public int AddProject(Project project) {
        RReadWriteLock rReadWriteLock = redissonClient.getReadWriteLock( ReadLock+ SecurityUtils.getUserId());
        RLock writeLock=rReadWriteLock.writeLock();
        try {
            writeLock.lock();
            //添加项目时，增加创建时间
            project.setProjectCreateTime(LocalDate.now());
            int rows=projectMapper.AddProject(project);
            Long id=projectMapper.selectIdByProjectId(project.getProjectId());
            projectMapper.AddId(id);
            return rows;
        }finally {
            writeLock.unlock();
        }

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
                redisCache.expire(projectNullKey, RedisConstants.RedisTTL, MINUTES);
                return true;
            }
            //Redis中写入过Null数据，则返回
            return true;
        }
        //当Redis中没有写入过数据，且不是条件查询时写入Redis，
        if (!redisCache.hasKey(projectKey)&& projectPageQueryDTO.equals(null)) {
            redisCache.setCacheList(projectKey, projects);
            redisCache.expire(projectKey, RedisConstants.RedisTTL, MINUTES);
        }
        return false;
    }
}
