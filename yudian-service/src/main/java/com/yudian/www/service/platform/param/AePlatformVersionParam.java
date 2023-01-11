package com.yudian.www.service.platform.param;

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
 * 平台版本
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "平台版本")
@ExcelIgnoreUnannotated
public class AePlatformVersionParam {

    /**
     * 平台版本id
     * int(11) 11
     */
    @ApiModelProperty(value = "平台版本id(新增不用传，修改要传)", required = false, position = 0)
    @NotNull(groups = {EditDomain.class}, message = "请传入平台版本id")
    private Integer platformVersionId;

    /**
     * 版本名称
     * varchar(24) 24
     */
    @ApiModelProperty(value = "版本名称", required = true, position = 1)
    @Length(max = 24, message = "版本名称参数过长")
    @NotBlank(message = "版本名称参数必传")
    @ExcelProperty(value = "版本名称")
    private String versionName;

    /**
     * 版本编码
     * int(9) 9
     */
    @ApiModelProperty(value = "版本编码", required = true, position = 2)
    @NotNull(message = "版本编码参数必传")
    @ExcelProperty(value = "版本编码")
    private Integer versionCode;

    /**
     * 版本内容
     * text tex
     */
    @ApiModelProperty(value = "版本内容", required = true, position = 3)
    @NotBlank(message = "版本内容参数必传")
    @ExcelProperty(value = "版本内容")
    private String versionContent;

    /**
     * 版本系统：1=安卓；2=IOS
     * int(1) 1
     */
    @ApiModelProperty(value = "版本系统：1=安卓；2=IOS", required = true, position = 4)
    @NotNull(message = "版本系统：1=安卓；2=IOS参数必传")
    @ExcelProperty(value = "版本系统：1=安卓；2=IOS")
    private Integer versionSystem;

    /**
     * 下载链接
     * varchar(255) 255
     */
    @ApiModelProperty(value = "下载链接", required = true, position = 5)
    @Length(max = 255, message = "下载链接参数过长")
    @NotBlank(message = "下载链接参数必传")
    @ExcelProperty(value = "下载链接")
    private String downloadUrl;

    /**
     * 是否强制更新
     * char(5) 5
     */
    @ApiModelProperty(value = "是否强制更新", required = true, position = 6)
    @Length(max = 5, message = "是否强制更新参数过长")
    @NotBlank(message = "是否强制更新参数必传")
    @ExcelProperty(value = "是否强制更新")
    private String isUpdate;

    public void initParam() {

    }
}