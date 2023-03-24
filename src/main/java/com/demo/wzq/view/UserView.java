package com.demo.wzq.view;

import com.demo.wzq.model.UserModel;
import com.demo.wzq.model.entity.R;
import com.demo.wzq.model.entity.Register;
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

    private UserModel userModel= new UserModel();

    @PostMapping(value = url_register)
    public R<Register> register(String nick, String password) {

        R<Register> registerR = new R<>();

        //判断是否符合入库条件
        if (!TextUtil.isEmpty(nick) ) {
            if(!TextUtil.isEmpty(password)){
                if( nick.trim().length() <= 6 ){
                    if(password.trim().length() >= 6){
                        if(password.trim().length() <= 18){
                            userModel.register(registerR);
                        }else{
                            registerR.setMsg("密码最长长度为18位");
                        }
                    }else{
                        registerR.setMsg("密码设定必须大于6位");
                    }
                }else{
                    registerR.setMsg("昵称最长6个字符");
                }
            }else{
                registerR.setMsg("密码不可为空");
            }
        }else{
            registerR.setMsg("昵称不可为空");
        }
        return registerR;
    }

}
