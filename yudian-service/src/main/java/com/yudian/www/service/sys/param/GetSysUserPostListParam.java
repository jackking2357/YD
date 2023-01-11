package com.yudian.www.service.sys.param;

import com.yudian.www.base.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

@Data
@ApiModel(description = "获取用户与岗位关联表列表参数")
public class GetSysUserPostListParam extends BaseParam {

    @ApiModelProperty(value = "用户与岗位关联表id列表", required = false, position = 1)
    private Set<Long> userIds;

}