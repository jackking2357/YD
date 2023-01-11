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
@ApiModel(description = "系统访问记录")
@ExcelIgnoreUnannotated
public class SysLogininforInfoVo {

    /**
     * 访问ID
     */
    @ApiModelProperty(value = "访问ID")
    private Long infoId;

    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号")
    @ExcelProperty(value = "用户账号")
    private String userName;

    /**
     * 登录IP地址
     */
    @ApiModelProperty(value = "登录IP地址")
    @ExcelProperty(value = "登录IP地址")
    private String ipaddr;

    /**
     * 登录地点
     */
    @ApiModelProperty(value = "登录地点")
    @ExcelProperty(value = "登录地点")
    private String loginLocation;

    /**
     * 浏览器类型
     */
    @ApiModelProperty(value = "浏览器类型")
    @ExcelProperty(value = "浏览器类型")
    private String browser;

    /**
     * 操作系统
     */
    @ApiModelProperty(value = "操作系统")
    @ExcelProperty(value = "操作系统")
    private String os;

    /**
     * 登录状态（0成功 1失败）
     */
    @ApiModelProperty(value = "登录状态（1成功 0失败）")
    @ExcelProperty(value = "登录状态：true=成功；false=失败）")
    private Boolean loginStatus;

    /**
     * 提示消息
     */
    @ApiModelProperty(value = "提示消息")
    @ExcelProperty(value = "提示消息")
    private String msg;

    /**
     * 访问时间
     */
    @ApiModelProperty(value = "访问时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "访问时间", converter = LocalDateTimeConverter.class)
    private LocalDateTime loginTime;

}