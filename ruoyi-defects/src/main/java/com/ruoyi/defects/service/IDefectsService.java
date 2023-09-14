package com.ruoyi.defects.service;

import java.util.List;
import com.ruoyi.defects.domain.Defects;

/**
 * 缺陷管理Service接口
 * 
 * @author hh
 * @date 2023-09-11
 */
public interface IDefectsService 
{
    /**
     * 查询缺陷管理
     * 
     * @param defectId 缺陷管理主键
     * @return 缺陷管理
     */
    public Defects selectDefectsByDefectId(Long defectId);

    /**
     * 查询缺陷管理列表
     * 
     * @param defects 缺陷管理
     * @return 缺陷管理集合
     */
    public List<Defects> selectDefectsList(Defects defects);

    /**
     * 新增缺陷管理
     * 
     * @param defects 缺陷管理
     * @return 结果
     */
    public int insertDefects(Defects defects);

    /**
     * 修改缺陷管理
     * 
     * @param defects 缺陷管理
     * @return 结果
     */
    public int updateDefects(Defects defects);

    /**
     * 批量删除缺陷管理
     * 
     * @param defectIds 需要删除的缺陷管理主键集合
     * @return 结果
     */
    public int deleteDefectsByDefectIds(Long[] defectIds);

    /**
     * 删除缺陷管理信息
     * 
     * @param defectId 缺陷管理主键
     * @return 结果
     */
    public int deleteDefectsByDefectId(Long defectId);
}
