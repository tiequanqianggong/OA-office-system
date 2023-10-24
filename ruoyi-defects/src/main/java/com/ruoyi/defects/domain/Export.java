package com.ruoyi.defects.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
//导出实体
public class Export {


    @Excel(name = "缺陷id")
    private Long defectId;


    @Excel(name = "用例编号")
    private Long caseId;


    @Excel(name = "所属模块")
    private String moduleName;


    @Excel(name = "所属项目")
    private String projectName;

    @Excel(name = "缺陷摘要")
    private String summary;


    @Excel(name = "缺陷详细描述")
    private String description;


    @Excel(name = "严重程度")
    private String severity;


    @Excel(name = "状态")
    private String status;



    @Excel(name = "发现者")
    private String detectedBy;


    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发现日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date detectedDate;


    @Excel(name = "指派给")
    private String teamName;


    @Excel(name = "优先级")
    private String priority;

    @Excel(name = "备注")
    private String remark;


    public Export() {
    }

    public Export(Long defectId, Long caseId, String moduleName, String projectName, String summary, String description, String severity, String status, String detectedBy, Date detectedDate, String teamName, String priority, String remark) {
        this.defectId = defectId;
        this.caseId = caseId;
        this.moduleName = moduleName;
        this.projectName = projectName;
        this.summary = summary;
        this.description = description;
        this.severity = severity;
        this.status = status;
        this.detectedBy = detectedBy;
        this.detectedDate = detectedDate;
        this.teamName = teamName;
        this.priority = priority;
        this.remark = remark;
    }

    /**
     * 获取
     * @return defectId
     */
    public Long getDefectId() {
        return defectId;
    }

    /**
     * 设置
     * @param defectId
     */
    public void setDefectId(Long defectId) {
        this.defectId = defectId;
    }

    /**
     * 获取
     * @return caseId
     */
    public Long getCaseId() {
        return caseId;
    }

    /**
     * 设置
     * @param caseId
     */
    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    /**
     * 获取
     * @return moduleName
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * 设置
     * @param moduleName
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * 获取
     * @return projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * 设置
     * @param projectName
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * 获取
     * @return summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 设置
     * @param summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * 获取
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取
     * @return severity
     */
    public String getSeverity() {
        return severity;
    }

    /**
     * 设置
     * @param severity
     */
    public void setSeverity(String severity) {
        this.severity = severity;
    }

    /**
     * 获取
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取
     * @return detectedBy
     */
    public String getDetectedBy() {
        return detectedBy;
    }

    /**
     * 设置
     * @param detectedBy
     */
    public void setDetectedBy(String detectedBy) {
        this.detectedBy = detectedBy;
    }

    /**
     * 获取
     * @return detectedDate
     */
    public Date getDetectedDate() {
        return detectedDate;
    }

    /**
     * 设置
     * @param detectedDate
     */
    public void setDetectedDate(Date detectedDate) {
        this.detectedDate = detectedDate;
    }

    /**
     * 获取
     * @return teamName
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * 设置
     * @param teamName
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     * 获取
     * @return priority
     */
    public String getPriority() {
        return priority;
    }

    /**
     * 设置
     * @param priority
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * 获取
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String toString() {
        return "Export{defectId = " + defectId + ", caseId = " + caseId + ", moduleName = " + moduleName + ", projectName = " + projectName + ", summary = " + summary + ", description = " + description + ", severity = " + severity + ", status = " + status + ", detectedBy = " + detectedBy + ", detectedDate = " + detectedDate + ", teamName = " + teamName + ", priority = " + priority + ", remark = " + remark + "}";
    }
}
