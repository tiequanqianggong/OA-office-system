package com.ruoyi.plan.domain.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liupian
 * @description 测试计划与项目中间表
 * @date 2023/9/18 14:59:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanProject {
    private static final long serialVersionUID = 1L;

    /**
     * 测试计划id
     */
    private Long testPlanId;

    /**
     * 项目id
     */
    private Long projectId;
}
