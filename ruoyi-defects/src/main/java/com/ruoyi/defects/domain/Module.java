package com.ruoyi.defects.domain;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
public class Module {

    //模块名称
    @Excel(name = "模块名称")
    @ApiModelProperty("模块名称")
    private String modelName;

    public String getModuleName() {
        return modelName;
    }

    public void setModuleName(String moduleName) {
        this.modelName = moduleName;
    }

    @Override
    public String toString() {
        return "Module{" +
                "moduleName='" + modelName + '\'' +
                '}';
    }
}
