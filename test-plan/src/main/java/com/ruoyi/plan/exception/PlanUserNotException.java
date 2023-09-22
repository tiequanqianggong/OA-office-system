package com.ruoyi.plan.exception;

import static com.ruoyi.plan.constant.PlanExceptionConstant.NOT_USER;

/**
 * @author liupian
 * @description 用户不存在
 * @date 2023/9/18 15:31:37
 */
public class PlanUserNotException extends PlanException{
    public PlanUserNotException() {
        super(NOT_USER, null);
    }
}
