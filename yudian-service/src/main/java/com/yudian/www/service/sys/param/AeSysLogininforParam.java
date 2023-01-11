package com.yudian.www.service.sys.param;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yudian.www.base.EditDomain;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Data
@ApiModel(description = "系统访问记录")
@ExcelIgnoreUnannotated
public class AeSysLogininforParam {


    @ApiModelProperty(value = "访问ID(新增不用传，修改要传)", required = false, position = 0)
    @NotNull(groups = {EditDomain.class}, message = "请传入访问ID")
    private Long infoId;


    @ApiModelProperty(value = "用户账号", required = true, position = 1)
    @Length(max = 50, message = "用户账号参数过长")
    @NotBlank(message = "用户账号参数必传")
    @ExcelProperty(value = "用户账号")
    private String userName;


    @ApiModelProperty(value = "登录IP地址", required = true, position = 2)
    @Length(max = 128, message = "登录IP地址参数过长")
    @NotBlank(message = "登录IP地址参数必传")
    @ExcelProperty(value = "登录IP地址")
    private String ipaddr;


    @ApiModelProperty(value = "登录地点", required = true, position = 3)
    @Length(max = 255, message = "登录地点参数过长")
    @NotBlank(message = "登录地点参数必传")
    @ExcelProperty(value = "登录地点")
    private String loginLocation;


    @ApiModelProperty(value = "浏览器类型", required = true, position = 4)
    @Length(max = 50, message = "浏览器类型参数过长")
    @NotBlank(message = "浏览器类型参数必传")
    @ExcelProperty(value = "浏览器类型")
    private String browser;


    @ApiModelProperty(value = "操作系统", required = true, position = 5)
    @Length(max = 50, message = "操作系统参数过长")
    @NotBlank(message = "操作系统参数必传")
    @ExcelProperty(value = "操作系统")
    private String os;


    @ApiModelProperty(value = "登录状态（0成功 1失败）", required = true, position = 6)
    @Length(max = 1, message = "登录状态（0成功 1失败）参数过长")
    @NotBlank(message = "登录状态（0成功 1失败）参数必传")
    @ExcelProperty(value = "登录状态（0成功 1失败）")
    private String status;


    @ApiModelProperty(value = "提示消息", required = true, position = 7)
    @Length(max = 255, message = "提示消息参数过长")
    @NotBlank(message = "提示消息参数必传")
    @ExcelProperty(value = "提示消息")
    private String msg;


    @ApiModelProperty(value = "访问时间", required = true, position = 8)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "访问时间参数必传")
    @ExcelProperty(value = "访问时间")
    private LocalDateTime loginTime;

    public void initParam() {

    }
}