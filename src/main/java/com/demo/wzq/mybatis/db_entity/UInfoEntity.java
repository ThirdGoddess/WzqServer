package com.demo.wzq.mybatis.db_entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ThirdGoddess
 * @version 1.0.0
 * @time 2023/3/24 16:55
 * @desc
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UInfoEntity {
    private int id;
    private String userNick;
    private String userPassword;
    private String userIntegral;
    private String userToken;
}
