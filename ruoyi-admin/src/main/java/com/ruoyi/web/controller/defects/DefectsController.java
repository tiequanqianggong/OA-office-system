package com.ruoyi.web.controller.defects;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.defects.domain.Defects;
import com.ruoyi.defects.service.IDefectsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 缺陷管理Controller
 * 
 * @author hh
 * @date 2023-09-11
 */
@RestController
@RequestMapping("/defects")
@Api("缺陷管理")
public class DefectsController extends BaseController
{
    @Autowired
    private IDefectsService defectsService;



    /**
     * 查询缺陷管理列表
     */
    @PreAuthorize("@ss.hasPermi('defects:defects:list')")
    @GetMapping("/list")

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
    public AjaxResult getInfo(@PathVariable("defectId") Long defectId)
    {
        Defects defects = defectsService.selectDefectsByDefectId(defectId);
        if (StringUtils.isNull(defects) || defects.getStatus().equals("0")){
            return error("此缺陷不存在!");
        }
        return success(defects);
    }

    /**
     * 新增缺陷管理
     */
    @PreAuthorize("@ss.hasPermi('defects:defects:add')")
    @Log(title = "缺陷管理", businessType = BusinessType.INSERT)
    @PostMapping
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
    public AjaxResult remove(@PathVariable Long[] defectIds)
    {
        return toAjax(defectsService.deleteDefectsByDefectIds(defectIds));
    }

    @PreAuthorize("@ss.hasPermi('defects:defects:remove')")
    @Log(title = "缺陷管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/test/{defectId}")
    public AjaxResult remove(@PathVariable Long defectId)
    {
        return toAjax(defectsService.deleteDefectsByDefectId(defectId));
    }
}
