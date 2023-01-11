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
import com.yudian.www.service.account.param.AeAccountCertParam;
import com.yudian.www.service.account.param.GetAccountCertListParam;
import com.yudian.www.service.account.IAccountCertServiceProcess;
import com.yudian.www.service.account.vo.AccountCertInfoVo;
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
 * 平台用户证件 前端控制器
 *
 * @author yudian
 * @since 2023-01-05
 */
@Api(tags = "平台用户证件")
@RestController
@RequestMapping("//account-cert")
public class AccountCertController {

    @Resource
    private IAccountCertServiceProcess accountCertServiceProcess;

    @ApiOperation(value = "平台用户证件(添加)", notes = "平台用户证件(添加)", tags = {})
    @ApiOperationSupport(order = 1)
    @PostMapping("/accountCertAdd")
    public R accountCertAdd(@Valid @RequestBody AeAccountCertParam aeAccountCertParam) {
        accountCertServiceProcess.accountCertAdd(aeAccountCertParam);
        return R.ok(null, "新增成功");
    }

    @ApiOperation(value = "平台用户证件(编辑)", notes = "平台用户证件(编辑)", tags = {})
    @ApiOperationSupport(order = 2)
    @PutMapping("/accountCertEdit")
    public R accountCertEdit(@Validated({EditDomain.class}) @RequestBody AeAccountCertParam aeAccountCertParam) {
        accountCertServiceProcess.accountCertEdit(aeAccountCertParam);
        return R.ok(null, "修改成功");
    }

    @ApiOperation(value = "平台用户证件(删除)", notes = "平台用户证件(删除)", tags = {})
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/accountCertRemove/{accountCertIds}")
    public R accountCertRemove(@ApiParam(name = "accountCertIds", value = "平台用户证件id", required = true) @PathVariable Long[] accountCertIds) {
        accountCertServiceProcess.accountCertRemove(accountCertIds);
        return R.ok(null, "删除成功");
    }

    @ApiOperation(value = "平台用户证件(列表)", notes = "平台用户证件(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getAccountCertList")
    public R<TableRecordVo<AccountCertInfoVo>> getAccountCertList(@Valid @RequestBody GetAccountCertListParam getAccountCertListParam) {
        TableRecordVo<AccountCertInfoVo> tableRecordVo = accountCertServiceProcess.getAccountCertList(getAccountCertListParam);
        return R.ok(tableRecordVo, "获取成功");
    }

    @ApiOperation(value = "平台用户证件(明细)", notes = "平台用户证件(明细)", tags = {})
    @ApiOperationSupport(order = 5)
    @GetMapping("/getAccountCertDetail/{accountCertId}")
    public R<AccountCertInfoVo> getAccountCertDetail(@ApiParam(name = "accountCertId", value = "平台用户证件id", required = true) @PathVariable Long accountCertId) {
        AccountCertInfoVo accountCertInfoVo = accountCertServiceProcess.getAccountCertDetail(accountCertId);
        return R.ok(accountCertInfoVo, "获取成功");
    }

    @ApiOperation(value = "平台用户证件(excel导出)", notes = "平台用户证件(excel导出)", produces = "application/octet-stream", tags = {})
    @ApiOperationSupport(order = 6)
    @PostMapping("/accountCertExport")
    public void accountCertExport(HttpServletResponse response, @Valid @RequestBody GetAccountCertListParam getAccountCertListParam) throws IOException {
        TableRecordVo<AccountCertInfoVo> list = accountCertServiceProcess.getAccountCertList(getAccountCertListParam);
        List<EasyExcelEntity<AccountCertInfoVo>> tList = new ArrayList<>();
        EasyExcelEntity<AccountCertInfoVo> easyExcelEntity = new EasyExcelEntity<>();
        easyExcelEntity.setSheetName("平台用户证件");
        easyExcelEntity.setClazz(AccountCertInfoVo.class);
        easyExcelEntity.setData(list.getRecords());
        tList.add(easyExcelEntity);
        EasyExcelUtils.webExport(response, "平台用户证件导出", tList);
    }

    @ApiOperation(value = "平台用户证件(excel导入)", notes = "平台用户证件(excel导入)", tags = {})
    @ApiOperationSupport(order = 7)
    @PostMapping("/accountCertImport")
    public R accountCertImport(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        List<AeAccountCertParam> dataList = EasyExcel.read(multipartFile.getInputStream()).sheet(0).head(AeAccountCertParam.class).doReadSync();
        accountCertServiceProcess.accountCertAddBatch(dataList);
        return R.ok(null, "导入成功");
    }

    @ApiOperation(value = "平台用户证件(vue资源下载)", notes = "平台用户证件(vue资源下载)", tags = {})
    @ApiOperationSupport(order = 99)
    @GetMapping(value = "/getAccountCertVue", produces = "application/octet-stream")
    public void getAccountCertVue(HttpServletResponse response) {
        try {
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("account-cert.zip", "utf-8"));
            ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(out);
            zaos.setUseZip64(Zip64Mode.AsNeeded);

            org.springframework.core.io.Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(null)
            .getResources("classpath:vue/account/account-cert/*");
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
