package com.demo.wzq.socket;

import org.springframework.util.ConcurrentReferenceHashMap;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ConcurrentMap;

/**
 * Socket长连接记录
 */
public class SocketManager {

    public static final int STATUS_COMMON = 1;
    public static final int STATUS_REMOTE_LOGIN = 3;


    public static ConcurrentMap<Integer, ImSocket> socketConcurrentMap = new ConcurrentReferenceHashMap<>();

    public static void put(int account, ImSocket imSocket) {
        socketConcurrentMap.put(account, imSocket);
    }

    public static ImSocket get(int account) {
        return socketConcurrentMap.get(account);
    }

    public static void remove(int account) {
        socketConcurrentMap.remove(account);
    }

    public static Collection<ImSocket> getValues() {
        return socketConcurrentMap.values();
    }

    //===========================================================================
    //业务代码

    /**
     * 验证通过
     *
     * @param account id
     * @return boolean
     */
    public static boolean verifyPass(int account) {
        ImSocket imSocket = get(account);
        if (null != imSocket) {
            get(account).setVerify(true);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 发送业务消息
     *
     * @param account 账号
     * @param msg     信息
     * @param data    data数据
     */
    public static void sendMessage_1(int account, String msg, Object data) {
        ImSocket imSocket = get(account);
        if (null != imSocket) {
            imSocket.sendMessage(STATUS_COMMON, msg,data);
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
            imSocket.sendMessage(STATUS_REMOTE_LOGIN, "账号在别的地方进行了登录",null);
            try {
                imSocket.getSession().close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
