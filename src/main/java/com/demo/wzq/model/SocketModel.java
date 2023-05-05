package com.demo.wzq.model;


import com.demo.wzq.game.WzqGameHelper;
import com.demo.wzq.game.WzqRoom;
import com.demo.wzq.model.entity.base.R;
import com.demo.wzq.socket.SocketManager;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SocketModel extends BaseModel {

    /**
     * 进入首页，推送初始化信息
     *
     * @param r
     * @param userId
     * @return
     */
    public R getRoomList(R r, int userId) {

        //推送房间列表
        List<WzqGameHelper.RoomInfo> wzqRoomList = WzqGameHelper.getInstance().getWzqRoomList();
        SocketManager.get(userId).sendMessage(SocketManager.STATUS_COMMON, SocketManager.TYPE_ROOM_LIST, "success", wzqRoomList);

        //TODO 推送个人昵称积分信息

        r.setSuccessRespond();
        return r;
    }

    /**
     * 进入首页，推送初始化信息
     *
     * @param r
     * @param userId
     * @return
     */
    public R getRoomInfo(R r, int userId) {
        int roomIdByUserId = WzqGameHelper.getInstance().getRoomIdByUserId(userId);
        if (-1 != roomIdByUserId) {
            WzqRoom wzqRoomInfo = WzqGameHelper.getInstance().getWzqRoomInfo(roomIdByUserId);
            SocketManager.get(userId).sendMessage(SocketManager.STATUS_COMMON, SocketManager.TYPE_ROOM_INFO, "success", wzqRoomInfo);
            r.setSuccessRespond();
        } else {
            r.setFailedState("用户绑定房间id异常");
        }
        return r;
    }


}
