package com.ruoyi.plan.exception;

import com.ruoyi.common.exception.base.BaseException;

/**
 * @author liupian
 * @description 测试计划异常
 * @date 2023/9/18 14:51:56
 */
public class PlanException extends BaseException {
    private static final long serialVersionUID = 1L;
    public PlanException(String code, Object[] args) {
        super("plan", code, args,null);
    }
}
