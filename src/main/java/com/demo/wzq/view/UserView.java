package com.demo.wzq.view;

import com.alibaba.fastjson2.JSONObject;
import com.demo.wzq.model.UserModel;
import com.demo.wzq.model.entity.base.R;
import com.demo.wzq.mybatis.MyBatisUtil;
import com.demo.wzq.uitls.IpUtil;
import com.demo.wzq.uitls.Log;
import com.demo.wzq.uitls.TextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ThirdGoddess
 * @version 1.0.0
 * @time 2023/3/24 17:04
 * @desc
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserView {

    private static final String url_register = "register";
    private static final String url_login = "login";

    private UserModel userModel = new UserModel();

    /**
     * 注册并登录
     *
     * @param nick
     * @param password
     * @return
     */
    @PostMapping(value = url_register)
    public R register(HttpServletRequest request, String nick, String password) {

        R registerR = new R();

        //判断是否符合入库条件
        if (!TextUtil.isEmpty(nick)) {
            String nickName = nick.trim().replaceAll(" ", "");
            if (!TextUtil.isEmpty(password)) {
                if (nickName.trim().length() <= 6) {
                    if (password.trim().length() >= 6) {
                        if (password.trim().length() <= 18) {
                            if (TextUtil.isPasswordStandard(password.trim())) {
                                registerR = userModel.register(registerR, nickName, password);
                            } else {
                                registerR.setCode(R.FAILED_CODE);
                                registerR.setFailedState("密码只能由大小写字母和数字组成");
                            }
                        } else {
                            registerR.setFailedState("密码最长18个字符");
                        }
                    } else {
                        registerR.setFailedState("密码设定必须大于6位");
                    }
                } else {
                    registerR.setFailedState("昵称最长6个字符");
                }
            } else {
                registerR.setFailedState("密码不可为空");
            }
        } else {
            registerR.setFailedState("昵称不可为空");
        }
        Log.respond(request, registerR);
        return registerR;
    }

    /**
     * 登录
     *
     * @param account
     * @param password
     * @return
     */
    @PostMapping(value = url_login)
    public R login(int account, String password) {
        R registerR = new R();
        userModel.login(registerR, account, password);
        return registerR;
    }

    @PostMapping(value = "test")
    public R test() {
        R registerR = new R();
        userModel.test();
        registerR.setSuccessRespond();
        return registerR;
    }

}
