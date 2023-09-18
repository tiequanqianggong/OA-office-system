package com.ruoyi.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用例步骤对象 test_case_step
 * 
 * @author wzk_jcb
 * @date 2023-09-12
 */
@ApiModel(value = "TestCaseStep 测试用例步骤类", description = "测试用例步骤类")
public class TestCaseStep extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用例测试步骤ID */
    @ApiModelProperty(value = "步骤ID", example = "8848")
    private Long id;

    /** 用例ID(test_case） */
    @ApiModelProperty(value = "测试用例ID", example = "8848")
    @Excel(name = "用例ID(test_case）")
    private Long testCaseId;

    /** 步骤名称 */
    @ApiModelProperty(value = "步骤名称", example = "步骤1")
    @Excel(name = "步骤名称")
    private String name;

    /** 预期结果 */
    @ApiModelProperty(value = "预期结果", example = "测试成功")
    @Excel(name = "预期结果")
    private String expectResult;

    /** 测试结果 */
    @ApiModelProperty(value = "测试结果", example = "测试失败")
    @Excel(name = "测试结果")
    private String testResult;

    /** 实际情况 */
    @ApiModelProperty(value = "实际情况", example = "不详")
    @Excel(name = "实际情况")
    private String actualSituation;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTestCaseId(Long testCaseId) 
    {
        this.testCaseId = testCaseId;
    }

    public Long getTestCaseId() 
    {
        return testCaseId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setExpectResult(String expectResult) 
    {
        this.expectResult = expectResult;
    }

    public String getExpectResult() 
    {
        return expectResult;
    }
    public void setTestResult(String testResult) 
    {
        this.testResult = testResult;
    }

    public String getTestResult() 
    {
        return testResult;
    }
    public void setActualSituation(String actualSituation) 
    {
        this.actualSituation = actualSituation;
    }

    public String getActualSituation() 
    {
        return actualSituation;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("testCaseId", getTestCaseId())
            .append("name", getName())
            .append("expectResult", getExpectResult())
            .append("testResult", getTestResult())
            .append("actualSituation", getActualSituation())
            .toString();
    }
}
