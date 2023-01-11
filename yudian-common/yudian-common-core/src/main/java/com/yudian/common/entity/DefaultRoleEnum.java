package com.yudian.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum DefaultRoleEnum {

    
    ONE(1L),
    
    TWO(2L),
    
    THREE(3L),
    ;

    private Long roleId;
}
