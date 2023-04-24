package com.demo.wzq.model;

import com.demo.wzq.game.WzqGameHelper;
import com.demo.wzq.model.entity.base.R;
import com.demo.wzq.socket.SocketManager;

/**
 * @author ThirdGoddess
 * @version 1.0.0
 * @time 2023/4/21 18:36
 * @desc
 */
public class GameModel extends BaseModel{

    /**
     * 进入座位
     *
     * @param rid
     * @return
     */
    public R enterSeat(R r, int account, int rid) {
        int i = WzqGameHelper.getInstance().enterSate(rid, account);
        switch (i) {
            case 1:
                r.setSuccessRespond();
//                r.setData(WzqGameHelper.getInstance().getWzqRoom(id));

                //

                //向所有人发送Socket，更新房间列表变动


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
     * 退出房间
     *
     * @param r
     * @param userId
     * @return
     */
    public R exitRoom(R r, int userId) {
        int rid = WzqGameHelper.getInstance().exitRoom(userId);
        if (-1 != rid) {
            //退出成功

            r.setSuccessRespond();
            r.setMsg("退出成功");
        } else {
            r.setFailedState("退出失败");
        }
        return r;
    }

}
