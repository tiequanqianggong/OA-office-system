package com.ruoyi.plan.service;

import com.ruoyi.plan.domain.pojo.Plan;
import com.ruoyi.plan.domain.vo.PlanListVO;

import java.util.List;

/**
 * @author lxz
 */
public interface PlanService {

    /**
     * 查询测试计划列表
     *
     * @return 测试计划
     */
    List<PlanListVO> getPlanList();

    /**
     * 通过测试计划ID删除测试计划
     *
     * @param planId 测试计划ID
     * @return 结果
     */
    int deletePlanById(Long planId);

    /**
     * 通过测试计划ID批量删除测试计划
     *
     * @param planIds 测试计划Id数组
     * @return 结果
     */
    int deletePlanByIds(Long[] planIds);


}
