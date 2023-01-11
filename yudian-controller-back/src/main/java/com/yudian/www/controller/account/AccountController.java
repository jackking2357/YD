package com.yudian.www.controller.account;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.yudian.common.entity.R;
import com.yudian.common.utils.easyexcel.EasyExcelEntity;
import com.yudian.common.utils.easyexcel.EasyExcelUtils;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.service.account.IAccountServiceProcess;
import com.yudian.www.service.account.param.GetAccountListParam;
import com.yudian.www.service.account.vo.AccountInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 平台用户 前端控制器
 *
 * @author yudian
 * @since 2023-01-05
 */
@Api(tags = "平台用户")
@RestController
@RequestMapping("//account")
public class AccountController {

    @Resource
    private IAccountServiceProcess accountServiceProcess;

//    @ApiOperation(value = "平台用户(添加)", notes = "平台用户(添加)", tags = {})
//    @ApiOperationSupport(order = 1)
//    @PostMapping("/accountAdd")
//    public R accountAdd(@Valid @RequestBody AeAccountParam aeAccountParam) {
//        accountServiceProcess.accountAdd(aeAccountParam);
//        return R.ok(null, "新增成功");
//    }
//
//    @ApiOperation(value = "平台用户(编辑)", notes = "平台用户(编辑)", tags = {})
//    @ApiOperationSupport(order = 2)
//    @PutMapping("/accountEdit")
//    public R accountEdit(@Validated({EditDomain.class}) @RequestBody AeAccountParam aeAccountParam) {
//        accountServiceProcess.accountEdit(aeAccountParam);
//        return R.ok(null, "修改成功");
//    }
//
//    @ApiOperation(value = "平台用户(删除)", notes = "平台用户(删除)", tags = {})
//    @ApiOperationSupport(order = 3)
//    @DeleteMapping("/accountRemove/{accountIds}")
//    public R accountRemove(@ApiParam(name = "accountIds", value = "平台用户id", required = true) @PathVariable Long[] accountIds) {
//        accountServiceProcess.accountRemove(accountIds);
//        return R.ok(null, "删除成功");
//    }

    @ApiOperation(value = "平台用户(列表)", notes = "平台用户(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getAccountList")
    public R<TableRecordVo<AccountInfoVo>> getAccountList(@Valid @RequestBody GetAccountListParam getAccountListParam) {
        getAccountListParam.setShowCert(true);
        TableRecordVo<AccountInfoVo> tableRecordVo = accountServiceProcess.getAccountList(getAccountListParam);
        return R.ok(tableRecordVo, "获取成功");
    }

    @ApiOperation(value = "平台用户(明细)", notes = "平台用户(明细)", tags = {})
    @ApiOperationSupport(order = 5)
    @GetMapping("/getAccountDetail/{accountId}")
    public R<AccountInfoVo> getAccountDetail(@ApiParam(name = "accountId", value = "平台用户id", required = true) @PathVariable Long accountId) {
        AccountInfoVo accountInfoVo = accountServiceProcess.getAccountDetail(accountId);
        return R.ok(accountInfoVo, "获取成功");
    }

    @ApiOperation(value = "平台用户(excel导出)", notes = "平台用户(excel导出)", produces = "application/octet-stream", tags = {})
    @ApiOperationSupport(order = 6)
    @PostMapping("/accountExport")
    public void accountExport(HttpServletResponse response, @Valid @RequestBody GetAccountListParam getAccountListParam) throws IOException {
        TableRecordVo<AccountInfoVo> list = accountServiceProcess.getAccountList(getAccountListParam);
        List<EasyExcelEntity<AccountInfoVo>> tList = new ArrayList<>();
        EasyExcelEntity<AccountInfoVo> easyExcelEntity = new EasyExcelEntity<>();
        easyExcelEntity.setSheetName("平台用户");
        easyExcelEntity.setClazz(AccountInfoVo.class);
        easyExcelEntity.setData(list.getRecords());
        tList.add(easyExcelEntity);
        EasyExcelUtils.webExport(response, "平台用户导出", tList);
    }

//    @ApiOperation(value = "平台用户(excel导入)", notes = "平台用户(excel导入)", tags = {})
//    @ApiOperationSupport(order = 7)
//    @PostMapping("/accountImport")
//    public R accountImport(@RequestParam("file") MultipartFile multipartFile) throws IOException {
//        List<AeAccountParam> dataList = EasyExcel.read(multipartFile.getInputStream()).sheet(0).head(AeAccountParam.class).doReadSync();
//        accountServiceProcess.accountAddBatch(dataList);
//        return R.ok(null, "导入成功");
//    }
//
//    @ApiOperation(value = "平台用户(vue资源下载)", notes = "平台用户(vue资源下载)", tags = {})
//    @ApiOperationSupport(order = 99)
//    @GetMapping(value = "/getAccountVue", produces = "application/octet-stream")
//    public void getAccountVue(HttpServletResponse response) {
//        try {
//            ServletOutputStream out = response.getOutputStream();
//            response.setContentType("application/octet-stream");
//            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("account.zip", "utf-8"));
//            ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(out);
//            zaos.setUseZip64(Zip64Mode.AsNeeded);
//
//            org.springframework.core.io.Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(null)
//            .getResources("classpath:vue/account/account/*");
//            for (org.springframework.core.io.Resource resource : resources) {
//                InputStream inputStream = resource.getInputStream();
//                String filename = resource.getFilename();
//                if (StringUtils.isBlank(filename)) {
//                    continue;
//                }
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                byte[] buffer = new byte[1024];
//                int len;
//                while ((len = inputStream.read(buffer)) != -1) {
//                    baos.write(buffer, 0, len);
//                }
//                baos.flush();
//                byte[] bytes = baos.toByteArray();
//                // 设置文件名
//                ArchiveEntry entry = new ZipArchiveEntry(filename);
//                zaos.putArchiveEntry(entry);
//                zaos.write(bytes);
//                zaos.closeArchiveEntry();
//                inputStream.close();
//                baos.close();
//            }
//            zaos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
