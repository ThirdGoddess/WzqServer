package com.demo.wzq.view;

import com.demo.wzq.model.SocketModel;
import com.demo.wzq.model.entity.base.R;
import com.demo.wzq.socket.SocketManager;
import com.demo.wzq.uitls.JwtUtils;
import com.demo.wzq.uitls.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("im")
public class SocketView {

    private static final String url_verify = "verify";
    private static final String url_enter_seat = "enterSeat";

    private SocketModel socketModel = new SocketModel();

    /**
     * 获取房间列表
     *
     * @return
     */
    @GetMapping(value = "enterHome")
    public R enterHome(HttpServletRequest request, @RequestHeader("UserToken") String token) {
        int userId = JwtUtils.getUserId(token);
        R r = new R();
        if (null != SocketManager.get(userId) && SocketManager.get(userId).isVerify()) {
            //向指定用户发送房间列表
            r = socketModel.enterHome(r, userId);
        } else {
            r.setFailedState("Socket连接状态错误");
        }
        Log.respond(request, r);
        return r;
    }



}
