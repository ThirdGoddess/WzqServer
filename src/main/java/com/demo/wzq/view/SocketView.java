package com.demo.wzq.view;

import com.demo.wzq.model.SocketModel;
import com.demo.wzq.model.entity.base.R;
import com.demo.wzq.socket.SocketManager;
import com.demo.wzq.uitls.JwtUtils;
import com.demo.wzq.uitls.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("im")
public class SocketView {

    private static final String url_verify = "verify";
    private static final String url_enter_seat = "enterSeat";

    private SocketModel socketModel = new SocketModel();

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
                r = socketModel.enterSeat(r, userId, id);
            } else {
                r.setFailedState("进入失败,房间id不符合规则");
            }
        } else {
            r.setFailedState("Socket未连接或未认证");
        }

        Log.respond(request, r);
        return r;
    }

}
