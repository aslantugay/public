package com.ruoyi.system.mapper;

import java.util.List;
import java.util.Map;
import com.ruoyi.system.domain.BizAsset;
import com.ruoyi.system.domain.BizReportView;

/**
 * 资产领用 数据层
 *
 * @author ruoyi
 */
public interface BizAssetMapper
{
    /**
     * 查询资产列表
     */
    public List<BizAsset> selectAssetList(BizAsset asset);

    /**
     * 查询资产
     */
    public BizAsset selectAssetById(Long assetId);

    /**
     * 查询最近领用动态（全局，用于工作台展示）
     */
    public List<BizAsset> selectRecentClaims(Integer limit);

    /**
     * 运行自定义报表
     */
    public List<Map<String, Object>> selectAssetReport(BizReportView view);

    /**
     * 更新资产库存/领用状态
     */
    public int updateAssetStock(BizAsset asset);

    /**
     * 新增资产
     */
    public int insertAsset(BizAsset asset);
}
