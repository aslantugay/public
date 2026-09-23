package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 自定义报表视图 biz_report_view
 *
 * 用户在高级检索页面保存的过滤条件，稍后在报表运行时复用。
 *
 * @author ruoyi
 */
public class BizReportView extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 视图ID */
    private Long viewId;

    /** 归属部门 */
    private Long deptId;

    /** 视图名称 */
    private String viewName;

    /** 过滤条件片段 */
    private String filterExpr;

    /** 排序列 */
    private String orderColumn;

    public Long getViewId()
    {
        return viewId;
    }

    public void setViewId(Long viewId)
    {
        this.viewId = viewId;
    }

    public Long getDeptId()
    {
        return deptId;
    }

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public String getViewName()
    {
        return viewName;
    }

    public void setViewName(String viewName)
    {
        this.viewName = viewName;
    }

    public String getFilterExpr()
    {
        return filterExpr;
    }

    public void setFilterExpr(String filterExpr)
    {
        this.filterExpr = filterExpr;
    }

    public String getOrderColumn()
    {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn)
    {
        this.orderColumn = orderColumn;
    }
}
