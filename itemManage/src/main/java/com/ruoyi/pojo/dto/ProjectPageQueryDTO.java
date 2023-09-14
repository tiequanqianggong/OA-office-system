package com.ruoyi.pojo.dto;

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
public  class
ProjectPageQueryDTO {
    @ApiParam(value = "项目id", name = "projectId", required = false)
    private Long   projectId;//项目id(可选）
    @ApiParam(value = "项目状态：0已挂起/1已关闭/2未完成/3进行中/4未开始", name = "projectStatus", required = false)
    private int    projectStatus;//项目状态 0已挂起/1已关闭/2未完成/3进行中/4未开始(可选）
    @ApiParam(value = "项目名称", name = "projectName", required = false)
    private String projectName;//项目名称(可选）
    @ApiParam(value = "项目所属类别", name = "projectType", required = false)
    private String projectType;//项目所属类别(可选）
    @ApiParam(value = "项目所属类别", name = "projectType", required = false)
    private String projectPrincipal;//项目负责人(可选）
    @ApiParam(value = "项目所属部门", name = "projectDepartment", required = false)
    private String projectDepartment;//项目所属部门(可选）
}
