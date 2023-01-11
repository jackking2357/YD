package com.yudian.www.service.account.param;

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
 * 平台用户证件
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "平台用户证件")
@ExcelIgnoreUnannotated
public class AeAccountCertParam {

    /**
     * 平台用户证件id
     * bigint(20) 20
     */
    @ApiModelProperty(value = "平台用户证件id(新增不用传，修改要传)", required = false, position = 0)
    @NotNull(groups = {EditDomain.class}, message = "请传入平台用户证件id")
    private Long accountCertId;

    /**
     * 证件正面
     * varchar(255) 255
     */
    @ApiModelProperty(value = "证件正面", required = true, position = 1)
    @Length(max = 255, message = "证件正面参数过长")
    @NotBlank(message = "证件正面参数必传")
    @ExcelProperty(value = "证件正面")
    private String certPhotoFront;

    /**
     * 证件反面
     * varchar(255) 255
     */
    @ApiModelProperty(value = "证件反面", required = true, position = 2)
    @Length(max = 255, message = "证件反面参数过长")
    @NotBlank(message = "证件反面参数必传")
    @ExcelProperty(value = "证件反面")
    private String certPhotoReverse;

    /**
     * 审核状态：0=待审核；1=已通过；2=未通过
     * int(1) 1
     */
    @ApiModelProperty(value = "审核状态：0=待审核；1=已通过；2=未通过", required = true, position = 3)
    @NotNull(message = "审核状态：0=待审核；1=已通过；2=未通过参数必传")
    @ExcelProperty(value = "审核状态：0=待审核；1=已通过；2=未通过")
    private Integer reviewStatus;

    /**
     * 审核备注
     * varchar(255) 255
     */
    @ApiModelProperty(value = "审核备注", required = true, position = 4)
    @Length(max = 255, message = "审核备注参数过长")
//    @NotBlank(message = "审核备注参数必传")
    @ExcelProperty(value = "审核备注")
    private String reviewRemark;

    public void initParam() {

    }
}