package com.ruoyi.plan.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.plan.domain.pojo.Plan;
import com.ruoyi.plan.domain.vo.PlanListVO;
import com.ruoyi.plan.service.PlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
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
    //    @PreAuthorize("@ss.hasPermi('test:plan:list')")
    @ApiOperation(value = "查询测试计划列表" )
    @Log(title = "测试计划管理", businessType = BusinessType.DELETE)
    @GetMapping("/list")
    public TableDataInfo getPlanList(){
        startPage();
        List<PlanListVO> list = planService.getPlanList();
        return getDataTable(list);

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
    //    @PreAuthorize("@ss.hasPermi('test:plan:remove')")
    @ApiOperation("删除测试计划")
    @Log(title = "测试计划管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{planIds}")
    @ApiImplicitParam(name = "planIds",value = "删除的Id数组")
    public AjaxResult remove(@PathVariable Long[] planIds)
    {
        return toAjax(planService.deletePlanByIds(planIds));
    }


}
