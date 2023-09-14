package com.ruoyi.pojo.dto;

import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectTeamAddDTO {
    @ApiParam(value = "关联的项目id", name = "projectId")
    private Long projectId;//关联的项目id
    @ApiParam(value = "项目成员id", name = "teamId")
    private Long teamId;//项目成员id
    @ApiParam(value = "项目成员姓名", name = "teamName")
    private String teamName;//项目成员姓名
    @ApiParam(value = "项目成员状态：1在岗/2离职/3休假", name = "teamStatus")
    private int teamStatus;//项目成员状态 1在岗/2离职/3休假
    @ApiParam(value = "项目成员责任描述，分配的模块", name = "teamResponsibility")
    private String teamResponsibility;//项目成员责任描述 分配的模块
    @ApiParam(value = "项目成员角色，例如开发、测试等", name = "teamRole")
    private String teamRole;//项目成员角色  开发/测试....等等
    @ApiParam(value = "项目成员创建者", name = "teamCreator")
    private String teamCreator;//项目成员创建者
    @ApiParam(value = "项目管理权限，项目管理员0/项目参与者1", name = "teamAuthority")
    private int teamAuthority;//项目管理权限      项目管理员0/项目参与者1
}
