package com.demo.wzq.view;

import com.demo.wzq.model.SocketModel;
import com.demo.wzq.model.entity.base.R;
import com.demo.wzq.uitls.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("im")
public class SocketView {

    private static final String url_verify = "verify";

    private SocketModel socketModel = new SocketModel();

    /**
     * Socket身份认证
     */
    @PostMapping(value = url_verify)
    public R verify(@RequestHeader("UserToken") String token) {
        log.info("线程名：{}", Thread.currentThread().getId());
        R r = new R();
        int userId = JwtUtils.getUserId(token);
        if (-1 != userId) {
            socketModel.verify(r, token, userId);
        } else {
            r.setFailedState("socket连接验证失败");
        }
        return r;
    }

}
