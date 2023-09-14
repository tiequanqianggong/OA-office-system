package com.ruoyi.plan.domain.pojo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *测试计划实体类
 * @author lxz
 */
@ApiModel(description = "测试计划实体类")
public class Plan extends BaseEntity{


    private static final long serialVersionUID = 1L;

    /**
     *测试计划id
     * */
    @ApiModelProperty(value = "测试计划id")
    private Long testPlanId;

    /**
     * 测试计划名称
     */
    @ApiModelProperty("测试计划名称")
    @Excel(name = "测试计划名称")
    private String testPlanName;

    /**
     * 测试计划优先级
     */
    @ApiModelProperty("测试计划优先级")
    @Excel(name = "测试计划优先级")
    private Long priority;

    /**
     * 状态(0: 未开始 1: 运行中)
     */
    @ApiModelProperty("状态(0: 未开始 1: 运行中)")
    @Excel(name = "状态(0: 未开始 1: 运行中)")
    private Integer state;

    /**
     *  是否关注(0是1否)
     */
    @ApiModelProperty("是否关注(0是1否)")
    @Excel(name = "是否关注(0是1否)")
    private Integer isAttention;

    /**
     * 完成率
     */
    @ApiModelProperty("完成率")
    @Excel(name = "完成率")
    private Long passRate;

    /**
     * 关联的项目
     */
    @ApiModelProperty("关联的项目")
    @Excel(name = "关联的项目")
    private Long itemId;

    /**
     * 截止时间
     */
    @ApiModelProperty("截止时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "截止时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    public Long getTestPlanId() {
        return testPlanId;
    }

    public void setTestPlanId(Long testPlanId) {
        this.testPlanId = testPlanId;
    }

    public String getTestPlanName() {
        return testPlanName;
    }

    public void setTestPlanName(String testPlanName) {
        this.testPlanName = testPlanName;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getIsAttention() {
        return isAttention;
    }

    public void setIsAttention(Integer isAttention) {
        this.isAttention = isAttention;
    }

    public Long getPassRate() {
        return passRate;
    }

    public void setPassRate(Long passRate) {
        this.passRate = passRate;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "testPlanId=" + testPlanId +
                ", testPlanName='" + testPlanName + '\'' +
                ", priority=" + priority +
                ", state=" + state +
                ", isAttention=" + isAttention +
                ", passRate=" + passRate +
                ", itemId=" + itemId +
                ", endTime=" + endTime +
                '}';
    }
}


