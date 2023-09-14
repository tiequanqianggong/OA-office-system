package com.ruoyi.mapper;

import com.ruoyi.pojo.ProjectTeam;
import com.ruoyi.pojo.dto.ProjectTeamAddDTO;
import com.ruoyi.pojo.dto.ProjectTeamDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectTeamMapper {
    /**
     * 项目成员分页查询
     * @param projectTeamDTO
     * @return
     */
    List<ProjectTeam> pageQuery(ProjectTeamDTO projectTeamDTO);


    /**
     * 根据成员teamid和姓名查询数据库中是否存在该项目成员
     * @return
     */
    ProjectTeam selectTeamByTeamIdAndName(ProjectTeamAddDTO projectTeamAddDTO);

    /**
     * 根据teamId删除team表数据
     * @param teamId
     * @return
     */

    int deleteByTeamId(Long teamId);

    /**
     * 项目成员修改 根据teamId
     * @param projectTeam
     * @return
     */
    int UpdateProjectTeam(ProjectTeam projectTeam);


    /**
     * 新增项目成员
     * @param projectTeam
     * @return
     */

    int AddProjectTeam(ProjectTeam projectTeam);


}
