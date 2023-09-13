package com.ruoyi.plan.mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 测试计划与测试用例关联Mapper接口
 *
 * @author ruoyi
 * @date 2023-09-07
 */
public interface PlanCaseMapper
{
    /**
     * 通过计划ID查询测试用例数量
     *
     * @param planId 测试计划id
     * @return 结果
     */
     int countPlanCaseById(Long planId);

    /**
     * 通过计划id删除中间中的数据
     * @param planId
     * @return
     */
     int deletePlanCaseByPlanId(Long planId);
    /**
     * 通过计划id批量删除中间中的数据
     * @param planIds
     * @return
     */
    int deletePlanCaseByPlanIds(Long[] planIds);



}
