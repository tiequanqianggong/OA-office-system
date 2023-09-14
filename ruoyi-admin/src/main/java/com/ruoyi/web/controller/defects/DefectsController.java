package com.ruoyi.web.controller.defects;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
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
import com.ruoyi.defects.domain.Defects;
import com.ruoyi.defects.service.IDefectsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 缺陷管理Controller
 * 
 * @author hh
 * @date 2023-09-11
 */
@RestController
@RequestMapping("/defects")
@Api(tags = "缺陷管理相关接口")
public class DefectsController extends BaseController
{
    @Autowired
    private IDefectsService defectsService;

    /**
     * 查询缺陷管理列表
     */
    @PreAuthorize("@ss.hasPermi('defects:defects:list')")
    @GetMapping("/list")
    @ApiOperation("查询缺陷管理列表")
    public TableDataInfo list(Defects defects)
    {
        startPage();
        List<Defects> list = defectsService.selectDefectsList(defects);
        return getDataTable(list);
    }

    /**
     * 导出缺陷管理列表
     */
    @PreAuthorize("@ss.hasPermi('defects:defects:export')")
    @Log(title = "缺陷管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出缺陷管理列表")
    public void export(HttpServletResponse response, Defects defects)
    {
        List<Defects> list = defectsService.selectDefectsList(defects);
        ExcelUtil<Defects> util = new ExcelUtil<Defects>(Defects.class);
        util.exportExcel(response, list, "缺陷管理数据");
    }

    /**
     * 获取缺陷管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('defects:defects:query')")
    @GetMapping(value = "/{defectId}")
    @ApiOperation("获取缺陷管理详细信息")
    public AjaxResult getInfo(@PathVariable("defectId") Long defectId)
    {
        return success(defectsService.selectDefectsByDefectId(defectId));
    }

    /**
     * 新增缺陷管理
     */
    @PreAuthorize("@ss.hasPermi('defects:defects:add')")
    @Log(title = "缺陷管理", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增缺陷管理")
    public AjaxResult add(@RequestBody Defects defects)
    {
        return toAjax(defectsService.insertDefects(defects));
    }

    /**
     * 修改缺陷管理
     */
    @PreAuthorize("@ss.hasPermi('defects:defects:edit')")
    @Log(title = "缺陷管理", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改缺陷管理")
    public AjaxResult edit(@RequestBody Defects defects)
    {
        return toAjax(defectsService.updateDefects(defects));
    }

    /**
     * 删除缺陷管理
     */
    @PreAuthorize("@ss.hasPermi('defects:defects:remove')")
    @Log(title = "缺陷管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{defectIds}")
    @ApiOperation("删除缺陷管理")
    public AjaxResult remove(@PathVariable Long[] defectIds)
    {
        return toAjax(defectsService.deleteDefectsByDefectIds(defectIds));
    }
}
