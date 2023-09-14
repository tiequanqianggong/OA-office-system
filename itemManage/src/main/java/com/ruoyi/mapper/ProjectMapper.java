package com.ruoyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.pojo.Project;
import com.ruoyi.pojo.dto.ProjectPageQueryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ProjectMapper extends BaseMapper<Project> {
    /**
     * 项目分页查询，（可选）项目id、项目状态、项目名称、项目所属类别、项目负责人、项目修改人、项目所属部门
     * @param projectPageQueryDTO
     * @return
     */
    public List<Project> pageQuery(ProjectPageQueryDTO projectPageQueryDTO);

    /**
     * 查询数据库中该条数据是否被逻辑删除
     *
     * @param projectPageQueryDTO
     * @return
     */
    Project selectDelFlag(ProjectPageQueryDTO projectPageQueryDTO);

    /**
     * 项目删除  逻辑删除 将字段 project_del_flag设为1     0存在   1不存在
     * @param projectId
     * @return
     */
    public  int delFlag(Long projectId);

    /**
     * 根据projectId  项目id进行修改
     * @param project
     * @return
     */
    int UpdateProject(Project project);

    /**
     * 增加项目
     * @param project
     * @return
     */
    int AddProject(Project project);


}
