package com.ruoyi.lcp.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.lcp.annotation.Percentage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("project")
@Builder
@ApiModel("Project")
public class Project implements Serializable {

    @ApiParam(value = "项目id", name = "projectId")
    @ApiModelProperty(value = "项目id")
    private Long projectId; // 项目id

    @ApiParam(value = "项目名称", name = "projectName")
    @ApiModelProperty(value = "项目名称")
    private String projectName; // 项目名称

    @ApiParam(value = "项目所属类别", name = "projectType")
    @ApiModelProperty(value = "项目所属类别")
    private String projectType; // 项目所属类别

    @ApiParam(value = "项目状态 0已挂起/1已关闭/2未完成/3进行中/4未开始", name = "projectStatus")
    @ApiModelProperty(value = "项目状态 0已挂起/1已关闭/2未完成/3进行中/4未开始")
    private int projectStatus; // 项目状态

    @ApiParam(value = "项目材料（图片url路径）", name = "projectMaterial")
    @ApiModelProperty(value = "项目材料（图片url路径）")
    private String projectMaterial; // 项目材料（图片url路径）

    @ApiParam(value = "项目描述", name = "projectDescription")
    @ApiModelProperty(value = "项目描述")
    private String projectDescription; // 项目描述

    @ApiParam(value = "项目负责人", name = "projectPrincipal")
    @ApiModelProperty(value = "项目负责人")
    private String projectPrincipal; // 项目负责人

    @ApiParam(value = "项目创建时间", name = "projectCreateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "项目创建时间")
    private LocalDate projectCreateTime; // 项目创建时间

    @ApiParam(value = "项目启用日期", name = "projectStartTime")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "项目启用日期")
    private LocalDate projectStartTime; // 项目启用日期

    @ApiParam(value = "项目结束日期", name = "projectEndTime")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "项目结束日期")
    private LocalDate projectEndTime; // 项目结束日期

    @ApiParam(value = "项目修改时间", name = "projectUpdateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "项目修改时间")
    private LocalDate projectUpdateTime; // 项目修改时间

    @ApiParam(value = "项目团队 (与team中的team_id关联）", name = "projectTeam")
    @ApiModelProperty(value = "项目团队 (与team中的team_id关联）")
    private String projectTeam; // 项目团队 (与team中的team_id关联）

    @ApiParam(value = "项目优先级 1低/2中/3高", name = "projectPriority")
    @ApiModelProperty(value = "项目优先级 1低/2中/3高")
    private int projectPriority; // 项目优先级 1低/2中/3高

    @ApiParam(value = "项目进度", name = "projectProgress")
    @Percentage
    @ApiModelProperty(value = "项目进度")
    private Double projectProgress; // 项目进度

    @ApiParam(value = "项目预算", name = "projectBudget")
    @ApiModelProperty(value = "项目预算")
    private String projectBudget; // 项目预算

    @ApiParam(value = "项目所属部门", name = "projectDepartment")
    @ApiModelProperty(value = "项目所属部门")
    private String projectDepartment; // 项目所属部门

    @ApiParam(value = "逻辑删除 默认值为0/存在   1/不存在", name = "projectDelFlag")
    @ApiModelProperty(value = "逻辑删除 默认值为0/存在   1/不存在")
    private String projectDelFlag = "0"; // 逻辑删除 默认值为0/存在   1/不存在

}
