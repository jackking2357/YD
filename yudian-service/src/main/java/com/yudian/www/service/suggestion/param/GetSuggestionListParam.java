package com.yudian.www.service.suggestion.param;

import com.yudian.www.base.BaseParam;
import java.util.Set;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 建议档案
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "获取建议档案列表参数")
public class GetSuggestionListParam extends BaseParam {

    @ApiModelProperty(value = "建议档案id列表", required = false, position = 1)
    private Set<Long>  suggestionIds;

}