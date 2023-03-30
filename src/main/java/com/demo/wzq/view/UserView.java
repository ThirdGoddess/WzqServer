package com.demo.wzq.view;

import com.demo.wzq.model.UserModel;
import com.demo.wzq.model.entity.R;
import com.demo.wzq.model.entity.Register;
import com.demo.wzq.mybatis.MyBatisUtil;
import com.demo.wzq.uitls.TextUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ThirdGoddess
 * @version 1.0.0
 * @time 2023/3/24 17:04
 * @desc
 */
@RestController
@RequestMapping("user")
public class UserView {

    private static final String url_register = "register";
    private static final String url_login = "login";

    private UserModel userModel = new UserModel();

    @PostMapping(value = url_register)
    public R register(String nick, String password) {

        R registerR = new R();

        //判断是否符合入库条件
        if (!TextUtil.isEmpty(nick)) {
            String nickName = nick.trim().replaceAll(" ", "");
            if (!TextUtil.isEmpty(password)) {
                if (nickName.trim().length() <= 6) {
                    if (password.trim().length() >= 6) {
                        if (password.trim().length() <= 18) {
                            if (TextUtil.isPasswordStandard(password.trim())) {
                                R register = userModel.register(registerR, nickName, password);
                                MyBatisUtil.commit();
                                return register;
                            } else {
                                registerR.setCode(R.FAILED_CODE);
                                registerR.setMsg("密码不符合规范，只能由大小写字母和数字组成");
                            }
                        } else {
                            registerR.setCode(R.FAILED_CODE);
                            registerR.setMsg("密码最长18个字符");
                        }
                    } else {
                        registerR.setCode(R.FAILED_CODE);
                        registerR.setMsg("密码设定必须大于6位");
                    }
                } else {
                    registerR.setCode(R.FAILED_CODE);
                    registerR.setMsg("昵称最长6个字符");
                }
            } else {
                registerR.setCode(R.FAILED_CODE);
                registerR.setMsg("密码不可为空");
            }
        } else {
            registerR.setCode(R.FAILED_CODE);
            registerR.setMsg("昵称不可为空");
        }
        return registerR;
    }

    @PostMapping(value = url_login)
    public R login(int account, String password) {
        R registerR = new R();


//        registerR.setCode();

        return registerR;
    }

    @PostMapping(value = "test")
    public R test(){
        R registerR = new R();
        userModel.test();
        registerR.setRespond(R.SUCCESS_CODE);
        return registerR;
    }

}
