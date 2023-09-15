package com.ruoyi.lcp.controller;


import com.ruoyi.lcp.pojo.Project;
import com.ruoyi.lcp.pojo.dto.ProjectPageQueryDTO;
import com.ruoyi.lcp.pojo.dto.ProjectTeamAddDTO;
import com.ruoyi.lcp.pojo.dto.ProjectTeamDTO;
import com.ruoyi.lcp.pojo.dto.ProjectTeamUpdateDTO;
import com.ruoyi.lcp.pojo.vo.ProjectTeamVO;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.lcp.pojo.ProjectTeam;

import com.ruoyi.lcp.service.IProjectService;
import com.ruoyi.lcp.service.IProjectTeamService;
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


@RestController
@Slf4j
@RequestMapping("/projectTeam")
@Api(tags = "项目团队管理")
public class ProjectTeamController extends BaseController {
    @Autowired
    private IProjectTeamService iProjectTeamService;

    @Autowired
    private IProjectService iProjectService;

    /**
     * 项目成员管理分页查询
     * @param projectTeamDTO
     * @return
     */
    @ApiOperation("项目团队管理分页查询")
    @PreAuthorize("@ss.hasPermi('system:projectteam:query')")
    @Log(title = "项目团队管理", businessType = BusinessType.OTHER)
    @GetMapping("/query")
    @ApiImplicitParam(name = "QueryProjectTeam",value = "分页查询/条件查询")
    public TableDataInfo QueryProjectTeam(@RequestBody(required = false) ProjectTeamDTO projectTeamDTO){
        //若依分页
        startPage();
        List<ProjectTeam> projectTeamList =iProjectTeamService.QueryProjectTeam(projectTeamDTO);
        //创建Vo集合  在循环中接收ProjectTeam
        List<ProjectTeamVO> projectTeamVOList = new ArrayList<>();
        for (ProjectTeam projectTeam:projectTeamList){

            //类的属性拷贝，
            ProjectTeamVO projectTeamVO=new ProjectTeamVO();
            BeanUtils.copyProperties(projectTeam,projectTeamVO);

            //根据Team表中的项目id从 项目表查出项目名称并设置到VO中返回给前端
            ProjectPageQueryDTO projectPageQueryDTO= ProjectPageQueryDTO.builder()
                            .projectId(projectTeam.getProjectId())
                            .build();
            //                                                       获取项目名称并设置到TeamVO中
            projectTeamVO.setProjectName(iProjectService.QueryProject(projectPageQueryDTO).get(0).getProjectName());
            //加入到集合
            projectTeamVOList.add(projectTeamVO);
        }

        //将VO传给前端
        return getDataTable(projectTeamVOList);
    }

    /**
     * 项目成员管理删除
     * @param teamIds
     * @return
     */
    //TODO 项目成员管理删除
    @ApiOperation("项目成员管理单个/批量删除")
    @PreAuthorize("@ss.hasPermi('system:projectteam:delete')")
    @Log(title = "项目成员管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete")
    @ApiImplicitParam(name = "DeleteProject",value = "根据成员id进行删除")
    public AjaxResult DeleteProject(List<Long> teamIds){
        //记录本次删除了多少条数据
        int num=0;
        for (Long teamId:teamIds){
          if(iProjectTeamService.deleteByTeamId(teamId)!=0){
              num++;
          }
        }
        log.info("本次删除了：{}"+num+"条项目成员数据");
      return toAjax(num);
    }

    /**
     * 项目成员管理修改
     * @param projectTeamUpdateDTO
     * @return
     */
    @ApiOperation("项目成员管理修改")
    @Log(title = "项目成员管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('system:projectteam:update')")
    @PutMapping("/update")
    @ApiImplicitParam(name = "UpdateProjectTeam",value = "根据成员id修改")
    public AjaxResult UpdateProjectTeam(@Validated @RequestBody ProjectTeamUpdateDTO projectTeamUpdateDTO){
        return toAjax(iProjectTeamService.UpdateProjectTeam(projectTeamUpdateDTO));
    }


    /**
     * 项目成员添加
     * @param projectTeam
     * @return
     */
    @ApiOperation("项目成员管理添加")
    @PreAuthorize("@ss.hasPermi('system:projectteam:add')")
    @Log(title = "项目成员管理", businessType = BusinessType.INSERT)
    @ApiImplicitParam(name = "AddProjectTeam",value = "项目成员添加")
    @PostMapping("/add")
    public AjaxResult AddProjectTeam(@Validated @RequestBody ProjectTeam projectTeam){
        ProjectTeamAddDTO projectTeamAddDTO=ProjectTeamAddDTO
                .builder()
                .teamId(projectTeam.getTeamId())
                .teamName(projectTeam.getTeamName())
                .build();
       //判断数据库中是否已存在该成员
        ProjectTeam projectTeam_dateBase=iProjectTeamService.selectTeamByTeamIdAndName(projectTeamAddDTO);
       if (projectTeam_dateBase!=null) {
           if (projectTeam_dateBase.getTeamId()!=null) {
               //根据传入的项目id判断已经存在该条数据
               return error("新增失败，"+projectTeam_dateBase.getTeamName()+"该成员已存在");
           }
       }

        //判断数据库中是否存在该成员所在的项目
        ProjectPageQueryDTO projectPageQueryDTO=new ProjectPageQueryDTO();
        projectPageQueryDTO.setProjectId(projectTeam.getProjectId());
        List<Project> projects=iProjectService.QueryProject(projectPageQueryDTO);
        if (projects.get(0).equals(null)){
            return error("新增失败，该成员所在项目不存在");
        }
        return toAjax(iProjectTeamService.AddProjectTeam(projectTeam));
    }
}
