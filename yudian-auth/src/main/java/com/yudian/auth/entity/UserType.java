package com.yudian.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum UserType {

    
    SYS_USER("sys_user:"),

    
    SYS_ACCOUNT("sys_account:"),

    ;

    private final String userType;
}
