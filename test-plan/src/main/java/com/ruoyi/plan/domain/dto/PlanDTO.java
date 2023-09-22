package com.ruoyi.plan.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * @author liupian
 * @description
 * @date 2023/9/15 17:46:30
 */
@Data
@ApiModel("添加测试计划DTO")
public class PlanDTO {


    /**
     * 项目计划类型
     */
    @ApiModelProperty("项目计划类型")
    private String type;

    /**
     * 测试计划内容
     */
    @ApiModelProperty("测试计划内容")
    private String content;

    /**
     *  状态(0: 未开始 1: 执行中2：完成)
     */
    @ApiModelProperty("状态(0: 未开始 1: 执行中2：完成)")
    private String state;

    /**
     * 产出内容
     */
    @ApiModelProperty("产出内容")
    private String output;

    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 截止时间
     */
    @ApiModelProperty("截止时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    /**
     * 关联的项目id
     */
    @ApiModelProperty("关联的项目id")
    private Long projectId;

    /**
     * 关联的用户id数组
     */
    @ApiModelProperty("关联的用户id数组")
    private Long[] userIds;
}
