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

/**
 * 操作日志记录
 *

 * @since 2022-01-06
 */
@Data
@ApiModel(description = "操作日志记录")
@ExcelIgnoreUnannotated
public class AeSysOperLogParam {

    /**
     * 日志主键
     * bigint(20) 20
     */
    @ApiModelProperty(value = "日志主键(新增不用传，修改要传)", required = false, position = 0)
    @NotNull(groups = {EditDomain.class}, message = "请传入日志主键")
    private Long operId;

    /**
     * 模块标题
     * varchar(50) 50
     */
    @ApiModelProperty(value = "模块标题", required = true, position = 1)
    @Length(max = 50, message = "模块标题参数过长")
    @NotBlank(message = "模块标题参数必传")
    @ExcelProperty(value = "模块标题")
    private String title;

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     * int(2) 2
     */
    @ApiModelProperty(value = "业务类型（0其它 1新增 2修改 3删除）", required = true, position = 2)
    @NotNull(message = "业务类型（0其它 1新增 2修改 3删除）参数必传")
    @ExcelProperty(value = "业务类型（0其它 1新增 2修改 3删除）")
    private Integer businessType;

    /**
     * 方法名称
     * varchar(100) 100
     */
    @ApiModelProperty(value = "方法名称", required = true, position = 3)
    @Length(max = 100, message = "方法名称参数过长")
    @NotBlank(message = "方法名称参数必传")
    @ExcelProperty(value = "方法名称")
    private String method;

    /**
     * 请求方式
     * varchar(10) 10
     */
    @ApiModelProperty(value = "请求方式", required = true, position = 4)
    @Length(max = 10, message = "请求方式参数过长")
    @NotBlank(message = "请求方式参数必传")
    @ExcelProperty(value = "请求方式")
    private String requestMethod;

    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     * int(1) 1
     */
    @ApiModelProperty(value = "操作类别（0其它 1后台用户 2手机端用户）", required = true, position = 5)
    @NotNull(message = "操作类别（0其它 1后台用户 2手机端用户）参数必传")
    @ExcelProperty(value = "操作类别（0其它 1后台用户 2手机端用户）")
    private Integer operatorType;

    /**
     * 操作人员
     * varchar(50) 50
     */
    @ApiModelProperty(value = "操作人员", required = true, position = 6)
    @Length(max = 50, message = "操作人员参数过长")
    @NotBlank(message = "操作人员参数必传")
    @ExcelProperty(value = "操作人员")
    private String operName;

    /**
     * 部门名称
     * varchar(50) 50
     */
    @ApiModelProperty(value = "部门名称", required = true, position = 7)
    @Length(max = 50, message = "部门名称参数过长")
    @NotBlank(message = "部门名称参数必传")
    @ExcelProperty(value = "部门名称")
    private String deptName;

    /**
     * 请求URL
     * varchar(255) 255
     */
    @ApiModelProperty(value = "请求URL", required = true, position = 8)
    @Length(max = 255, message = "请求URL参数过长")
    @NotBlank(message = "请求URL参数必传")
    @ExcelProperty(value = "请求URL")
    private String operUrl;

    /**
     * 主机地址
     * varchar(128) 128
     */
    @ApiModelProperty(value = "主机地址", required = true, position = 9)
    @Length(max = 128, message = "主机地址参数过长")
    @NotBlank(message = "主机地址参数必传")
    @ExcelProperty(value = "主机地址")
    private String operIp;

    /**
     * 操作地点
     * varchar(255) 255
     */
    @ApiModelProperty(value = "操作地点", required = true, position = 10)
    @Length(max = 255, message = "操作地点参数过长")
    @NotBlank(message = "操作地点参数必传")
    @ExcelProperty(value = "操作地点")
    private String operLocation;

    /**
     * 请求参数
     * varchar(2000) 2000
     */
    @ApiModelProperty(value = "请求参数", required = true, position = 11)
    @Length(max = 2000, message = "请求参数参数过长")
    @NotBlank(message = "请求参数参数必传")
    @ExcelProperty(value = "请求参数")
    private String operParam;

    /**
     * 返回参数
     * varchar(2000) 2000
     */
    @ApiModelProperty(value = "返回参数", required = true, position = 12)
    @Length(max = 2000, message = "返回参数参数过长")
    @NotBlank(message = "返回参数参数必传")
    @ExcelProperty(value = "返回参数")
    private String jsonResult;

    /**
     * 操作状态（0正常 1异常）
     * int(1) 1
     */
    @ApiModelProperty(value = "操作状态（0正常 1异常）", required = true, position = 13)
    @NotNull(message = "操作状态（0正常 1异常）参数必传")
    @ExcelProperty(value = "操作状态（0正常 1异常）")
    private Integer status;

    /**
     * 错误消息
     * varchar(2000) 2000
     */
    @ApiModelProperty(value = "错误消息", required = true, position = 14)
    @Length(max = 2000, message = "错误消息参数过长")
    @NotBlank(message = "错误消息参数必传")
    @ExcelProperty(value = "错误消息")
    private String errorMsg;

    /**
     * 操作时间
     * datetime datetim
     */
    @ApiModelProperty(value = "操作时间", required = true, position = 15)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "操作时间参数必传")
    @ExcelProperty(value = "操作时间")
    private LocalDateTime operTime;

    public void initParam() {

    }
}