package com.ruoyi.common.core.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.ui.Model;

import java.io.Serializable;
import java.util.List;

/**
 * 表格分页数据对象
 * 
 * @author ruoyi
 */
@ApiModel(value = "TableDataInfo 表格分页数据对象(ruoyi)")
public class TableDataInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 总记录数 */
    @ApiModelProperty(value = "总记录数(ruoyi)", example = "50")
    private long total;

    /** 列表数据 */
    @ApiModelProperty(value = "列表数据(ruoyi)")
    private List<?> rows;

    /** 消息状态码 */
    @ApiModelProperty(value = "消息状态码(ruoyi)")
    private int code;

    /** 消息内容 */
    @ApiModelProperty(value = "消息内容(ruoyi)")
    private String msg;

    /**
     * 表格数据对象
     */
    public TableDataInfo()
    {
    }

    /**
     * 分页
     * 
     * @param list 列表数据
     * @param total 总记录数
     */
    public TableDataInfo(List<?> list, int total)
    {
        this.rows = list;
        this.total = total;
    }

    public long getTotal()
    {
        return total;
    }

    public void setTotal(long total)
    {
        this.total = total;
    }

    public List<?> getRows()
    {
        return rows;
    }

    public void setRows(List<?> rows)
    {
        this.rows = rows;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }
}
