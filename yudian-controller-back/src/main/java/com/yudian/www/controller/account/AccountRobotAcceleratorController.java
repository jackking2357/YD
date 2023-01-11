package com.yudian.www.controller.account;

import com.alibaba.excel.EasyExcel;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
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

    @ApiOperation(value = "平台用户机器人加速器(添加)", notes = "平台用户机器人加速器(添加)", tags = {})
    @ApiOperationSupport(order = 1)
    @PostMapping("/accountRobotAcceleratorAdd")
    public R accountRobotAcceleratorAdd(@Valid @RequestBody AeAccountRobotAcceleratorParam aeAccountRobotAcceleratorParam) {
        accountRobotAcceleratorServiceProcess.accountRobotAcceleratorAdd(aeAccountRobotAcceleratorParam);
        return R.ok(null, "新增成功");
    }

    @ApiOperation(value = "平台用户机器人加速器(编辑)", notes = "平台用户机器人加速器(编辑)", tags = {})
    @ApiOperationSupport(order = 2)
    @PutMapping("/accountRobotAcceleratorEdit")
    public R accountRobotAcceleratorEdit(@Validated({EditDomain.class}) @RequestBody AeAccountRobotAcceleratorParam aeAccountRobotAcceleratorParam) {
        accountRobotAcceleratorServiceProcess.accountRobotAcceleratorEdit(aeAccountRobotAcceleratorParam);
        return R.ok(null, "修改成功");
    }

    @ApiOperation(value = "平台用户机器人加速器(删除)", notes = "平台用户机器人加速器(删除)", tags = {})
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/accountRobotAcceleratorRemove/{accountRobotAcceleratorIds}")
    public R accountRobotAcceleratorRemove(@ApiParam(name = "accountRobotAcceleratorIds", value = "平台用户机器人加速器id", required = true) @PathVariable Long[] accountRobotAcceleratorIds) {
        accountRobotAcceleratorServiceProcess.accountRobotAcceleratorRemove(accountRobotAcceleratorIds);
        return R.ok(null, "删除成功");
    }

    @ApiOperation(value = "平台用户机器人加速器(列表)", notes = "平台用户机器人加速器(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getAccountRobotAcceleratorList")
    public R<TableRecordVo<AccountRobotAcceleratorInfoVo>> getAccountRobotAcceleratorList(@Valid @RequestBody GetAccountRobotAcceleratorListParam getAccountRobotAcceleratorListParam) {
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

    @ApiOperation(value = "平台用户机器人加速器(excel导出)", notes = "平台用户机器人加速器(excel导出)", produces = "application/octet-stream", tags = {})
    @ApiOperationSupport(order = 6)
    @PostMapping("/accountRobotAcceleratorExport")
    public void accountRobotAcceleratorExport(HttpServletResponse response, @Valid @RequestBody GetAccountRobotAcceleratorListParam getAccountRobotAcceleratorListParam) throws IOException {
        TableRecordVo<AccountRobotAcceleratorInfoVo> list = accountRobotAcceleratorServiceProcess.getAccountRobotAcceleratorList(getAccountRobotAcceleratorListParam);
        List<EasyExcelEntity<AccountRobotAcceleratorInfoVo>> tList = new ArrayList<>();
        EasyExcelEntity<AccountRobotAcceleratorInfoVo> easyExcelEntity = new EasyExcelEntity<>();
        easyExcelEntity.setSheetName("平台用户机器人加速器");
        easyExcelEntity.setClazz(AccountRobotAcceleratorInfoVo.class);
        easyExcelEntity.setData(list.getRecords());
        tList.add(easyExcelEntity);
        EasyExcelUtils.webExport(response, "平台用户机器人加速器导出", tList);
    }

    @ApiOperation(value = "平台用户机器人加速器(excel导入)", notes = "平台用户机器人加速器(excel导入)", tags = {})
    @ApiOperationSupport(order = 7)
    @PostMapping("/accountRobotAcceleratorImport")
    public R accountRobotAcceleratorImport(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        List<AeAccountRobotAcceleratorParam> dataList = EasyExcel.read(multipartFile.getInputStream()).sheet(0).head(AeAccountRobotAcceleratorParam.class).doReadSync();
        accountRobotAcceleratorServiceProcess.accountRobotAcceleratorAddBatch(dataList);
        return R.ok(null, "导入成功");
    }

    @ApiOperation(value = "平台用户机器人加速器(vue资源下载)", notes = "平台用户机器人加速器(vue资源下载)", tags = {})
    @ApiOperationSupport(order = 99)
    @GetMapping(value = "/getAccountRobotAcceleratorVue", produces = "application/octet-stream")
    public void getAccountRobotAcceleratorVue(HttpServletResponse response) {
        try {
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("account-robot-accelerator.zip", "utf-8"));
            ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(out);
            zaos.setUseZip64(Zip64Mode.AsNeeded);

            org.springframework.core.io.Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(null)
                    .getResources("classpath:vue/account/account-robot-accelerator/*");
            for (org.springframework.core.io.Resource resource : resources) {
                InputStream inputStream = resource.getInputStream();
                String filename = resource.getFilename();
                if (StringUtils.isBlank(filename)) {
                    continue;
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                baos.flush();
                byte[] bytes = baos.toByteArray();
                // 设置文件名
                ArchiveEntry entry = new ZipArchiveEntry(filename);
                zaos.putArchiveEntry(entry);
                zaos.write(bytes);
                zaos.closeArchiveEntry();
                inputStream.close();
                baos.close();
            }
            zaos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
