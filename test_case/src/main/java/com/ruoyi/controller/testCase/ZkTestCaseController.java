package com.ruoyi.controller.testCase;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.domain.TestCase;
import com.ruoyi.domain.TestCaseStep;
import com.ruoyi.service.ITestCaseService;
import com.ruoyi.service.ITestCaseStepService;
import com.ruoyi.utils.UUID;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Date: 2023.09.12.16:31
 * @Author: 子康
 * @Description: 测试用例模块 扩展接口
 */
@RestController
@Api(tags = "测试用例模块接口")
@RequestMapping("zk")
public class ZkTestCaseController extends BaseController {
    @Autowired
    private ITestCaseService testCaseService;
    @Autowired
    private ITestCaseStepService testCaseStepService;

    /**
     * 根据ID查询详情
     * @return 用例详情
     * */
    @GetMapping("getTestCaseById")
    @ApiOperation("根据ID查询用例详情-用于：点击用例标题")
    public AjaxResult getTestCaseById(@ApiParam("用例ID") Integer id)
    {
        HashMap<String, Object> map = new HashMap<>();

        TestCase testCase = testCaseService.selectTestCaseById(Long.valueOf(id));
        TestCaseStep testCaseStep = new TestCaseStep();
        testCaseStep.setTestCaseId(Long.valueOf(id));
        List<TestCaseStep> testCaseSteps = testCaseStepService.selectTestCaseStepList(testCaseStep);
        testCase.setTestCaseSteps(testCaseSteps);

        map.put("testCase", testCase);
        return success(map);
    }

    /**
     * 新增用例: 用例信息 + 用例步骤信息
     */
    @PostMapping("/addTestCase")
    @ApiOperation("新增用例: 用例信息 + 用例步骤信息集合")
    public AjaxResult addTestCase(@RequestBody @ApiParam("用例信息") TestCase testCase)
    {
        //生成 测试用例ID
        Long caseId_uuid = (long) testCaseService.selectMaxId() + 1;
        if (caseId_uuid == null)
            caseId_uuid = 1L;

        System.err.println("maxID " + caseId_uuid);

//        testCase.setExecuteName(getUsername());// 创建人
        testCase.setCreateDate(new Date());  // 创建时间
        testCase.setId(caseId_uuid);
        //添加测试用例
        int result1 = testCaseService.insertTestCase(testCase);

        //添加测试用例步骤
        int result2 = 0;
        List<TestCaseStep> testCaseSteps = testCase.getTestCaseSteps();
        if(testCaseSteps.size()>0)
        {
            for (TestCaseStep testCaseStep : testCaseSteps) {
                testCaseStep.setTestCaseId(caseId_uuid);
                testCaseStepService.insertTestCaseStep(testCaseStep);
            }
        }

        return toAjax(result1+result2);
    }

    /**
     * 删除用例By case ID
     */
    /**
     * 删除用例
     */
    @DeleteMapping("/{ids}")
    public AjaxResult delTestCase(@PathVariable Long[] ids)
    {

        int resule1 = testCaseService.deleteTestCaseByIds(ids);
        int result2 = testCaseStepService.deleteTestCaseStepByCaseIds(ids);


        return toAjax(resule1+result2);
    }

    /**
     * 根据ID查询用例步骤
     */
    @GetMapping("getTestCaseStep")
    @ApiOperation("根据ID查询用例步骤-用于：操作-执行")
    public AjaxResult getTestCaseStepById(@ApiParam("用例ID") Integer id)
    {
        HashMap<String, Object> map = new HashMap<>();

        TestCaseStep testCaseStep = new TestCaseStep();
        testCaseStep.setTestCaseId(Long.valueOf(id));
        List<TestCaseStep> testCaseSteps = testCaseStepService.selectTestCaseStepList(testCaseStep);
        map.put("testCaseSteps", testCaseSteps);

        return success(map);
    }

    /**
     * 根据 用例步骤id 删除
     */
    @GetMapping("deleteCaseStepById")
    @ApiOperation("根据用例步骤id 删除用例步骤")
    public AjaxResult deleteCaseStepById(@ApiParam("步骤ID")Integer stepId)
    {
        int result = testCaseStepService.deleteTestCaseStepById(Long.valueOf(stepId));

        if(result<=0)
        {
            return error("用例步骤删除失败,步骤不存在");
        }

        return success("用例步骤删除成功");
    }

    /**
     * 根据用例步骤id 修改用例步骤
     */
    @PostMapping("updateCaseStepById")
    @ApiOperation("根据'步骤id' 修改用例步骤-用于：操作-执行")
    public AjaxResult updateCaseStepById(@ApiParam("用例步骤")TestCaseStep testCaseStep)
    {
        //用户调用执行方法：修改执行时间、执行人昵称
//        String loginUserName = getUsername();

        TestCase testCase = new TestCase();
        testCase.setId(testCaseStep.getTestCaseId());
//        testCase.setExecuteName(loginUserName);
        testCase.setExecuteDate(new Date());
        int result1 = testCaseService.updateTestCase(testCase);
        if(result1<=0)
        {
            return error("用例信息修改失败");
        }
        int result2 = testCaseStepService.updateTestCaseStep(testCaseStep);
        if(result1<=0)
        {
            return error("用例步骤修改失败");
        }
        return success("用例步骤修改成功");
    }

    /**
     * 添加用例步骤
     */
    @PostMapping("addCaseStepById")
    @ApiOperation("根据'用例id' 修改用例步骤")
    public AjaxResult addCaseStepById(@ApiParam("用例步骤")TestCaseStep testCaseStep)
    {
        int i = testCaseStepService.insertTestCaseStep(testCaseStep);
        if(i<=0)
        {
            return error("用例步骤添加失败");
        }

        return success("用例步骤添加成功");
    }

    /**
     * 查询用例列表根据优先级排序
     * positive sequence: 正序
     * Reverse order    ：逆序
     */
    @GetMapping("/pslist")
    @ApiOperation("查询用例列表根据优先级排序-正序")
    public TableDataInfo pslist(TestCase testCase)
    {
        startPage();
        List<TestCase> list = testCaseService.selectTestCaseList(testCase);
        list.sort(new Comparator<TestCase>() {
            @Override
            public int compare(TestCase o1, TestCase o2) {

                return (int) (o1.getPriority()-o2.getPriority());
            }
        });
        return getDataTable(list);
    }

    @GetMapping("/rolist")
    @ApiOperation("查询用例列表根据优先级排序-逆序")
    public TableDataInfo rolist(TestCase testCase)
    {
        startPage();
        List<TestCase> list = testCaseService.selectTestCaseList(testCase);
        list.sort(new Comparator<TestCase>() {
            @Override
            public int compare(TestCase o1, TestCase o2) {

                return -(int) (o1.getPriority()-o2.getPriority());
            }
        });
        return getDataTable(list);
    }

//    /**
//     * 根据项目ID查询用例 【x 重复了】
//     */
//    @GetMapping("/listByItemId")
//    @ApiOperation("根据项目ID查询用例")
//    public TableDataInfo listByItemId(TestCase testCase)
//    {
//        startPage();
//        List<TestCase> list = testCaseService.selectTestCaseList(testCase);
//        return getDataTable(list);
//    }


}
