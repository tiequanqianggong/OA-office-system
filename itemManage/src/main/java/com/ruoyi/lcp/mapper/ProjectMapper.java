package com.ruoyi.lcp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.lcp.pojo.Project;
import com.ruoyi.lcp.pojo.dto.ProjectPageQueryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


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
     * @param projectName
     * @return
     */
    Project selectDelFlag(String projectName);

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

    /**
     * 修改project表的自增id序列      修改表自增序列的起始值
     */
    void update();

    /**
     * 根据项目名称查询
     * @param projectPageQueryDTO
     * @return
     */
    List<Project> QueryProjectByProjectName(ProjectPageQueryDTO projectPageQueryDTO);

    List<Long> countAllProject();

    /**
     * 根据项目id查询项目状态
     */
    int  selectStatusByProjectId(Long projectId);


//    /**
//     * 根据project 修改projectId
//     *
//     * @param project
//     */
//    void UpdateProjectId(Project project);
    List<Map<String,Object>> getAllProject();
}
