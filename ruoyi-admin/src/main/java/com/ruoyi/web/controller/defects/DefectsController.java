package com.ruoyi.web.controller.defects;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.defects.domain.CaseIdAndProjectName;
import com.ruoyi.defects.domain.Defects;
import com.ruoyi.defects.domain.Module;
import com.ruoyi.defects.domain.Team;
import com.ruoyi.defects.service.IDefectsService;
import com.ruoyi.defects.service.ITeamService;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
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
    @Autowired
    private ITeamService teamService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private TokenService tokenService;

    /**
     *动态查询缺陷信息
     */
    @PreAuthorize("@ss.hasPermi('defects:defects:list')")
    @GetMapping("/list")
    @ApiOperation("动态查询缺陷信息")
    public TableDataInfo list(Defects defects)
    {
        startPage();
        List<Defects> list = defectsService.selectDefectsList(defects);
        return getDataTable(list);
    }

    /**
     *查询所有员工姓名
     */
    @PreAuthorize("@ss.hasPermi('defects:defects:list')")
    @GetMapping("/teamList")
    @ApiOperation("查询员工姓名")
    public  TableDataInfo teamList(String teamName)
    {
        startPage();
        List<Team> list = teamService.selectTeamList(teamName);
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



//    导入功能实现
    @Log(title = "缺陷管理", businessType = BusinessType.IMPORT)
    @PostMapping("/importDefectsData")
    @ApiOperation("导入缺陷管理列表")
    @ResponseBody
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        //1
//        ExcelUtil<Defects> util = new ExcelUtil<Defects>(SysUser.class);
//        List<Defects> defectsList = util.importExcel(file.getInputStream());
//        String operName =  getUsername();
//        String message = userService.importUser(defectsList, updateSupport, operName);
//        return AjaxResult.success(message);
//        2
        ExcelUtil<Defects> util = new ExcelUtil<Defects>(Defects.class);
        List<Defects> defectsList = util.importExcel(file.getInputStream());
        for (int i = 0; i < defectsList.size(); i++) {
            Defects defects=defectsList.get(i);
            defectsService.insertDefects(defects);
        }
//        return AjaxResult.success(message);

        return AjaxResult.success();

    }
    //下载模板
    @GetMapping("/importDefectsTemplate")
    @ApiOperation("导入下载模板")
    public AjaxResult importTemplate()
    {
        ExcelUtil<Defects> util = new ExcelUtil<Defects>(Defects.class);
        return util.importTemplateExcel("缺陷数据");
    }
    /**
     * 根据ID查询
     */
    @PreAuthorize("@ss.hasPermi('defects:defects:query')")
    @GetMapping(value = "/{defectId}")
    @ApiOperation("根据id查询缺陷信息")
    public AjaxResult getInfo(@PathVariable("defectId") Long defectId)
    {
        Defects defects = defectsService.selectDefectsByDefectId(defectId);
        if (StringUtils.isNull(defects)){
            return error("此缺陷不存在!");
        }
        return success(defects);
    }
    /**
     * 根据用例ID查询用例id和项目名称
     */
    @PreAuthorize("@ss.hasPermi('defects:defects:query')")
    @GetMapping(value = "/caseId/{caseId}")
    @ApiOperation("根据用例ID查询用例id和项目名称")
    public AjaxResult getCaseIdAndProjectName(@PathVariable("caseId") Long caseId)
    {
        CaseIdAndProjectName caseIdAndProjectName = teamService.selectCaseIdAndProjectName(caseId);
        return success(caseIdAndProjectName);
    }


    /**
     * 根据用例ID查询模块名称
     */
    @PreAuthorize("@ss.hasPermi('defects:defects:query')")
    @GetMapping(value = "/moduleName/{caseId}")
    @ApiOperation("根据用例ID查询用例id和项目名称")
    public AjaxResult getModuleName(@PathVariable("caseId") Long caseId)
    {
             Module module = teamService.selectModuleName(caseId);
        return success(module);
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
        Long caseId_uuid = 1L;
        try {
            long maxid = defectsService.selectMaxId();
            //生成 测试用例ID
            caseId_uuid= maxid+1;
        }catch (Exception e)
        {

        }




        System.err.println("maxID " + caseId_uuid);

        defects.setDefectId(caseId_uuid);
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
     * 批量删除缺陷管理
     */
    @PreAuthorize("@ss.hasPermi('defects:defects:remove')")
    @Log(title = "缺陷管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{defectIds}")
    @ApiOperation("批量删除缺陷管理")
    public AjaxResult remove(@PathVariable Long[] defectIds)
    {
        return toAjax(defectsService.deleteDefectsByDefectIds(defectIds));
    }

    /**
     * 根据id删除缺陷管理
     */
    @PreAuthorize("@ss.hasPermi('defects:defects:remove')")
    @Log(title = "缺陷管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/test/{defectId}")
    @ApiOperation("根据id删除缺陷管理-带缓存")
    public AjaxResult remove(@PathVariable Long defectId)
    {
        return toAjax(defectsService.deleteDefectsByDefectId(defectId));
    }




}