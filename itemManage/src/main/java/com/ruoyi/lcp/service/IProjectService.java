package com.ruoyi.lcp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.lcp.pojo.Project;
import com.ruoyi.lcp.pojo.dto.ProjectPageQueryDTO;
import com.ruoyi.lcp.pojo.dto.ProjectUpdateDTO;

import java.util.List;

public interface IProjectService extends IService<Project> {
    /**
     * 项目分页查询，（可选）项目名称、项目状态....
     * @param projectPageQueryDTO
     * @return
     */
    List<Project> QueryProject(ProjectPageQueryDTO projectPageQueryDTO);

    /**
     * 查询数据库中是否有同名的项目
     * @param projectPageQueryDTO
     * @return
     */
    List<Project> QueryProjectByProjectName(ProjectPageQueryDTO projectPageQueryDTO);



    /**
     * 查询数据库中该条数据是否被逻辑删除
     *
     * @param projectName
     * @return
     */
    Project  selectDelFlag(String projectName);


    /**
     * 项目删除  逻辑删除 将字段 project_del_flag设为1     0存在   1不存在
     * @param projectId
     * @return
     */
    int deleteProject(Long projectId);

    /**
     * 项目修改
     * @param projectUpdateDTO
     * @return
     */
    int UpdateProject(ProjectUpdateDTO projectUpdateDTO);

    /**
     * 添加项目
     * @param project
     * @return
     */
    int AddProject(Project project);


    /**
     * 计算表中有几条数据
     * @return
     */
     List<Long> countAllProject();


    /**
     * 根据项目id查询项目状态
     * @param id
     * @return
     */
    int selectStatusByProjectId(Long id);
    /**
     * 批量删除项目
     * @param projectIds
     * @return
     */
    int deleteListProject(List<Long> projectIds);



//    /**
//     * 根据project 修改projectId
//     *
//     * @param project
//     */
//    void UpdateProjectId(Project project);
}
