package com.ruoyi.plan.mapper;

import com.ruoyi.plan.domain.pojo.PlanProject;
import com.ruoyi.plan.domain.vo.PlanProjectVO;

/**
 * @author liupian
 * @description 测试计划项目
 * @date 2023/9/15 18:08:18
 */
public interface PlanProjectMapper {
    /**
     * 根据测试计划id查找项目名称
     * @author liupian
     * @date 2023/9/15 18:15:15
     * @param planId 测试计划
     * @return 项目名
     */
    PlanProjectVO selectProjectNameByPlanId(Long planId);

    /**
     * 查询id是否存在
     * @param projectId
     * @return
     */
    boolean selectCountProject(Long projectId);
    /**
     * 添加
     * @param planProject 添加的数据
     * @return 结果
     */
    int  insertPlanProject(PlanProject planProject);
    /**
     * 修改
     * @param planProject 修改的数据
     * @return 结果
     */
    int  updatePlanProject(PlanProject planProject);

    int deletePlanProject(Long[] planIds);

}
