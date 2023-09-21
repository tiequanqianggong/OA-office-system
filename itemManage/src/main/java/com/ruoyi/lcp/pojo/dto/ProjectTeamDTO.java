package com.ruoyi.lcp.pojo.dto;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTeamDTO {
    private Long teamId;//项目成员id
    private String teamName;//项目成员姓名
    private int teamStatus;//项目成员状态 1在岗/2离职/3休假
    private String teamRole;//项目成员角色
    private Integer modelId; // 模块表id 分配的模块  项目管理/测试用例.....

}
