package com.demo.wzq.model;


import com.demo.wzq.game.WzqGameHelper;
import com.demo.wzq.model.entity.base.R;
import com.demo.wzq.socket.SocketManager;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SocketModel extends BaseModel {

    /**
     * 进入座位
     *
     * @param id
     * @return
     */
    public R enterSeat(R r, int account, int id) {
        int i = WzqGameHelper.getInstance().enterSate(id, account);
        switch (i) {
            case 1:
                r.setSuccessRespond();
                r.setData(WzqGameHelper.getInstance().getWzqRoom(id));
                break;
            case 2:
                r.setFailedState("房间对局座位已满，加入失败");
                break;
            case 3:
                r.setFailedState("已经加入房间，进入失败");
                break;
        }
        return r;
    }

    /**
     * 获取房间列表
     *
     * @param r
     * @param userId
     * @return
     */
    public R getRoomList(R r, int userId) {
        List<WzqGameHelper.RoomInfo> wzqRoomList = WzqGameHelper.getInstance().getWzqRoomList();
        SocketManager.get(userId).sendMessage(SocketManager.STATUS_COMMON, SocketManager.TYPE_ROOM_LIST, "success", wzqRoomList);
        r.setSuccessRespond();
        return r;
    }
}
