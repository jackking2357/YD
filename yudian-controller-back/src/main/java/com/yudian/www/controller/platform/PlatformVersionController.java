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
import com.yudian.www.service.platform.param.AePlatformVersionParam;
import com.yudian.www.service.platform.param.GetPlatformVersionListParam;
import com.yudian.www.service.platform.IPlatformVersionServiceProcess;
import com.yudian.www.service.platform.vo.PlatformVersionInfoVo;
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
 * 平台版本 前端控制器
 *
 * @author yudian
 * @since 2023-01-05
 */
@Api(tags = "平台版本")
@RestController
@RequestMapping("//platform-version")
public class PlatformVersionController {

    @Resource
    private IPlatformVersionServiceProcess platformVersionServiceProcess;

    @ApiOperation(value = "平台版本(添加)", notes = "平台版本(添加)", tags = {})
    @ApiOperationSupport(order = 1)
    @PostMapping("/platformVersionAdd")
    public R platformVersionAdd(@Valid @RequestBody AePlatformVersionParam aePlatformVersionParam) {
        platformVersionServiceProcess.platformVersionAdd(aePlatformVersionParam);
        return R.ok(null, "新增成功");
    }

    @ApiOperation(value = "平台版本(编辑)", notes = "平台版本(编辑)", tags = {})
    @ApiOperationSupport(order = 2)
    @PutMapping("/platformVersionEdit")
    public R platformVersionEdit(@Validated({EditDomain.class}) @RequestBody AePlatformVersionParam aePlatformVersionParam) {
        platformVersionServiceProcess.platformVersionEdit(aePlatformVersionParam);
        return R.ok(null, "修改成功");
    }

    @ApiOperation(value = "平台版本(删除)", notes = "平台版本(删除)", tags = {})
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/platformVersionRemove/{platformVersionIds}")
    public R platformVersionRemove(@ApiParam(name = "platformVersionIds", value = "平台版本id", required = true) @PathVariable Integer[] platformVersionIds) {
        platformVersionServiceProcess.platformVersionRemove(platformVersionIds);
        return R.ok(null, "删除成功");
    }

    @ApiOperation(value = "平台版本(列表)", notes = "平台版本(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getPlatformVersionList")
    public R<TableRecordVo<PlatformVersionInfoVo>> getPlatformVersionList(@Valid @RequestBody GetPlatformVersionListParam getPlatformVersionListParam) {
        TableRecordVo<PlatformVersionInfoVo> tableRecordVo = platformVersionServiceProcess.getPlatformVersionList(getPlatformVersionListParam);
        return R.ok(tableRecordVo, "获取成功");
    }

    @ApiOperation(value = "平台版本(明细)", notes = "平台版本(明细)", tags = {})
    @ApiOperationSupport(order = 5)
    @GetMapping("/getPlatformVersionDetail/{platformVersionId}")
    public R<PlatformVersionInfoVo> getPlatformVersionDetail(@ApiParam(name = "platformVersionId", value = "平台版本id", required = true) @PathVariable Integer platformVersionId) {
        PlatformVersionInfoVo platformVersionInfoVo = platformVersionServiceProcess.getPlatformVersionDetail(platformVersionId);
        return R.ok(platformVersionInfoVo, "获取成功");
    }

    @ApiOperation(value = "平台版本(excel导出)", notes = "平台版本(excel导出)", produces = "application/octet-stream", tags = {})
    @ApiOperationSupport(order = 6)
    @PostMapping("/platformVersionExport")
    public void platformVersionExport(HttpServletResponse response, @Valid @RequestBody GetPlatformVersionListParam getPlatformVersionListParam) throws IOException {
        TableRecordVo<PlatformVersionInfoVo> list = platformVersionServiceProcess.getPlatformVersionList(getPlatformVersionListParam);
        List<EasyExcelEntity<PlatformVersionInfoVo>> tList = new ArrayList<>();
        EasyExcelEntity<PlatformVersionInfoVo> easyExcelEntity = new EasyExcelEntity<>();
        easyExcelEntity.setSheetName("平台版本");
        easyExcelEntity.setClazz(PlatformVersionInfoVo.class);
        easyExcelEntity.setData(list.getRecords());
        tList.add(easyExcelEntity);
        EasyExcelUtils.webExport(response, "平台版本导出", tList);
    }

    @ApiOperation(value = "平台版本(excel导入)", notes = "平台版本(excel导入)", tags = {})
    @ApiOperationSupport(order = 7)
    @PostMapping("/platformVersionImport")
    public R platformVersionImport(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        List<AePlatformVersionParam> dataList = EasyExcel.read(multipartFile.getInputStream()).sheet(0).head(AePlatformVersionParam.class).doReadSync();
        platformVersionServiceProcess.platformVersionAddBatch(dataList);
        return R.ok(null, "导入成功");
    }

    @ApiOperation(value = "平台版本(vue资源下载)", notes = "平台版本(vue资源下载)", tags = {})
    @ApiOperationSupport(order = 99)
    @GetMapping(value = "/getPlatformVersionVue", produces = "application/octet-stream")
    public void getPlatformVersionVue(HttpServletResponse response) {
        try {
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("platform-version.zip", "utf-8"));
            ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(out);
            zaos.setUseZip64(Zip64Mode.AsNeeded);

            org.springframework.core.io.Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(null)
            .getResources("classpath:vue/platform/platform-version/*");
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
