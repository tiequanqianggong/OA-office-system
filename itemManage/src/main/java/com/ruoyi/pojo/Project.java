package com.ruoyi.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.Annotation.Percentage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("project")
@Builder
@ApiModel("Project")
public class Project implements Serializable {
    @ApiParam(value = "项目表id", name = "Id" , required = false)
    private Long Id;//项目表id
    @ApiParam(value = "项目id", name = "projectId")
    private Long projectId;//项目id
    @ApiParam(value = "项目名称", name = "projectName")
    private String projectName;//项目名称
    @ApiParam(value = "项目所属类别", name = "projectType")
    private String projectType;//项目所属类别
    @ApiParam(value = "项目所属类别", name = "projectType")
    private int projectStatus;//项目状态 0已挂起/1已关闭/2未完成/3进行中/4未开始
    @ApiParam(value = "项目材料（图片url路径）", name = "projectMaterial")
    private String projectMaterial;//项目材料（图片url路径）
    @ApiParam(value = "项目描述", name = "projectDescription")
    private String projectDescription;//项目描述
    @ApiParam(value = "项目负责人", name = "projectPrincipal")
    private String projectPrincipal;//项目负责人
    @ApiParam(value = "项目创建时间", name = "projectCreateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDate projectCreateTime;//项目创建时间
    @ApiParam(value = "项目启用日期", name = "projectStartTime")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate projectStartTime;//项目启用日期
    @ApiParam(value = "项目结束日期", name = "projectEndTime")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate projectEndTime;//项目结束日期
    @ApiParam(value = "项目修改时间", name = "projectUpdateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDate projectUpdateTime;//项目修改时间
    @ApiParam(value = "项目团队 (与team中的team_id关联）", name = "projectTeam")
    private String projectTeam;//项目团队 (与team中的team_id关联）
    @ApiParam(value = "项目优先级 1低/2中/3高", name = "projectPriority")
    private int projectPriority;//项目优先级 1低/2中/3高
    @ApiParam(value = "项目进度", name = "projectProgress")
    @Percentage
    private Double projectProgress;//项目进度
    @ApiParam(value = "项目进度", name = "projectProgress")
    private String projectBudget;//项目预算
    @ApiParam(value = "项目所属部门", name = "projectDepartment")
    private String projectDepartment;//项目所属部门
    @ApiParam(value = "逻辑删除  默认值为0/存在   1/不存在", name = "projectDelFlag")
    private String projectDelFlag="0";//逻辑删除 默认值为0/存在   1/不存在
}
