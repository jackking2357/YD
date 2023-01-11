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
@ApiModel(description = "操作日志记录")
@ExcelIgnoreUnannotated
public class SysOperLogInfoVo {

    /**
     * 日志主键
     */
    @ApiModelProperty(value = "日志主键")
    private Long operId;

    /**
     * 模块标题
     */
    @ApiModelProperty(value = "模块标题")
    @ExcelProperty(value = "模块标题")
    private String title;

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    @ApiModelProperty(value = "业务类型（0其它 1新增 2修改 3删除）")
    @ExcelProperty(value = "业务类型：0=其它；1=新增；2=修改；3=删除")
    private Integer businessType;

    /**
     * 方法名称
     */
    @ApiModelProperty(value = "方法名称")
    @ExcelProperty(value = "方法名称")
    private String method;

    /**
     * 请求方式
     */
    @ApiModelProperty(value = "请求方式")
    @ExcelProperty(value = "请求方式")
    private String requestMethod;

    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
    @ApiModelProperty(value = "操作类别（0其它 1后台用户 2手机端用户）")
    @ExcelProperty(value = "操作类别：0=其它；1=后台用户；2=手机端用户")
    private Integer operatorType;

    /**
     * 操作人员
     */
    @ApiModelProperty(value = "操作人员")
    @ExcelProperty(value = "操作人员")
    private String operName;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    @ExcelProperty(value = "部门名称")
    private String deptName;

    /**
     * 请求URL
     */
    @ApiModelProperty(value = "请求URL")
    @ExcelProperty(value = "请求URL")
    private String operUrl;

    /**
     * 主机地址
     */
    @ApiModelProperty(value = "主机地址")
    @ExcelProperty(value = "主机地址")
    private String operIp;

    /**
     * 操作地点
     */
    @ApiModelProperty(value = "操作地点")
    @ExcelProperty(value = "操作地点")
    private String operLocation;

    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    @ExcelProperty(value = "请求参数")
    private String operParam;

    /**
     * 返回参数
     */
    @ApiModelProperty(value = "返回参数")
    @ExcelProperty(value = "返回参数")
    private String jsonResult;

    /**
     * 操作状态（0正常 1异常）
     */
    @ApiModelProperty(value = "操作状态（0正常 1异常）")
    @ExcelProperty(value = "操作状态：0=正常；1=异常")
    private Integer status;

    /**
     * 错误消息
     */
    @ApiModelProperty(value = "错误消息")
    @ExcelProperty(value = "错误消息")
    private String errorMsg;

    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "操作时间", converter = LocalDateTimeConverter.class)
    private LocalDateTime operTime;

}