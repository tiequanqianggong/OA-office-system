package com.ruoyi.controller.testCase;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.domain.TestCaseStep;
import com.ruoyi.service.ITestCaseStepService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用例步骤Controller
 * 
 * @author wzk_jcb
 * @date 2023-09-12
 */
@RestController
@RequestMapping("/step/step")
public class TestCaseStepController extends BaseController
{
    @Autowired
    private ITestCaseStepService testCaseStepService;

    /**
     * 查询用例步骤列表
     */
    @PreAuthorize("@ss.hasPermi('step:step:list')")
    @GetMapping("/list")
    public TableDataInfo list(TestCaseStep testCaseStep)
    {
        startPage();
        List<TestCaseStep> list = testCaseStepService.selectTestCaseStepList(testCaseStep);
        return getDataTable(list);
    }

    /**
     * 导出用例步骤列表
     */
    @PreAuthorize("@ss.hasPermi('step:step:export')")
    @Log(title = "用例步骤", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TestCaseStep testCaseStep)
    {
        List<TestCaseStep> list = testCaseStepService.selectTestCaseStepList(testCaseStep);
        ExcelUtil<TestCaseStep> util = new ExcelUtil<TestCaseStep>(TestCaseStep.class);
        util.exportExcel(response, list, "用例步骤数据");
    }

    /**
     * 获取用例步骤详细信息
     */
    @PreAuthorize("@ss.hasPermi('step:step:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(testCaseStepService.selectTestCaseStepById(id));
    }

    /**
     * 新增用例步骤
     */
    @PreAuthorize("@ss.hasPermi('step:step:add')")
    @Log(title = "用例步骤", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TestCaseStep testCaseStep)
    {
        return toAjax(testCaseStepService.insertTestCaseStep(testCaseStep));
    }

    /**
     * 修改用例步骤
     */
    @PreAuthorize("@ss.hasPermi('step:step:edit')")
    @Log(title = "用例步骤", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TestCaseStep testCaseStep)
    {
        return toAjax(testCaseStepService.updateTestCaseStep(testCaseStep));
    }

    /**
     * 删除用例步骤
     */
    @PreAuthorize("@ss.hasPermi('step:step:remove')")
    @Log(title = "用例步骤", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(testCaseStepService.deleteTestCaseStepByIds(ids));
    }
}
