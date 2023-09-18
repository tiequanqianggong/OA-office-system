package com.ruoyi.service;

import java.util.List;
import com.ruoyi.domain.TestCase;

/**
 * 用例Service接口
 * 
 * @author wzk_jcb
 * @date 2023-09-12
 */
public interface ITestCaseService 
{
    /**
     * 查询用例
     * 
     * @param id 用例主键
     * @return 用例
     */
    public TestCase selectTestCaseById(Long id);

    /**
     * 查询用例列表
     * 
     * @param testCase 用例
     * @return 用例集合
     */
    public List<TestCase> selectTestCaseList(TestCase testCase);

    /**
     * 查询最大id
     */
    public int selectMaxId();

    /**
     * 新增用例
     * 
     * @param testCase 用例
     * @return 结果
     */
    public int insertTestCase(TestCase testCase);

    /**
     * 修改用例
     * 
     * @param testCase 用例
     * @return 结果
     */
    public int updateTestCase(TestCase testCase);

    /**
     * 批量删除用例
     * 
     * @param ids 需要删除的用例主键集合
     * @return 结果
     */
    public int deleteTestCaseByIds(Long[] ids);

    /**
     * 删除用例信息
     * 
     * @param id 用例主键
     * @return 结果
     */
    public int deleteTestCaseById(Long id);

}
