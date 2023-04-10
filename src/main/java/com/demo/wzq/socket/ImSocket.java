package com.demo.wzq.socket;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
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
    public void onOpen(@PathParam("token") String token, Session session) {

        int account = JwtUtils.getUserId(token);
        this.session = session;
        this.account = account;

        Log.socketRequestMessage(account, isVerify, "通过Token打开连接=" + token);
        if (-1 != account) {
            UserInfoMapper mapper = MyBatisUtil.getMapper(UserInfoMapper.class);
            UInfoEntity userTemp = mapper.getUserById(account);
            MyBatisUtil.commit();
            if (userTemp.getUserToken().equals(token)) {
                ImSocket imSocket = SocketManager.get(account);
                if (null == imSocket) {
                    isVerify = true;
                    SocketManager.put(account, this);
                    Log.socketInfoMessage(account, isVerify, "认证通过,长连接成功");
                } else {
                    sendMessage(SocketManager.STATUS_FAILED, 0, "连接已存在，拒绝连接", null);
                    Log.socketInfoMessage(account, isVerify, "连接已存在，拒绝连接");
                    try {
                        this.session.close();
                    } catch (Exception ignored) {

                    }
                }
            } else {
                sendMessage(SocketManager.STATUS_FAILED, 0, "非法连接，Token失效", null);
                Log.socketInfoMessage(account, isVerify, "非法连接，Token失效");
                try {
                    this.session.close();
                } catch (Exception ignored) {

                }
            }
        } else {
            sendMessage(SocketManager.STATUS_FAILED, 0, "非法连接，Token校验错误", null);
            Log.socketInfoMessage(account, isVerify, "非法连接，Token校验错误");
            try {
                this.session.close();
            } catch (Exception ignored) {

            }
        }
    }

    //关闭时执行
    @OnClose
    public void onClose() {
        SocketManager.remove(account);
        Log.socketInfoMessage(account, isVerify, "断开连接");
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
    public void sendMessage(int status, int type, String message, Object data) {

        //构建消息
        JSONObject json = new JSONObject();
        json.put("status", status);
        json.put("msg", message);

        if (0 != type || null != data) {
            json.putObject("body");
            if (0 != type) {
                json.getJSONObject("body").put("type", type);
            }
            if (null != data) {
                json.getJSONObject("body").put("data", data);
            }
        }
        String jsonString = JSONObject.toJSONString(json, JSONWriter.Feature.WriteMapNullValue);

        //发送消息
        try {
            this.session.getBasicRemote().sendText(jsonString);
            Log.socketSendMessage(account, isVerify, jsonString);
        } catch (Exception ignored) {
            Log.socketSendMessage(account, isVerify, "ERROR:" + ignored + ":" + jsonString);
        }


    }

}
