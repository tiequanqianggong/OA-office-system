package com.ruoyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.TestCaseStepMapper;
import com.ruoyi.domain.TestCaseStep;
import com.ruoyi.service.ITestCaseStepService;

/**
 * 用例步骤Service业务层处理
 * 
 * @author wzk_jcb
 * @date 2023-09-12
 */
@Service
public class TestCaseStepServiceImpl implements ITestCaseStepService 
{
    @Autowired
    private TestCaseStepMapper testCaseStepMapper;

    /**
     * 查询用例步骤
     * 
     * @param id 用例步骤主键
     * @return 用例步骤
     */
    @Override
    public TestCaseStep selectTestCaseStepById(Long id)
    {
        return testCaseStepMapper.selectTestCaseStepById(id);
    }

    /**
     * 查询用例步骤列表
     * 
     * @param testCaseStep 用例步骤
     * @return 用例步骤
     */
    @Override
    public List<TestCaseStep> selectTestCaseStepList(TestCaseStep testCaseStep)
    {
        return testCaseStepMapper.selectTestCaseStepList(testCaseStep);
    }

    /**
     * 新增用例步骤
     * 
     * @param testCaseStep 用例步骤
     * @return 结果
     */
    @Override
    public int insertTestCaseStep(TestCaseStep testCaseStep)
    {
        return testCaseStepMapper.insertTestCaseStep(testCaseStep);
    }

    /**
     * 修改用例步骤
     * 
     * @param testCaseStep 用例步骤
     * @return 结果
     */
    @Override
    public int updateTestCaseStep(TestCaseStep testCaseStep)
    {
        return testCaseStepMapper.updateTestCaseStep(testCaseStep);
    }

    /**
     * 批量删除用例步骤
     * 
     * @param ids 需要删除的用例步骤主键
     * @return 结果
     */
    @Override
    public int deleteTestCaseStepByIds(Long[] ids)
    {
        return testCaseStepMapper.deleteTestCaseStepByIds(ids);
    }

    /**
     * 删除用例步骤信息
     * 
     * @param id 用例步骤主键
     * @return 结果
     */
    @Override
    public int deleteTestCaseStepById(Long id)
    {
        return testCaseStepMapper.deleteTestCaseStepById(id);
    }

    @Override
    public int deleteTestCaseStepByCaseIds(Long[] ids) {
        return testCaseStepMapper.deleteTestCaseStepByCaseIds(ids);
    }
}
