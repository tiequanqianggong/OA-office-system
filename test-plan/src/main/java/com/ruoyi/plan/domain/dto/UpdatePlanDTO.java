package com.ruoyi.plan.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author liupian
 * @description 修改测试计划DTO
 * @date 2023/9/22 08:59:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("修改测试计划DTO")
public class UpdatePlanDTO {

    /**
     * 测试计划
     */
    @NotNull(message = "测试计划id 不能为空")
    private Long testPlanId;
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
     * 修改用户id数组
     */
    @ApiModelProperty("原用户id数组")
    private Long[] userIds;


}
