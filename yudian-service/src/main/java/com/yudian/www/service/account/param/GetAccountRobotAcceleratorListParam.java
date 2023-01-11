package com.yudian.www.service.account.param;

import com.yudian.www.base.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * 平台用户机器人加速器
 *
 * @author yudian
 * @since 2023-01-07
 */
@Data
@ApiModel(description = "获取平台用户机器人加速器列表参数")
public class GetAccountRobotAcceleratorListParam extends BaseParam {

    @ApiModelProperty(value = "平台用户机器人加速器id列表", required = false, position = 1)
    private Set<Long> accountRobotAcceleratorIds;

    @ApiModelProperty(value = "平台用户id", required = false, hidden = true, position = 1)
    private Long accountId;
}