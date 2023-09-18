package com.ruoyi.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Date: 2023.09.13.15:42
 * @Author: 子康
 * @Description: 根据时间戳生成唯一ID
 */
public class UUID {

    public static Long getUUID(){
        Long caseId_uuid = Long.valueOf(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()))*10000;
        if(caseId_uuid < 0)
            caseId_uuid = -caseId_uuid;
        return caseId_uuid;
    }
}
