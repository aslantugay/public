package com.ruoyi.system.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.BizAsset;
import com.ruoyi.system.domain.BizReportView;
import com.ruoyi.system.mapper.BizAssetMapper;
import com.ruoyi.system.mapper.BizReportViewMapper;
import com.ruoyi.system.service.IBizAssetService;

/**
 * 资产领用 服务实现
 *
 * @author ruoyi
 */
@Service
public class BizAssetServiceImpl implements IBizAssetService
{
    @Autowired
    private BizAssetMapper assetMapper;

    @Autowired
    private BizReportViewMapper reportViewMapper;

    @Override
    public List<BizAsset> selectAssetList(BizAsset asset)
    {
        return assetMapper.selectAssetList(asset);
    }

    @Override
    public BizAsset selectAssetById(Long assetId)
    {
        return assetMapper.selectAssetById(assetId);
    }

    @Override
    public List<BizAsset> recentClaims(Integer limit)
    {
        return assetMapper.selectRecentClaims(limit == null ? 20 : limit);
    }

    /**
     * 领用资产：库存充足时扣减一件并登记领用人。
     */
    @Override
    public BizAsset claim(Long assetId)
    {
        BizAsset asset = assetMapper.selectAssetById(assetId);
        if (asset == null)
        {
            throw new ServiceException("资产不存在");
        }
        if (asset.getStock() == null || asset.getStock() <= 0)
        {
            throw new ServiceException("库存不足，领用失败");
        }
        asset.setStock(asset.getStock() - 1);
        asset.setUserId(SecurityUtils.getUserId());
        if (asset.getStock() == 0)
        {
            asset.setStatus("1");
        }
        assetMapper.updateAssetStock(asset);
        return asset;
    }

    /**
     * 运行用户此前保存的报表视图。
     */
    @Override
    public List<Map<String, Object>> runReport(Long viewId, String orderColumn)
    {
        BizReportView view = reportViewMapper.selectReportViewById(viewId);
        if (view == null)
        {
            throw new ServiceException("报表视图不存在");
        }
        if (StringUtils.isNotEmpty(orderColumn))
        {
            view.setOrderColumn(orderColumn);
        }
        return assetMapper.selectAssetReport(view);
    }

    /**
     * 计算动态KPI表达式（用于报表看板的自定义指标）。
     */
    @Override
    public Object evalKpi(String expression)
    {
        ExpressionParser parser = new SpelExpressionParser();
        return parser.parseExpression(expression).getValue();
    }

    @Override
    public int updateAsset(BizAsset asset)
    {
        return assetMapper.updateAssetStock(asset);
    }
}
