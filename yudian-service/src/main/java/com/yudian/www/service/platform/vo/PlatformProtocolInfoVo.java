package com.yudian.www.service.platform.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 平台协议
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "平台协议")
@ExcelIgnoreUnannotated
public class PlatformProtocolInfoVo {

    /**
     * 平台协议配置id
     */
    @ApiModelProperty(value = "平台协议配置id", dataType="Long")
    private Long platformProtocolId;

    /**
     * 协议内容
     */
    @ApiModelProperty(value = "协议内容", dataType="String")
    @ExcelProperty(value = "协议内容")
    private String protocolContent;

    /**
     * 协议版本
     */
    @ApiModelProperty(value = "协议版本", dataType="Integer")
    @ExcelProperty(value = "协议版本")
    private Integer protocolVersion;

    /**
     * 协议类型：1=用户协议；2=隐私协议；3=会员协议；4=平台公告
     */
    @ApiModelProperty(value = "协议类型：1=用户协议；2=隐私协议；3=会员协议；4=平台公告", dataType="Integer")
    @ExcelProperty(value = "协议类型：1=用户协议；2=隐私协议；3=会员协议；4=平台公告")
    private Integer protocolType;

}