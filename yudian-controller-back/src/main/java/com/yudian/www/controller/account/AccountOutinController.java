package com.yudian.www.controller.account;

import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.alibaba.excel.EasyExcel;
import com.yudian.common.entity.R;
import com.yudian.common.utils.easyexcel.EasyExcelEntity;
import com.yudian.common.utils.easyexcel.EasyExcelUtils;
import com.yudian.www.base.EditDomain;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.service.account.param.AeAccountOutinParam;
import com.yudian.www.service.account.param.GetAccountOutinListParam;
import com.yudian.www.service.account.IAccountOutinServiceProcess;
import com.yudian.www.service.account.vo.AccountOutinInfoVo;
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
import org.springframework.web.bind.annotation.RestController;
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

    @ApiOperation(value = "平台用户流水(添加)", notes = "平台用户流水(添加)", tags = {})
    @ApiOperationSupport(order = 1)
    @PostMapping("/accountOutinAdd")
    public R accountOutinAdd(@Valid @RequestBody AeAccountOutinParam aeAccountOutinParam) {
        accountOutinServiceProcess.accountOutinAdd(aeAccountOutinParam);
        return R.ok(null, "新增成功");
    }

    @ApiOperation(value = "平台用户流水(编辑)", notes = "平台用户流水(编辑)", tags = {})
    @ApiOperationSupport(order = 2)
    @PutMapping("/accountOutinEdit")
    public R accountOutinEdit(@Validated({EditDomain.class}) @RequestBody AeAccountOutinParam aeAccountOutinParam) {
        accountOutinServiceProcess.accountOutinEdit(aeAccountOutinParam);
        return R.ok(null, "修改成功");
    }

    @ApiOperation(value = "平台用户流水(删除)", notes = "平台用户流水(删除)", tags = {})
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/accountOutinRemove/{accountOutinIds}")
    public R accountOutinRemove(@ApiParam(name = "accountOutinIds", value = "平台用户流水id", required = true) @PathVariable Long[] accountOutinIds) {
        accountOutinServiceProcess.accountOutinRemove(accountOutinIds);
        return R.ok(null, "删除成功");
    }

    @ApiOperation(value = "平台用户流水(列表)", notes = "平台用户流水(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getAccountOutinList")
    public R<TableRecordVo<AccountOutinInfoVo>> getAccountOutinList(@Valid @RequestBody GetAccountOutinListParam getAccountOutinListParam) {
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

    @ApiOperation(value = "平台用户流水(excel导出)", notes = "平台用户流水(excel导出)", produces = "application/octet-stream", tags = {})
    @ApiOperationSupport(order = 6)
    @PostMapping("/accountOutinExport")
    public void accountOutinExport(HttpServletResponse response, @Valid @RequestBody GetAccountOutinListParam getAccountOutinListParam) throws IOException {
        TableRecordVo<AccountOutinInfoVo> list = accountOutinServiceProcess.getAccountOutinList(getAccountOutinListParam);
        List<EasyExcelEntity<AccountOutinInfoVo>> tList = new ArrayList<>();
        EasyExcelEntity<AccountOutinInfoVo> easyExcelEntity = new EasyExcelEntity<>();
        easyExcelEntity.setSheetName("平台用户流水");
        easyExcelEntity.setClazz(AccountOutinInfoVo.class);
        easyExcelEntity.setData(list.getRecords());
        tList.add(easyExcelEntity);
        EasyExcelUtils.webExport(response, "平台用户流水导出", tList);
    }

    @ApiOperation(value = "平台用户流水(excel导入)", notes = "平台用户流水(excel导入)", tags = {})
    @ApiOperationSupport(order = 7)
    @PostMapping("/accountOutinImport")
    public R accountOutinImport(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        List<AeAccountOutinParam> dataList = EasyExcel.read(multipartFile.getInputStream()).sheet(0).head(AeAccountOutinParam.class).doReadSync();
        accountOutinServiceProcess.accountOutinAddBatch(dataList);
        return R.ok(null, "导入成功");
    }

    @ApiOperation(value = "平台用户流水(vue资源下载)", notes = "平台用户流水(vue资源下载)", tags = {})
    @ApiOperationSupport(order = 99)
    @GetMapping(value = "/getAccountOutinVue", produces = "application/octet-stream")
    public void getAccountOutinVue(HttpServletResponse response) {
        try {
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("account-outin.zip", "utf-8"));
            ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(out);
            zaos.setUseZip64(Zip64Mode.AsNeeded);

            org.springframework.core.io.Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(null)
            .getResources("classpath:vue/account/account-outin/*");
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
