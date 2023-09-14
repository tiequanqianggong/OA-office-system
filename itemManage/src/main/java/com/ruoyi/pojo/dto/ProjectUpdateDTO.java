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
    private Long projectId;//项目id(必须)
    private String projectName;//项目名称
    private String projectType;//项目所属类别
    private int projectStatus;//项目状态 0已挂起/1已关闭/2未完成/3进行中/4未开始
    private String projectMaterial;//项目材料（图片url路径）
    private String projectDescription;//项目描述
    private String projectPrincipal;//项目负责人
    private String projectTeam;//项目团队 (与team中的team_id关联）
    private int projectPriority;//项目优先级 1低/2中/3高

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate projectStartTime;//项目启用日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate projectEndTime;//项目结束日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDate projectUpdateTime;//项目修改时间
    @Percentage
    private Double projectProgress;//项目进度
    private String projectBudget;//项目预算
    private String projectDepartment;//项目所属部门
    private String projectDelFlag="0";//逻辑删除 默认值为0/存在   1/不存在
}
