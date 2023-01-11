package com.yudian.www.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.yudian.auth.utils.LoginUtils;
import com.yudian.common.entity.R;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.service.account.IAccountRobotServiceProcess;
import com.yudian.www.service.account.param.GetAccountRobotListParam;
import com.yudian.www.service.account.vo.AccountRobotInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 平台用户机器人 前端控制器
 *
 * @author yudian
 * @since 2023-01-05
 */
@Api(tags = "平台用户机器人")
@RestController
@RequestMapping("//account-robot")
public class AccountRobotController {

    @Resource
    private IAccountRobotServiceProcess accountRobotServiceProcess;

    @ApiOperation(value = "平台用户机器人(列表)", notes = "平台用户机器人(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getAccountRobotList")
    public R<TableRecordVo<AccountRobotInfoVo>> getAccountRobotList(@Valid @RequestBody GetAccountRobotListParam getAccountRobotListParam) {
        getAccountRobotListParam.setAccountId(LoginUtils.getUserId());
        getAccountRobotListParam.setIsEffective(true);
        TableRecordVo<AccountRobotInfoVo> tableRecordVo = accountRobotServiceProcess.getAccountRobotList(getAccountRobotListParam);
        return R.ok(tableRecordVo, "获取成功");
    }

    @ApiOperation(value = "平台用户机器人(明细)", notes = "平台用户机器人(明细)", tags = {})
    @ApiOperationSupport(order = 5)
    @GetMapping("/getAccountRobotDetail/{accountRobotId}")
    public R<AccountRobotInfoVo> getAccountRobotDetail(@ApiParam(name = "accountRobotId", value = "平台用户机器人id", required = true) @PathVariable Long accountRobotId) {
        AccountRobotInfoVo accountRobotInfoVo = accountRobotServiceProcess.getAccountRobotDetail(accountRobotId);
        return R.ok(accountRobotInfoVo, "获取成功");
    }
}
