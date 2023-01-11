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
import com.yudian.www.service.account.param.AeAccountRobotParam;
import com.yudian.www.service.account.param.GetAccountRobotListParam;
import com.yudian.www.service.account.IAccountRobotServiceProcess;
import com.yudian.www.service.account.vo.AccountRobotInfoVo;
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

    @ApiOperation(value = "平台用户机器人(添加)", notes = "平台用户机器人(添加)", tags = {})
    @ApiOperationSupport(order = 1)
    @PostMapping("/accountRobotAdd")
    public R accountRobotAdd(@Valid @RequestBody AeAccountRobotParam aeAccountRobotParam) {
        accountRobotServiceProcess.accountRobotAdd(aeAccountRobotParam);
        return R.ok(null, "新增成功");
    }

    @ApiOperation(value = "平台用户机器人(编辑)", notes = "平台用户机器人(编辑)", tags = {})
    @ApiOperationSupport(order = 2)
    @PutMapping("/accountRobotEdit")
    public R accountRobotEdit(@Validated({EditDomain.class}) @RequestBody AeAccountRobotParam aeAccountRobotParam) {
        accountRobotServiceProcess.accountRobotEdit(aeAccountRobotParam);
        return R.ok(null, "修改成功");
    }

    @ApiOperation(value = "平台用户机器人(删除)", notes = "平台用户机器人(删除)", tags = {})
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/accountRobotRemove/{accountRobotIds}")
    public R accountRobotRemove(@ApiParam(name = "accountRobotIds", value = "平台用户机器人id", required = true) @PathVariable Long[] accountRobotIds) {
        accountRobotServiceProcess.accountRobotRemove(accountRobotIds);
        return R.ok(null, "删除成功");
    }

    @ApiOperation(value = "平台用户机器人(列表)", notes = "平台用户机器人(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getAccountRobotList")
    public R<TableRecordVo<AccountRobotInfoVo>> getAccountRobotList(@Valid @RequestBody GetAccountRobotListParam getAccountRobotListParam) {
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

    @ApiOperation(value = "平台用户机器人(excel导出)", notes = "平台用户机器人(excel导出)", produces = "application/octet-stream", tags = {})
    @ApiOperationSupport(order = 6)
    @PostMapping("/accountRobotExport")
    public void accountRobotExport(HttpServletResponse response, @Valid @RequestBody GetAccountRobotListParam getAccountRobotListParam) throws IOException {
        TableRecordVo<AccountRobotInfoVo> list = accountRobotServiceProcess.getAccountRobotList(getAccountRobotListParam);
        List<EasyExcelEntity<AccountRobotInfoVo>> tList = new ArrayList<>();
        EasyExcelEntity<AccountRobotInfoVo> easyExcelEntity = new EasyExcelEntity<>();
        easyExcelEntity.setSheetName("平台用户机器人");
        easyExcelEntity.setClazz(AccountRobotInfoVo.class);
        easyExcelEntity.setData(list.getRecords());
        tList.add(easyExcelEntity);
        EasyExcelUtils.webExport(response, "平台用户机器人导出", tList);
    }

    @ApiOperation(value = "平台用户机器人(excel导入)", notes = "平台用户机器人(excel导入)", tags = {})
    @ApiOperationSupport(order = 7)
    @PostMapping("/accountRobotImport")
    public R accountRobotImport(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        List<AeAccountRobotParam> dataList = EasyExcel.read(multipartFile.getInputStream()).sheet(0).head(AeAccountRobotParam.class).doReadSync();
        accountRobotServiceProcess.accountRobotAddBatch(dataList);
        return R.ok(null, "导入成功");
    }

    @ApiOperation(value = "平台用户机器人(vue资源下载)", notes = "平台用户机器人(vue资源下载)", tags = {})
    @ApiOperationSupport(order = 99)
    @GetMapping(value = "/getAccountRobotVue", produces = "application/octet-stream")
    public void getAccountRobotVue(HttpServletResponse response) {
        try {
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("account-robot.zip", "utf-8"));
            ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(out);
            zaos.setUseZip64(Zip64Mode.AsNeeded);

            org.springframework.core.io.Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(null)
            .getResources("classpath:vue/account/account-robot/*");
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
