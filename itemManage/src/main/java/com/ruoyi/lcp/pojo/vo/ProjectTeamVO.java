package com.ruoyi.lcp.pojo.vo;

import lombok.Data;

@Data
public class ProjectTeamVO {
    private String projectName;//项目名称
    private Long teamId;//项目成员id
    private String teamName;//项目成员姓名
    private int teamStatus;//项目成员状态 1在岗/2离职/3休假
    private Integer modelId; // 模块表id 分配的模块  项目管理/测试用例.....
    private String teamRole;//项目成员角色  开发/测试....等等
    private int teamAuthority;//项目管理权限      项目管理员0/项目参与者1
}
