package com.ruoyi.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.Annotation.Percentage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectVO implements Serializable {
    private Long projectId;//项目id
    private String projectName;//项目名称
    private String projectType;//项目所属类别
    private int projectStatus;//项目状态 0已挂起/1已关闭/2未完成/3进行中/4未开始
    private String projectMaterial;//项目材料（图片url路径）
    private String projectDescription;//项目描述
    private String projectPrincipal;//项目负责人
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDateTime projectStartTime;//项目启用日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDateTime projectEndTime;//项目结束日期
    @Percentage
    private Double projectProgress;//项目进度
    private int projectPriority;//项目优先级 1低/2中/3高

}