package com.yudian.www.service.robot.param;

import com.alibaba.excel.annotation.ExcelProperty;
import com.yudian.www.base.BaseParam;

import java.util.Set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 机器人订单
 *
 * @author yudian
 * @since 2023-01-06
 */
@Data
@ApiModel(description = "获取机器人订单列表参数")
public class GetRobotOrderListParam extends BaseParam {

    @ApiModelProperty(value = "机器人订单id列表", required = false, position = 1)
    private Set<Long> robotOrderIds;

    private String phoneNumber;

    @ApiModelProperty(value = "订单状态：1=待付款；2=已付款；3=订单取消（关闭）；4=审核付款", dataType = "Integer")
    @ExcelProperty(value = "订单状态：1=待付款；2=已付款；3=订单取消（关闭）；4=审核付款")
    private Integer orderStatus;

}