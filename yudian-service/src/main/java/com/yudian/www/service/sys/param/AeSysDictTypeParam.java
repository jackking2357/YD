package com.yudian.www.service.sys.param;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yudian.www.base.EditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 字典类型表（暂未使用）
 *

 * @since 2022-02-15
 */
@Data
@ApiModel(description = "字典类型表（暂未使用）")
@ExcelIgnoreUnannotated
public class AeSysDictTypeParam {

    /**
     * 字典主键
     * bigint(20) 20
     */
    @ApiModelProperty(value = "字典主键(新增不用传，修改要传)", required = false, position = 0)
    @NotNull(groups = {EditDomain.class}, message = "请传入字典主键")
    private Long dictId;

    /**
     * 字典名称
     * varchar(100) 100
     */
    @ApiModelProperty(value = "字典名称", required = true, position = 1)
    @Length(max = 100, message = "字典名称参数过长")
    @NotBlank(message = "字典名称参数必传")
    @ExcelProperty(value = "字典名称")
    private String dictName;

    /**
     * 字典key
     * varchar(100) 100
     */
    @ApiModelProperty(value = "字典key", required = true, position = 2)
    @Length(max = 100, message = "字典key参数过长")
    @NotBlank(message = "字典key参数必传")
    @ExcelProperty(value = "字典key")
    private String dictKey;

    /**
     * 状态：1=正常；0=停用；
     * int(1) 1
     */
    @ApiModelProperty(value = "状态：1=正常；0=停用；", required = true, position = 3)
    @NotNull(message = "状态：1=正常；0=停用；参数必传")
    @ExcelProperty(value = "状态：1=正常；0=停用；")
    private Integer dictStatus;

    /**
     * 备注
     * varchar(500) 500
     */
    @ApiModelProperty(value = "备注", required = false, position = 4)
    @Length(max = 500, message = "备注参数过长")
//    @NotBlank(message = "备注参数必传")
    @ExcelProperty(value = "备注")
    private String remark;

    public void initParam() {

    }
}