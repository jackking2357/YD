package com.yudian.www.service.sys.param;

import com.yudian.www.base.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * 字典数据表
 *

 * @since 2022-02-15
 */
@Data
@ApiModel(description = "获取字典数据表列表参数")
public class GetSysDictDataListParam extends BaseParam {

    @ApiModelProperty(value = "字典数据表id列表", required = false, position = 1)
    private Set<Long> dictDataIds;

    @ApiModelProperty(value = "字典key")
    private String dictKey;
}