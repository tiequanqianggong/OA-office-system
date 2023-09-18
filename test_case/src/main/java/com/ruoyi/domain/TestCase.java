package com.ruoyi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * 用例对象 test_case
 * 
 * @author wzk_jcb
 * @date 2023-09-12
 */
@ApiModel(value = "TestCase 测试用例类", description = "测试用例类")
public class TestCase extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 测试用例ID */
    @ApiModelProperty(value = "用例ID", example = "8848")
    private Long id;

    /** 所属产品ID（关联项目ID） */
    @ApiModelProperty(value = "所属产品ID（所属产品ID）", example = "8848")
    @Excel(name = "所属产品ID", readConverterExp = "关=联项目ID")
    private Long itemId;

    /** 用例标题 */
    @ApiModelProperty(value = "用例标题", example = "测试用例")
    @Excel(name = "用例标题")
    private String title;

    /** 优先级 */
    @ApiModelProperty(value = "优先级", example = "1")
    @Excel(name = "优先级")
    private Long priority;

    /** 创建人 */
    @ApiModelProperty(value = "创建人名称", example = "子康")
    @Excel(name = "创建人")
    private String createName;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间", example = "2023-09-18")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /** 执行人 */
    @ApiModelProperty(value = "执行人名称", example = "程北")
    @Excel(name = "执行人")
    private String executeName;

    /** 执行时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "执行时间", example = "2023-09-18")
    @Excel(name = "执行时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date executeDate;

    /** 前置条件 */
    @ApiModelProperty(value = "前置条件", example = "登录系统")
    @Excel(name = "前置条件")
    private String preconditions;

    /** 关键词 */
    @ApiModelProperty(value = "关键词", example = "测试")
    @Excel(name = "关键词")
    private String keywords;

    /** 备注 */
    @ApiModelProperty(value = "备注", example = "这是一条没有的备注")
    @Excel(name = "备注")
    private String remarks;

    /** 用例步骤集合 */
    @ApiModelProperty(value = "用例步骤", example = "用例步骤1")
    @Excel(name = "用例步骤")
    private List<TestCaseStep> testCaseSteps;

    public List<TestCaseStep> getTestCaseSteps() {
        return testCaseSteps;
    }

    public void setTestCaseSteps(List<TestCaseStep> testCaseSteps) {
        this.testCaseSteps = testCaseSteps;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setItemId(Long itemId) 
    {
        this.itemId = itemId;
    }

    public Long getItemId() 
    {
        return itemId;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setPriority(Long priority) 
    {
        this.priority = priority;
    }

    public Long getPriority() 
    {
        return priority;
    }
    public void setCreateName(String createName) 
    {
        this.createName = createName;
    }

    public String getCreateName() 
    {
        return createName;
    }
    public void setCreateDate(Date createDate) 
    {
        this.createDate = createDate;
    }

    public Date getCreateDate() 
    {
        return createDate;
    }
    public void setExecuteName(String executeName) 
    {
        this.executeName = executeName;
    }

    public String getExecuteName() 
    {
        return executeName;
    }
    public void setExecuteDate(Date executeDate) 
    {
        this.executeDate = executeDate;
    }

    public Date getExecuteDate() 
    {
        return executeDate;
    }
    public void setPreconditions(String preconditions) 
    {
        this.preconditions = preconditions;
    }

    public String getPreconditions() 
    {
        return preconditions;
    }
    public void setKeywords(String keywords) 
    {
        this.keywords = keywords;
    }

    public String getKeywords() 
    {
        return keywords;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }

    @Override
    public String toString() {
        return "TestCase{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", title='" + title + '\'' +
                ", priority=" + priority +
                ", createName='" + createName + '\'' +
                ", createDate=" + createDate +
                ", executeName='" + executeName + '\'' +
                ", executeDate=" + executeDate +
                ", preconditions='" + preconditions + '\'' +
                ", keywords='" + keywords + '\'' +
                ", remarks='" + remarks + '\'' +
                ", testCaseSteps=" + testCaseSteps +
                '}';
    }
}
