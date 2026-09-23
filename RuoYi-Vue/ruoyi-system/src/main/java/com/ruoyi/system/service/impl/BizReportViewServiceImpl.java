package com.ruoyi.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.BizReportView;
import com.ruoyi.system.mapper.BizReportViewMapper;
import com.ruoyi.system.service.IBizReportViewService;

/**
 * 自定义报表视图 服务实现
 *
 * @author ruoyi
 */
@Service
public class BizReportViewServiceImpl implements IBizReportViewService
{
    @Autowired
    private BizReportViewMapper reportViewMapper;

    @Override
    public BizReportView createView(BizReportView view)
    {
        view.setDeptId(SecurityUtils.getDeptId());
        reportViewMapper.insertReportView(view);
        return view;
    }

    @Override
    public BizReportView selectReportViewById(Long viewId)
    {
        return reportViewMapper.selectReportViewById(viewId);
    }
}
