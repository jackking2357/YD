package com.yudian.www.service.sys.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "字典数据表")
@ExcelIgnoreUnannotated
public class SysDictDataInfoVo {

    /**
     * 字典数据id
     */
    @ApiModelProperty(value = "字典数据id")
    private Long dictDataId;

    /**
     * 字典key
     */
    @ApiModelProperty(value = "字典key")
    @ExcelProperty(value = "字典key")
    private String dictKey;

    /**
     * 字典排序
     */
    @ApiModelProperty(value = "字典排序")
    @ExcelProperty(value = "字典排序")
    private Integer dictSort;

    /**
     * 字典标签
     */
    @ApiModelProperty(value = "字典标签")
    @ExcelProperty(value = "字典标签")
    private String dictLabel;

    /**
     * 字典键值类型
     * int(1) 1  字典键值类型：1=字符串；2=图片
     */
    @ApiModelProperty(value = "字典键值类型")
    @ExcelProperty(value = "字典键值类型：1=字符串；2=图片")
    private Integer dictValueType;

    /**
     * 字典键值
     */
    @ApiModelProperty(value = "字典键值")
    @ExcelProperty(value = "字典键值")
    private String dictValue;

    /**
     * 样式属性（其他样式扩展）
     */
    @ApiModelProperty(value = "样式属性（其他样式扩展）")
    @ExcelProperty(value = "样式属性（其他样式扩展）")
    private String cssClass;

    /**
     * 表格回显样式
     */
    @ApiModelProperty(value = "表格回显样式")
    @ExcelProperty(value = "表格回显样式")
    private String listClass;

    /**
     * 是否默认（Y是 N否）
     */
    @ApiModelProperty(value = "是否默认（Y是 N否）")
    @ExcelProperty(value = "是否默认（Y是 N否）")
    private String isDefault;

    /**
     * 字典状态：1=正常；0=停用；
     */
    @ApiModelProperty(value = "字典状态：1=正常；0=停用；")
    @ExcelProperty(value = "字典状态：1=正常；0=停用；")
    private Integer dictStatus;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @ExcelProperty(value = "备注")
    private String remark;

}