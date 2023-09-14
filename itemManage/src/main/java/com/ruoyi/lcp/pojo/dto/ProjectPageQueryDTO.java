package com.ruoyi.lcp.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public  class ProjectPageQueryDTO {
    private Long   projectId;//项目id(可选）
    private int    projectStatus;//项目状态 0已挂起/1已关闭/2未完成/3进行中/4未开始(可选）
    private String projectName;//项目名称(可选）
    private String projectType;//项目所属类别(可选）
    private String projectPrincipal;//项目负责人(可选）
    private String projectDepartment;//项目所属部门(可选）
}
