package com.ruoyi.controller.testCase;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.domain.TestCase;
import com.ruoyi.service.ITestCaseService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用例Controller
 * 
 * @author wzk_jcb
 * @date 2023-09-12
 */
@RestController
@RequestMapping("/case/case")
public class TestCaseController extends BaseController
{
    @Autowired
    private ITestCaseService testCaseService;

    @GetMapping("test")
    public String test()
    {
        return "测试成功";
    }

    /**
     * 查询用例列表
     */
    @PreAuthorize("@ss.hasPermi('case:case:list')")
    @GetMapping("/list")
    @ApiOperation("废弃 ：请调用/zk/list")
    public TableDataInfo list(TestCase testCase)
    {
        startPage();
        List<TestCase> list = testCaseService.selectTestCaseList(testCase);
        return getDataTable(list);
    }

    /**
     * 导出用例列表
     */
    @PreAuthorize("@ss.hasPermi('case:case:export')")
    @Log(title = "用例", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TestCase testCase)
    {
        List<TestCase> list = testCaseService.selectTestCaseList(testCase);
        ExcelUtil<TestCase> util = new ExcelUtil<TestCase>(TestCase.class);
        util.exportExcel(response, list, "用例数据");
    }

    /**
     * 获取用例详细信息
     */
    @PreAuthorize("@ss.hasPermi('case:case:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(testCaseService.selectTestCaseById(id));
    }

    /**
     * 新增用例
     */
    @PreAuthorize("@ss.hasPermi('case:case:add')")
    @Log(title = "用例", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("废弃 ：请调用/zk/addTestCase")
    public AjaxResult add(@RequestBody TestCase testCase)
    {
        return toAjax(testCaseService.insertTestCase(testCase));
    }

    /**
     * 修改用例
     */
    @PreAuthorize("@ss.hasPermi('case:case:edit')")
    @Log(title = "用例", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TestCase testCase)
    {
        return toAjax(testCaseService.updateTestCase(testCase));
    }

    /**
     * 删除用例
     */
    @PreAuthorize("@ss.hasPermi('case:case:remove')")
    @Log(title = "用例", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(testCaseService.deleteTestCaseByIds(ids));
    }





}
