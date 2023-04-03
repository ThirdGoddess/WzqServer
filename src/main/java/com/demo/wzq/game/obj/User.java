package com.demo.wzq.game.obj;

import lombok.*;

/**
 * @author ThirdGoddess
 * @version 1.0.0
 * @time 2023/4/3 15:28
 * @desc 选手信息
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String nick;//用户昵称
    private int account;//用户账号
    private int integral;//用户积分
    private boolean isReady = false;//是否准备
    private long gameTime;//剩余局时
    private long stepTime;//剩余步时
    private int chessType;//执棋信息
}
