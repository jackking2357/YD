package com.yudian.www.service.sys.param;

import com.yudian.www.base.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * 岗位信息表
 *

 * @since 2022-02-12
 */
@Data
@ApiModel(description = "获取岗位信息表列表参数")
public class GetSysPostListParam extends BaseParam {

    @ApiModelProperty(value = "岗位信息表id列表", required = false, position = 1)
    private Set<Long> postIds;

    @ApiModelProperty(value = "岗位状态", required = false, position = 2)
    private Boolean postStatus;

    @ApiModelProperty(value = "岗位编码", required = false, position = 3)
    private String postCode;

    @ApiModelProperty(value = "岗位名称", required = false, position = 4)
    private String postName;

}