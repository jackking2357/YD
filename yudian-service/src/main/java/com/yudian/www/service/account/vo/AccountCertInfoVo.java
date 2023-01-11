package com.yudian.www.service.account.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 平台用户证件
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "平台用户证件")
@ExcelIgnoreUnannotated
public class AccountCertInfoVo {

    /**
     * 平台用户证件id
     */
    @ApiModelProperty(value = "平台用户证件id", dataType="Long")
    private Long accountCertId;

    /**
     * 证件正面
     */
    @ApiModelProperty(value = "证件正面", dataType="String")
    @ExcelProperty(value = "证件正面")
    private String certPhotoFront;

    /**
     * 证件反面
     */
    @ApiModelProperty(value = "证件反面", dataType="String")
    @ExcelProperty(value = "证件反面")
    private String certPhotoReverse;

    /**
     * 审核状态：0=待审核；1=已通过；2=未通过
     */
    @ApiModelProperty(value = "审核状态：0=待审核；1=已通过；2=未通过", dataType="Integer")
    @ExcelProperty(value = "审核状态：0=待审核；1=已通过；2=未通过")
    private Integer reviewStatus;

    /**
     * 审核备注
     */
    @ApiModelProperty(value = "审核备注", dataType="String")
    @ExcelProperty(value = "审核备注")
    private String reviewRemark;

}