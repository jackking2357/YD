package com.yudian.www.service.account.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.yudian.common.utils.easyexcel.LocalDateTimeConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 平台用户流水
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "平台用户流水")
@ExcelIgnoreUnannotated
public class AccountOutinInfoVo {

    /**
     * 平台用户流水记录id
     */
    @ApiModelProperty(value = "平台用户流水记录id", dataType="Long")
    private Long accountOutinId;

    /**
     * 平台用户id
     */
    @ApiModelProperty(value = "平台用户id", dataType="Long")
    private Long accountId;

    /**
     * 流水类目
     */
    @ApiModelProperty(value = "流水类目", dataType="String")
    @ExcelProperty(value = "流水类目")
    private String outinName;

    /**
     * 流水金额
     */
    @ApiModelProperty(value = "流水金额", dataType="BigDecimal")
    @ExcelProperty(value = "流水金额")
    private BigDecimal outinAmount;

    /**
     * 流水状态：0=无，1=审核中，2=已通过
     */
    @ApiModelProperty(value = "流水状态：0=无，1=审核中，2=已通过", dataType="Integer")
    @ExcelProperty(value = "流水状态：0=无，1=审核中，2=已通过")
    private Integer outinStatus;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "创建时间", converter = LocalDateTimeConverter.class)
    private LocalDateTime createDatetime;
}