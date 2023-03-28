package com.demo.wzq.model;

import com.demo.wzq.model.entity.R;
import com.demo.wzq.model.entity.Register;
import com.demo.wzq.mybatis.MyBatisUtil;
import com.demo.wzq.mybatis.db_mapper.UserInfoMapper;
import com.demo.wzq.uitls.JwtUtils;

/**
 * @author ThirdGoddess
 * @version 1.0.0
 * @time 2023/3/24 17:26
 * @desc 用户操作相关
 */
public class UserModel extends BaseModel {

    public R register(R r, String nick, String password) {
        //入库
        UserInfoMapper mapper = MyBatisUtil.getMapper(UserInfoMapper.class);
        int addIndex = mapper.addUser("nihaoya", "pass", 0, "tokenStr");
        if (1 == addIndex) {
            r.setRespond(R.SUCCESS_CODE);
            String token = JwtUtils.createToken(addIndex, "nihaoya");
            r.setData(new Register(nick,addIndex,token));
        }
        MyBatisUtil.commit();
        return r;
    }

}
