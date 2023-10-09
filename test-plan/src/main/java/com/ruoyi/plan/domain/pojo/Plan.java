package com.ruoyi.plan.domain.pojo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 *测试计划实体类
 * @author lxz
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ApiModel("测试计划实体类")
public class Plan extends BaseEntity{


    private static final long serialVersionUID = 1L;

    /**
     * 测试计划
     */
    @ApiModelProperty("项目计划id")
    private Long testPlanId;

    /**
     * 项目计划类型
     */
    @ApiModelProperty("项目计划类型")
    @Excel(name = "项目计划类型")
    private String type;

    /**
     * 测试计划内容
     */
    @ApiModelProperty("测试计划内容")
    @Excel(name = "测试计划内容")
    private String content;

    /**
     *  状态(0: 未开始 1: 执行中2：完成)
     */
    @ApiModelProperty("状态(0: 未开始 1: 执行中2：完成)")
    @Excel(name = "状态(0: 未开始 1: 执行中2：完成)")
    private String state;

    /**
     * 产出内容
     */
    @ApiModelProperty("产出内容")
    @Excel(name = "产出内容")
    private String output;

    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 截止时间
     */
    @ApiModelProperty("截止时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "截止时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

}


