package com.yudian.www.service.sys.param;

import com.yudian.www.base.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * 用户信息表
 *

 * @since 2022-01-06
 */
@Data
@ApiModel(description = "获取用户信息表列表参数")
public class GetSysUserListParam extends BaseParam {

    @ApiModelProperty(value = "用户id列表", required = false, position = 1)
    private Set<Long> userIds;

    @ApiModelProperty(value = "账号", example = "1", required = false, position = 1)
    private String userName;

    @ApiModelProperty(value = "昵称", example = "1", required = false, position = 2)
    private String userNickname;

    @ApiModelProperty(value = "员工状态", example = "true", required = false, position = 3)
    private Boolean userStatus;

    @ApiModelProperty(value = "账户地址")
    private String userAddress;

    @ApiModelProperty(value = "账户邮箱")
    private String userEmail;

    @ApiModelProperty(value = "账户电话")
    private String userTel;

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "部门id")
    private Long deptId;
}