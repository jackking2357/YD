package com.yudian.www.controller.sys;

import com.alibaba.excel.EasyExcel;
import com.yudian.common.entity.R;
import com.yudian.common.utils.easyexcel.EasyExcelEntity;
import com.yudian.common.utils.easyexcel.EasyExcelUtils;
import com.yudian.www.base.EditDomain;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.base.TreeSelect;
import com.yudian.www.entity.sys.SysDept;
import com.yudian.www.service.sys.ISysDeptServiceProcess;
import com.yudian.www.service.sys.param.AeSysDeptParam;
import com.yudian.www.service.sys.param.GetSysDeptListParam;
import com.yudian.www.service.sys.vo.SysDeptInfoVo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.lang3.ArrayUtils;
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

@Api(tags = "部门表")
@RestController
@RequestMapping("/system/sys-dept")
public class SysDeptController {

    @Resource
    private ISysDeptServiceProcess sysDeptServiceProcess;

    @ApiOperation(value = "部门表(添加)", notes = "部门表(添加)", tags = {})
    @ApiOperationSupport(order = 1)
    @PostMapping("/sysDeptAdd")
    public R sysDeptAdd(@Valid @RequestBody AeSysDeptParam aeSysDeptParam) {
        sysDeptServiceProcess.sysDeptAdd(aeSysDeptParam);
        return R.ok(null, "新增成功");
    }

    @ApiOperation(value = "部门表(编辑)", notes = "部门表(编辑)", tags = {})
    @ApiOperationSupport(order = 2)
    @PutMapping("/sysDeptEdit")
    public R sysDeptEdit(@Validated({EditDomain.class}) @RequestBody AeSysDeptParam aeSysDeptParam) {
        sysDeptServiceProcess.sysDeptEdit(aeSysDeptParam);
        return R.ok(null, "修改成功");
    }

    @ApiOperation(value = "部门表(删除)", notes = "部门表(删除)", tags = {})
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/sysDeptRemove/{deptIds}")
    public R sysDeptRemove(@ApiParam(name = "deptIds", value = "部门表id", required = true) @PathVariable Long[] deptIds) {
        sysDeptServiceProcess.sysDeptRemove(deptIds);
        return R.ok(null, "删除成功");
    }

    @ApiOperation(value = "部门表(列表)", notes = "部门表(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getSysDeptList")
    public R<TableRecordVo<SysDeptInfoVo>> getSysDeptList(@Valid @RequestBody GetSysDeptListParam getSysDeptListParam) {
        TableRecordVo<SysDeptInfoVo> tableRecordVo = sysDeptServiceProcess.getSysDeptList(getSysDeptListParam);
        return R.ok(tableRecordVo, "获取成功");
    }

    @ApiOperation(value = "部门表(明细)", notes = "部门表(明细)", tags = {})
    @ApiOperationSupport(order = 5)
    @GetMapping("/getSysDeptDetail/{deptId}")
    public R<SysDeptInfoVo> getSysDeptDetail(@ApiParam(name = "deptId", value = "部门表id", required = true) @PathVariable Long deptId) {
        SysDeptInfoVo sysDeptInfoVo = sysDeptServiceProcess.getSysDeptDetail(deptId);
        return R.ok(sysDeptInfoVo, "获取成功");
    }

    @ApiOperation(value = "部门表(excel导出)", notes = "部门表(excel导出)", produces = "application/octet-stream", tags = {})
    @ApiOperationSupport(order = 6)
    @PostMapping("/sysDeptExport")
    public void sysDeptExport(HttpServletResponse response, @Valid @RequestBody GetSysDeptListParam getSysDeptListParam) throws IOException {
        TableRecordVo<SysDeptInfoVo> list = sysDeptServiceProcess.getSysDeptList(getSysDeptListParam);
        List<EasyExcelEntity<SysDeptInfoVo>> tList = new ArrayList<>();
        EasyExcelEntity<SysDeptInfoVo> easyExcelEntity = new EasyExcelEntity<>();
        easyExcelEntity.setSheetName("部门表");
        easyExcelEntity.setClazz(SysDeptInfoVo.class);
        easyExcelEntity.setData(list.getRecords());
        tList.add(easyExcelEntity);
        EasyExcelUtils.webExport(response, "部门表导出", tList);
    }

    @ApiOperation(value = "部门表(excel导入)", notes = "部门表(excel导入)", tags = {})
    @ApiOperationSupport(order = 7)
    @PostMapping("/sysDeptImport")
    public R sysDeptImport(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        List<AeSysDeptParam> dataList = EasyExcel.read(multipartFile.getInputStream()).sheet(0).head(AeSysDeptParam.class).doReadSync();
        sysDeptServiceProcess.sysDeptAddBatch(dataList);
        return R.ok(null, "导入成功");
    }

    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "查询部门列表（排除节点）", notes = "查询部门列表（排除节点）")
    @GetMapping("/exclude/{deptId}")
    public R excludeChild(@ApiParam(name = "deptId", value = "部门id", required = false) @PathVariable(value = "deptId", required = false) Long deptId) {
        GetSysDeptListParam getSysDeptListParam = new GetSysDeptListParam();
        getSysDeptListParam.setPageNo(1);
        getSysDeptListParam.setPageSize(-1);
        List<SysDeptInfoVo> sysDeptInfoVoList = sysDeptServiceProcess.getSysDeptList(getSysDeptListParam).getRecords();
        sysDeptInfoVoList.removeIf(d -> d.getDeptId().intValue() == deptId || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + ""));
        return R.ok(sysDeptInfoVoList, "获取成功");
    }

    @ApiOperationSupport(order = 9)
    @ApiOperation(value = "查询部门下拉树结构", notes = "查询部门下拉树结构")
    @GetMapping("/treeSelect")
    public R treeSelect() {
        List<SysDept> sysDeptList = sysDeptServiceProcess.getDeptList();
        List<TreeSelect> treeSelects = sysDeptServiceProcess.buildDeptTreeSelect(sysDeptList);
        return R.ok(treeSelects, "获取成功");
    }

    @ApiOperation(value = "部门表(vue资源下载)", notes = "部门表(vue资源下载)", tags = {})
    @ApiOperationSupport(order = 99)
    @GetMapping(value = "/getSysDeptVue", produces = "application/octet-stream")
    public void getSysDeptVue(HttpServletResponse response) {
        try {
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("sys-dept.zip", "utf-8"));
            ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(out);
            zaos.setUseZip64(Zip64Mode.AsNeeded);

            org.springframework.core.io.Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(null)
                    .getResources("classpath:vue/sys/sys-dept/*");
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
