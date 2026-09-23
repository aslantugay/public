package com.ruoyi.system.service;

import com.ruoyi.system.domain.BizReportView;

/**
 * 自定义报表视图 服务层
 *
 * @author ruoyi
 */
public interface IBizReportViewService
{
    /**
     * 保存报表视图
     */
    public BizReportView createView(BizReportView view);

    /**
     * 查询报表视图
     */
    public BizReportView selectReportViewById(Long viewId);
}
