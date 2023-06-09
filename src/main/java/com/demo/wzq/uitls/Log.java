package com.demo.wzq.uitls;

import com.alibaba.fastjson2.JSONObject;
import com.demo.wzq.model.entity.base.R;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ThirdGoddess
 * @version 1.0.0
 * @time 2023/4/6 19:24
 * @desc
 */
@Slf4j
public class Log {

    /**
     * 响应log
     *
     * @param request
     * @param r
     */
    public static void respond(HttpServletRequest request, R r) {
        respond(request, JSONObject.toJSONString(r));
    }

    /**
     * 响应log
     *
     * @param request
     * @param r
     */
    public static void respond(HttpServletRequest request, String r) {
        String ipStr = IpUtil.getIpStr(request);
        String requestURI = request.getRequestURI();
        String userId;
        String userToken = request.getHeader("UserToken");
        if (!TextUtil.isEmpty(userToken)) {
            int userIdTemp = JwtUtils.getUserId(userToken);
            if (-1 != userIdTemp) {
                userId = String.valueOf(userIdTemp);
            } else {
                userId = "null";
            }
        } else {
            userId = "null";
        }
        log.info("id:{} ip:{} <= {} :{}", userId, ipStr, requestURI, r);
    }

    /**
     * 请求log
     *
     * @param request
     */
    public static void request(HttpServletRequest request) {
        String ipStr = IpUtil.getIpStr(request);
        String userId;
        String userToken = request.getHeader("UserToken");
        if (!TextUtil.isEmpty(userToken)) {
            int userIdTemp = JwtUtils.getUserId(userToken);
            if (-1 != userIdTemp) {
                userId = String.valueOf(userIdTemp);
            } else {
                userId = "null";
            }
        } else {
            userId = "null";
        }

        String requestURI = request.getRequestURI();
        log.info("id:{} ip:{} -> {}", userId, ipStr, requestURI);
    }

    /**
     * socket日志
     *
     * @param account
     * @param isVerify
     * @param jsonString
     */
    public static void socketSendMessage(int account, boolean isVerify, String jsonString) {
        if (isVerify) {
            log.info("socket = id:{} 已认证 <= {}", account, jsonString);
        } else {
            log.info("socket = id:{} 未认证 <= {}", account, jsonString);
        }
    }

    public static void socketRequestMessage(int account, boolean isVerify, String jsonString) {
        if (isVerify) {
            log.info("socket = id:{} 已认证 -> {}", account, jsonString);
        } else {
            log.info("socket = id:{} 未认证 -> {}", account, jsonString);
        }
    }

    public static void socketInfoMessage(int account, boolean isVerify, String str) {
        if (isVerify) {
            log.info("socket = id:{} 已认证 || {}", account, str);
        } else {
            log.info("socket = id:{} 未认证 || {}", account, str);
        }
    }
}
