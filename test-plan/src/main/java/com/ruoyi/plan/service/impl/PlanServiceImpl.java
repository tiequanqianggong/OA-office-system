package com.ruoyi.plan.service.impl;

import com.ruoyi.plan.domain.pojo.Plan;
import com.ruoyi.plan.domain.vo.PlanListVO;
import com.ruoyi.plan.mapper.PlanCaseMapper;
import com.ruoyi.plan.mapper.PlanMapper;
import com.ruoyi.plan.service.PlanService;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.plan.mapper.UserPlanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.ruoyi.common.core.page.TableSupport.PAGE_NUM;

/**
 * @author lxz
 */
@Service
public class PlanServiceImpl implements PlanService {

    private static final Logger log = LoggerFactory.getLogger(PlanServiceImpl.class);
    @Resource
    private RedisCache redisCache;
    @Resource
    private PlanMapper planMapper;
    @Resource
    private PlanCaseMapper planCaseMapper;
    @Resource
    private UserPlanMapper userPlanMapper;


    /**
     * @description 查询测试计划列表
     * @author liupian
     * @date 2023/9/12 15:29:11
     * @return 测试计划
     */
    @Override
    public List<PlanListVO> getPlanList() {
        List<Plan> planList = planMapper.selectPlanList();
        List<PlanListVO> list = planList.stream()
            .map(plan -> {
                PlanListVO planVo = new PlanListVO();
                BeanUtils.copyBeanProp(planVo,plan);
                planVo.setCaseCount((long) planCaseMapper.countPlanCaseById(plan.getTestPlanId()));
                planVo.setUserList(userPlanMapper.findUserListById(plan.getTestPlanId()));
                planVo.setItemName(planMapper.selectItemName(plan.getItemId()));
                return planVo;
            }).collect(Collectors.toList());

        return list;
    }


    /**
     * @description 通过测试计划ID删除测试计划
     * @author liupian
     * @date 2023/9/12 15:27:59
     * @param planId 测试计划Id
     * @return 结果
     */
    @Transactional
    @Override
    public int deletePlanById(Long planId)
    {
        // 删除测试计划与测试用例关联
        planCaseMapper.deletePlanCaseByPlanId(planId);
        // 删除测试计划与用户表
        userPlanMapper.deleteUserPlanByPlanId(planId);
        return planMapper.deleteByPlanId(planId);
    }

    /**
     * @description 通过测试计划Id批量删除测试计划
     * @author liupian
     * @date 2023/9/12 16:05:21
     * @param planIds 测试计划Id数组
     * @return 结果
     */
    @Transactional
    @Override
    public int deletePlanByIds(Long[] planIds) {
        // 删除测试计划与测试用例关联
        planCaseMapper.deletePlanCaseByPlanIds(planIds);
        // 删除测试计划与用户表
        userPlanMapper.deleteUserPlanByPlanIds(planIds);
        return planMapper.deleteByPlanIds(planIds);
    }

    
}
