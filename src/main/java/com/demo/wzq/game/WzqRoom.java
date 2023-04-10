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

    //0-重置对局消息[棋盘,当前座位选手信息,选手准备/取消准备消息]
    //1-双方已准备，对局开始消息
    //2-分配选手执棋消息
    //3-可以下棋消息
    //4-选手[落子,求和,认输,超时,离开]消息，携带棋盘
    //5-对局结果消息
    //6-结算奖励消息
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
    //5-黑子连珠状态（高亮）
    //6-白子连珠状态（高亮）
    @Getter
    private int map[][] = new int[15][15];

    /**
     * 重置棋盘
     */
    public void resettingMap() {
        for (int[] ints : map) Arrays.fill(ints, 0);
    }

    /**
     * 获取房间内所有用户（对局者，观战者）
     */
    public List<User> getAllUser() {
        User userA = getUserA();
        User userB = getUserB();
        List<User> observers = getObservers();
        List<User> allUsers = new ArrayList<>();
        if (null != userA) allUsers.add(userA);
        if (null != userB) allUsers.add(userB);
        allUsers.addAll(observers);
        return allUsers;
    }


}
