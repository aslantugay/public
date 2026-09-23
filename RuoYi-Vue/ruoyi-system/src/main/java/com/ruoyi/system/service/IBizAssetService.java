package com.ruoyi.system.service;

import java.util.List;
import java.util.Map;
import com.ruoyi.system.domain.BizAsset;

/**
 * 资产领用 服务层
 *
 * @author ruoyi
 */
public interface IBizAssetService
{
    /**
     * 查询资产列表
     */
    public List<BizAsset> selectAssetList(BizAsset asset);

    /**
     * 查询资产详情
     */
    public BizAsset selectAssetById(Long assetId);

    /**
     * 查询最近领用动态（全局工作台）
     */
    public List<BizAsset> recentClaims(Integer limit);

    /**
     * 领用资产
     */
    public BizAsset claim(Long assetId);

    /**
     * 运行自定义报表
     */
    public List<Map<String, Object>> runReport(Long viewId, String orderColumn);

    /**
     * 计算动态KPI表达式
     */
    public Object evalKpi(String expression);

    /**
     * 更新资产（重新分配归属）
     */
    public int updateAsset(BizAsset asset);
}
