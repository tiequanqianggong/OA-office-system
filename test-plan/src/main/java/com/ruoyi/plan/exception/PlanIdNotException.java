package com.ruoyi.plan.exception;

import com.ruoyi.common.exception.base.BaseException;

import static com.ruoyi.plan.constant.PlanExceptionConstant.NOT_PLAN_ID;

/**
 * @author liupian
 * @description 测试计划id为空yic
 * @date 2023/9/20 13:06:27
 */
public class PlanIdNotException extends  PlanException {
    public PlanIdNotException() {
        super(NOT_PLAN_ID,null);
    }
}
