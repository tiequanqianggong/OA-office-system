package com.ruoyi.plan.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lxz
 */
@Data
public class UserVO implements Serializable {

    private Long userId;

    /** 用户昵称 */
    private String nickName;

    /** 用户头像 */
    private String avatar;


}
