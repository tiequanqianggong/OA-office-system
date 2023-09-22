package com.ruoyi.plan.mapper;

import com.ruoyi.plan.domain.pojo.UserPlan;
import com.ruoyi.plan.domain.vo.UserVO;

import java.util.List;

/**
 * 测试计划与用户关联Mapper接口
 *
 * @author lxz
 * @date 2023-09-07
 */
public interface UserPlanMapper {

    /**
     * 根据计划id查找对应的user集合
     * @param planId 测试计划id
     * @return user集合
     */

    List<UserVO> findUserListById(Long planId);

    /**
     * 根据计划id删除与用户的关联
     * @param planId 计划id
     * @return 结果
     */
    int deleteUserPlanByPlanId(Long planId);

    /**
     * 批量删除测试计划id
     * @param planIds 测试计划数组
     * @return 结果
     */
    int deleteUserPlanByPlanIds(Long[] planIds);

    /**
     * 查询用户是否存在
     * @param userId 用户id
     * @return 结果
     */
    boolean selectCountUser(Long userId);

    /**
     * 添加
     * @param userPlan 用户测试计划id
     * @return 结果
     */
    int insertUserPlan(UserPlan userPlan);
}
