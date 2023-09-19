package com.ruoyi.lcp.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.lcp.pojo.Project;
import com.ruoyi.lcp.pojo.dto.ProjectUpdateDTO;
import com.ruoyi.lcp.pojo.dto.ProjectPageQueryDTO;
import com.ruoyi.lcp.pojo.vo.ProjectVO;
import com.ruoyi.lcp.service.IProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

import static com.ruoyi.common.utils.PageUtils.startPage;
import static com.ruoyi.lcp.constant.ProjectConstants.ProjectPending;

@RestController
@Slf4j
@RequestMapping("/project")
@Api(tags = "项目管理")
public class ProjectController extends BaseController {

    @Autowired
    private IProjectService iProjectService;


    /**
     * 项目管理分页查询
     * @param projectPageQueryDTO
     * @return
     */
    @ApiOperation("项目管理分页查询")
    @PreAuthorize("@ss.hasPermi('system:project:query')")
    @Log(title = "项目管理", businessType = BusinessType.OTHER)
    @PostMapping("/query")
    @ApiImplicitParam(name = "QueryProject",value = "分页查询/条件查询")
    public TableDataInfo QueryProject(@RequestBody(required = false)ProjectPageQueryDTO projectPageQueryDTO){
        List<Project> projectList =iProjectService.QueryProject(projectPageQueryDTO);
        List<ProjectVO> projectVOList = new ArrayList<>();
        for (Project project:projectList){
            if (project!=null) {
                ProjectVO projectVO = new ProjectVO();
                //类的属性拷贝，
                BeanUtils.copyProperties(project, projectVO);
                projectVOList.add(projectVO);
            }
        }

        //手动封装若依框架中提供的TableDataInfo
        TableDataInfo tableDataInfo=new TableDataInfo();
        tableDataInfo.setCode(HttpStatus.SUCCESS);
        tableDataInfo.setMsg("查询成功");
        tableDataInfo.setRows(projectVOList);
        tableDataInfo.setTotal(iProjectService.countAllProject().size());

        //将VO传给前端
        return tableDataInfo;
    }



    /**
     * 项目管理删除  需要提供项目id
     * @param projectId
     * @return
     */
    //TODO 项目管理删除
    @ApiOperation("项目管理删除")
    @PreAuthorize("@ss.hasPermi('system:project:delete')")
    @Log(title = "项目管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/{projectId}")
    @ApiImplicitParam(name = "DeleteProject",value = "根据projectId删除")
    public AjaxResult DeleteProject(@PathVariable Long projectId){
        //通过提供id条件 分页查询
        ProjectPageQueryDTO projectPageQueryDTO=ProjectPageQueryDTO.builder()
                .projectId(projectId)
                .build();
        List<Project> projectList =iProjectService.QueryProject(projectPageQueryDTO);
        //得到id查出的第一条数据
        Project project=projectList.get(0);
        //判断项目状态
        if (project.getProjectStatus()==ProjectPending){
            return warn("项目已挂起，不允许删除");
        }
        return toAjax(iProjectService.deleteProject(projectId));
    }




    /**
     * 项目管理修改
     * @param projectUpdateDTO
     * @return
     */
    //TODO 项目管理修改
    @ApiOperation("项目管理修改")
    @Log(title = "项目管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('system:project:update')")
    @PutMapping("/update")
    @ApiImplicitParam(name = "UpdateProject",value = "根据projectId修改")
    public AjaxResult UpdateProject(@Validated @RequestBody ProjectUpdateDTO projectUpdateDTO){
        return toAjax(iProjectService.UpdateProject(projectUpdateDTO));
    }


    /**
     * 项目添加
     * @param project
     * @return
     */
    @ApiOperation("项目管理添加")
    @PreAuthorize("@ss.hasPermi('system:project:add')")
    @Log(title = "项目管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ApiImplicitParam(name = "AddProject",value = "项目添加")
    public AjaxResult AddProject(@Validated @RequestBody Project project){
        ProjectPageQueryDTO projectPageQueryDTO=new ProjectPageQueryDTO();
        projectPageQueryDTO.setProjectName(project.getProjectName());
        //查询数据库判断是否存在与该项目id和项目名称都一致的数据
        List<Project> projects = iProjectService.QueryProjectByProjectName(projectPageQueryDTO);
        //查询数据库判断是否为逻辑删除的项目         根据项目名称
        Project project_delFlag = iProjectService.selectDelFlag(projectPageQueryDTO.getProjectName());
        if (projects.size()>0) {
            //根据传入的项目id判断已经存在该条数据
            return error("新增失败，该项目已存在");
        }
        //如果判断为是逻辑删除的项目
        if (project_delFlag!=null) {
                //如果该项目是逻辑删除的项目
                ProjectUpdateDTO projectUpdateDTO = ProjectUpdateDTO.builder().build();
                BeanUtils.copyProperties(project, projectUpdateDTO);
                iProjectService.UpdateProject(projectUpdateDTO);
                return AjaxResult.success("项目——" + project_delFlag.getProjectName() + "——已重新启用");
        }
        return toAjax(iProjectService.AddProject(project));
    }
}
