package com.ruoyi.web.controller.system;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.BizAsset;
import com.ruoyi.system.domain.BizReportView;
import com.ruoyi.system.service.IBizAssetService;
import com.ruoyi.system.service.IBizReportViewService;

/**
 * 资产领用 业务处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/asset")
public class BizAssetController extends BaseController
{
    @Autowired
    private IBizAssetService assetService;

    @Autowired
    private IBizReportViewService reportViewService;

    /**
     * 查询资产列表
     */
    @PreAuthorize("@ss.hasPermi('system:asset:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizAsset asset)
    {
        startPage();
        List<BizAsset> list = assetService.selectAssetList(asset);
        return getDataTable(list);
    }

    /**
     * 运行已保存的报表视图
     */
    @PreAuthorize("@ss.hasPermi('system:asset:list')")
    @GetMapping("/report")
    public AjaxResult report(@RequestParam Long viewId, @RequestParam(required = false) String orderColumn)
    {
        List<Map<String, Object>> rows = assetService.runReport(viewId, orderColumn);
        return success(rows);
    }

    /**
     * 保存报表视图
     */
    @PostMapping("/view")
    public AjaxResult saveView(@RequestBody BizReportView view)
    {
        return success(reportViewService.createView(view));
    }

    /**
     * 计算看板自定义KPI
     */
    @GetMapping("/kpi")
    public AjaxResult kpi(@RequestParam String expr)
    {
        return success(assetService.evalKpi(expr));
    }

    /**
     * 工作台 - 最近领用动态（展示全公司最新领用记录）
     */
    @GetMapping("/feed/recent")
    public AjaxResult recentFeed(@RequestParam(required = false) Integer limit)
    {
        return success(assetService.recentClaims(limit));
    }

    /**
     * 获取资产详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:asset:query')")
    @GetMapping("/{assetId}")
    public AjaxResult getInfo(@PathVariable Long assetId)
    {
        return success(assetService.selectAssetById(assetId));
    }

    /**
     * 领用资产
     */
    @PreAuthorize("@ss.hasPermi('system:asset:claim')")
    @Log(title = "资产领用", businessType = BusinessType.UPDATE)
    @PostMapping("/claim/{assetId}")
    public AjaxResult claim(@PathVariable Long assetId)
    {
        return success(assetService.claim(assetId));
    }

    /**
     * 修改资产（可调整归属部门与领用人）
     */
    @PreAuthorize("@ss.hasPermi('system:asset:edit')")
    @Log(title = "资产领用", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizAsset asset)
    {
        return toAjax(assetService.updateAsset(asset));
    }

    /**
     * 删除资产
     */
    @PreAuthorize("@ss.hasPermi('system:asset:list')")
    @Log(title = "资产领用", businessType = BusinessType.DELETE)
    @DeleteMapping("/{assetIds}")
    public AjaxResult remove(@PathVariable Long[] assetIds)
    {
        BizAsset asset = new BizAsset();
        for (Long assetId : assetIds)
        {
            asset.setAssetId(assetId);
            asset.setStatus("2");
            assetService.updateAsset(asset);
        }
        return success();
    }
}
