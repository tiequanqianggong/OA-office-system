package com.ruoyi.defects.service;

import com.ruoyi.defects.domain.CaseIdAndProjectName;
import com.ruoyi.defects.domain.Module;
import com.ruoyi.defects.domain.Team;

import java.util.List;

public interface ITeamService {

    //查询员工id和姓名
    List<Team> selectTeamList(String teamName);

    // 根据用例ID查询用例id和项目名称
    CaseIdAndProjectName selectCaseIdAndProjectName(Long caseId);

    Module selectModuleName(Long caseId);
}
