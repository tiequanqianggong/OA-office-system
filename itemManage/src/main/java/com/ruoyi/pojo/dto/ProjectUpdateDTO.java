package com.ruoyi.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.Annotation.Percentage;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class ProjectUpdateDTO {
    @ApiParam(value = "项目id", name = "projectId", required = true)
    private Long projectId;//项目id(必须)
    @ApiParam(value = "项目名称", name = "projectName")
    private String projectName;//项目名称
    @ApiParam(value = "项目所属类别", name = "projectType")
    private String projectType;//项目所属类别
    @ApiParam(value = "项目状态：0已挂起/1已关闭/2未完成/3进行中/4未开始", name = "projectStatus")
    private int projectStatus;//项目状态 0已挂起/1已关闭/2未完成/3进行中/4未开始
    @ApiParam(value = "项目材料（图片url路径）", name = "projectMaterial")
    private String projectMaterial;//项目材料（图片url路径）
    @ApiParam(value = "项目描述", name = "projectDescription")
    private String projectDescription;//项目描述
    @ApiParam(value = "项目负责人", name = "projectPrincipal")
    private String projectPrincipal;//项目负责人
    @ApiParam(value = "项目团队 (与team中的team_id关联）", name = "projectTeam")
    private String projectTeam;//项目团队 (与team中的team_id关联）
    @ApiParam(value = "项目优先级 1低/2中/3高", name = "projectPriority")
    private int projectPriority;//项目优先级 1低/2中/3高

    @ApiParam(value = "项目启用日期", name = "projectStartTime")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate projectStartTime;//项目启用日期
    @ApiParam(value = "项目结束日期", name = "projectEndTime")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate projectEndTime;//项目结束日期
    @ApiParam(value = "项目修改时间", name = "projectUpdateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDate projectUpdateTime;//项目修改时间
    @Percentage
    @ApiParam(value = "项目进度", name = "projectProgress")
    private Double projectProgress;//项目进度
    @ApiParam(value = "项目预算", name = "projectBudget")
    private String projectBudget;//项目预算
    @ApiParam(value = "项目所属部门", name = "projectDepartment")
    private String projectDepartment;//项目所属部门
    @ApiParam(value = "逻辑删除  默认值为0/存在   1/不存在", name = "projectDelFlag")
    private String projectDelFlag="0";//逻辑删除 默认值为0/存在   1/不存在
}
