package com.yudian.www.base;

public enum BaseRoleEnum {

    
    ONE(1L),
    
    TWO(2L),
    
    THREE(3L),
    ;

    private Long roleId;

    BaseRoleEnum(Long roleId) {
        this.roleId = roleId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
