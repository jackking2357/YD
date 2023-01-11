package com.yudian.common.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Base64;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {

    private static final String salt = "BI/N1gt/sdq6fdMI45NhEA==";

    
    public static String encryptPassword(String password) {


        AES aes = SecureUtil.aes(Base64.getDecoder().decode(salt));
        return aes.encryptHex(password);
    }

    
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {


        AES aes = SecureUtil.aes(Base64.getDecoder().decode(salt));
        return aes.encryptHex(rawPassword).equals(encodedPassword);
    }

    
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    public static void main(String[] args) {
        System.out.println(encryptPassword("admin123"));
    }
}
