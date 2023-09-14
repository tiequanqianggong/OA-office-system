package com.ruoyi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.pojo.Project;
import com.ruoyi.pojo.dto.ProjectUpdateDTO;
import com.ruoyi.pojo.dto.ProjectPageQueryDTO;

import java.util.List;

public interface IProjectService extends IService<Project> {
    /**
     * 项目分页查询，（可选）项目名称、项目状态....
     * @param projectPageQueryDTO
     * @return
     */
    List<Project> QueryProject(ProjectPageQueryDTO projectPageQueryDTO);

    /**
     * 查询数据库中该条数据是否被逻辑删除
     * @param projectPageQueryDTO
     * @return
     */
    Project  selectDelFlag(ProjectPageQueryDTO projectPageQueryDTO);


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
}
