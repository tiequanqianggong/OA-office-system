package com.ruoyi.plan.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.plan.domain.dto.PlanDTO;
import com.ruoyi.plan.domain.dto.UpdatePlanDTO;
import com.ruoyi.plan.domain.pojo.Plan;
import com.ruoyi.plan.domain.vo.PlanListVO;
import com.ruoyi.plan.service.PlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import java.util.List;


/**
 * 测试计划相关接口
 * @author lxz
 */
@RestController
@RequestMapping("/test/plan")
@Api(tags = "测试计划")
public class PlanController extends BaseController {

    @Resource
    private PlanService planService;

    /**
     * 查询测试计划列表
     * @author liupian
     * @date 2023/9/12 16:24:52
     */
    @PreAuthorize("@ss.hasPermi('test:plan:list')")
    @ApiOperation(value = "查询测试计划列表" )
    @GetMapping("/list")
    public TableDataInfo getPlanList(){
        startPage();
        List<PlanListVO> list = planService.getPlanList();
        return getDataTable(list);

    }
    /**
     * 查询测试计划
     * @author liupian
     * @date 2023/9/12 16:24:52
     */
    @ApiOperation(value = "查询测试计划" )
    @GetMapping("/{planId}")
    public AjaxResult getPlanOne(@PathVariable Long planId){
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.put("date",planService.selectPlanByTestPlanId(planId));
        return ajaxResult;
    }
    /**
     * 查询最近完成的五条测试计划
     * @author liupian
     * @date 2023/9/18 13:42:15
     * @return 五条测试计划
     */
    @ApiOperation(value = "查询最近完成的五条测试计划" )
    @GetMapping("/recent")
    public  TableDataInfo getPlanRecent(){
        return getDataTable(planService.getPlanRecent());
    }
    /**
     * 导出测试计划列表
     */
    @PreAuthorize("@ss.hasPermi('test:plan:export')")
    @Log(title = "测试计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出测试计划列表")
    public void export(HttpServletResponse response)
    {
        List<PlanListVO> list = planService.getPlanList();
        ExcelUtil<PlanListVO> util = new ExcelUtil<PlanListVO>(PlanListVO.class);
        util.exportExcel(response, list, "测试计划数据");
    }
    /**
     * @description 删除测试计划
     * @author liupian
     * @date 2023/9/12 16:24:52
     */
    @PreAuthorize("@ss.hasPermi('test:plan:remove')")
    @ApiOperation("删除测试计划")
    @Log(title = "测试计划管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{planIds}")
    @ApiImplicitParam(name = "planIds",value = "删除的Id数组")
    public AjaxResult remove(@Validated @PathVariable Long[] planIds)
    {
        return toAjax(planService.deletePlanByTestPlanIds(planIds));
    }
    /**
     * 添加测试计划
     * @author liupian
     * @date 2023/9/22 17:10:36
     * @param planDTO 添加的数据
     * @return TODO
     */
    @PreAuthorize("@ss.hasPermi('test:plan:add')")
    @ApiImplicitParam(name = "planDTO",value = "添加的数据")
    @PostMapping()
    @ApiOperation("添加测试计划")
    @Log(title = "测试计划管理", businessType = BusinessType.INSERT)
    public AjaxResult addPlan(@RequestBody   PlanDTO planDTO){
        return toAjax(planService.insertPlan(planDTO));
    }

    @PreAuthorize("@ss.hasPermi('test:plan:edit')")
    @ApiImplicitParam(name = "updatePlanDTO",value = "修改的数据")
    @PutMapping()
    @ApiOperation("修改测试计划")
    @Log(title = "测试计划管理", businessType = BusinessType.INSERT)
    public AjaxResult updatePlan(@Validated @RequestBody UpdatePlanDTO updatePlanDTO){
        return toAjax(planService.updatePlan(updatePlanDTO));
    }
}
