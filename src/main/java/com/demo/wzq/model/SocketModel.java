package com.demo.wzq.model;


import com.demo.wzq.game.WzqGameHelper;
import com.demo.wzq.model.entity.base.R;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SocketModel extends BaseModel {

    /**
     * 进入座位
     *
     * @param id
     * @return
     */
    public R enterSeat(R r, int account, int id) {
        int i = WzqGameHelper.getInstance().enterSate(id,account );
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
}
