package com.ruoyi.defects.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 缺陷管理对象 defects
 * 
 * @author hh
 * @date 2023-09-11
 */
public class Defects extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 缺陷id */
    private Long defectId;

    /** 用例编号 */
    @Excel(name = "用例编号")
    private Long caseId;

    /** 缺陷摘要 */
    @Excel(name = "缺陷摘要")
    private String summary;

    /** 缺陷详细描述(用例id、操作步骤、预期结果、实际结果) */
    @Excel(name = "缺陷详细描述(用例id、操作步骤、预期结果、实际结果)")
    private String description;

    /** 严重程度(高、中、低优先级) */
    @Excel(name = "严重程度(高、中、低优先级)")
    private String severity;

    /** 状态(待解决、待测试、已关闭) */
    @Excel(name = "状态(待解决、待测试、已关闭)")
    private String status;

    /** 发现者 */
    @Excel(name = "发现者")
    private String detectedBy;

    /** 发现日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发现日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date detectedDate;

    /** 分配给某开发人员 */
    @Excel(name = "分配给某开发人员")
    private String assignedTo;

    /** 解决日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "解决日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date resolutionDate;

    public void setDefectId(Long defectId) 
    {
        this.defectId = defectId;
    }

    public Long getDefectId() 
    {
        return defectId;
    }
    public void setCaseId(Long caseId) 
    {
        this.caseId = caseId;
    }

    public Long getCaseId() 
    {
        return caseId;
    }
    public void setSummary(String summary) 
    {
        this.summary = summary;
    }

    public String getSummary() 
    {
        return summary;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setSeverity(String severity) 
    {
        this.severity = severity;
    }

    public String getSeverity() 
    {
        return severity;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setDetectedBy(String detectedBy) 
    {
        this.detectedBy = detectedBy;
    }

    public String getDetectedBy() 
    {
        return detectedBy;
    }
    public void setDetectedDate(Date detectedDate) 
    {
        this.detectedDate = detectedDate;
    }

    public Date getDetectedDate() 
    {
        return detectedDate;
    }
    public void setAssignedTo(String assignedTo) 
    {
        this.assignedTo = assignedTo;
    }

    public String getAssignedTo() 
    {
        return assignedTo;
    }
    public void setResolutionDate(Date resolutionDate) 
    {
        this.resolutionDate = resolutionDate;
    }

    public Date getResolutionDate() 
    {
        return resolutionDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("defectId", getDefectId())
            .append("caseId", getCaseId())
            .append("summary", getSummary())
            .append("description", getDescription())
            .append("severity", getSeverity())
            .append("status", getStatus())
            .append("detectedBy", getDetectedBy())
            .append("detectedDate", getDetectedDate())
            .append("assignedTo", getAssignedTo())
            .append("resolutionDate", getResolutionDate())
            .append("remark", getRemark())
            .toString();
    }
}
