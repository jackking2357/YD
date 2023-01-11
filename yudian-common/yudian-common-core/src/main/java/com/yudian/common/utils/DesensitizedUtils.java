package com.yudian.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class DesensitizedUtils {

    
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {



        String regExp = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[3,5,6,7,8])" +
                "|(18[0-9])|(19[8,9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }


    public static String desensitizedMobile(String mobile) {
        if (StringUtils.isBlank(mobile) || (mobile.length() != 11)) {
            return mobile;
        }
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }


    public static String desensitizedIdNumber(String idNumber) {
        if (StringUtils.isNotBlank(idNumber)) {
            if (idNumber.length() == 15) {
                idNumber = idNumber.replaceAll("(\\w{6})\\w*(\\w{3})", "$1******$2");
                return idNumber;
            }
            if (idNumber.length() == 18) {
                idNumber = idNumber.replaceAll("(\\w{6})\\w*(\\w{3})", "$1*********$2");
                return idNumber;
            }
            idNumber = idNumber.replaceAll("(\\w{6})\\w*(\\w{3})", "$1*********$2");
        }
        return idNumber;
    }


    public static String desensitizedName(String fullName) {
        if (StringUtils.isNotBlank(fullName)) {
            String name = StringUtils.right(fullName, 1);
            return StringUtils.leftPad(name, StringUtils.length(fullName), "*");
        }
        return fullName;
    }


    public static String desensitizedBankAcct(String bankAcct) {
        if (bankAcct == null) {
            return "";
        }
        return replaceBetween(bankAcct, 6, bankAcct.length() - 4, null);
    }


    public static String desensitizedBank(String bankAcct) {
        if (bankAcct == null) {
            return "";
        }
        return replaceBetween(bankAcct, 0, bankAcct.length() - 4, null);
    }

    
    private static String replaceBetween(String sourceStr, int begin, int end, String replacement) {
        if (sourceStr == null) {
            return "";
        }
        if (replacement == null) {
            replacement = "*";
        }
        int replaceLength = end - begin;
        if (StringUtils.isNotBlank(sourceStr) && replaceLength > 0) {
            StringBuilder sb = new StringBuilder(sourceStr);
            sb.replace(begin, end, StringUtils.repeat(replacement, replaceLength));
            return sb.toString();
        } else {
            return sourceStr;
        }
    }

    public static void main(String[] args) {
        System.out.println(DesensitizedUtils.desensitizedIdNumber("441821199709150612"));
        System.out.println(DesensitizedUtils.desensitizedName("曾华"));
        System.out.println(DesensitizedUtils.desensitizedBank("62220186016"));
        System.out.println(DesensitizedUtils.desensitizedMobile("15811726527"));
    }
}
