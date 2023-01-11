package com.yudian.www.controller.sys;

import com.alibaba.excel.EasyExcel;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.yudian.common.entity.R;
import com.yudian.common.utils.easyexcel.EasyExcelEntity;
import com.yudian.common.utils.easyexcel.EasyExcelUtils;
import com.yudian.www.base.EditDomain;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.service.sys.ISysDictTypeServiceProcess;
import com.yudian.www.service.sys.param.AeSysDictTypeParam;
import com.yudian.www.service.sys.param.GetSysDictTypeListParam;
import com.yudian.www.service.sys.vo.SysDictTypeInfoVo;
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

@Api(tags = "字典类型表")
@RestController
@RequestMapping("/system/sys-dict-type")
public class SysDictTypeController {

    @Resource
    private ISysDictTypeServiceProcess sysDictTypeServiceProcess;

    @ApiOperation(value = "字典类型表(添加)", notes = "字典类型表(添加)", tags = {})
    @ApiOperationSupport(order = 1)
    @PostMapping("/sysDictTypeAdd")
    public R sysDictTypeAdd(@Valid @RequestBody AeSysDictTypeParam aeSysDictTypeParam) {
        sysDictTypeServiceProcess.sysDictTypeAdd(aeSysDictTypeParam);
        return R.ok(null, "新增成功");
    }

    @ApiOperation(value = "字典类型表(编辑)", notes = "字典类型表(编辑)", tags = {})
    @ApiOperationSupport(order = 2)
    @PutMapping("/sysDictTypeEdit")
    public R sysDictTypeEdit(@Validated({EditDomain.class}) @RequestBody AeSysDictTypeParam aeSysDictTypeParam) {
        sysDictTypeServiceProcess.sysDictTypeEdit(aeSysDictTypeParam);
        return R.ok(null, "修改成功");
    }

    @ApiOperation(value = "字典类型表(删除)", notes = "字典类型表(删除)", tags = {})
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/sysDictTypeRemove/{dictIds}")
    public R sysDictTypeRemove(@ApiParam(name = "dictIds", value = "字典类型表id", required = true) @PathVariable Long[] dictIds) {
        sysDictTypeServiceProcess.sysDictTypeRemove(dictIds);
        return R.ok(null, "删除成功");
    }

    @ApiOperation(value = "字典类型表(列表)", notes = "字典类型表(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getSysDictTypeList")
    public R<TableRecordVo<SysDictTypeInfoVo>> getSysDictTypeList(@Valid @RequestBody GetSysDictTypeListParam getSysDictTypeListParam) {
        TableRecordVo<SysDictTypeInfoVo> tableRecordVo = sysDictTypeServiceProcess.getSysDictTypeList(getSysDictTypeListParam);
        return R.ok(tableRecordVo, "获取成功");
    }

    @ApiOperation(value = "字典类型表(明细)", notes = "字典类型表(明细)", tags = {})
    @ApiOperationSupport(order = 5)
    @GetMapping("/getSysDictTypeDetail/{dictId}")
    public R<SysDictTypeInfoVo> getSysDictTypeDetail(@ApiParam(name = "dictId", value = "字典类型表id", required = true) @PathVariable Long dictId) {
        SysDictTypeInfoVo sysDictTypeInfoVo = sysDictTypeServiceProcess.getSysDictTypeDetail(dictId);
        return R.ok(sysDictTypeInfoVo, "获取成功");
    }

    @ApiOperation(value = "字典类型表(excel导出)", notes = "字典类型表(excel导出)", produces = "application/octet-stream", tags = {})
    @ApiOperationSupport(order = 6)
    @PostMapping("/sysDictTypeExport")
    public void sysDictTypeExport(HttpServletResponse response, @Valid @RequestBody GetSysDictTypeListParam getSysDictTypeListParam) throws IOException {
        TableRecordVo<SysDictTypeInfoVo> list = sysDictTypeServiceProcess.getSysDictTypeList(getSysDictTypeListParam);
        List<EasyExcelEntity<SysDictTypeInfoVo>> tList = new ArrayList<>();
        EasyExcelEntity<SysDictTypeInfoVo> easyExcelEntity = new EasyExcelEntity<>();
        easyExcelEntity.setSheetName("字典类型表");
        easyExcelEntity.setClazz(SysDictTypeInfoVo.class);
        easyExcelEntity.setData(list.getRecords());
        tList.add(easyExcelEntity);
        EasyExcelUtils.webExport(response, "字典类型表导出", tList);
    }

    @ApiOperation(value = "字典类型表(excel导入)", notes = "字典类型表(excel导入)", tags = {})
    @ApiOperationSupport(order = 7)
    @PostMapping("/sysDictTypeImport")
    public R sysDictTypeImport(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        List<AeSysDictTypeParam> dataList = EasyExcel.read(multipartFile.getInputStream()).sheet(0).head(AeSysDictTypeParam.class).doReadSync();
        sysDictTypeServiceProcess.sysDictTypeAddBatch(dataList);
        return R.ok(null, "导入成功");
    }

    @ApiOperation(value = "字典类型表(vue资源下载)", notes = "字典类型表(vue资源下载)", tags = {})
    @ApiOperationSupport(order = 99)
    @GetMapping(value = "/getSysDictTypeVue", produces = "application/octet-stream")
    public void getSysDictTypeVue(HttpServletResponse response) {
        try {
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("sys-dict-type.zip", "utf-8"));
            ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(out);
            zaos.setUseZip64(Zip64Mode.AsNeeded);

            org.springframework.core.io.Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(null)
                    .getResources("classpath:vue/sys/sys-dict-type/*");
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
