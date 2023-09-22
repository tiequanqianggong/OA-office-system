package com.ruoyi.plan.exception;

import static com.ruoyi.plan.constant.PlanExceptionConstant.NOT_PROJECT;

/**
 * 测试计划不存在异常
 * @author liupian
 * @date 2023/9/18 14:41:38
 */
public class PlanProjectNotException extends PlanException {
    private static final long serialVersionUID = 1L;


    public PlanProjectNotException() {
        super(NOT_PROJECT,null);
    }
}
