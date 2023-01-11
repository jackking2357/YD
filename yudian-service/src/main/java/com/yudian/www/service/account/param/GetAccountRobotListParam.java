package com.yudian.www.service.account.param;

import com.yudian.www.base.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * 平台用户机器人
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "获取平台用户机器人列表参数")
public class GetAccountRobotListParam extends BaseParam {

    @ApiModelProperty(value = "平台用户机器人id列表", required = false, hidden = true, position = 1)
    private Set<Long> accountRobotIds;

    @ApiModelProperty(value = "平台用户id", required = false, hidden = true, position = 1)
    private Long accountId;

    @ApiModelProperty(value = "是否有效", required = false, hidden = true, position = 1)
    private Boolean isEffective;
}