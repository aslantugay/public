package com.macro.mall.portal.reward.feign;

import com.macro.mall.common.api.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 调用后台订单服务（跨服务）。
 */
@FeignClient(value = "mall-admin", path = "/order")
public interface MallAdminFeignClient {

    @GetMapping("/{id}")
    CommonResult<Object> getOrderDetail(@PathVariable("id") Long id);

    @PostMapping("/update/note")
    CommonResult<Object> updateOrderNote(@RequestParam("id") Long id, @RequestParam("note") String note);
}
