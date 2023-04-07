package com.demo.wzq.socket;

import com.alibaba.fastjson2.JSONObject;
import com.demo.wzq.game.WzqGameHelper;
import com.demo.wzq.mybatis.MyBatisUtil;
import com.demo.wzq.mybatis.db_entity.UInfoEntity;
import com.demo.wzq.mybatis.db_mapper.UserInfoMapper;
import com.demo.wzq.uitls.JwtUtils;
import com.demo.wzq.uitls.Log;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@Slf4j
@Component
@ServerEndpoint(value = "/im/{token}")
public class ImSocket {

    //用户账号
    private int account;

    //session对象
    @Getter
    private Session session;

    //是否通过验证
    @Setter
    @Getter
    private boolean isVerify = false;

    //连接时执行
    @OnOpen
    public void onOpen(@PathParam("token") String token, Session session) throws IOException {

        int account = JwtUtils.getUserId(token);
        this.session = session;
        this.account = account;

        Log.socketRequestMessage(account, isVerify, "通过Token打开连接=" + token);
        if (-1 != account) {
            UserInfoMapper mapper = MyBatisUtil.getMapper(UserInfoMapper.class);
            UInfoEntity userTemp = mapper.getUserById(account);
            if (userTemp.getUserToken().equals(token)) {
                ImSocket imSocket = SocketManager.get(account);
                if (null == imSocket) {
                    isVerify = true;
                    SocketManager.put(account, this);
                } else {
                    sendMessage(SocketManager.STATUS_FAILED, "连接已存在，拒绝连接", null);
                    session.close();
                }
            } else {
                sendMessage(SocketManager.STATUS_FAILED, "非法连接，Token失效", null);
                session.close();
            }
        } else {
            sendMessage(SocketManager.STATUS_FAILED, "非法连接，Token校验错误", null);
            session.close();
        }
    }

    //关闭时执行
    @OnClose
    public void onClose() {
        SocketManager.remove(account);
    }

    //连接错误时执行
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SocketManager.remove(account);
    }

    //收到消息
    @OnMessage
    public void onMessage(Session session, String message) {
        if (isVerify) {

        }
    }

    /**
     * 发送消息
     *
     * @param status
     * @param message
     * @param data
     */
    public void sendMessage(int status, String message, Object data) {

        JSONObject json = new JSONObject();
        json.put("status", status);
        json.put("msg", message);

        if (null != data) {
            json.put("data", data);
        }
        String jsonString = json.toString();
        try {
            this.session.getBasicRemote().sendText(jsonString);
            Log.socketSendMessage(account, isVerify, jsonString);
        } catch (Exception ignored) {
            Log.socketSendMessage(account, isVerify, jsonString + ignored);
        }


    }

}
