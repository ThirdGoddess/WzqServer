package com.demo.wzq.game;

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
     * 获取五子棋房间
     *
     * @param roomId 房间id
     * @return WzqRoom
     */
    public WzqRoom getWzqRoom(int roomId) {
        return wzqGames.get(roomId);
    }

    /**
     * 获取房间列表
     *
     * @return List<RoomInfo>
     */
    public List<RoomInfo> getWzqRoomList() {
        List<RoomInfo> roomInfos = new ArrayList<>();
        for (int i = 1; i < (MAX_ROOM_COUNT+1); i++) {
            WzqRoom value = wzqGames.get(i);
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
            roomInfos.add(roomInfo);
        }
        return roomInfos;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    static class RoomInfo {
        private int id;
        private int type;
        private int userIdA;
        private String userNickA;
        private int userIdB;
        private String userNickB;
        private int observerCount;
    }


}
