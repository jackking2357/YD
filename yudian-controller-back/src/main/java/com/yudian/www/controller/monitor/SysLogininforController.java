package com.yudian.www.controller.monitor;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.yudian.common.entity.R;
import com.yudian.common.utils.easyexcel.EasyExcelEntity;
import com.yudian.common.utils.easyexcel.EasyExcelUtils;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.service.sys.ISysLogininforServiceProcess;
import com.yudian.www.service.sys.param.GetSysLogininforListParam;
import com.yudian.www.service.sys.vo.SysLogininforInfoVo;
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

@Api(tags = "系统访问记录")
@RestController
@RequestMapping("/monitor/sys-logininfor")
public class SysLogininforController {

    @Resource
    private ISysLogininforServiceProcess sysLogininforServiceProcess;

//    @ApiOperation(value = "系统访问记录(添加)", notes = "系统访问记录(添加)", tags = {})
//    @ApiOperationSupport(order = 1)
//    @PostMapping("/sysLogininforAdd")
//    public R sysLogininforAdd(@Valid @RequestBody AeSysLogininforParam aeSysLogininforParam) {
//        sysLogininforServiceProcess.sysLogininforAdd(aeSysLogininforParam);
//        return R.ok(null, "新增成功");
//    }
//
//    @ApiOperation(value = "系统访问记录(编辑)", notes = "系统访问记录(编辑)", tags = {})
//    @ApiOperationSupport(order = 2)
//    @PutMapping("/sysLogininforEdit")
//    public R sysLogininforEdit(@Validated({EditDomain.class}) @RequestBody AeSysLogininforParam aeSysLogininforParam) {
//        sysLogininforServiceProcess.sysLogininforEdit(aeSysLogininforParam);
//        return R.ok(null, "修改成功");
//    }

    @SaCheckPermission(value = {"monitor:logininfor:remove"}, orRole = "admin", mode = SaMode.OR)
    @ApiOperation(value = "系统访问记录(删除)", notes = "系统访问记录(删除)", tags = {})
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/sysLogininforRemove/{infoIds}")
    public R sysLogininforRemove(@ApiParam(name = "infoIds", value = "系统访问记录id", required = true) @PathVariable Long[] infoIds) {
        sysLogininforServiceProcess.sysLogininforRemove(infoIds);
        return R.ok(null, "删除成功");
    }

    @SaCheckPermission(value = {"monitor:logininfor:list"}, orRole = "admin", mode = SaMode.OR)
    @ApiOperation(value = "系统访问记录(列表)", notes = "系统访问记录(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getSysLogininforList")
    public R<TableRecordVo<SysLogininforInfoVo>> getSysLogininforList(@Valid @RequestBody GetSysLogininforListParam getSysLogininforListParam) {
        TableRecordVo<SysLogininforInfoVo> tableRecordVo = sysLogininforServiceProcess.getSysLogininforList(getSysLogininforListParam);
        return R.ok(tableRecordVo, "获取成功");
    }

    @SaCheckPermission(value = {"monitor:logininfor:query"}, orRole = "admin", mode = SaMode.OR)
    @ApiOperation(value = "系统访问记录(明细)", notes = "系统访问记录(明细)", tags = {})
    @ApiOperationSupport(order = 5)
    @GetMapping("/getSysLogininforDetail/{infoId}")
    public R<SysLogininforInfoVo> getSysLogininforDetail(@ApiParam(name = "infoId", value = "系统访问记录id", required = true) @PathVariable Long infoId) {
        SysLogininforInfoVo sysLogininforInfoVo = sysLogininforServiceProcess.getSysLogininforDetail(infoId);
        return R.ok(sysLogininforInfoVo, "获取成功");
    }

    @SaCheckPermission(value = {"monitor:logininfor:export"}, orRole = "admin", mode = SaMode.OR)
    @ApiOperation(value = "系统访问记录(excel导出)", notes = "系统访问记录(excel导出)", produces = "application/octet-stream", tags = {})
    @ApiOperationSupport(order = 6)
    @PostMapping("/sysLogininforExport")
    public void sysLogininforExport(HttpServletResponse response, @Valid @RequestBody GetSysLogininforListParam getSysLogininforListParam) throws IOException {
        TableRecordVo<SysLogininforInfoVo> list = sysLogininforServiceProcess.getSysLogininforList(getSysLogininforListParam);
        List<EasyExcelEntity<SysLogininforInfoVo>> tList = new ArrayList<>();
        EasyExcelEntity<SysLogininforInfoVo> easyExcelEntity = new EasyExcelEntity<>();
        easyExcelEntity.setSheetName("系统访问记录");
        easyExcelEntity.setClazz(SysLogininforInfoVo.class);
        easyExcelEntity.setData(list.getRecords());
        tList.add(easyExcelEntity);
        EasyExcelUtils.webExport(response, "系统访问记录导出", tList);
    }

//    @ApiOperation(value = "系统访问记录(excel导入)", notes = "系统访问记录(excel导入)", tags = {})
//    @ApiOperationSupport(order = 7)
//    @PostMapping("/sysLogininforImport")
//    public R sysLogininforImport(@RequestParam("file") MultipartFile multipartFile) throws IOException {
//        List<AeSysLogininforParam> dataList = EasyExcel.read(multipartFile.getInputStream()).sheet(0).head(AeSysLogininforParam.class).doReadSync();
//        sysLogininforServiceProcess.sysLogininforAddBatch(dataList);
//        return R.ok(null, "导入成功");
//    }
//
//    @ApiOperation(value = "系统访问记录(vue资源下载)", notes = "系统访问记录(vue资源下载)", tags = {})
//    @ApiOperationSupport(order = 99)
//    @GetMapping(value = "/getSysLogininforVue", produces = "application/octet-stream")
//    public void getSysLogininforVue(HttpServletResponse response) {
//        try {
//            ServletOutputStream out = response.getOutputStream();
//            response.setContentType("application/octet-stream");
//            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("sys-logininfor.zip", "utf-8"));
//            ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(out);
//            zaos.setUseZip64(Zip64Mode.AsNeeded);
//
//            org.springframework.core.io.Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(null)
//                    .getResources("classpath:vue//sys-logininfor/*");
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

    @ApiOperation(value = "日志-访问记录(清空全部)", notes = "日志-访问记录(清空全部)")
    @ApiOperationSupport(order = 5)
    @DeleteMapping("/clearAll")
    public R clearAll() {
        sysLogininforServiceProcess.truncateTable();
        return R.ok(null, "获取成功");
    }
}
