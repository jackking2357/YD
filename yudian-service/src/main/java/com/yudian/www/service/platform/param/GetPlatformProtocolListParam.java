package com.yudian.www.service.platform.param;

import com.yudian.www.base.BaseParam;
import java.util.Set;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 平台协议
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "获取平台协议列表参数")
public class GetPlatformProtocolListParam extends BaseParam {

    @ApiModelProperty(value = "平台协议id列表", required = false, position = 1)
    private Set<Long>  platformProtocolIds;

}