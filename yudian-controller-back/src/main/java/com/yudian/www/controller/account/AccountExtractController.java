package com.yudian.www.controller.account;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.yudian.common.entity.R;
import com.yudian.common.utils.easyexcel.EasyExcelEntity;
import com.yudian.common.utils.easyexcel.EasyExcelUtils;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.entity.account.AccountExtract;
import com.yudian.www.entity.account.AccountOutin;
import com.yudian.www.service.account.IAccountExtractService;
import com.yudian.www.service.account.IAccountExtractServiceProcess;
import com.yudian.www.service.account.IAccountOutinService;
import com.yudian.www.service.account.param.AeAccountExtractParam;
import com.yudian.www.service.account.param.GetAccountExtractListParam;
import com.yudian.www.service.account.vo.AccountExtractInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 平台用户提取记录 前端控制器
 *
 * @author yudian
 * @since 2023-01-05
 */
@Api(tags = "平台用户提取记录")
@RestController
@RequiredArgsConstructor
@RequestMapping("//account-extract")
public class AccountExtractController {

    private final IAccountOutinService accountOutinService;
    private final IAccountExtractService accountExtractService;
    private final IAccountExtractServiceProcess accountExtractServiceProcess;

    @ApiOperation(value = "平台用户提取记录(添加)", notes = "平台用户提取记录(添加)", tags = {})
    @ApiOperationSupport(order = 1)
    @PostMapping("/accountExtractAdd")
    public R accountExtractAdd(@Valid @RequestBody AeAccountExtractParam aeAccountExtractParam) {
        accountExtractServiceProcess.accountExtractAdd(aeAccountExtractParam);
        return R.ok(null, "新增成功");
    }

//    @ApiOperation(value = "平台用户提取记录(编辑)", notes = "平台用户提取记录(编辑)", tags = {})
//    @ApiOperationSupport(order = 2)
//    @PutMapping("/accountExtractEdit")
//    public R accountExtractEdit(@Validated({EditDomain.class}) @RequestBody AeAccountExtractParam aeAccountExtractParam) {
//        accountExtractServiceProcess.accountExtractEdit(aeAccountExtractParam);
//        return R.ok(null, "修改成功");
//    }

    @ApiOperation(value = "平台用户提取记录(已打款)", notes = "平台用户提取记录(已打款)", tags = {})
    @ApiOperationSupport(order = 2)
    @PutMapping("/accountExtractSend")
    public R accountExtractSend(@RequestBody JSONObject jsonObject) {
        Long accountExtractId = jsonObject.getLong("accountExtractId");
        BigDecimal sendMoney = jsonObject.getBigDecimal("sendMoney");
        String targetZfbQrCode = jsonObject.getString("targetZfbQrCode");
        String targetZfbUsername = jsonObject.getString("targetZfbUsername");

        AccountExtract accountExtract = accountExtractService.getById(accountExtractId);
        if (null == accountExtract) {
            return R.error(null, "记录不存在");
        }

        Integer reviewStatus = accountExtract.getReviewStatus();
        if (0 != reviewStatus) {
            return R.error(null, "订单非待审核状态");
        }

        accountExtractService.update(Wrappers.<AccountExtract>lambdaUpdate()
                .set(AccountExtract::getReviewStatus, 1)
                .set(AccountExtract::getSendMoney, sendMoney)
                .set(AccountExtract::getTargetZfbQrCode, targetZfbQrCode)
                .set(AccountExtract::getTargetZfbUsername, targetZfbUsername)
                .eq(AccountExtract::getAccountExtractId, accountExtractId));

        accountOutinService.update(Wrappers.<AccountOutin>lambdaUpdate()
                .set(AccountOutin::getOutinStatus, 2)
                .eq(AccountOutin::getOutinTable, "AccountExtract")
                .eq(AccountOutin::getOutinTableId, accountExtractId));
        return R.ok(null, "操作成功");
    }

    @ApiOperation(value = "平台用户提取记录(删除)", notes = "平台用户提取记录(删除)", tags = {})
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/accountExtractRemove/{accountExtractIds}")
    public R accountExtractRemove(@ApiParam(name = "accountExtractIds", value = "平台用户提取记录id", required = true) @PathVariable Long[] accountExtractIds) {
        accountExtractServiceProcess.accountExtractRemove(accountExtractIds);
        return R.ok(null, "删除成功");
    }

    @ApiOperation(value = "平台用户提取记录(列表)", notes = "平台用户提取记录(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getAccountExtractList")
    public R<TableRecordVo<AccountExtractInfoVo>> getAccountExtractList(@Valid @RequestBody GetAccountExtractListParam getAccountExtractListParam) {
        TableRecordVo<AccountExtractInfoVo> tableRecordVo = accountExtractServiceProcess.getAccountExtractList(getAccountExtractListParam);
        return R.ok(tableRecordVo, "获取成功");
    }

    @ApiOperation(value = "平台用户提取记录(明细)", notes = "平台用户提取记录(明细)", tags = {})
    @ApiOperationSupport(order = 5)
    @GetMapping("/getAccountExtractDetail/{accountExtractId}")
    public R<AccountExtractInfoVo> getAccountExtractDetail(@ApiParam(name = "accountExtractId", value = "平台用户提取记录id", required = true) @PathVariable Long accountExtractId) {
        AccountExtractInfoVo accountExtractInfoVo = accountExtractServiceProcess.getAccountExtractDetail(accountExtractId);
        return R.ok(accountExtractInfoVo, "获取成功");
    }

    @ApiOperation(value = "平台用户提取记录(excel导出)", notes = "平台用户提取记录(excel导出)", produces = "application/octet-stream", tags = {})
    @ApiOperationSupport(order = 6)
    @PostMapping("/accountExtractExport")
    public void accountExtractExport(HttpServletResponse response, @Valid @RequestBody GetAccountExtractListParam getAccountExtractListParam) throws IOException {
        TableRecordVo<AccountExtractInfoVo> list = accountExtractServiceProcess.getAccountExtractList(getAccountExtractListParam);
        List<EasyExcelEntity<AccountExtractInfoVo>> tList = new ArrayList<>();
        EasyExcelEntity<AccountExtractInfoVo> easyExcelEntity = new EasyExcelEntity<>();
        easyExcelEntity.setSheetName("平台用户提取记录");
        easyExcelEntity.setClazz(AccountExtractInfoVo.class);
        easyExcelEntity.setData(list.getRecords());
        tList.add(easyExcelEntity);
        EasyExcelUtils.webExport(response, "平台用户提取记录导出", tList);
    }

    @ApiOperation(value = "平台用户提取记录(excel导入)", notes = "平台用户提取记录(excel导入)", tags = {})
    @ApiOperationSupport(order = 7)
    @PostMapping("/accountExtractImport")
    public R accountExtractImport(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        List<AeAccountExtractParam> dataList = EasyExcel.read(multipartFile.getInputStream()).sheet(0).head(AeAccountExtractParam.class).doReadSync();
        accountExtractServiceProcess.accountExtractAddBatch(dataList);
        return R.ok(null, "导入成功");
    }

    @ApiOperation(value = "平台用户提取记录(vue资源下载)", notes = "平台用户提取记录(vue资源下载)", tags = {})
    @ApiOperationSupport(order = 99)
    @GetMapping(value = "/getAccountExtractVue", produces = "application/octet-stream")
    public void getAccountExtractVue(HttpServletResponse response) {
        try {
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("account-extract.zip", "utf-8"));
            ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(out);
            zaos.setUseZip64(Zip64Mode.AsNeeded);

            org.springframework.core.io.Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(null)
                    .getResources("classpath:vue/account/account-extract/*");
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
