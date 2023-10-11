package com.ruoyi.mapper;

import java.util.List;
import com.ruoyi.domain.TestCaseStep;

/**
 * 用例步骤Mapper接口
 * 
 * @author wzk_jcb
 * @date 2023-09-12
 */
public interface TestCaseStepMapper 
{
    /**
     * 查询用例步骤
     * 
     * @param id 用例步骤主键
     * @return 用例步骤
     */
    public TestCaseStep selectTestCaseStepById(Long id);

    /**
     * 查询用例步骤列表
     * 
     * @param testCaseStep 用例步骤
     * @return 用例步骤集合
     */
    public List<TestCaseStep> selectTestCaseStepList(TestCaseStep testCaseStep);

    /**
     * 新增用例步骤
     * 
     * @param testCaseStep 用例步骤
     * @return 结果
     */
    public int insertTestCaseStep(TestCaseStep testCaseStep);

    /**
     * 修改用例步骤
     * 
     * @param testCaseStep 用例步骤
     * @return 结果
     */
    public int updateTestCaseStep(TestCaseStep testCaseStep);

    /**
     * 删除用例步骤
     * 
     * @param id 用例步骤主键
     * @return 结果
     */
    public int deleteTestCaseStepById(Long id);

    /**
     * 批量删除用例步骤
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTestCaseStepByIds(Long[] ids);

    /**
     * 批量删除用例步骤 by TestCaseId
     *
     * @param ids 需要删除的数据TestCaseId键集合
     * @return 结果
     */
    public int deleteTestCaseStepByCaseIds(Long[] ids);
}
