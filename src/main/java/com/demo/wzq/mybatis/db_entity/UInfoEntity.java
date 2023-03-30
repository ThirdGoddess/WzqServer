package com.demo.wzq.mybatis.db_entity;

import lombok.*;

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
@ToString
public class UInfoEntity {
    private int id;
    private String userNick;
    private String userPassword;
    private int userIntegral;
    private String userToken;

    public UInfoEntity(String userNick, String userPassword, int userIntegral, String userToken) {
        this.userNick = userNick;
        this.userPassword = userPassword;
        this.userIntegral = userIntegral;
        this.userToken = userToken;
    }
}
