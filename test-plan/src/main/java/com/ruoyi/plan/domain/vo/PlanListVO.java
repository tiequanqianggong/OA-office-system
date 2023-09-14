package com.ruoyi.plan.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * Plan集合Vo
 * @author lxz
 */
@Data
public class PlanListVO {

    /**
     *测试计划id
     * */
    private Long testPlanId;

    /**
     * 测试计划名称
     */
    private String testPlanName;

    /**
     *测试计划优先级
     */
    private Long priority;

    /**
     * 状态(0: 未开始 1: 运行中)
     */
    private Integer state;
    /**
     *  是否关注(0是1否)
     */
    private Integer isAttention;
    /**
     * 关联的项目
     */
    private Long itemId;

    /**
     * 关联的项目名称
     */
    private String itemName;

    /**
     * 截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    /**
     * 测试用例数量
     */
    private Long caseCount;


    private List<UserVO> userList;


    public PlanListVO() {
    }


}
