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
     * 查询测试计划
     *
     * @param testPlanId 测试计划主键
     * @return 测试计划
     */
    Plan selectPlanByTestPlanId(Long testPlanId);

    /**
     *  查询测试计划列表
     * @author liupian
     * @date 2023/9/15 17:32:36
     * @return  测试计划集合
     */

    List<Plan> selectPlanList();
    /**
     * 查询最近完成的5条最近完成测试计划
     * @author liupian
     * @date 2023/9/18 13:35:45
     * @return 最近完成测试计划集合
     */
    List<Plan> selectPlanRecently();

    /**
     * 新增测试计划(单个所有字段)
     * @author liupian
     * @date 2023/9/15 17:32:36
     * @param plan 测试计划
     * @return 结果
     */
     int insertPlan(Plan plan);
    /**
     * 修改测试计划
     * @author liupian
     * @date 2023/9/15 17:32:36
     * @param plan 测试计划
     * @return 结果
     */
    int updateTestPlan(Plan plan);
    /**
     * 修改测试计划(修改所有字段)
     * @author liupian
     * @date 2023/9/15 17:32:36
     * @param plan 测试计划
     * @return 结果
     */
     int updatePlan(Plan plan);

    /**
     * 批量删除测试计划
     * @author liupian
     * @date 2023/9/15 17:32:36
     * @param testPlanIds 需要删除的测试计划主键集合
     * @return 结果
     */
     int deletePlanByTestPlanIds(Long[] testPlanIds);

    /**
     * 删除测试计划信息
     * @author liupian
     * @date 2023/9/15 17:32:36
     * @param testPlanId 测试计划主键
     * @return 结果
     */
     int deletePlanByTestPlanId(Long testPlanId);

    /**
     * 彻底删除逻辑删除中的数据
     * @author liupian
     * @date 2023/9/15 17:37:44
     * @return 结果
     */
    int deleteComplete();
    /**
     * 模糊查询
     * @author liupian
     * @date 2023/9/25 10:35:44
     * @return 结果
     */
    List<Plan> searchTestPlans(Plan plan);


}
