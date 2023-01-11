package com.yudian.www.service.sys.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yudian.common.utils.easyexcel.ListConverter;
import com.yudian.common.utils.easyexcel.LocalDateTimeConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@ApiModel(description = "商家信息表")
@ExcelIgnoreUnannotated
public class SysMerchantInfoVo {

    /**
     * 商家ID
     */
    @ApiModelProperty(value = "商家ID")
    private Long userId;

    /**
     * 商家昵称
     */
    @ApiModelProperty(value = "商家昵称")
    @ExcelProperty(value = "商家昵称")
    private String userNickname;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    @ExcelProperty(value = "账号")
    private String userName;

//    /**
//     * 登录密码
//     */
//    @ApiModelProperty(value = "登录密码")
//    @ExcelProperty(value = "登录密码")
//    private String password;

    /**
     * 商家地址
     * varchar(255) 255
     */
    @ApiModelProperty(value = "商家地址")
    @ExcelProperty(value = "商家地址")
    private String userAddress;

    /**
     * 商家邮箱
     */
    @ApiModelProperty(value = "商家邮箱")
    @ExcelProperty(value = "商家邮箱")
    private String userEmail;

    /**
     * 商家电话
     */
    @ApiModelProperty(value = "商家电话")
    @ExcelProperty(value = "商家电话")
    private String userTel;

    /**
     * 商家头像地址
     */
    @ApiModelProperty(value = "商家头像地址")
//    @ExcelProperty(value = "商家头像地址")
    private String userAvatar;

    /**
     * 帐号状态：1=正常；0=冻结
     */
    @ApiModelProperty(value = "帐号状态：1=正常；0=冻结")
    @ExcelProperty(value = "帐号状态：1=正常；0=冻结")
    private Boolean userStatus;

    /**
     * 最后登录IP
     */
    @ApiModelProperty(value = "最后登录IP")
    @ExcelProperty(value = "最后登录IP")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @ApiModelProperty(value = "最后登录时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "最后登录时间", converter = LocalDateTimeConverter.class)
    private LocalDateTime loginDate;

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

    @ApiModelProperty(value = "最后更新时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "最后更新时间", converter = LocalDateTimeConverter.class)
    private LocalDateTime lastUpdateDatetime;

    @ApiModelProperty(value = "角色id列表")
    private Set<Long> roleIds;
    @ApiModelProperty(value = "角色名称列表")
    @ExcelProperty(value = "角色", converter = ListConverter.class)
    private List<String> roleNames;
}