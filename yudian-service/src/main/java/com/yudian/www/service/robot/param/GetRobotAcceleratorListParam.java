package com.yudian.www.service.robot.param;

import com.yudian.www.base.BaseParam;
import java.util.Set;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 机器人加速器
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "获取机器人加速器列表参数")
public class GetRobotAcceleratorListParam extends BaseParam {

    @ApiModelProperty(value = "机器人加速器id列表", required = false, position = 1)
    private Set<Long>  robotAcceleratorIds;

}