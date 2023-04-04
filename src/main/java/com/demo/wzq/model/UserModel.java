package com.demo.wzq.model;

import com.demo.wzq.model.entity.LoginEntity;
import com.demo.wzq.model.entity.base.R;
import com.demo.wzq.model.entity.RegisterEntity;
import com.demo.wzq.mybatis.MyBatisUtil;
import com.demo.wzq.mybatis.db_entity.UInfoEntity;
import com.demo.wzq.mybatis.db_mapper.UserInfoMapper;
import com.demo.wzq.socket.SocketManager;
import com.demo.wzq.uitls.JwtUtils;
import com.squareup.okhttp.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

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
                SocketManager.sendMessage_3(account);

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

    private final OkHttpClient client = new OkHttpClient();

    public void test() {

        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                String json = "{\"text\":\"你好呀ChatGPT" + getFlag() + "\"}";

                RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

                Request request = new Request.Builder().url("http://doubleshi.com:8081/openai/chat").post(body).build();

                Call call = client.newCall(request);
                try {
                    Response response = call.execute();
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private static int flag = 1;

    private synchronized int getFlag() {
        flag++;
        return flag;
    }

}
