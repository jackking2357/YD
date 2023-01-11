package com.yudian.www.service.platform.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.yudian.www.base.EditDomain;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
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
public class AePlatformProtocolParam {

    /**
     * 平台协议配置id
     * bigint(20) 20
     */
    @ApiModelProperty(value = "平台协议配置id(新增不用传，修改要传)", required = false, position = 0)
    @NotNull(groups = {EditDomain.class}, message = "请传入平台协议配置id")
    private Long platformProtocolId;

    /**
     * 协议内容
     * text tex
     */
    @ApiModelProperty(value = "协议内容", required = true, position = 1)
    @NotBlank(message = "协议内容参数必传")
    @ExcelProperty(value = "协议内容")
    private String protocolContent;

    /**
     * 协议版本
     * int(10) 10
     */
    @ApiModelProperty(value = "协议版本", required = true, position = 2)
    @NotNull(message = "协议版本参数必传")
    @ExcelProperty(value = "协议版本")
    private Integer protocolVersion;

    /**
     * 协议类型：1=用户协议；2=隐私协议；3=会员协议；4=平台公告
     * int(1) 1
     */
    @ApiModelProperty(value = "协议类型：1=用户协议；2=隐私协议；3=会员协议；4=平台公告", required = true, position = 3)
    @NotNull(message = "协议类型：1=用户协议；2=隐私协议；3=会员协议；4=平台公告参数必传")
    @ExcelProperty(value = "协议类型：1=用户协议；2=隐私协议；3=会员协议；4=平台公告")
    private Integer protocolType;

    public void initParam() {

    }
}