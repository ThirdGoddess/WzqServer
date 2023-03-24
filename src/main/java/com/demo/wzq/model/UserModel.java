package com.demo.wzq.model;

import com.demo.wzq.model.entity.R;
import com.demo.wzq.mybatis.MyBatisUtil;
import lombok.AllArgsConstructor;

/**
 * @author ThirdGoddess
 * @version 1.0.0
 * @time 2023/3/24 17:26
 * @desc 用户操作相关
 */
public class UserModel extends BaseModel {

    public R register(R r) {


        //入库
        MyBatisUtil.getMapper()


        return r;
    }

}
