package com.yudian.common.utils.wechat;


import lombok.Data;

import java.io.Serializable;


@Data
public class WeiXinUserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    
    private String subscribe;

    
    private String openid;

    
    private String nickname;

    
    private String sex;

    
    private String language;

    
    private String city;

    
    private String province;

    
    private String country;

    
    private String headimgurl;

    
    private String subscribeTime;

    
    private String remark;

    
    private String subscribeScene;

    
    private String qrScene;

    
    private String qrSceneStr;

    
    private String unionid;

    private String groupid;

}
