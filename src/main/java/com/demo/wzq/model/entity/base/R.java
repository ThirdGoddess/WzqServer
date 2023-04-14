package com.demo.wzq.model.entity.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ThirdGoddess
 * @version 1.0.0
 * @time 2023/3/24 17:12
 * @desc
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class R {
    private int code;
    private String msg;
    private String sneer;
    private Object data;

    public static final int SUCCESS_CODE = 200;
    public static final String SUCCESS_CODE_MESSAGE = "success";

    public static final int FAILED_CODE = 600;
    public static final String FAILED_CODE_MSG = "failed";

    public static final int FAILED_CODE_OTHER_LOGIN = 602;
    public static final String FAILED_CODE_OTHER_LOGIN_MSG = "账号在其它设备登录，请重新登录";

    public void setSuccessRespond() {
        setCode(SUCCESS_CODE);
        setMsg(SUCCESS_CODE_MESSAGE);
    }

    public void setFailedState(String msg) {
        setCode(FAILED_CODE);
        setMsg(msg);
    }

    public void setFailedState(int code) {
        switch (code){

            //异地登录
            case FAILED_CODE_OTHER_LOGIN:
                setCode(FAILED_CODE_OTHER_LOGIN);
                setMsg(FAILED_CODE_OTHER_LOGIN_MSG);
                break;
        }
    }


    //    public  void T R<T> getInstance() {
//        return new R<T>();
//    }
}
