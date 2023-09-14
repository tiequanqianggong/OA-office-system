package com.ruoyi.lcp.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
public class ProjectAddDTO {
    private Long projectId;//项目id
    private String projectName;//项目名称
    private String projectType;//项目所属类别
    private int projectStatus;//项目状态 0已挂起/1已关闭/2未完成/3进行中/4未开始
    private String projectMaterial;//项目材料（图片url路径）
    private String projectDescription;//项目描述
    private String projectPrincipal;//项目负责人
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate projectStartTime;//项目启用日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate projectEndTime;//项目结束日期
}
