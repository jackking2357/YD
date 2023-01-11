package com.yudian.www.base;

import com.alibaba.fastjson.JSON;
import com.yudian.www.entity.sys.SysUser;
import com.github.dadiyang.equator.Equator;
import com.github.dadiyang.equator.FieldInfo;
import com.github.dadiyang.equator.GetterBaseEquator;

import java.time.LocalDateTime;
import java.util.List;

public class EquatorTest {
    public static void main(String[] args) {
        SysUser sysUser1 = new SysUser();
        sysUser1.setUserId(0L);
        sysUser1.setDeptId(0L);
        sysUser1.setUserNickname("");
        sysUser1.setUserName("");
        sysUser1.setPassword("");
        sysUser1.setUserType(0);
        sysUser1.setUserAddress("");
        sysUser1.setUserEmail("");
        sysUser1.setUserTel("");
        sysUser1.setUserAvatar("");
        sysUser1.setUserStatus(false);
        sysUser1.setLoginIp("");
        sysUser1.setLoginDate(LocalDateTime.now());
        sysUser1.setRemark("");
        sysUser1.setUserPhone("");
        sysUser1.setUserSex(0);
        sysUser1.setCreateDatetime(LocalDateTime.now());
        sysUser1.setLastUpdateDatetime(LocalDateTime.now());
        sysUser1.setCreateBy(0L);
        sysUser1.setLastUpdateBy(0L);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SysUser sysUser2 = new SysUser();
        sysUser2.setUserId(0L);
        sysUser2.setDeptId(0L);
        sysUser2.setUserNickname("");
        sysUser2.setUserName("");
        sysUser2.setPassword("");
        sysUser2.setUserType(0);
        sysUser2.setUserAddress("");
        sysUser2.setUserEmail("");
        sysUser2.setUserTel("");
        sysUser2.setUserAvatar("");
        sysUser2.setUserStatus(false);
        sysUser2.setLoginIp("");
        sysUser2.setLoginDate(LocalDateTime.now());
        sysUser2.setRemark("");
        sysUser2.setUserPhone("");
        sysUser2.setUserSex(0);
        sysUser2.setCreateDatetime(LocalDateTime.now());
        sysUser2.setLastUpdateDatetime(LocalDateTime.now());
        sysUser2.setCreateBy(0L);
        sysUser2.setLastUpdateBy(0L);


        Equator equator = new GetterBaseEquator();

        equator.isEquals(sysUser1, sysUser2);


        List<FieldInfo> diff = equator.getDiffFields(sysUser1, sysUser2);


        System.out.println(JSON.toJSONString(diff));
    }
}
