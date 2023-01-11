package com.yudian.www.service.platform.param;

import com.yudian.www.base.BaseParam;
import java.util.Set;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 平台收款钱包
 *
 * @author yudian
 * @since 2023-01-06
 */
@Data
@ApiModel(description = "获取平台收款钱包列表参数")
public class GetPlatformWalletListParam extends BaseParam {

    @ApiModelProperty(value = "平台收款钱包id列表", required = false, position = 1)
    private Set<Long>  platformWalletIds;

}