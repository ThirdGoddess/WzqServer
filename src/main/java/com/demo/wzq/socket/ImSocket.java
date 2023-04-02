package com.demo.wzq.socket;

import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@Component
@ServerEndpoint(value = "/im/{account}")
public class ImSocket {

    //用户账号
    private int account;

    //session对象
    @Getter
    private Session session;

    //是否验证
    @Setter
    private boolean isVerify = false;

    //连接时执行
    @OnOpen
    public void onOpen(@PathParam("account") int account, Session session) throws IOException {
        this.session = session;
        this.account = account;
        System.out.println("打开连接:" + account);
//        System.out.println(String.valueOf(account));
    }

    //关闭时执行
    @OnClose
    public void onClose() {
        System.out.println("连接关闭");
    }

    //连接错误时执行
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
        System.out.println(error.toString());
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

        try {
            this.session.getBasicRemote().sendText(json.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
