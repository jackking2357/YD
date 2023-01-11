package com.yudian.www.service.sys.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yudian.common.utils.easyexcel.LocalDateTimeConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 字典类型表（暂未使用）
 *

 * @since 2022-02-15
 */
@Data
@ApiModel(description = "字典类型表（暂未使用）")
@ExcelIgnoreUnannotated
public class SysDictTypeInfoVo {

    /**
     * 字典主键
     */
    @ApiModelProperty(value = "字典主键")
    private Long dictId;

    /**
     * 字典名称
     */
    @ApiModelProperty(value = "字典名称")
    @ExcelProperty(value = "字典名称")
    private String dictName;

    /**
     * 字典key
     */
    @ApiModelProperty(value = "字典key")
    @ExcelProperty(value = "字典key")
    private String dictKey;

    /**
     * 状态：1=正常；0=停用；
     */
    @ApiModelProperty(value = "状态：1=正常；0=停用；")
    @ExcelProperty(value = "状态：1=正常；0=停用；")
    private Integer dictStatus;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @ExcelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "创建时间", converter = LocalDateTimeConverter.class)
    private LocalDateTime createDatetime;

}