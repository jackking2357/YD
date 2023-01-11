package com.yudian.www.service.sys.param;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.yudian.www.base.EditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 用户与岗位关联表
 *

 * @since 2022-02-12
 */
@Data
@ApiModel(description = "用户与岗位关联表")
@ExcelIgnoreUnannotated
public class AeSysUserPostParam {

    /**
     * 用户ID
     * bigint(20) 20
     */
    @ApiModelProperty(value = "用户ID(新增不用传，修改要传)", required = false, position = 0)
    @NotNull(groups = {EditDomain.class}, message = "请传入用户ID")
    private Long userId;

    /**
     * 岗位ID
     * bigint(20) 20
     */
    @ApiModelProperty(value = "岗位ID", required = true, position = 1)
    @NotNull(message = "岗位ID参数必传")
    private Long postId;

    public void initParam() {

    }
}