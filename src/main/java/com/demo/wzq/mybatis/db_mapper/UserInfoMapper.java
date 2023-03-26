package com.demo.wzq.mybatis.db_mapper;

/**
 * @author ThirdGoddess
 * @version 1.0.0
 * @time 2023/3/24 19:55
 * @desc
 */
public interface UserInfoMapper {

    /**
     * 获取用户空间大小
     *
     * @return UInfoEntity
     */
    int addUser(String userNick,int userAccount,String userPassword,int userIntegral ,String userToken);


}
