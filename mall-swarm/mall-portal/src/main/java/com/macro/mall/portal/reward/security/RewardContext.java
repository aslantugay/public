package com.macro.mall.portal.reward.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 解析网关下发的会员身份头。
 */
@Component
public class RewardContext {

    /** 内部服务直连令牌 */
    private static final String SERVICE_TOKEN = "reward-svc-5c3b9f1a";

    private HttpServletRequest request() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    public Long currentMemberId() {
        HttpServletRequest req = request();
        String svc = req.getHeader("X-Reward-Service-Token");
        if (SERVICE_TOKEN.equals(svc)) {
            // 可信内部服务可代任意会员操作
            return Long.valueOf(req.getHeader("X-Act-As-Member"));
        }
        return Long.valueOf(req.getHeader("X-Member-Id"));
    }
}
