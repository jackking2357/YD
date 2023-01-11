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
 * 字典数据表
 *
 * @since 2022-02-15
 */
@Data
@ApiModel(description = "字典数据表")
@ExcelIgnoreUnannotated
public class AeSysDictDataParam {

    /**
     * 字典数据id
     * bigint(20) 20
     */
    @ApiModelProperty(value = "字典数据id(新增不用传，修改要传)", required = false, position = 0)
    @NotNull(groups = {EditDomain.class}, message = "请传入字典数据id")
    private Long dictDataId;

    /**
     * 字典key
     * varchar(100) 100
     */
    @ApiModelProperty(value = "字典key", required = true, position = 1)
    @Length(max = 100, message = "字典key参数过长")
    @NotBlank(message = "字典key参数必传")
    @ExcelProperty(value = "字典key")
    private String dictKey;

    /**
     * 字典排序
     * int(4) 4
     */
    @ApiModelProperty(value = "字典排序", required = true, position = 2)
    @NotNull(message = "字典排序参数必传")
    @ExcelProperty(value = "字典排序")
    private Integer dictSort;

    /**
     * 字典标签
     * varchar(100) 100
     */
    @ApiModelProperty(value = "字典标签", required = true, position = 3)
    @Length(max = 100, message = "字典标签参数过长")
    @NotBlank(message = "字典标签参数必传")
    @ExcelProperty(value = "字典标签")
    private String dictLabel;

    /**
     * 字典键值
     * varchar(100) 100
     */
    @ApiModelProperty(value = "字典键值", required = true, position = 4)
    @Length(max = 100, message = "字典键值参数过长")
    @NotBlank(message = "字典键值参数必传")
    @ExcelProperty(value = "字典键值")
    private String dictValue;

    @ApiModelProperty(value = "字典键值类型", required = false, position = 4)
    private Integer dictValueType;

    /**
     * 样式属性（其他样式扩展）
     * varchar(100) 100
     */
    @ApiModelProperty(value = "样式属性（其他样式扩展）", required = false, position = 5)
    @Length(max = 100, message = "样式属性（其他样式扩展）参数过长")
//    @NotBlank(message = "样式属性（其他样式扩展）参数必传")
    @ExcelProperty(value = "样式属性（其他样式扩展）")
    private String cssClass;

    /**
     * 表格回显样式
     * varchar(100) 100
     */
    @ApiModelProperty(value = "表格回显样式", required = false, position = 6)
    @Length(max = 100, message = "表格回显样式参数过长")
//    @NotBlank(message = "表格回显样式参数必传")
    @ExcelProperty(value = "表格回显样式")
    private String listClass;

    /**
     * 是否默认（Y是 N否）
     * char(1) 1
     */
    @ApiModelProperty(value = "是否默认（Y是 N否）", required = false, position = 7)
    @Length(max = 1, message = "是否默认（Y是 N否）参数过长")
//    @NotBlank(message = "是否默认（Y是 N否）参数必传")
    @ExcelProperty(value = "是否默认（Y是 N否）")
    private String isDefault;

    /**
     * 字典状态：1=正常；0=停用；
     * int(1) 1
     */
    @ApiModelProperty(value = "字典状态：1=正常；0=停用；", required = true, position = 8)
    @NotNull(message = "字典状态：1=正常；0=停用；参数必传")
    @ExcelProperty(value = "字典状态：1=正常；0=停用；")
    private Integer dictStatus;

    /**
     * 备注
     * varchar(128) 128
     */
    @ApiModelProperty(value = "备注", required = false, position = 9)
    @Length(max = 128, message = "备注参数过长")
//    @NotBlank(message = "备注参数必传")
    @ExcelProperty(value = "备注")
    private String remark;

    public void initParam() {

    }
}