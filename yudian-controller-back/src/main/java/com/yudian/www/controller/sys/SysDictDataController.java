package com.yudian.www.controller.sys;

import com.alibaba.excel.EasyExcel;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.yudian.common.entity.R;
import com.yudian.common.utils.easyexcel.EasyExcelEntity;
import com.yudian.common.utils.easyexcel.EasyExcelUtils;
import com.yudian.www.base.EditDomain;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.service.sys.ISysDictDataServiceProcess;
import com.yudian.www.service.sys.param.AeSysDictDataParam;
import com.yudian.www.service.sys.param.GetSysDictDataListParam;
import com.yudian.www.service.sys.vo.SysDictDataInfoVo;
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

@Api(tags = "字典数据表")
@RestController
@RequestMapping("/system/sys-dict-data")
public class SysDictDataController {

    @Resource
    private ISysDictDataServiceProcess sysDictDataServiceProcess;

    @ApiOperation(value = "字典数据表(添加)", notes = "字典数据表(添加)", tags = {})
    @ApiOperationSupport(order = 1)
    @PostMapping("/sysDictDataAdd")
    public R sysDictDataAdd(@Valid @RequestBody AeSysDictDataParam aeSysDictDataParam) {
        sysDictDataServiceProcess.sysDictDataAdd(aeSysDictDataParam);
        return R.ok(null, "新增成功");
    }

    @ApiOperation(value = "字典数据表(编辑)", notes = "字典数据表(编辑)", tags = {})
    @ApiOperationSupport(order = 2)
    @PutMapping("/sysDictDataEdit")
    public R sysDictDataEdit(@Validated({EditDomain.class}) @RequestBody AeSysDictDataParam aeSysDictDataParam) {
        sysDictDataServiceProcess.sysDictDataEdit(aeSysDictDataParam);
        return R.ok(null, "修改成功");
    }

    @ApiOperation(value = "字典数据表(删除)", notes = "字典数据表(删除)", tags = {})
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/sysDictDataRemove/{dictDataIds}")
    public R sysDictDataRemove(@ApiParam(name = "dictDataIds", value = "字典数据表id", required = true) @PathVariable Long[] dictDataIds) {
        sysDictDataServiceProcess.sysDictDataRemove(dictDataIds);
        return R.ok(null, "删除成功");
    }

    @ApiOperation(value = "字典数据表(列表)", notes = "字典数据表(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getSysDictDataList")
    public R<TableRecordVo<SysDictDataInfoVo>> getSysDictDataList(@Valid @RequestBody GetSysDictDataListParam getSysDictDataListParam) {
        TableRecordVo<SysDictDataInfoVo> tableRecordVo = sysDictDataServiceProcess.getSysDictDataList(getSysDictDataListParam);
        return R.ok(tableRecordVo, "获取成功");
    }

    @ApiOperation(value = "字典数据表(明细)", notes = "字典数据表(明细)", tags = {})
    @ApiOperationSupport(order = 5)
    @GetMapping("/getSysDictDataDetail/{dictDataId}")
    public R<SysDictDataInfoVo> getSysDictDataDetail(@ApiParam(name = "dictDataId", value = "字典数据表id", required = true) @PathVariable Long dictDataId) {
        SysDictDataInfoVo sysDictDataInfoVo = sysDictDataServiceProcess.getSysDictDataDetail(dictDataId);
        return R.ok(sysDictDataInfoVo, "获取成功");
    }

    @ApiOperation(value = "字典数据表(excel导出)", notes = "字典数据表(excel导出)", produces = "application/octet-stream", tags = {})
    @ApiOperationSupport(order = 6)
    @PostMapping("/sysDictDataExport")
    public void sysDictDataExport(HttpServletResponse response, @Valid @RequestBody GetSysDictDataListParam getSysDictDataListParam) throws IOException {
        TableRecordVo<SysDictDataInfoVo> list = sysDictDataServiceProcess.getSysDictDataList(getSysDictDataListParam);
        List<EasyExcelEntity<SysDictDataInfoVo>> tList = new ArrayList<>();
        EasyExcelEntity<SysDictDataInfoVo> easyExcelEntity = new EasyExcelEntity<>();
        easyExcelEntity.setSheetName("字典数据表");
        easyExcelEntity.setClazz(SysDictDataInfoVo.class);
        easyExcelEntity.setData(list.getRecords());
        tList.add(easyExcelEntity);
        EasyExcelUtils.webExport(response, "字典数据表导出", tList);
    }

    @ApiOperation(value = "字典数据表(excel导入)", notes = "字典数据表(excel导入)", tags = {})
    @ApiOperationSupport(order = 7)
    @PostMapping("/sysDictDataImport")
    public R sysDictDataImport(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        List<AeSysDictDataParam> dataList = EasyExcel.read(multipartFile.getInputStream()).sheet(0).head(AeSysDictDataParam.class).doReadSync();
        sysDictDataServiceProcess.sysDictDataAddBatch(dataList);
        return R.ok(null, "导入成功");
    }

    @ApiOperation(value = "字典数据表(vue资源下载)", notes = "字典数据表(vue资源下载)", tags = {})
    @ApiOperationSupport(order = 99)
    @GetMapping(value = "/getSysDictDataVue", produces = "application/octet-stream")
    public void getSysDictDataVue(HttpServletResponse response) {
        try {
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("sys-dict-data.zip", "utf-8"));
            ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(out);
            zaos.setUseZip64(Zip64Mode.AsNeeded);

            org.springframework.core.io.Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(null)
                    .getResources("classpath:vue/sys/sys-dict-data/*");
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
