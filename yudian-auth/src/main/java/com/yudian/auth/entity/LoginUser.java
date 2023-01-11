package com.yudian.auth.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 1L;

    
    private Long userId;






    
    private String token;

    
    private Long loginTime;

    
    private Long expireTime;

    
    private String ipaddr;

    
    private String loginLocation;

    
    private String browser;

    
    private String os;

    
    private Set<String> menuPermission;

    
    private Set<String> rolePermission;

    
    private String username;

}
