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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterEntity {
    private String userNick;
    private int account;
    private String token;
    private int integral;
}
