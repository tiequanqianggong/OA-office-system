package com.ruoyi.plan.domain.pojo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 测试计划与用户关联对象 test_user_plan
 *
 * @author ruoyi
 * @date 2023-09-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 测试计划id */
    @Excel(name = "测试计划id")
    private Long testPlanId;


}
