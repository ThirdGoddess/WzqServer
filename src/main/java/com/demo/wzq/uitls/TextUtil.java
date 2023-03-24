package com.demo.wzq.uitls;

/**
 * @author ThirdGoddess
 * @version 1.0.0
 * @time 2023/3/24 17:09
 * @desc 文本工具类
 */
public class TextUtil {
    public static boolean isEmpty(String text){
        return null == text || text.trim().length() == 0;
    }
}
