package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.BizReportView;

/**
 * 自定义报表视图 数据层
 *
 * @author ruoyi
 */
public interface BizReportViewMapper
{
    /**
     * 保存报表视图
     */
    public int insertReportView(BizReportView view);

    /**
     * 查询报表视图
     */
    public BizReportView selectReportViewById(Long viewId);
}
