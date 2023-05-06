package com.demo.wzq.socket;

import com.demo.wzq.game.WzqGameHelper;
import com.demo.wzq.game.WzqRoom;
import com.demo.wzq.game.obj.User;
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

    //==================================================================================================================
    //STATUS码
    public static final int STATUS_OPEN = 20;//打开连接回调
    public static final int STATUS_COMMON = 21;//一般消息
    public static final int STATUS_FAILED = 22;//一般失败消息
    public static final int STATUS_REMOTE_LOGIN = 3;//异地登录消息
    //==================================================================================================================
    //TYPE值
    public static final int TYPE_ROOM_LIST = 1;//大厅房间列表消息
    public static final int TYPE_ROOM_LIST_CHANGE = 2;//针对房间列表部分房间刷新
    public static final int TYPE_ROOM_INFO = 3;//房间内消息，房间内整个详情消息（不包括聊天）
    public static final int TYPE_ROOM_USER_READY = 10;//房间内选手准备消息
    public static final int TYPE_ROOM_START = 11;//房间棋局开始消息

    public static ConcurrentMap<Integer, ImSocket> socketConcurrentMap = new ConcurrentReferenceHashMap<>();

    public static void put(int account, ImSocket imSocket) {
        socketConcurrentMap.put(account, imSocket);
    }

    public static ImSocket get(int account) {
        return socketConcurrentMap.get(account);
    }

    public static void remove(int account) {
        ImSocket imSocket = get(account);

        //更新游戏状态
        WzqGameHelper.getInstance().disconnect(account);

        socketConcurrentMap.remove(account);
    }

    public static Collection<ImSocket> getValues() {
        return socketConcurrentMap.values();
    }

    //===========================================================================
    //业务代码

    /**
     * 发送消息
     *
     * @param account 账号
     * @param status  状态码
     * @param msg     message
     */
    public static void sendMessage(int account, int status, String msg) {
        sendMessage(account, status, 0, msg, null);
    }

    /**
     * 发送消息
     *
     * @param account 账号
     * @param status  状态码
     * @param type    消息type值
     * @param msg     消息
     * @param data    data数据
     */
    public static void sendMessage(int account, int status, int type, String msg, Object data) {
        ImSocket imSocket = socketConcurrentMap.get(account);
        if (null != imSocket) {
            imSocket.sendMessage(status, type, msg, data);
        }
    }

    /**
     * 向房间内的所有用户发送消息
     *
     * @param status 状态码
     * @param type   消息type值
     * @param msg    消息
     * @param data   data数据
     */
    public static void sendMessageToRoom(int roomId, int status, int type, String msg, Object data) {
        new Thread(() -> {
            WzqRoom wzqRoomInfo = WzqGameHelper.getInstance().getWzqRoomInfo(roomId);
            List<User> allUser = wzqRoomInfo.getAllUser();
            for (User user : allUser) {
                int account = user.getAccount();
                ImSocket imSocket = socketConcurrentMap.get(account);
                if (null != imSocket) {
                    imSocket.sendMessage(status, type, msg, data);
                }
            }
        }).start();
    }


    /**
     * 向所有已认证的socket发送消息
     *
     * @param status
     * @param msg
     * @param data
     */
    public static void sendMessageToAll(int status, int type, String msg, Object data) {
        new Thread(() -> {
            for (ImSocket imSocket : socketConcurrentMap.values()) {
                imSocket.sendMessage(status, type, msg, data);
            }
        }).start();
    }

}
