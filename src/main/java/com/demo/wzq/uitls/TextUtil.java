package com.demo.wzq.uitls;

/**
 * @author ThirdGoddess
 * @version 1.0.0
 * @time 2023/3/24 17:09
 * @desc 文本工具类
 */
public class TextUtil {

    private static final String PASSWORD_CONSTITUTE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static boolean isEmpty(String text) {
        return null == text || text.trim().length() == 0;
    }

    public static boolean isPasswordStandard(String passwordText) {
        if (isEmpty(passwordText)) return false;
        char[] chars = passwordText.toCharArray();
        for (char aChar : chars) {
            if (!PASSWORD_CONSTITUTE.contains(String.valueOf(aChar))) {
                return false;
            }
        }
        return true;
    }
}
