package com.yudian.www.service.account.param;

import com.yudian.www.base.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * 平台用户
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "获取平台用户列表参数")
public class GetAccountListParam extends BaseParam {

    @ApiModelProperty(value = "平台用户id列表", required = false, position = 1)
    private Set<Long> accountIds;


    @ApiModelProperty(value = "手机号码(模糊)")
    private String phoneNumber;
    @ApiModelProperty(value = "手机号码(精确)")
    private String phoneNumbers;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "微信用户唯一标识")
    private String wxUnionid;
    @ApiModelProperty(value = "是否显示证件信息", hidden = true)
    private Boolean showCert;

}