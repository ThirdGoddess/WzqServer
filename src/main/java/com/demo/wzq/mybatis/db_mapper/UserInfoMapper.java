package com.demo.wzq.mybatis.db_mapper;

import com.demo.wzq.mybatis.db_entity.UInfoEntity;

/**
 * @author ThirdGoddess
 * @version 1.0.0
 * @time 2023/3/24 19:55
 * @desc
 */
public class UserInfoMapper {

    /**
     * 获取用户空间大小
     *
     * @param id 用户id
     * @return UInfoEntity
     */
    UInfoEntity getUserSpace(int id);


    /**
     * 修改用户已使用空间大小-必须使用安全的线程调用
     *
     * @param data UInfoEntity
     */
    void updateUserUsedSpace(UInfoEntity data);

    /**
     * 添加用户
     *
     * @param data UInfoEntity
     * @return index
     */
    int addAccount(UInfoEntity data);

    /**
     * 根据手机号获取用户
     *
     * @param phone 手机号
     * @return UInfoEntity
     */
    UInfoEntity getUserByPhone(@Param(value = "phone") String phone);

    /**
     * 根据id获取用户
     *
     * @param id id
     * @return UInfoEntity
     */
    UInfoEntity getUserById(@Param(value = "id") int id);

    /**
     * 更新用户salt，移动设备
     *
     * @param salt Token salt
     * @return index
     */
    int updateUserSaltOfMobileDevice(@Param(value = "salt") String salt, @Param(value = "userId") int userId);

    /**
     * 更新用户salt，Web端
     *
     * @param salt Token salt
     * @return index
     */
    int updateUserSaltOfWeb(@Param(value = "salt") String salt, @Param(value = "userId") int userId);

    /**
     * 获取移动设备salt
     *
     * @param userId userId
     * @return Salt String
     */
    String getSaltOfMobileDevice(@Param(value = "userId") int userId);

    /**
     * 获取web端salt
     *
     * @param userId userId
     * @return Salt String
     */
    String getSaltOfWeb(@Param(value = "userId") int userId);
}
