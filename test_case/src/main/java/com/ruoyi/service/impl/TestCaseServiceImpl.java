package com.ruoyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.TestCaseMapper;
import com.ruoyi.domain.TestCase;
import com.ruoyi.service.ITestCaseService;

/**
 * 用例Service业务层处理
 * 
 * @author wzk_jcb
 * @date 2023-09-12
 */
@Service
public class TestCaseServiceImpl implements ITestCaseService 
{
    @Autowired
    private TestCaseMapper testCaseMapper;

    /**
     * 查询用例
     * 
     * @param id 用例主键
     * @return 用例
     */
    @Override
    public TestCase selectTestCaseById(Long id)
    {
        return testCaseMapper.selectTestCaseById(id);
    }

    /**
     * 查询用例列表
     * 
     * @param testCase 用例
     * @return 用例
     */
    @Override
    public List<TestCase> selectTestCaseList(TestCase testCase)
    {
        return testCaseMapper.selectTestCaseList(testCase);
    }

    /**
     * 查询最大id
     */
    public int selectMaxId(){
        return testCaseMapper.selectMaxId();
    }

    /**
     * 新增用例
     * 
     * @param testCase 用例
     * @return 结果
     */
    @Override
    public int insertTestCase(TestCase testCase)
    {
        return testCaseMapper.insertTestCase(testCase);
    }

    /**
     * 修改用例
     * 
     * @param testCase 用例
     * @return 结果
     */
    @Override
    public int updateTestCase(TestCase testCase)
    {
        return testCaseMapper.updateTestCase(testCase);
    }

    /**
     * 批量删除用例
     * 
     * @param ids 需要删除的用例主键
     * @return 结果
     */
    @Override
    public int deleteTestCaseByIds(Long[] ids)
    {
        return testCaseMapper.deleteTestCaseByIds(ids);
    }

    /**
     * 删除用例信息
     * 
     * @param id 用例主键
     * @return 结果
     */
    @Override
    public int deleteTestCaseById(Long id)
    {
        return testCaseMapper.deleteTestCaseById(id);
    }

    /**
     * 根据ID判断项目模块是否存在
     */
    public int Model_isEmpty(Long id){
        return testCaseMapper.Model_isEmpty(id);
    }

}
