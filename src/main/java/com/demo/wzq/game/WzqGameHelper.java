package com.demo.wzq.game;

import com.demo.wzq.game.obj.User;
import com.demo.wzq.model.GameModel;
import com.demo.wzq.model.entity.base.R;
import com.demo.wzq.mybatis.MyBatisUtil;
import com.demo.wzq.mybatis.db_entity.UInfoEntity;
import com.demo.wzq.mybatis.db_mapper.UserInfoMapper;
import com.demo.wzq.socket.SocketManager;
import lombok.*;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * @author ThirdGoddess
 * @version 1.0.0
 * @time 2023/4/3 16:22
 * @desc
 */
public class WzqGameHelper {

    private static final WzqGameHelper wzqGameHelper = new WzqGameHelper();

    //最大房间数
    private static final int MAX_ROOM_COUNT = 100;

    //所有五子棋对局
    private static ConcurrentMap<Integer, WzqRoom> wzqGames = new ConcurrentReferenceHashMap();

    //已经在房间内的所有用户
    private static ConcurrentMap<Integer, User> roomUsers = new ConcurrentReferenceHashMap<>();

    private WzqGameHelper() {
    }

    public static WzqGameHelper getInstance() {
        return wzqGameHelper;
    }

    //==================================================================================================================

    /**
     * 初始化五子棋Helper
     */
    public void init() {
        for (int i = 1; i < (MAX_ROOM_COUNT + 1); i++) {
            WzqRoom wzqRoom = new WzqRoom();
            wzqRoom.setRoomId(i);
            wzqGames.put(i, wzqRoom);
        }
    }

    /**
     * 获取五子棋房间-详细
     *
     * @param roomId 房间id
     * @return WzqRoom
     */
    public WzqRoom getWzqRoomInfo(int roomId) {
        return wzqGames.get(roomId);
    }

    /**
     * 获取五子棋房间-基本数据
     *
     * @param roomId
     * @return
     */
    public RoomInfo getWzqRoom(int roomId) {
        WzqRoom value = wzqGames.get(roomId);
        RoomInfo roomInfo = new RoomInfo();
        roomInfo.setId(value.getRoomId());
        roomInfo.setType(value.getType());
        if (null != value.getUserA()) {
            roomInfo.setUserIdA(value.getUserA().getAccount());
            roomInfo.setUserNickA(value.getUserA().getNick());
        }

        if (null != value.getUserB()) {
            roomInfo.setUserIdB(value.getUserB().getAccount());
            roomInfo.setUserNickB(value.getUserB().getNick());
        }

        roomInfo.setObserverCount(value.getObservers().size());
        return roomInfo;
    }

    /**
     * 获取房间列表-基本信息
     *
     * @return List<RoomInfo>
     */
    public List<RoomInfo> getWzqRoomList() {
        List<RoomInfo> roomInfos = new ArrayList<>();
        for (int i = 1; i < (MAX_ROOM_COUNT + 1); i++) {
            roomInfos.add(getWzqRoom(i));
        }
        return roomInfos;
    }

    /**
     * 加入指定房间
     *
     * @param id
     * @return 1-进入A成功，2-进入失败,房间内对战座位已满，3-进入失败,已经加入房间
     */
    public synchronized int enterSate(int id, int account) {
        synchronized (WzqGameHelper.class) {
            if (null == roomUsers.get(account)) {
                WzqRoom wzqRoom = wzqGames.get(id);
                User userA = wzqRoom.getUserA();
                User userB = wzqRoom.getUserB();

                if (userA == null || userB == null) {
                    if (userA == null) {
                        //A座位为空
                        UserInfoMapper mapper = MyBatisUtil.getMapper(UserInfoMapper.class);
                        UInfoEntity user = mapper.getUserById(account);
                        User userATemp = new User(user.getUserNick(), user.getId(), user.getUserIntegral(), false, 0, 0, 0, id);
                        wzqRoom.setUserA(userATemp);
                        roomUsers.put(account, userATemp);
                    } else {
                        //B座位为空
                        UserInfoMapper mapper = MyBatisUtil.getMapper(UserInfoMapper.class);
                        UInfoEntity user = mapper.getUserById(account);
                        User userBTemp = new User(user.getUserNick(), user.getId(), user.getUserIntegral(), false, 0, 0, 0, id);
                        wzqRoom.setUserB(userBTemp);
                        roomUsers.put(account, userBTemp);
                    }

                    //向所有人发送Socket，更新房间列表变动
                    SocketManager.sendMessageToAll(SocketManager.STATUS_COMMON, SocketManager.TYPE_ROOM_LIST_CHANGE, "change", WzqGameHelper.getInstance().getWzqRoom(id));

                    //TODO 向房间内所有用户通知房间状态

                    return 1;
                } else {
                    //房间对局座位已满
                    return 2;
                }
            } else {
                return 3;
            }
        }
    }

    /**
     * 退出房间
     *
     * @param account
     */
    public int exitRoom(int account) {
        synchronized (WzqGameHelper.class) {
            for (User value : roomUsers.values()) {
                if (value.getAccount() == account) {
                    WzqRoom wzqRoom = wzqGames.get(value.getRoomId());
                    if (null != wzqRoom.getUserA() && wzqRoom.getUserA().getAccount() == account) {
                        wzqRoom.setUserA(null);
                    } else if (null != wzqRoom.getUserB() && wzqRoom.getUserB().getAccount() == account) {
                        wzqRoom.setUserB(null);
                    }
                    roomUsers.remove(account);

                    //向所有人发送Socket，更新房间列表
                    SocketManager.sendMessageToAll(SocketManager.STATUS_COMMON, SocketManager.TYPE_ROOM_LIST_CHANGE, "change", WzqGameHelper.getInstance().getWzqRoom(value.getRoomId()));

                    //TODO 向该房间内的所有用户发送对局结果

                    return value.getRoomId();
                }
            }
        }
        return -1;
    }

    /**
     * 断开连接调用
     */
    public void disconnect(int account) {

        //退出房间（包含Socket通知全体房间变动、房间内对局结果）
        GameModel gameModel = new GameModel();
        gameModel.exitRoom(new R(), account);

    }

    /**
     * 根据用户ID获取用户所在的房间ID
     */
    public int getRoomIdByUserId(int account) {
        if (null != roomUsers.get(account)) {
            return roomUsers.get(account).getRoomId();
        } else {
            return -1;
        }
    }

    /**
     * 准备/取消准备
     *
     * @param type 1准备;2取消准备
     * @return 1准备成功，2取消准备成功，3用户与房间id绑定异常，4当前房间状态不允许准备/取消准备,5type类型错误
     */
    public int ready(int type, int account) {
        synchronized (WzqGameHelper.class) {
            int roomId = getRoomIdByUserId(account);
            if (-1 != roomId) {
                WzqRoom wzqRoom = wzqGames.get(roomId);

                //判断房间状态是否可以准备
                int roomType = wzqRoom.getType();

                //判断房间状态，选手是否对应
                if (roomType == 0 && ((null != wzqRoom.getUserA() && wzqRoom.getUserA().getAccount() == account) || (null != wzqRoom.getUserB() && wzqRoom.getUserB().getAccount() == account))) {

                    switch (type) {
                        case 1:

                            //准备
                            if (null != wzqRoom.getUserA() && wzqRoom.getUserA().getAccount() == account) {
                                wzqRoom.getUserA().setReady(true);
                            } else if (null != wzqRoom.getUserB() && wzqRoom.getUserB().getAccount() == account) {
                                wzqRoom.getUserB().setReady(true);
                            }

                            //TODO 向房间内所有用户下发准备状态消息
//                            SocketManager.sendMessage();

                            //判断双方是否准备，准备开局
                            if (null != wzqRoom.getUserA() && null != wzqRoom.getUserB() && wzqRoom.getUserA().isReady() && wzqRoom.getUserB().isReady()) {
                                //双方均已准备
                                //向房间内所有用户下发棋局开始消息
                                wzqRoom.setType(11);
                            }

                            return 1;
                        case 2:
                            //取消准备
                            if (wzqRoom.getUserA().getAccount() == account) {
                                wzqRoom.getUserA().setReady(false);
                            } else if (wzqRoom.getUserB().getAccount() == account) {
                                wzqRoom.getUserB().setReady(false);
                            }

                            //TODO 向房间内所有用户下发准备状态消息

                            return 1;
                        default:
                            return 5;
                    }

                } else {
                    return 4;
                }
            } else {
                return 3;
            }
        }
    }


    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoomInfo {
        private int id;
        private int type;
        private int userIdA;
        private String userNickA;
        private int userIdB;
        private String userNickB;
        private int observerCount;
    }


}
