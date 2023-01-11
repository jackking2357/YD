package com.yudian.common.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMsg {

    @ApiModelProperty(value = "行数")
    private Integer index;

    @ApiModelProperty(value = "表头")
    private String tableLabel;

    @ApiModelProperty(value = "异常信息")
    private String errorMsg;

    @ApiModelProperty(value = "修改意见")
    private String reviseOpinion;
}
