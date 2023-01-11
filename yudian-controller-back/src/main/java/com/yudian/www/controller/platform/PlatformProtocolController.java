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
import com.yudian.www.service.platform.param.AePlatformProtocolParam;
import com.yudian.www.service.platform.param.GetPlatformProtocolListParam;
import com.yudian.www.service.platform.IPlatformProtocolServiceProcess;
import com.yudian.www.service.platform.vo.PlatformProtocolInfoVo;
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
 * 平台协议 前端控制器
 *
 * @author yudian
 * @since 2023-01-05
 */
@Api(tags = "平台协议")
@RestController
@RequestMapping("//platform-protocol")
public class PlatformProtocolController {

    @Resource
    private IPlatformProtocolServiceProcess platformProtocolServiceProcess;

    @ApiOperation(value = "平台协议(添加)", notes = "平台协议(添加)", tags = {})
    @ApiOperationSupport(order = 1)
    @PostMapping("/platformProtocolAdd")
    public R platformProtocolAdd(@Valid @RequestBody AePlatformProtocolParam aePlatformProtocolParam) {
        platformProtocolServiceProcess.platformProtocolAdd(aePlatformProtocolParam);
        return R.ok(null, "新增成功");
    }

    @ApiOperation(value = "平台协议(编辑)", notes = "平台协议(编辑)", tags = {})
    @ApiOperationSupport(order = 2)
    @PutMapping("/platformProtocolEdit")
    public R platformProtocolEdit(@Validated({EditDomain.class}) @RequestBody AePlatformProtocolParam aePlatformProtocolParam) {
        platformProtocolServiceProcess.platformProtocolEdit(aePlatformProtocolParam);
        return R.ok(null, "修改成功");
    }

    @ApiOperation(value = "平台协议(删除)", notes = "平台协议(删除)", tags = {})
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/platformProtocolRemove/{platformProtocolIds}")
    public R platformProtocolRemove(@ApiParam(name = "platformProtocolIds", value = "平台协议id", required = true) @PathVariable Long[] platformProtocolIds) {
        platformProtocolServiceProcess.platformProtocolRemove(platformProtocolIds);
        return R.ok(null, "删除成功");
    }

    @ApiOperation(value = "平台协议(列表)", notes = "平台协议(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getPlatformProtocolList")
    public R<TableRecordVo<PlatformProtocolInfoVo>> getPlatformProtocolList(@Valid @RequestBody GetPlatformProtocolListParam getPlatformProtocolListParam) {
        TableRecordVo<PlatformProtocolInfoVo> tableRecordVo = platformProtocolServiceProcess.getPlatformProtocolList(getPlatformProtocolListParam);
        return R.ok(tableRecordVo, "获取成功");
    }

    @ApiOperation(value = "平台协议(明细)", notes = "平台协议(明细)", tags = {})
    @ApiOperationSupport(order = 5)
    @GetMapping("/getPlatformProtocolDetail/{platformProtocolId}")
    public R<PlatformProtocolInfoVo> getPlatformProtocolDetail(@ApiParam(name = "platformProtocolId", value = "平台协议id", required = true) @PathVariable Long platformProtocolId) {
        PlatformProtocolInfoVo platformProtocolInfoVo = platformProtocolServiceProcess.getPlatformProtocolDetail(platformProtocolId);
        return R.ok(platformProtocolInfoVo, "获取成功");
    }

    @ApiOperation(value = "平台协议(excel导出)", notes = "平台协议(excel导出)", produces = "application/octet-stream", tags = {})
    @ApiOperationSupport(order = 6)
    @PostMapping("/platformProtocolExport")
    public void platformProtocolExport(HttpServletResponse response, @Valid @RequestBody GetPlatformProtocolListParam getPlatformProtocolListParam) throws IOException {
        TableRecordVo<PlatformProtocolInfoVo> list = platformProtocolServiceProcess.getPlatformProtocolList(getPlatformProtocolListParam);
        List<EasyExcelEntity<PlatformProtocolInfoVo>> tList = new ArrayList<>();
        EasyExcelEntity<PlatformProtocolInfoVo> easyExcelEntity = new EasyExcelEntity<>();
        easyExcelEntity.setSheetName("平台协议");
        easyExcelEntity.setClazz(PlatformProtocolInfoVo.class);
        easyExcelEntity.setData(list.getRecords());
        tList.add(easyExcelEntity);
        EasyExcelUtils.webExport(response, "平台协议导出", tList);
    }

    @ApiOperation(value = "平台协议(excel导入)", notes = "平台协议(excel导入)", tags = {})
    @ApiOperationSupport(order = 7)
    @PostMapping("/platformProtocolImport")
    public R platformProtocolImport(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        List<AePlatformProtocolParam> dataList = EasyExcel.read(multipartFile.getInputStream()).sheet(0).head(AePlatformProtocolParam.class).doReadSync();
        platformProtocolServiceProcess.platformProtocolAddBatch(dataList);
        return R.ok(null, "导入成功");
    }

    @ApiOperation(value = "平台协议(vue资源下载)", notes = "平台协议(vue资源下载)", tags = {})
    @ApiOperationSupport(order = 99)
    @GetMapping(value = "/getPlatformProtocolVue", produces = "application/octet-stream")
    public void getPlatformProtocolVue(HttpServletResponse response) {
        try {
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("platform-protocol.zip", "utf-8"));
            ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(out);
            zaos.setUseZip64(Zip64Mode.AsNeeded);

            org.springframework.core.io.Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(null)
            .getResources("classpath:vue/platform/platform-protocol/*");
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
