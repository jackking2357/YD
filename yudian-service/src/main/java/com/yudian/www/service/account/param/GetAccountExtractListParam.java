package com.yudian.www.service.account.param;

import com.yudian.www.base.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * 平台用户提取记录
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "获取平台用户提取记录列表参数")
public class GetAccountExtractListParam extends BaseParam {

    @ApiModelProperty(value = "平台用户提取记录id列表", required = false, position = 1)
    private Set<Long> accountExtractIds;

    private String phoneNumber;
    private Integer reviewStatus;
}