package com.ruoyi.plan.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 返回详细的测试计划
 * @author liupian
 * @description
 * @date 2023/9/15 17:44:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanVO {
    /**
     * 测试计划
     */
    private Long testPlanId;

    /**
     * 项目计划类型
     */
    private String type;

    /**
     * 测试计划内容
     */
    private String content;

    /**
     *  状态(0: 未开始 1: 执行中2：完成)
     */
    private String state;

    /**
     * 产出内容
     */
    private String output;

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

    /**
     * 关联的用户
     */
    private List<UserVO> userList;

    /**
     * 项目名
     */
    private String projectName;

}
