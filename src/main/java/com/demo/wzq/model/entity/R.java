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

    public void setRespond(int code) {
        switch (code) {
            case SUCCESS_CODE:
                setCode(SUCCESS_CODE);
                setMsg(SUCCESS_MSG);
                break;
            case FAILED_CODE:
                setCode(FAILED_CODE);
                setMsg(FAILED_MSG);
                break;
        }
    }


    //    public  void T R<T> getInstance() {
//        return new R<T>();
//    }
}
