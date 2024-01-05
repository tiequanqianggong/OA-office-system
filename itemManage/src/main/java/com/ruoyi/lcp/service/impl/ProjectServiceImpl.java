package com.ruoyi.lcp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.lcp.constant.BaseContext;
import com.ruoyi.lcp.constant.RedisConstants;
import com.ruoyi.lcp.mapper.ProjectMapper;
import com.ruoyi.lcp.pojo.Project;
import com.ruoyi.lcp.pojo.dto.ProjectPageQueryDTO;
import com.ruoyi.lcp.pojo.dto.ProjectUpdateDTO;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.lcp.service.IProjectService;
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
import java.util.List;
import java.util.Map;

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
    @Resource
    private RedisU redisU;

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

           PageDomain pageDomain = TableSupport.buildPageRequest();
           Integer pageNum=pageDomain.getPageNum();
           Integer pageSize=pageDomain.getPageSize();
//           if(pageDomain.getPageNum()!=null&& pageDomain.getPageSize()!=null){
//               //若依分页
//               startPage();
//               //如果有分页参数，则走数据库进行查询
//               return projectMapper.pageQuery(projectPageQueryDTO);
//           }

           //     如果缓存不为空      且不是条件查询
           //     从缓存中获取数据
           if (redisCache.hasKey(projectKey)&& projectPageQueryDTO==null){

               /**
                * 通过读取若依获取的分页参数， 根据分页算法获取redis中的数据，     不完善：
                */
               // 获取符合模式为 "project_manage:CACHE_Project*" 的哈希表
               Map<String, String> hashData = redisCache.getCacheMap(projectKey);
               //设置线程参数记录数据长度
               BaseContext.setCountNum((long) hashData.size());
               //如果是分页查询有分页参数进入Redis分页工具类进行分页并返回
               if (pageNum!=null&&pageSize!=null) {
                   List<Project> projectList = redisU.getDataFromRedis(pageNum, pageSize, projectKey, hashData, Project.class);
                   return projectList;
               }
               //无分页参数直接返回Redis中的所有数据,通过redisU工具类将 Map转化为List
               return  redisU.getHashToList(Project.class,hashData);
           }
           //从数据库查询
           List<Project> projects = projectMapper.pageQuery(projectPageQueryDTO);
           //将获取数据的大小记录到线程中
           BaseContext.setCountNum((long) projects.size());


           //解决缓存穿透
           if (extracted(projectPageQueryDTO, projectKey, projectNullKey, projects)) {
               return redisCache.getCacheList(projectNullKey);
           }

           //如果不是条件查询    通过分页工具将集合进行分页
           if (projectPageQueryDTO==null) {
               PageUtil<Project> pageUtil = new PageUtil<>(pageNum, pageSize, projects);

           return  pageUtil.getPageList();
           }
           return projects;
       }finally {
           readLock.unlock();
       }

    }
    /**
     * 查询数据库中是否有同名的项目
     * @param projectPageQueryDTO
     * @return
     */
    @Override
    public List<Project> QueryProjectByProjectName(ProjectPageQueryDTO projectPageQueryDTO) {
        RReadWriteLock rReadWriteLock = redissonClient.getReadWriteLock( ReadLock+ SecurityUtils.getUserId());
        RLock readLock=rReadWriteLock.readLock();
        try {
            readLock.lock();
           return projectMapper.QueryProjectByProjectName(projectPageQueryDTO);
        }finally {
            readLock.unlock();
        }

    }
    /**
     * 查询数据库中该条数据是否被逻辑删除
     *
     * @param projectName
     * @return
     */
    @Override
    public Project selectDelFlag(String projectName) {
        RReadWriteLock rReadWriteLock = redissonClient.getReadWriteLock( ReadLock+ SecurityUtils.getUserId());
        RLock readLock=rReadWriteLock.readLock();
        try {
            readLock.lock();
            Project project= projectMapper.selectDelFlag(projectName);
            return project;
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

    /**
     * 添加项目
     * @param project
     * @return
     */
    @Override
    @Transactional
    public int AddProject(Project project) {
        RReadWriteLock rReadWriteLock = redissonClient.getReadWriteLock( ReadLock+ SecurityUtils.getUserId());
        RLock writeLock=rReadWriteLock.writeLock();
        try {
            writeLock.lock();
            //添加项目时，增加创建时间
            project.setProjectCreateTime(LocalDate.now());
            projectMapper.update();
            int rows=projectMapper.AddProject(project);
            //如果缓存存在，删除缓存
            if (redisCache.hasKey(projectKey)){
                redisCache.deleteObject(projectKey);
            }
            return rows;
        }finally {
            writeLock.unlock();
        }

    }


//    /**
//     * 根据project 修改projectId
//     *
//     * @param project
//     */
//    @Override
//    public void UpdateProjectId(Project project) {
//        projectMapper.UpdateProjectId(project);
//    }


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
        if (!redisCache.hasKey(projectKey)&& ObjectUtils.isEmpty(projectPageQueryDTO)) {
            for (Project project:projects) {
                /**
                 * 采用Redis Hash存储 List<Project> 对象，根据自增id作为hashkey存入Redis
                 */
                redisCache.setCacheMapValue(projectKey,project.getProjectId().toString()+System.currentTimeMillis(),project);
            }
            redisCache.expire(projectKey, RedisConstants.RedisTTL, MINUTES);
        }
        return false;
    }

    /**
     * 计算表中有几条数据
     * @return
     */
    @Override
    public List<Long> countAllProject(){
        return projectMapper.countAllProject();
    }

    /**
     * 根据项目id查询项目状态
     * @param projectId
     * @return
     */
    public int  selectStatusByProjectId(Long projectId){
        return projectMapper.selectStatusByProjectId(projectId);
    }
    /**
     * 批量删除项目
     * @param projectIds
     * @return
     */
    @Override
    public int deleteListProject(List<Long> projectIds) {
        RReadWriteLock rReadWriteLock = redissonClient.getReadWriteLock( ReadLock+ SecurityUtils.getUserId());
        RLock readLock=rReadWriteLock.readLock();
        try {
            readLock.lock();
            //如果缓存存在，删除缓存
            if(redisCache.hasKey(projectKey)){
                redisCache.deleteObject(projectKey);
            }
            int row = 0;
            for (Long id:projectIds){
                projectMapper.delFlag(id);
                row++;
            }
            return row;
        }finally {
            readLock.unlock();
        }
    }
    @Override
    public List<Map<String,Object>> getAllProject(){
        return projectMapper.getAllProject();
    }
}
