package com.ruoyi.plan.domain.pojo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 测试计划与用户关联对象 test_user_plan
 *
 * @author ruoyi
 * @date 2023-09-07
 */
public class UserPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 测试计划id */
    @Excel(name = "测试计划id")
    private Long testPlanId;

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setTestPlanId(Long testPlanId)
    {
        this.testPlanId = testPlanId;
    }

    public Long getTestPlanId()
    {
        return testPlanId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("testPlanId", getTestPlanId())
            .toString();
    }
}
