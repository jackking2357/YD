package com.yudian.www.controller.platform;

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
import com.yudian.www.service.platform.param.AePlatformWalletParam;
import com.yudian.www.service.platform.param.GetPlatformWalletListParam;
import com.yudian.www.service.platform.IPlatformWalletServiceProcess;
import com.yudian.www.service.platform.vo.PlatformWalletInfoVo;
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
 * 平台收款钱包 前端控制器
 *
 * @author yudian
 * @since 2023-01-06
 */
@Api(tags = "平台收款钱包")
@RestController
@RequestMapping("//platform-wallet")
public class PlatformWalletController {

    @Resource
    private IPlatformWalletServiceProcess platformWalletServiceProcess;

    @ApiOperation(value = "平台收款钱包(添加)", notes = "平台收款钱包(添加)", tags = {})
    @ApiOperationSupport(order = 1)
    @PostMapping("/platformWalletAdd")
    public R platformWalletAdd(@Valid @RequestBody AePlatformWalletParam aePlatformWalletParam) {
        platformWalletServiceProcess.platformWalletAdd(aePlatformWalletParam);
        return R.ok(null, "新增成功");
    }

    @ApiOperation(value = "平台收款钱包(编辑)", notes = "平台收款钱包(编辑)", tags = {})
    @ApiOperationSupport(order = 2)
    @PutMapping("/platformWalletEdit")
    public R platformWalletEdit(@Validated({EditDomain.class}) @RequestBody AePlatformWalletParam aePlatformWalletParam) {
        platformWalletServiceProcess.platformWalletEdit(aePlatformWalletParam);
        return R.ok(null, "修改成功");
    }

    @ApiOperation(value = "平台收款钱包(删除)", notes = "平台收款钱包(删除)", tags = {})
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/platformWalletRemove/{platformWalletIds}")
    public R platformWalletRemove(@ApiParam(name = "platformWalletIds", value = "平台收款钱包id", required = true) @PathVariable Long[] platformWalletIds) {
        platformWalletServiceProcess.platformWalletRemove(platformWalletIds);
        return R.ok(null, "删除成功");
    }

    @ApiOperation(value = "平台收款钱包(列表)", notes = "平台收款钱包(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getPlatformWalletList")
    public R<TableRecordVo<PlatformWalletInfoVo>> getPlatformWalletList(@Valid @RequestBody GetPlatformWalletListParam getPlatformWalletListParam) {
        TableRecordVo<PlatformWalletInfoVo> tableRecordVo = platformWalletServiceProcess.getPlatformWalletList(getPlatformWalletListParam);
        return R.ok(tableRecordVo, "获取成功");
    }

    @ApiOperation(value = "平台收款钱包(明细)", notes = "平台收款钱包(明细)", tags = {})
    @ApiOperationSupport(order = 5)
    @GetMapping("/getPlatformWalletDetail/{platformWalletId}")
    public R<PlatformWalletInfoVo> getPlatformWalletDetail(@ApiParam(name = "platformWalletId", value = "平台收款钱包id", required = true) @PathVariable Long platformWalletId) {
        PlatformWalletInfoVo platformWalletInfoVo = platformWalletServiceProcess.getPlatformWalletDetail(platformWalletId);
        return R.ok(platformWalletInfoVo, "获取成功");
    }

    @ApiOperation(value = "平台收款钱包(excel导出)", notes = "平台收款钱包(excel导出)", produces = "application/octet-stream", tags = {})
    @ApiOperationSupport(order = 6)
    @PostMapping("/platformWalletExport")
    public void platformWalletExport(HttpServletResponse response, @Valid @RequestBody GetPlatformWalletListParam getPlatformWalletListParam) throws IOException {
        TableRecordVo<PlatformWalletInfoVo> list = platformWalletServiceProcess.getPlatformWalletList(getPlatformWalletListParam);
        List<EasyExcelEntity<PlatformWalletInfoVo>> tList = new ArrayList<>();
        EasyExcelEntity<PlatformWalletInfoVo> easyExcelEntity = new EasyExcelEntity<>();
        easyExcelEntity.setSheetName("平台收款钱包");
        easyExcelEntity.setClazz(PlatformWalletInfoVo.class);
        easyExcelEntity.setData(list.getRecords());
        tList.add(easyExcelEntity);
        EasyExcelUtils.webExport(response, "平台收款钱包导出", tList);
    }

    @ApiOperation(value = "平台收款钱包(excel导入)", notes = "平台收款钱包(excel导入)", tags = {})
    @ApiOperationSupport(order = 7)
    @PostMapping("/platformWalletImport")
    public R platformWalletImport(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        List<AePlatformWalletParam> dataList = EasyExcel.read(multipartFile.getInputStream()).sheet(0).head(AePlatformWalletParam.class).doReadSync();
        platformWalletServiceProcess.platformWalletAddBatch(dataList);
        return R.ok(null, "导入成功");
    }

    @ApiOperation(value = "平台收款钱包(vue资源下载)", notes = "平台收款钱包(vue资源下载)", tags = {})
    @ApiOperationSupport(order = 99)
    @GetMapping(value = "/getPlatformWalletVue", produces = "application/octet-stream")
    public void getPlatformWalletVue(HttpServletResponse response) {
        try {
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("platform-wallet.zip", "utf-8"));
            ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(out);
            zaos.setUseZip64(Zip64Mode.AsNeeded);

            org.springframework.core.io.Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(null)
            .getResources("classpath:vue/platform/platform-wallet/*");
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
