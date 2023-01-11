package com.yudian.www.controller;

import com.alibaba.excel.EasyExcel;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.yudian.auth.utils.LoginUtils;
import com.yudian.common.entity.R;
import com.yudian.common.utils.easyexcel.EasyExcelEntity;
import com.yudian.common.utils.easyexcel.EasyExcelUtils;
import com.yudian.www.base.EditDomain;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.service.account.IAccountRobotAcceleratorServiceProcess;
import com.yudian.www.service.account.param.AeAccountRobotAcceleratorParam;
import com.yudian.www.service.account.param.GetAccountRobotAcceleratorListParam;
import com.yudian.www.service.account.vo.AccountRobotAcceleratorInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 平台用户机器人加速器 前端控制器
 *
 * @author yudian
 * @since 2023-01-07
 */
@Api(tags = "平台用户机器人加速器")
@RestController
@RequestMapping("//account-robot-accelerator")
public class AccountRobotAcceleratorController {

    @Resource
    private IAccountRobotAcceleratorServiceProcess accountRobotAcceleratorServiceProcess;

    @ApiOperation(value = "平台用户机器人加速器(列表)", notes = "平台用户机器人加速器(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getAccountRobotAcceleratorList")
    public R<TableRecordVo<AccountRobotAcceleratorInfoVo>> getAccountRobotAcceleratorList(@Valid @RequestBody GetAccountRobotAcceleratorListParam getAccountRobotAcceleratorListParam) {
        getAccountRobotAcceleratorListParam.setAccountId(LoginUtils.getUserId());
        TableRecordVo<AccountRobotAcceleratorInfoVo> tableRecordVo = accountRobotAcceleratorServiceProcess.getAccountRobotAcceleratorList(getAccountRobotAcceleratorListParam);
        return R.ok(tableRecordVo, "获取成功");
    }

    @ApiOperation(value = "平台用户机器人加速器(明细)", notes = "平台用户机器人加速器(明细)", tags = {})
    @ApiOperationSupport(order = 5)
    @GetMapping("/getAccountRobotAcceleratorDetail/{accountRobotAcceleratorId}")
    public R<AccountRobotAcceleratorInfoVo> getAccountRobotAcceleratorDetail(@ApiParam(name = "accountRobotAcceleratorId", value = "平台用户机器人加速器id", required = true) @PathVariable Long accountRobotAcceleratorId) {
        AccountRobotAcceleratorInfoVo accountRobotAcceleratorInfoVo = accountRobotAcceleratorServiceProcess.getAccountRobotAcceleratorDetail(accountRobotAcceleratorId);
        return R.ok(accountRobotAcceleratorInfoVo, "获取成功");
    }
}
