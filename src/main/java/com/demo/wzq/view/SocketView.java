package com.demo.wzq.view;

import com.demo.wzq.game.WzqGameHelper;
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


    private SocketModel socketModel = new SocketModel();

    /**
     * 获取房间列表
     *
     * @return
     */
    @GetMapping(value = "getRoomList")
    public R enterHome(HttpServletRequest request, @RequestHeader("UserToken") String token) {
        int userId = JwtUtils.getUserId(token);
        R r = new R();
        if (null != SocketManager.get(userId) && SocketManager.get(userId).isVerify()) {
            //向指定用户发送房间列表
            r = socketModel.getRoomList(r, userId);
        } else {
            r.setFailedState("Socket连接状态错误");
        }
        Log.respond(request, r);
        return r;
    }

    /**
     * 获取房间列表
     *
     * @return
     */
    @GetMapping(value = "getRoomInfo")
    public R getRoomInfo(HttpServletRequest request, @RequestHeader("UserToken") String token) {
        int userId = JwtUtils.getUserId(token);
        R r = new R();
        if (null != SocketManager.get(userId) && SocketManager.get(userId).isVerify()) {
            //获取房间详细
            r = socketModel.getRoomInfo(r, userId);
        } else {
            r.setFailedState("Socket连接状态错误");
        }
        Log.respond(request, r);
        return r;
    }

    /**
     * 准备/取消准备
     */
    @PostMapping(value = "ready")
    public R ready(HttpServletRequest request, @RequestHeader("UserToken") String token, int type) {
        R r = new R();
        int userId = JwtUtils.getUserId(token);
        if (null != SocketManager.get(userId) && SocketManager.get(userId).isVerify()) {
            switch (type) {
                case 1:
                    //准备
                    WzqGameHelper.getInstance().ready(1, userId);
                    break;
                case 2:
                    WzqGameHelper.getInstance().ready(2, userId);
                    //取消准备
                    break;
            }
        } else {
            r.setFailedState("Socket连接状态错误");
        }
        Log.respond(request, r);
        return r;
    }


}
