package com.yudian.www.service.platform.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 平台版本
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "平台版本")
@ExcelIgnoreUnannotated
public class PlatformVersionInfoVo {

    /**
     * 平台版本id
     */
    @ApiModelProperty(value = "平台版本id", dataType="Integer")
    private Integer platformVersionId;

    /**
     * 版本名称
     */
    @ApiModelProperty(value = "版本名称", dataType="String")
    @ExcelProperty(value = "版本名称")
    private String versionName;

    /**
     * 版本编码
     */
    @ApiModelProperty(value = "版本编码", dataType="Integer")
    @ExcelProperty(value = "版本编码")
    private Integer versionCode;

    /**
     * 版本内容
     */
    @ApiModelProperty(value = "版本内容", dataType="String")
    @ExcelProperty(value = "版本内容")
    private String versionContent;

    /**
     * 版本系统：1=安卓；2=IOS
     */
    @ApiModelProperty(value = "版本系统：1=安卓；2=IOS", dataType="Integer")
    @ExcelProperty(value = "版本系统：1=安卓；2=IOS")
    private Integer versionSystem;

    /**
     * 下载链接
     */
    @ApiModelProperty(value = "下载链接", dataType="String")
    @ExcelProperty(value = "下载链接")
    private String downloadUrl;

    /**
     * 是否强制更新
     */
    @ApiModelProperty(value = "是否强制更新", dataType="String")
    @ExcelProperty(value = "是否强制更新")
    private String isUpdate;

}