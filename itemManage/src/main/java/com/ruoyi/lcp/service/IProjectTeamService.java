package com.ruoyi.lcp.service;

import com.ruoyi.lcp.pojo.dto.ProjectTeamAddDTO;
import com.ruoyi.lcp.pojo.dto.ProjectTeamDTO;
import com.ruoyi.lcp.pojo.dto.ProjectTeamUpdateDTO;
import com.ruoyi.lcp.pojo.ProjectTeam;

import java.util.List;

public interface IProjectTeamService{
    /**
     * 项目成员管理分页查询
     * @param projectTeamDTO
     * @return
     */
    List<ProjectTeam> QueryProjectTeam(ProjectTeamDTO projectTeamDTO);


    /**
     * 根据成员teamid和姓名查询数据库中是否存在该项目成员
     * @return
     */
    ProjectTeam selectTeamByTeamIdAndName(ProjectTeamAddDTO projectTeamAddDTO);

    /**
     * 根据teamId删除
     * @param teamId
     * @return
     */
    int deleteByTeamId(Long teamId);

    /**
     * 根据teamId 修改项目团队成员
     * @param projectTeamUpdateDTO
     * @return
     */
    int UpdateProjectTeam(ProjectTeamUpdateDTO projectTeamUpdateDTO);

    /**
     * 添加团队成员
     * @param projectTeam
     * @return
     */
    int AddProjectTeam(ProjectTeam projectTeam);

    /**
     * 计算ProjectTeam表有几个数据
     * @return
     */
    List<Long> countAllProjectTeam();
}
