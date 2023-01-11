package com.yudian.www.service.sys.param;

import com.yudian.www.base.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * 字典类型表（暂未使用）
 *

 * @since 2022-02-15
 */
@Data
@ApiModel(description = "获取字典类型表（暂未使用）列表参数")
public class GetSysDictTypeListParam extends BaseParam {

    @ApiModelProperty(value = "字典类型表（暂未使用）id列表", required = false, position = 1)
    private Set<Long> dictIds;

    @ApiModelProperty(value = "字典名称")
    private String dictName;

    @ApiModelProperty(value = "字典key")
    private String dictKey;

    @ApiModelProperty(value = "状态：1=正常；0=停用；")
    private Integer dictStatus;

}