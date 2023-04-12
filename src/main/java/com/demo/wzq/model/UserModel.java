package com.demo.wzq.model;

import com.demo.wzq.model.entity.LoginEntity;
import com.demo.wzq.model.entity.base.R;
import com.demo.wzq.model.entity.RegisterEntity;
import com.demo.wzq.mybatis.MyBatisUtil;
import com.demo.wzq.mybatis.db_entity.UInfoEntity;
import com.demo.wzq.mybatis.db_mapper.UserInfoMapper;
import com.demo.wzq.socket.SocketManager;
import com.demo.wzq.uitls.JwtUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ThirdGoddess
 * @version 1.0.0
 * @time 2023/3/24 17:26
 * @desc 用户操作相关
 */
@Slf4j
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
                r.setSuccessRespond();
                r.setData(new RegisterEntity(nick, uInfoEntity.getId(), token, uInfoEntity.getUserIntegral()));
            }
        } else {
            r.setCode(R.FAILED_CODE);
            r.setMsg("当前昵称已存在");
        }
        return r;
    }

    /**
     * 登录
     *
     * @param r
     * @param account
     * @param password
     * @return
     */
    public R login(R r, int account, String password) {
        //获取mapper
        UserInfoMapper mapper = MyBatisUtil.getMapper(UserInfoMapper.class);
        UInfoEntity userInfo = mapper.getUserById(account);
        if (null != userInfo) {
            String userPassword = userInfo.getUserPassword();
            if (userPassword.equals(getSha1(password))) {

                //只要登录，不管有没有socket连接，向socket发送异地登录消息
                SocketManager.sendMessage(account, 3, "该账号在别处别登录");

                String token = setUserToken(mapper, account);
                r.setSuccessRespond();
                r.setData(new LoginEntity(userInfo.getUserNick(), userInfo.getId(), token, userInfo.getUserIntegral()));
            } else {
                r.setFailedState("用户名或密码错误");
            }
        } else {
            r.setFailedState("用户名或密码错误");
        }
        return r;
    }

    /**
     * Token登录
     *
     * @param r
     * @param userToken
     * @param userId
     * @return
     */
    public R loginByToken(R r, String userToken, int userId) {
        UserInfoMapper mapper = MyBatisUtil.getMapper(UserInfoMapper.class);
        UInfoEntity userInfo = mapper.getUserById(userId);
        if (null != userInfo) {
            if(userToken.equals( userInfo.getUserToken())){
                r.setSuccessRespond();
                r.setData(new LoginEntity(userInfo.getUserNick(), userInfo.getId(), userInfo.getUserToken(), userInfo.getUserIntegral()));
            }else{
                r.setFailedState("账号在其它设备登录，请重新登录");
            }
        } else {
            r.setFailedState("用户异常");
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
