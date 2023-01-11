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
import com.yudian.www.service.account.param.AeAccountRobotWorkParam;
import com.yudian.www.service.account.param.GetAccountRobotWorkListParam;
import com.yudian.www.service.account.IAccountRobotWorkServiceProcess;
import com.yudian.www.service.account.vo.AccountRobotWorkInfoVo;
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
 * 平台用户机器人工作记录 前端控制器
 *
 * @author yudian
 * @since 2023-01-05
 */
@Api(tags = "平台用户机器人工作记录")
@RestController
@RequestMapping("//account-robot-work")
public class AccountRobotWorkController {

    @Resource
    private IAccountRobotWorkServiceProcess accountRobotWorkServiceProcess;

    @ApiOperation(value = "平台用户机器人工作记录(添加)", notes = "平台用户机器人工作记录(添加)", tags = {})
    @ApiOperationSupport(order = 1)
    @PostMapping("/accountRobotWorkAdd")
    public R accountRobotWorkAdd(@Valid @RequestBody AeAccountRobotWorkParam aeAccountRobotWorkParam) {
        accountRobotWorkServiceProcess.accountRobotWorkAdd(aeAccountRobotWorkParam);
        return R.ok(null, "新增成功");
    }

    @ApiOperation(value = "平台用户机器人工作记录(编辑)", notes = "平台用户机器人工作记录(编辑)", tags = {})
    @ApiOperationSupport(order = 2)
    @PutMapping("/accountRobotWorkEdit")
    public R accountRobotWorkEdit(@Validated({EditDomain.class}) @RequestBody AeAccountRobotWorkParam aeAccountRobotWorkParam) {
        accountRobotWorkServiceProcess.accountRobotWorkEdit(aeAccountRobotWorkParam);
        return R.ok(null, "修改成功");
    }

    @ApiOperation(value = "平台用户机器人工作记录(删除)", notes = "平台用户机器人工作记录(删除)", tags = {})
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/accountRobotWorkRemove/{accountRobotWorkIds}")
    public R accountRobotWorkRemove(@ApiParam(name = "accountRobotWorkIds", value = "平台用户机器人工作记录id", required = true) @PathVariable Long[] accountRobotWorkIds) {
        accountRobotWorkServiceProcess.accountRobotWorkRemove(accountRobotWorkIds);
        return R.ok(null, "删除成功");
    }

    @ApiOperation(value = "平台用户机器人工作记录(列表)", notes = "平台用户机器人工作记录(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getAccountRobotWorkList")
    public R<TableRecordVo<AccountRobotWorkInfoVo>> getAccountRobotWorkList(@Valid @RequestBody GetAccountRobotWorkListParam getAccountRobotWorkListParam) {
        TableRecordVo<AccountRobotWorkInfoVo> tableRecordVo = accountRobotWorkServiceProcess.getAccountRobotWorkList(getAccountRobotWorkListParam);
        return R.ok(tableRecordVo, "获取成功");
    }

    @ApiOperation(value = "平台用户机器人工作记录(明细)", notes = "平台用户机器人工作记录(明细)", tags = {})
    @ApiOperationSupport(order = 5)
    @GetMapping("/getAccountRobotWorkDetail/{accountRobotWorkId}")
    public R<AccountRobotWorkInfoVo> getAccountRobotWorkDetail(@ApiParam(name = "accountRobotWorkId", value = "平台用户机器人工作记录id", required = true) @PathVariable Long accountRobotWorkId) {
        AccountRobotWorkInfoVo accountRobotWorkInfoVo = accountRobotWorkServiceProcess.getAccountRobotWorkDetail(accountRobotWorkId);
        return R.ok(accountRobotWorkInfoVo, "获取成功");
    }

    @ApiOperation(value = "平台用户机器人工作记录(excel导出)", notes = "平台用户机器人工作记录(excel导出)", produces = "application/octet-stream", tags = {})
    @ApiOperationSupport(order = 6)
    @PostMapping("/accountRobotWorkExport")
    public void accountRobotWorkExport(HttpServletResponse response, @Valid @RequestBody GetAccountRobotWorkListParam getAccountRobotWorkListParam) throws IOException {
        TableRecordVo<AccountRobotWorkInfoVo> list = accountRobotWorkServiceProcess.getAccountRobotWorkList(getAccountRobotWorkListParam);
        List<EasyExcelEntity<AccountRobotWorkInfoVo>> tList = new ArrayList<>();
        EasyExcelEntity<AccountRobotWorkInfoVo> easyExcelEntity = new EasyExcelEntity<>();
        easyExcelEntity.setSheetName("平台用户机器人工作记录");
        easyExcelEntity.setClazz(AccountRobotWorkInfoVo.class);
        easyExcelEntity.setData(list.getRecords());
        tList.add(easyExcelEntity);
        EasyExcelUtils.webExport(response, "平台用户机器人工作记录导出", tList);
    }

    @ApiOperation(value = "平台用户机器人工作记录(excel导入)", notes = "平台用户机器人工作记录(excel导入)", tags = {})
    @ApiOperationSupport(order = 7)
    @PostMapping("/accountRobotWorkImport")
    public R accountRobotWorkImport(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        List<AeAccountRobotWorkParam> dataList = EasyExcel.read(multipartFile.getInputStream()).sheet(0).head(AeAccountRobotWorkParam.class).doReadSync();
        accountRobotWorkServiceProcess.accountRobotWorkAddBatch(dataList);
        return R.ok(null, "导入成功");
    }

    @ApiOperation(value = "平台用户机器人工作记录(vue资源下载)", notes = "平台用户机器人工作记录(vue资源下载)", tags = {})
    @ApiOperationSupport(order = 99)
    @GetMapping(value = "/getAccountRobotWorkVue", produces = "application/octet-stream")
    public void getAccountRobotWorkVue(HttpServletResponse response) {
        try {
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("account-robot-work.zip", "utf-8"));
            ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(out);
            zaos.setUseZip64(Zip64Mode.AsNeeded);

            org.springframework.core.io.Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(null)
            .getResources("classpath:vue/account/account-robot-work/*");
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
