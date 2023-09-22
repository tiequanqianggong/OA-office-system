package com.ruoyi.plan.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liupian
 * @description 项目VO
 * @date 2023/9/22 17:52:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanProjectVO {

    /**
     * 项目id
     */
    private Long projectId;
    /**
     * 项目名称
     */
    private String projectName;
}
