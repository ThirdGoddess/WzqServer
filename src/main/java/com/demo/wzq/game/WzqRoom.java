package com.demo.wzq.game;

import com.demo.wzq.game.obj.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ThirdGoddess
 * @version 1.0.0
 * @time 2023/4/3 15:14
 * @desc
 */
public class WzqRoom {

    //0-下发重置对局消息[棋盘,当前座位选手信息]
    //1-下发对局未开始，选手准备/取消准备消息
    //2-下发双方已准备，对局开始消息
    //3-下发分配选手执棋消息
    //4-下发可以下棋消息
    //5-下发选手[落子,求和,认输,超时,离开]消息，携带棋盘
    //6-下发对局结果消息
    //7-下发结算奖励消息
    @Getter
    @Setter
    private int type;

    //房间号
    @Getter
    @Setter
    private int roomId;

    //选手A
    @Getter
    @Setter
    private User userA;

    //选手B
    @Getter
    @Setter
    private User userB;

    //观战者
    @Getter
    @Setter
    private List<User> observers = new ArrayList<>();

    //棋盘15*15
    //0-未下子
    //1-黑子
    //2-白子
    //3-黑子高亮状态（最新步）
    //4-白子高亮状态（最新步）
    @Getter
    private int map[][] = new int[15][15];

    /**
     * 重置棋盘
     */
    public void resettingMap() {
        for (int[] ints : map) Arrays.fill(ints, 0);
    }


}
