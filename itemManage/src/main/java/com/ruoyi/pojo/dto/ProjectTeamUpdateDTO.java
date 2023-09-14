package com.ruoyi.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectTeamUpdateDTO {
    private Long projectId;//关联的项目id
    private Long teamId;//项目成员id
    private String teamName;//项目成员姓名
    private int teamStatus;//项目成员状态 1在岗/2离职/3休假
    private String teamResponsibility;//项目成员责任描述 分配的模块
    private String teamRole;//项目成员角色  开发/测试....等等
    private String teamCreator;//项目成员创建者
    private int teamAuthority;//项目管理权限      项目管理员0/项目参与者1
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate teamStartTime;//项目成员加入团队时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDate teamCreateTime;//项目成员信息创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDate teamUpdateTime;//项目成员信息修改时间
}
