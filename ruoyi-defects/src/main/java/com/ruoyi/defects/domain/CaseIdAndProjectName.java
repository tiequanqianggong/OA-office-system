package com.ruoyi.defects.domain;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;

public class CaseIdAndProjectName {
    //项目名称
    @Excel(name = "项目名称")
    @ApiModelProperty("项目名称")
    private  String projectName;
    //用例id
    @Excel(name = "用例id")
    @ApiModelProperty("用例id")
    private Long id;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CaseIdAndProjectName{" +
                "projectName='" + projectName + '\'' +
                ", id=" + id +
                '}';
    }
}
