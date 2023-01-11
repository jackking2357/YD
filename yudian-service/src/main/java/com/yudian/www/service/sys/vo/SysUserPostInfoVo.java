package com.yudian.www.service.sys.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "用户与岗位关联表")
@ExcelIgnoreUnannotated
public class SysUserPostInfoVo {

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    /**
     * 岗位ID
     */
    @ApiModelProperty(value = "岗位ID")
    private Long postId;

}