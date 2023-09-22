package com.ruoyi.plan.service.impl;

import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.plan.domain.dto.PlanDTO;
import com.ruoyi.plan.domain.dto.UpdatePlanDTO;
import com.ruoyi.plan.domain.pojo.Plan;
import com.ruoyi.plan.domain.pojo.PlanProject;
import com.ruoyi.plan.domain.pojo.UserPlan;
import com.ruoyi.plan.domain.vo.PlanListVO;
import com.ruoyi.plan.domain.vo.PlanRecentlyVO;
import com.ruoyi.plan.domain.vo.PlanVO;
import com.ruoyi.plan.exception.PlanIdNotException;
import com.ruoyi.plan.exception.PlanProjectNotException;
import com.ruoyi.plan.exception.PlanUserNotException;
import com.ruoyi.plan.mapper.PlanMapper;
import com.ruoyi.plan.mapper.PlanProjectMapper;
import com.ruoyi.plan.service.PlanService;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.plan.mapper.UserPlanMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private UserPlanMapper userPlanMapper;
    @Resource
    private PlanProjectMapper planProjectMapper;

    /**
     * 查询测试计划
     * @author lxz
     * @param testPlanId 测试计划主键
     * @return 测试计划
     */
    @Override
    public PlanVO selectPlanByTestPlanId(Long testPlanId) {
        //根据id查项目
        Plan plan = planMapper.selectPlanByTestPlanId(testPlanId);
        PlanVO planVo = new PlanVO();
        BeanUtils.copyBeanProp(planVo, plan);
        //查找关联项目名
        planVo.setProjectName(planProjectMapper.selectProjectNameByPlanId(planVo.getTestPlanId()));
        //查找关联的用户
        planVo.setUserList(userPlanMapper.findUserListById(planVo.getTestPlanId()));
        return planVo;
    }

    /**
     * 查询测试计划列表
     * @author lxz
     * @return 测试计划集合
     */
    @Override
    public List<PlanListVO> getPlanList() {
        List<Plan> planList = planMapper.selectPlanList();
        return planList.stream()
        .map(plan -> {
            PlanListVO planVo = new PlanListVO();
            BeanUtils.copyBeanProp( planVo, plan);
            //查询计划人员并封装
            planVo.setUserList(userPlanMapper.findUserListById(planVo.getTestPlanId()));
            return planVo;
        }).collect(Collectors.toList());
    }

    /**
     * 查询最近完成的五条测试计划
     * @return 计划集合
     */
    public List<PlanRecentlyVO> getPlanRecent(){
        List<Plan> planList = planMapper.selectPlanRecently();
        return planList.stream()
                .map(plan -> {
                    PlanRecentlyVO planVO = new PlanRecentlyVO();
                    BeanUtils.copyBeanProp( planVO, plan);
                    return planVO;
                }).collect(Collectors.toList());

    }
    /**
     * 添加测试计划
     * @author lxz
     * @param planDTO 修改测试计划DTO
     * @return 结果
     */
    @Transactional
    @Override
    public int insertPlan(PlanDTO planDTO) {
        //添加到测试计划
        Plan plan = Plan.builder().build();
        BeanUtils.copyBeanProp(plan,planDTO);
        int res = planMapper.insertPlan(plan);
        log.info("往数据库添加数据: -> {} 条",res);

        //查询项目是否存在

        if (!planProjectMapper.selectCountProject(planDTO.getProjectId()))
        {
            throw new PlanProjectNotException();
        }
        //添加测试计划与项目关联

        PlanProject planProject = PlanProject.builder()
                        .testPlanId(plan.getTestPlanId())
                        .projectId(planDTO.getProjectId())
                        .build();
        planProjectMapper.insertPlanProject(planProject);

        //查询用户是否存在
        Long[] userIds = planDTO.getUserIds();
        for (Long userId : userIds) {
           if (!userPlanMapper.selectCountUser(userId)){
               throw new PlanUserNotException();
           }
            UserPlan userPlan = UserPlan.builder()
                    .testPlanId(plan.getTestPlanId())
                    .userId(userId).build();
           userPlanMapper.insertUserPlan(userPlan);
        }


        return res;
    }

    /**
     * 修改测试计划
     * @author lxz
     * @param updatePlanDTO 修改测试计划DTO
     * @return 结果
     */
    @Override
    @Transactional
    public int updatePlan(UpdatePlanDTO updatePlanDTO) {
        //判断planId是否为空
        Optional<Long> planIdOptional = Optional.ofNullable(updatePlanDTO.getTestPlanId());
        if (!planIdOptional.isPresent()) {
           throw new PlanIdNotException();
        }

        //添加到测试计划
        Plan plan = Plan.builder().build();
        BeanUtils.copyBeanProp(plan,updatePlanDTO);
        int res = planMapper.updatePlan(plan);
        //当项目id不为空时执行修改
        Optional<Long> projectIdOptional = Optional.ofNullable(updatePlanDTO.getProjectId());
        if (projectIdOptional.isPresent()) {
            this.updatePlanProject(updatePlanDTO);
        }
        //当用户id不为空时执行修改
        Optional<Long[]> useIdOptional = Optional.ofNullable(updatePlanDTO.getUserIds());
        if (useIdOptional.isPresent()) {
            this.updatePlanUser(updatePlanDTO);
        }
        return res;
    }
    /**
     * 批量删除测试计划
     * @author lxz
     * @param testPlanIds 需要删除的测试计划主键集合
     * @return 结果
     */
    @Override
    public int deletePlanByTestPlanIds(Long[] testPlanIds) {
        return planMapper.deletePlanByTestPlanIds(testPlanIds);
    }

    /**
     * 删除测试计划信息
     * @author lxz
     * @param testPlanId 测试计划主键
     * @return 结果
     */
    @Override
    public int deletePlanByTestPlanId(Long testPlanId) {
        return planMapper.deletePlanByTestPlanId(testPlanId);
    }



    /**
     * @description 删除已被逻辑删除的数据
     * @author liupian
     * @date 2023/9/15 18:37:54
     */
    public void delComplete(){
        int i = planMapper.deleteComplete();
        log.info("删除了测试计划表中已被逻辑删除的数据: -> {} 条",i);

    }

    public int updatePlanProject(UpdatePlanDTO updatePlanDTO){

        //查询项目是否存在
        if (!planProjectMapper.selectCountProject(updatePlanDTO.getProjectId()))
        {
            throw new PlanProjectNotException();
        }
        //修改测试计划与项目关联

        PlanProject planProject = PlanProject.builder()
                .testPlanId(updatePlanDTO.getTestPlanId())
                .projectId(updatePlanDTO.getProjectId())
                .build();
        int res = planProjectMapper.updatePlanProject(planProject);

        return res;
    }
    public void updatePlanUser(UpdatePlanDTO updatePlanDTO){

        //查询用户是否存在
        Long[] userIds = updatePlanDTO.getUserIds();
        for (Long userId : userIds) {
            if (!userPlanMapper.selectCountUser(userId)){
                throw new PlanUserNotException();
            }
            UserPlan  userPlan = UserPlan.builder()
                    .testPlanId(updatePlanDTO.getTestPlanId())
                    .userId(userId)
                    .build();
            //先删除所有的testPlanId的数据
            userPlanMapper.deleteUserPlanByPlanId(userPlan.getTestPlanId());
            userPlanMapper.insertUserPlan(userPlan);
        }

    }
}
