package com.ruoyi.pojo.dto;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTeamDTO {
    @ApiParam(value = "项目成员id", name = "teamId")
    private Long teamId;//项目成员id
    @ApiParam(value = "项目成员姓名", name = "teamName")
    private String teamName;//项目成员姓名
    @ApiParam(value = "项目成员状态：1在岗/2离职/3休假", name = "teamStatus")
    private int teamStatus;//项目成员状态 1在岗/2离职/3休假
    @ApiParam(value = "项目成员状态：1在岗/2离职/3休假", name = "teamStatus")
    private String teamRole;//项目成员角色
    @ApiParam(value = "项目成员责任描述，分配的模块", name = "teamResponsibility")
    private String teamResponsibility;//项目成员责任描述 分配的模块

}
