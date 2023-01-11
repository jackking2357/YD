package com.yudian.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordCheckUtil {

    
    private static final String REG_NUMBER = ".*\\d+.*";
    
    private static final String REG_UPPERCASE = ".*[A-Z]+.*";
    
    private static final String REG_LOWERCASE = ".*[a-z]+.*";
    
    private static final String REG_SYMBOL = ".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*";

    private static final Pattern p = Pattern.compile("[\u4E00-\u9FA5|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]");

    public static boolean checkPasswordRule(String password) {

        if (password == null || password.length() < 8) return false;
        int i = 0;
        if (password.matches(REG_NUMBER)) {
            i++;
        }
        if (password.matches(REG_LOWERCASE)) {
            i++;
        }
        if (password.matches(REG_UPPERCASE)) {
            i++;
        }
        if (password.matches(REG_SYMBOL)) {
            i++;
        }
        if (i < 1) {
            return false;
        }
        return true;
    }

    
    public static boolean isContainChinese(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
}
