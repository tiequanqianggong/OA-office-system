package com.ruoyi.defects.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.defects.mapper.DefectsMapper;
import com.ruoyi.defects.domain.Defects;
import com.ruoyi.defects.service.IDefectsService;

/**
 * 缺陷管理Service业务层处理
 * 
 * @author hh
 * @date 2023-09-11
 */
@Service
public class DefectsServiceImpl implements IDefectsService 
{
    @Autowired
    private DefectsMapper defectsMapper;

    /**
     * 查询缺陷管理
     * 
     * @param defectId 缺陷管理主键
     * @return 缺陷管理
     */
    @Override
    public Defects selectDefectsByDefectId(Long defectId)
    {
        return defectsMapper.selectDefectsByDefectId(defectId);
    }

    /**
     * 查询缺陷管理列表
     * 
     * @param defects 缺陷管理
     * @return 缺陷管理
     */
    @Override
    public List<Defects> selectDefectsList(Defects defects)
    {
        return defectsMapper.selectDefectsList(defects);
    }

    /**
     * 新增缺陷管理
     * 
     * @param defects 缺陷管理
     * @return 结果
     */
    @Override
    public int insertDefects(Defects defects)
    {
        return defectsMapper.insertDefects(defects);
    }

    /**
     * 修改缺陷管理
     * 
     * @param defects 缺陷管理
     * @return 结果
     */
    @Override
    public int updateDefects(Defects defects)
    {
        return defectsMapper.updateDefects(defects);
    }

    /**
     * 批量删除缺陷管理
     * 
     * @param defectIds 需要删除的缺陷管理主键
     * @return 结果
     */
    @Override
    public int deleteDefectsByDefectIds(Long[] defectIds)
    {
        return defectsMapper.deleteDefectsByDefectIds(defectIds);
    }

    /**
     * 删除缺陷管理信息
     * 
     * @param defectId 缺陷管理主键
     * @return 结果
     */
    @Override
    public int deleteDefectsByDefectId(Long defectId)
    {
        return defectsMapper.deleteDefectsByDefectId(defectId);
    }
}
