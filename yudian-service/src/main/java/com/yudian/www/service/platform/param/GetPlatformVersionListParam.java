package com.yudian.www.service.platform.param;

import com.yudian.www.base.BaseParam;
import java.util.Set;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 平台版本
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "获取平台版本列表参数")
public class GetPlatformVersionListParam extends BaseParam {

    @ApiModelProperty(value = "平台版本id列表", required = false, position = 1)
    private Set<Long>  platformVersionIds;

}