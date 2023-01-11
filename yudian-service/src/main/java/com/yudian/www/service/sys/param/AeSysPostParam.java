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
 * 岗位信息表
 *

 * @since 2022-02-12
 */
@Data
@ApiModel(description = "岗位信息表")
@ExcelIgnoreUnannotated
public class AeSysPostParam {

    /**
     * 职位ID
     * bigint(20) 20
     */
    @ApiModelProperty(value = "职位ID(新增不用传，修改要传)", required = false, position = 0)
    @NotNull(groups = {EditDomain.class}, message = "请传入职位ID")
    private Long postId;

    /**
     * 职位编码
     * varchar(64) 64
     */
    @ApiModelProperty(value = "职位编码", required = true, position = 1)
    @Length(max = 64, message = "职位编码参数过长")
    @NotBlank(message = "职位编码参数必传")
    @ExcelProperty(value = "职位编码")
    private String postCode;

    /**
     * 职位名称
     * varchar(50) 50
     */
    @ApiModelProperty(value = "职位名称", required = true, position = 2)
    @Length(max = 50, message = "职位名称参数过长")
    @NotBlank(message = "职位名称参数必传")
    @ExcelProperty(value = "职位名称")
    private String postName;

    /**
     * 显示顺序
     * int(4) 4
     */
    @ApiModelProperty(value = "显示顺序", required = true, position = 3)
    @NotNull(message = "显示顺序参数必传")
    @ExcelProperty(value = "显示顺序")
    private Integer postSort;

    /**
     * 职位状态：1=正常；0=停用；
     * bit(1) 1
     */
    @ApiModelProperty(value = "职位状态：1=正常；0=停用；", required = true, position = 4)
    @NotNull(message = "职位状态：1=正常；0=停用；参数必传")
    @ExcelProperty(value = "职位状态：1=正常；0=停用；")
    private Boolean postStatus;

    public void initParam() {

    }
}