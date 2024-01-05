package com.ruoyi.plan.service;

import com.ruoyi.plan.domain.dto.PlanDTO;
import com.ruoyi.plan.domain.dto.UpdatePlanDTO;
import com.ruoyi.plan.domain.pojo.Plan;
import com.ruoyi.plan.domain.vo.PlanListVO;
import com.ruoyi.plan.domain.vo.PlanRecentlyVO;
import com.ruoyi.plan.domain.vo.PlanVO;

import java.util.List;

/**
 * 测试计划接口
 * @author lxz
 */

public interface PlanService {

    /**
     * 查询测试计划
     * @author lxz
     * @param testPlanId 测试计划主键
     * @return 测试计划
     */
    PlanVO selectPlanByTestPlanId(Long testPlanId);

    /**
     * 查询测试计划列表
     * @author lxz
     * @return 测试计划集合
     */
    List<PlanListVO> getPlanList(Plan plan);
    /**
     * 模糊查询查询测试计划
     * @author lxz
     * @return 测试计划集合
     */
    List<PlanListVO> getPlanLikeList(Plan plan);
    /**
     * 查询最近完成的五条测试计划
     * @author lxz
     * @return 计划集合
     */
    List<PlanRecentlyVO> getPlanRecent();

    /**
     * 新增测试计划
     * @author lxz
     * @param planDTO 测试计划DTO
     * @return 结果
     */
    int insertPlan(PlanDTO planDTO);

    /**
     * 修改测试计划
     * @author lxz
     * @param updatePlanDTO 修改测试计划DTO
     * @return 结果
     */
    int updatePlan(UpdatePlanDTO updatePlanDTO);


    /**
     * 批量删除测试计划
     * @author lxz
     * @param testPlanIds 需要删除的测试计划主键集合
     * @return 结果
     */
    int deletePlanByTestPlanIds(Long[] testPlanIds);

    /**
     * 删除测试计划信息
     * @author lxz
     * @param testPlanId 测试计划主键
     * @return 结果
     */
    int deletePlanByTestPlanId(Long testPlanId);

    List<Plan> exportPlanList();
}
