package com.demo.wzq.model;


import com.demo.wzq.model.entity.base.R;
import com.demo.wzq.mybatis.MyBatisUtil;
import com.demo.wzq.mybatis.db_entity.UInfoEntity;
import com.demo.wzq.mybatis.db_mapper.UserInfoMapper;
import com.demo.wzq.socket.SocketManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SocketModel extends BaseModel {

    public void verify(R r, String token, int userId) {
        log.info("线程名：{}", Thread.currentThread().getId());
        UserInfoMapper mapper = MyBatisUtil.getMapper(UserInfoMapper.class);
        UInfoEntity user = mapper.getUserById(userId);
        if (null != user) {
            if (user.getUserToken().equals(token)) {
                if (SocketManager.verifyPass(userId)) {
                    r.setSuccessRespond();
                } else {
                    r.setFailedState("未建立socket连接");
                }
            } else {
                r.setFailedState("socket连接验证失败");
            }
        } else {
            r.setFailedState("socket连接验证失败");
        }
    }

}
