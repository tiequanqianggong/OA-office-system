package com.ruoyi.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("project_team")
@ApiModel("ProjectTeam")
public class ProjectTeam  implements Serializable {
    @ApiParam(value = "项目成员表id", name = "Id")
    private Long Id;//项目成员表id(不需要，自增)
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
    @ApiParam(value = "项目成员加入团队时间", name = "teamStartTime")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate teamStartTime;//项目成员加入团队时间
    @ApiParam(value = "项目成员加入团队时间", name = "teamStartTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDate teamCreateTime;//项目成员信息创建时间
    @ApiParam(value = "项目成员信息修改时间", name = "teamUpdateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDate teamUpdateTime;//项目成员信息修改时间
}
