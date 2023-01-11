package com.yudian.www.service.suggestion.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yudian.common.utils.easyexcel.LocalDateTimeConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 建议档案
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "建议档案")
@ExcelIgnoreUnannotated
public class SuggestionInfoVo {

    /**
     * 建议id
     */
    @ApiModelProperty(value = "建议id", dataType="Long")
    private Long suggestionId;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容", dataType="String")
    @ExcelProperty(value = "内容")
    private String suggestionContent;

    /**
     * 照片（JSON数组）
     */
    @ApiModelProperty(value = "照片（JSON数组）", dataType="String")
    @ExcelProperty(value = "照片（JSON数组）")
    private String suggestionPhotos;

    /**
     * 处理状态：1=待处理；2=已处理；3=处理中
     */
    @ApiModelProperty(value = "处理状态：1=待处理；2=已处理；3=处理中", dataType="Integer")
    @ExcelProperty(value = "处理状态：1=待处理；2=已处理；3=处理中")
    private Integer suggestionStatus;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "创建时间", converter = LocalDateTimeConverter.class)
    private LocalDateTime createDatetime;
}