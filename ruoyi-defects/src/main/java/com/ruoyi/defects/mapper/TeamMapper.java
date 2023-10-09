package com.ruoyi.defects.mapper;

import com.ruoyi.defects.domain.ProjectName;
import com.ruoyi.defects.domain.Module;
import com.ruoyi.defects.domain.Team;

import java.util.List;

public interface TeamMapper {

    //查询员工id和姓名
    public List<Team> selectTeamList(String teamName);

    //根据用例id查询项目名称
    public ProjectName selectProjectName(Long caseId);
    // 根据用例ID查询模块名称
    Module selectModuleName(Long caseId);
}
