package com.demo.wzq.socket;

import com.demo.wzq.game.WzqGameHelper;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * Socket长连接记录
 */
public class SocketManager {

    public static final int STATUS_COMMON = 21;
    public static final int STATUS_FAILED = 22;
    public static final int STATUS_REMOTE_LOGIN = 3;


    public static ConcurrentMap<Integer, ImSocket> socketConcurrentMap = new ConcurrentReferenceHashMap<>();

    public static void put(int account, ImSocket imSocket) {
        socketConcurrentMap.put(account, imSocket);
    }

    public static ImSocket get(int account) {
        return socketConcurrentMap.get(account);
    }

    public static void remove(int account) {

        ImSocket imSocket = get(account);
        if (imSocket.isVerify()) {
            //退出房间
            WzqGameHelper.getInstance().exitRoom(account);
        }

        socketConcurrentMap.remove(account);
    }

    public static Collection<ImSocket> getValues() {
        return socketConcurrentMap.values();
    }

    //===========================================================================
    //业务代码

    /**
     * 发送业务消息
     *
     * @param account 账号
     * @param msg     信息
     * @param data    data数据
     */
    public static void sendMessage_21(int account, String msg, Object data) {
        ImSocket imSocket = get(account);
        if (null != imSocket) {
            imSocket.sendMessage(STATUS_COMMON, msg, data);
        }
    }

    /**
     * 发送一般错误消息
     *
     * @param account 账号
     * @param msg     信息
     */
    public static void sendMessage_22(int account, String msg) {
        ImSocket imSocket = get(account);
        if (null != imSocket) {
            imSocket.sendMessage(STATUS_FAILED, msg, null);
        }
    }

    /**
     * 发送被异地登录消息
     *
     * @param account 账号
     */
    public static void sendMessage_3(int account) {
        ImSocket imSocket = get(account);
        if (null != imSocket) {
            imSocket.sendMessage(STATUS_REMOTE_LOGIN, "账号在别的地方进行了登录", null);
            try {
                imSocket.getSession().close();
                remove(account);
            } catch (Exception ignored) {

            }
        }
    }


    /**
     * 向所有已认证的socket发送消息
     *
     * @param status
     * @param msg
     * @param data
     */
    public static void sendMessageToAll(int status, String msg, Object data) {
        for (ImSocket imSocket : socketConcurrentMap.values()) {
            imSocket.sendMessage(status, msg, data);
        }
    }

}
