package com.ruoyi.plan.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author liupian
 * @description 最近完成的测试计划VO
 * @date 2023/9/18 13:21:56
 */
@Data
public class PlanRecentlyVO {
    /**
     * 测试计划
     */
    private Long testPlanId;

    /**
     * 测试计划内容
     */
    private String content;


    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

}
