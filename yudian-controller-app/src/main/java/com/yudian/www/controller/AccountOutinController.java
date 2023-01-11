package com.yudian.www.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.yudian.auth.utils.LoginUtils;
import com.yudian.common.entity.R;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.service.account.IAccountOutinServiceProcess;
import com.yudian.www.service.account.param.GetAccountOutinListParam;
import com.yudian.www.service.account.vo.AccountOutinInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 平台用户流水 前端控制器
 *
 * @author yudian
 * @since 2023-01-05
 */
@Api(tags = "平台用户流水")
@RestController
@RequestMapping("//account-outin")
public class AccountOutinController {

    @Resource
    private IAccountOutinServiceProcess accountOutinServiceProcess;

    @ApiOperation(value = "平台用户流水(列表)", notes = "平台用户流水(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getAccountOutinList")
    public R<TableRecordVo<AccountOutinInfoVo>> getAccountOutinList(@Valid @RequestBody GetAccountOutinListParam getAccountOutinListParam) {
        getAccountOutinListParam.setAccountId(LoginUtils.getUserId());
        TableRecordVo<AccountOutinInfoVo> tableRecordVo = accountOutinServiceProcess.getAccountOutinList(getAccountOutinListParam);
        return R.ok(tableRecordVo, "获取成功");
    }

    @ApiOperation(value = "平台用户流水(明细)", notes = "平台用户流水(明细)", tags = {})
    @ApiOperationSupport(order = 5)
    @GetMapping("/getAccountOutinDetail/{accountOutinId}")
    public R<AccountOutinInfoVo> getAccountOutinDetail(@ApiParam(name = "accountOutinId", value = "平台用户流水id", required = true) @PathVariable Long accountOutinId) {
        AccountOutinInfoVo accountOutinInfoVo = accountOutinServiceProcess.getAccountOutinDetail(accountOutinId);
        return R.ok(accountOutinInfoVo, "获取成功");
    }

}
