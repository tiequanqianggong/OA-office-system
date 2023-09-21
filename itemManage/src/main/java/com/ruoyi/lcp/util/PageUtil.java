package com.ruoyi.lcp.util;

import java.util.ArrayList;
import java.util.List;

//定义一个分页工具类
public class PageUtil<T> {
    //当前页码
    private int pageNum;
    //每页显示的记录数
    private int pageSize;
    //总记录数
    private int totalRecords;
    //总页数
    private int totalPage;
    //当前页的记录列表
    private List<T> list;

    //构造方法，传入参数并计算相关属性
    public PageUtil(int pageNum, int pageSize, List<T> list) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.list = list;
        this.totalRecords = list.size();
        this.totalPage = (totalRecords % pageSize) > 0 ? (totalRecords / pageSize) + 1 : (totalRecords / pageSize);
    }

    //获取当前页的记录列表
    public List<T> getPageList() {
        //开始索引
        int fromIndex = (pageNum - 1) * pageSize;
        //结束索引
        int toIndex = fromIndex + pageSize;
        //如果结束索引大于总记录数，则规定结束索引为总记录数
        if (toIndex > totalRecords) {
            toIndex = totalRecords;
        }
        //如果开始索引小于等于总记录数，则返回子列表，否则返回空列表
        if (fromIndex <= totalRecords) {
            return list.subList(fromIndex, toIndex);
        } else {
            return new ArrayList<>();
        }
    }

    //获取当前页码
    public int getPageNum() {
        return pageNum;
    }

    //获取每页显示的记录数
    public int getPageSize() {
        return pageSize;
    }

    //获取总记录数
    public int getTotalRecords() {
        return totalRecords;
    }

    //获取总页数
    public int getTotalPage() {
        return totalPage;
    }
}
