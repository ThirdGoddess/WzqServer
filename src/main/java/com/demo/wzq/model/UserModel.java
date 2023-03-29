package com.demo.wzq.model;

import com.demo.wzq.model.entity.R;
import com.demo.wzq.model.entity.Register;
import com.demo.wzq.mybatis.MyBatisUtil;
import com.demo.wzq.mybatis.db_entity.UInfoEntity;
import com.demo.wzq.mybatis.db_mapper.UserInfoMapper;
import com.demo.wzq.uitls.JwtUtils;

/**
 * @author ThirdGoddess
 * @version 1.0.0
 * @time 2023/3/24 17:26
 * @desc 用户操作相关
 */
public class UserModel extends BaseModel {

    /**
     * 用户注册
     *
     * @param r
     * @param nick
     * @param password
     * @return
     */
    public R register(R r, String nick, String password) {

        //获取mapper
        UserInfoMapper mapper = MyBatisUtil.getMapper(UserInfoMapper.class);

        //判断昵称是否已存在
        String nickName = mapper.getNickByNick(nick.trim());
        if (null == nickName) {
            //入库
            UInfoEntity uInfoEntity = new UInfoEntity(nick.trim(), getSha1(password.trim()), 0, "");
            int addIndex = mapper.addUser(uInfoEntity);
            int id = uInfoEntity.getId();
            if (1 == addIndex) {
                String token = setUserToken(mapper, id);
                r.setRespond(R.SUCCESS_CODE);
                r.setData(new Register(nick, uInfoEntity.getId(), token));
            }
        } else {
            r.setCode(R.FAILED_CODE);
            r.setMsg("当前昵称已存在");
        }
        return r;
    }

    /**
     * 设置用户Token
     *
     * @param mapper
     * @param id
     * @return
     */
    private String setUserToken(UserInfoMapper mapper, int id) {
        String token = JwtUtils.createToken(id);
        mapper.updateUserToken(id, token);
        return token;
    }

}
