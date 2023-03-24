package com.demo.wzq.model.entity;

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
public class R<T> {
    private int code;
    private String msg;
    private T data;

//    public  void T R<T> getInstance() {
//        return new R<T>();
//    }
}
