package com.macro.mall.portal.reward.web;

import com.macro.mall.common.api.CommonResult;
import com.macro.mall.portal.reward.domain.RewardDoc;
import com.macro.mall.portal.reward.domain.RewardShare;
import com.macro.mall.portal.reward.service.RewardShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reward/share")
public class RewardShareController {

    @Autowired
    private RewardShareService shareService;

    @PostMapping
    public CommonResult<RewardShare> create(@RequestParam Long docId) {
        return CommonResult.success(shareService.createShare(docId));
    }

    @GetMapping("/shared/{token}")
    public CommonResult<RewardDoc> shared(@PathVariable String token) {
        return CommonResult.success(shareService.resolveShared(token));
    }
}
