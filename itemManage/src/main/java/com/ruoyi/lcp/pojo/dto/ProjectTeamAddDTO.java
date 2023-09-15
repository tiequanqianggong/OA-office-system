package com.ruoyi.lcp.pojo.dto;

import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectTeamAddDTO {
    private Long projectId;//关联的项目id
    private Long teamId;//项目成员id
    private String teamName;//项目成员姓名
    private int teamStatus;//项目成员状态 1在岗/2离职/3休假
    private String teamResponsibility;//项目成员责任描述 分配的模块
    private String teamRole;//项目成员角色  开发/测试....等等
    private String teamCreator;//项目成员创建者
    private int teamAuthority;//项目管理权限      项目管理员0/项目参与者1
}
