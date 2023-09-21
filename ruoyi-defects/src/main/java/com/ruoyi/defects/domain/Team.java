package com.ruoyi.defects.domain;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;

public class Team {

    /** 成员id */
    @Excel(name = "分配给某成员")
    @ApiModelProperty("分配给某成员")
    private Long teamId;
    /** 成员姓名 */
    @Excel(name = "成员姓名")
    @ApiModelProperty("成员姓名")
    private String teamName;

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                '}';
    }
}
