package com.ruoyi.defects.domain;

public class Module {
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
