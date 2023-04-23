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
                if (userA == null) {
                    //A座位为空
                    UserInfoMapper mapper = MyBatisUtil.getMapper(UserInfoMapper.class);
                    UInfoEntity user = mapper.getUserById(account);
                    User userATemp = new User(user.getUserNick(), user.getId(), user.getUserIntegral(), false, 0, 0, 0, id);
                    wzqRoom.setUserA(userATemp);
                    roomUsers.put(account, userATemp);
                    return 1;
                } else if (userB == null) {
                    //B座位为空
                    UserInfoMapper mapper = MyBatisUtil.getMapper(UserInfoMapper.class);
                    UInfoEntity user = mapper.getUserById(account);
                    User userBTemp = new User(user.getUserNick(), user.getId(), user.getUserIntegral(), false, 0, 0, 0, id);
                    wzqRoom.setUserB(userBTemp);
                    roomUsers.put(account, userBTemp);
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
    public void disconnect(int account){

        //退出房间（包含Socket通知全体房间变动、房间内对局结果）
        GameModel gameModel = new GameModel();
        gameModel.exitRoom(new R(),account);

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
