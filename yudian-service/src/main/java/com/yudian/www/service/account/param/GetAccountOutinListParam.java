package com.yudian.www.service.account.param;

import com.yudian.www.base.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * 平台用户流水
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "获取平台用户流水列表参数")
public class GetAccountOutinListParam extends BaseParam {

    @ApiModelProperty(value = "平台用户流水id列表", required = false, position = 1)
    private Set<Long> accountOutinIds;


    /**
     * 平台用户id
     */
    @ApiModelProperty(value = "平台用户id", dataType = "Long")
    private Long accountId;
}