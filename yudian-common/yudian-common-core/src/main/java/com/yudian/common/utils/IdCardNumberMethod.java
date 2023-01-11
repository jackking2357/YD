package com.yudian.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class IdCardNumberMethod {

    
    public static String getBirthFromIdCard(String idCard) {
        if (idCard.length() != 18 && idCard.length() != 15) {
            return "请输入正确的身份证号码";
        }
        if (idCard.length() == 18) {
            String year = idCard.substring(6).substring(0, 4);
            String month = idCard.substring(10).substring(0, 2);
            String day = idCard.substring(12).substring(0, 2);
            return (year + "-" + month + "-" + day);
        } else if (idCard.length() == 15) {
            String year = "19" + idCard.substring(6, 8);
            String month = idCard.substring(8, 10);
            String day = idCard.substring(10, 12);
            return (year + "-" + month + "-" + day);
        }
        return null;
    }

    
    public static Date getBirthDayFromIdCard(String idCard) throws ParseException {
        Date birth = null;
        if (idCard.length() == 18) {
            String year = idCard.substring(6).substring(0, 4);
            String month = idCard.substring(10).substring(0, 2);
            String day = idCard.substring(12).substring(0, 2);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            birth = format.parse(year + "-" + month + "-" + day);
        } else if (idCard.length() == 15) {
            String year = "19" + idCard.substring(6, 8);
            String month = idCard.substring(8, 10);
            String day = idCard.substring(10, 12);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            birth = format.parse(year + "-" + month + "-" + day);
        }
        return birth;
    }

    
    public static int getAgeForIdcard(String idcard) {
        try {
            int age = 0;
            if (StringUtils.isEmpty(idcard)) {
                return age;
            }

            String birth = "";
            if (idcard.length() == 18) {
                birth = idcard.substring(6, 14);
            } else if (idcard.length() == 15) {
                birth = "19" + idcard.substring(6, 12);
            }

            int year = Integer.parseInt(birth.substring(0, 4));
            int month = Integer.parseInt(birth.substring(4, 6));
            int day = Integer.parseInt(birth.substring(6));
            Calendar cal = Calendar.getInstance();
            age = cal.get(Calendar.YEAR) - year;

            if (cal.get(Calendar.MONTH) < (month - 1) || (cal.get(Calendar.MONTH) == (month - 1) && cal.get(Calendar.DATE) < day)) {
                age--;
            }
            return age;
        } catch (Exception e) {
            e.getMessage();
        }
        return -1;
    }

    
    public static StringBuffer IdCardMethod15To18(String idCard) {

        StringBuffer stringBuffer = new StringBuffer(idCard);

        char[] checkIndex = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
        int sum = 0;

        stringBuffer.insert(6, "19");
        for (int i = 0; i < stringBuffer.length(); i++) {
            char c = stringBuffer.charAt(i);

            int ai = Integer.parseInt(String.valueOf(c));

            int wi = ((int) Math.pow(2, stringBuffer.length() - i)) % 11;

            sum = sum + ai * wi;
        }

        int indexOf = sum % 11;

        stringBuffer.append(checkIndex[indexOf]);
        return stringBuffer;
    }

    
    public int getSexFromIdCard(String idCard) {
        int sex = 9;

        if (idCard == "" || idCard.length() <= 0) {
            return sex = 0;
        }
        if (idCard.length() == 18) {
            if (Integer.parseInt(idCard.substring(16).substring(0, 1)) % 2 == 0) {
                sex = 2;
            } else {
                sex = 1;
            }
        } else if (idCard.length() == 15) {
            String usex = idCard.substring(14, 15);
            if (Integer.parseInt(usex) % 2 == 0) {
                sex = 2;
            } else {
                sex = 1;
            }
        }
        return sex;
    }
}

