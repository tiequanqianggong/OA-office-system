package com.ruoyi.defects.service.impl;

import com.ruoyi.defects.domain.ProjectName;
import com.ruoyi.defects.domain.Module;
import com.ruoyi.defects.domain.Team;
import com.ruoyi.defects.mapper.TeamMapper;
import com.ruoyi.defects.service.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TeamServiceImpl implements ITeamService {



    @Autowired
    private TeamMapper teamMapper;
    @Override
    /**
     *查询所有员工姓名
     */
    public List<Team> selectTeamList(String teamName) {
        List<Team> teamList = teamMapper.selectTeamList(teamName);
        return teamList;
    }


    /**
     * 根据用例ID查项目名称
     */
    @Override
    public ProjectName selectProjectName(Long caseId) {
       ProjectName ProjectName= teamMapper.selectProjectName(caseId);
        return ProjectName;
    }
   //     根据用例ID查询模块名称
    @Override
    public Module selectModuleName(Long caseId) {
        Module module = teamMapper.selectModuleName(caseId);
        return module;
    }
}
