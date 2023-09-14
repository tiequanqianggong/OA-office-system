package com.ruoyi.plan.domain.pojo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author lxz
 */
public class PlanCase extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 测试计划id */
    @Excel(name = "测试计划id")
    private Long testPlanId;

    /** 测试用例id */
    @Excel(name = "测试用例id")
    private Long testCaseId;

    public void setTestPlanId(Long testPlanId)
    {
        this.testPlanId = testPlanId;
    }

    public Long getTestPlanId()
    {
        return testPlanId;
    }
    public void setTestCaseId(Long testCaseId)
    {
        this.testCaseId = testCaseId;
    }

    public Long getTestCaseId()
    {
        return testCaseId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("testPlanId", getTestPlanId())
            .append("testCaseId", getTestCaseId())
            .toString();
    }
}