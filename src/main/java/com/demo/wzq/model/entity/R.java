package com.demo.wzq.model.entity;

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
    private Object data;

    public static final int SUCCESS_CODE = 200;
    public static final String SUCCESS_MSG = "success";

    public static final int FAILED_CODE = 600;
    public static final String FAILED_MSG = "failed";

    public void setSuccessRespond() {
        setCode(SUCCESS_CODE);
        setMsg(SUCCESS_MSG);
    }

    public void setFailedState(String msg) {
        setCode(FAILED_CODE);
        setMsg(msg);
    }


    //    public  void T R<T> getInstance() {
//        return new R<T>();
//    }
}
