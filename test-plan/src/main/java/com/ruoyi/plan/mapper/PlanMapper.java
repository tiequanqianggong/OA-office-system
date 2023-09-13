package com.ruoyi.plan.mapper;

import com.ruoyi.plan.domain.pojo.Plan;

import java.util.List;

/**
 * 测试计划对象 test_plan
 *
 * @author lxz
 * @date 2023-09-07
 */
public interface  PlanMapper {


    /**
     * 查询测试计划列表
     *
     * @return 测试计划集合
     */
     List<Plan> selectPlanList();

    /**
     * 根据测试计划id查询关联的项目
     * @param planId  测试计划id
     * @return 项目名
     */
     String selectItemName(long planId);
    /**
     * 根据测试计划删除
     * @param planId  测试计划id
     * @return 结果
     */
    int deleteByPlanId(Long planId);

    int deleteByPlanIds(Long[] planIds);
}
