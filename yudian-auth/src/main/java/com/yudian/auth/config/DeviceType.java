package com.yudian.auth.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeviceType {

    
    PC("pc"),

    
    APP("app"),

    
    APPLETS("applets"),
    ;

    private final String device;
}
