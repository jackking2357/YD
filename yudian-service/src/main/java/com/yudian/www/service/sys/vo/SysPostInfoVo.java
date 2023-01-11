package com.yudian.www.service.sys.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yudian.common.utils.easyexcel.LocalDateTimeConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "岗位信息表")
@ExcelIgnoreUnannotated
public class SysPostInfoVo {

    /**
     * 职位ID
     */
    @ApiModelProperty(value = "职位ID")
    private Long postId;

    /**
     * 职位编码
     */
    @ApiModelProperty(value = "职位编码")
    @ExcelProperty(value = "职位编码")
    private String postCode;

    /**
     * 职位名称
     */
    @ApiModelProperty(value = "职位名称")
    @ExcelProperty(value = "职位名称")
    private String postName;

    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序")
    @ExcelProperty(value = "显示顺序")
    private Integer postSort;

    /**
     * 职位状态：1=正常；0=停用；
     */
    @ApiModelProperty(value = "职位状态：1=正常；0=停用；")
    @ExcelProperty(value = "职位状态：1=正常；0=停用；")
    private Boolean postStatus;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "创建时间", converter = LocalDateTimeConverter.class)
    private LocalDateTime createDatetime;
}