package com.demo.wzq.mybatis.db_mapper;

import com.demo.wzq.mybatis.db_entity.UInfoEntity;

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
    int addUser(UInfoEntity entity);

    /**
     * 修改用户Token
     *
     * @param id
     * @param userToken
     * @return
     */
    int updateUserToken(int id, String userToken);

    /**
     * 根据昵称获取id
     *
     * @param nickName
     * @return
     */
    String getNickByNick(String nickName);

    /**
     * 根据id获取用户
     *
     * @param id
     * @return
     */
    UInfoEntity getUserById(int id);

}
