package com.demo.wzq.view;

import com.demo.wzq.model.GameModel;
import com.demo.wzq.model.entity.base.R;
import com.demo.wzq.socket.SocketManager;
import com.demo.wzq.uitls.JwtUtils;
import com.demo.wzq.uitls.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ThirdGoddess
 * @version 1.0.0
 * @time 2023/4/21 18:35
 * @desc
 */
@Slf4j
@RestController
@RequestMapping("game")
public class GameView {

    private  GameModel gameModel = new GameModel();

    /**
     * 进入座位
     *
     * @param id 房间id
     * @return
     */
    @PostMapping(value = "enterSeat")
    public R enterSeat(HttpServletRequest request, @RequestHeader("UserToken") String token, int id) {
        int userId = JwtUtils.getUserId(token);
        R r = new R();
        if (null != SocketManager.get(userId) && SocketManager.get(userId).isVerify()) {
            if (id > 0 && id <= 100) {
                r = gameModel.enterSeat(r, userId, id);
            } else {
                r.setFailedState("进入失败,房间id不符合规则");
            }
        } else {
            r.setFailedState("Socket未连接或未认证");
        }

        Log.respond(request, r);
        return r;
    }

    /**
     * 退出房间
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("exitRoom")
    public R exitRoom(HttpServletRequest request, @RequestHeader("UserToken") String token) {
        int userId = JwtUtils.getUserId(token);
        R r = new R();
        if (null != SocketManager.get(userId) && SocketManager.get(userId).isVerify()) {
            //向指定用户发送房间列表
            r = gameModel.exitRoom(r, userId);
        } else {
            r.setFailedState("Socket连接状态错误");
        }
        Log.respond(request, r);
        return r;
    }

}
