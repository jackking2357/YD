package com.yudian.www.service.account.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yudian.common.utils.easyexcel.LocalDateTimeConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 平台用户提取记录
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "平台用户提取记录")
@ExcelIgnoreUnannotated
public class AccountExtractInfoVo {

    /**
     * 平台用户提取记录id
     */
    @ApiModelProperty(value = "平台用户提取记录id", dataType = "Long")
    private Long accountExtractId;

    /**
     * 平台用户id
     */
    @ApiModelProperty(value = "平台用户id", dataType = "Long")
    private Long accountId;

    @ApiModelProperty(value = "平台用户信息")
    private AccountInfoVo accountInfoVo;

    /**
     * 提取金额
     */
    @ApiModelProperty(value = "提取金额", dataType = "BigDecimal")
    @ExcelProperty(value = "提取金额")
    private BigDecimal extractAmount;

    /**
     * 审核状态：0=待审核；1=已通过；2=拒绝
     */
    @ApiModelProperty(value = "审核状态：0=待审核；1=已通过；2=拒绝", dataType = "Integer")
    @ExcelProperty(value = "审核状态：0=待审核；1=已通过；2=拒绝")
    private Integer reviewStatus;

    /**
     * 审核备注
     */
    @ApiModelProperty(value = "审核备注", dataType = "String")
    @ExcelProperty(value = "审核备注")
    private String reviewRemark;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "创建时间", converter = LocalDateTimeConverter.class)
    private LocalDateTime createDatetime;

    private BigDecimal oldScore;

    private BigDecimal newScore;

    private String targetZfbUsername;
    private String targetZfbQrCode;
    private BigDecimal sendMoney;
}