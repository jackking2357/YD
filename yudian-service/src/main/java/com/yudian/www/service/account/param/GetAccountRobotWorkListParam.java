package com.yudian.www.service.account.param;

import com.yudian.www.base.BaseParam;
import java.util.Set;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 平台用户机器人工作记录
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "获取平台用户机器人工作记录列表参数")
public class GetAccountRobotWorkListParam extends BaseParam {

    @ApiModelProperty(value = "平台用户机器人工作记录id列表", required = false, position = 1)
    private Set<Long>  accountRobotWorkIds;

}