package com.yudian.www.service.sys.param;

import com.yudian.www.base.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统访问记录
 *

 * @since 2022-01-06
 */
@Data
@ApiModel(description = "获取系统访问记录列表参数")
public class GetSysLogininforListParam extends BaseParam {

    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号")
    private String userName;

    /**
     * 操作的IP地址
     */
    @ApiModelProperty(value = "操作的IP地址")
    private String ipaddr;

    /**
     * 登录状态：1=成功；0=失败
     */
    @ApiModelProperty(value = "登录状态：1=成功；0=失败")
    private Boolean loginStatus;
}