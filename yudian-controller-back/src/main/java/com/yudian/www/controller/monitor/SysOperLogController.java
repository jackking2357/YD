package com.yudian.www.controller.monitor;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.yudian.common.entity.R;
import com.yudian.common.utils.easyexcel.EasyExcelEntity;
import com.yudian.common.utils.easyexcel.EasyExcelUtils;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.service.sys.ISysOperLogServiceProcess;
import com.yudian.www.service.sys.param.GetSysOperLogListParam;
import com.yudian.www.service.sys.vo.SysOperLogInfoVo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
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
 * 操作日志记录 前端控制器
 */
@Api(tags = "操作日志记录")
@RestController
@RequestMapping("/monitor/sys-oper-log")
public class SysOperLogController {

    @Resource
    private ISysOperLogServiceProcess sysOperLogServiceProcess;

//    @ApiOperation(value = "操作日志记录(添加)", notes = "操作日志记录(添加)", tags = {})
//    @ApiOperationSupport(order = 1)
//    @PostMapping("/sysOperLogAdd")
//    public R sysOperLogAdd(@Valid @RequestBody AeSysOperLogParam aeSysOperLogParam) {
//        sysOperLogServiceProcess.sysOperLogAdd(aeSysOperLogParam);
//        return R.ok(null, "新增成功");
//    }
//
//    @ApiOperation(value = "操作日志记录(编辑)", notes = "操作日志记录(编辑)", tags = {})
//    @ApiOperationSupport(order = 2)
//    @PutMapping("/sysOperLogEdit")
//    public R sysOperLogEdit(@Validated({EditDomain.class}) @RequestBody AeSysOperLogParam aeSysOperLogParam) {
//        sysOperLogServiceProcess.sysOperLogEdit(aeSysOperLogParam);
//        return R.ok(null, "修改成功");
//    }

    @SaCheckPermission(value = {"monitor:operlog:remove"}, orRole = "admin", mode = SaMode.OR)
    @ApiOperation(value = "操作日志记录(删除)", notes = "操作日志记录(删除)", tags = {})
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/sysOperLogRemove/{operIds}")
    public R sysOperLogRemove(@ApiParam(name = "operIds", value = "操作日志记录id", required = true) @PathVariable Long[] operIds) {
        sysOperLogServiceProcess.sysOperLogRemove(operIds);
        return R.ok(null, "删除成功");
    }

    @SaCheckPermission(value = {"monitor:operlog:list"}, orRole = "admin", mode = SaMode.OR)
    @ApiOperation(value = "操作日志记录(列表)", notes = "操作日志记录(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getSysOperLogList")
    public R<TableRecordVo<SysOperLogInfoVo>> getSysOperLogList(@Valid @RequestBody GetSysOperLogListParam getSysOperLogListParam) {
        TableRecordVo<SysOperLogInfoVo> tableRecordVo = sysOperLogServiceProcess.getSysOperLogList(getSysOperLogListParam);
        return R.ok(tableRecordVo, "获取成功");
    }

    @SaCheckPermission(value = {"monitor:operlog:query"}, orRole = "admin", mode = SaMode.OR)
    @ApiOperation(value = "操作日志记录(明细)", notes = "操作日志记录(明细)", tags = {})
    @ApiOperationSupport(order = 5)
    @GetMapping("/getSysOperLogDetail/{operId}")
    public R<SysOperLogInfoVo> getSysOperLogDetail(@ApiParam(name = "operId", value = "操作日志记录id", required = true) @PathVariable Long operId) {
        SysOperLogInfoVo sysOperLogInfoVo = sysOperLogServiceProcess.getSysOperLogDetail(operId);
        return R.ok(sysOperLogInfoVo, "获取成功");
    }

    @SaCheckPermission(value = {"monitor:operlog:export"}, orRole = "admin", mode = SaMode.OR)
    @ApiOperation(value = "操作日志记录(excel导出)", notes = "操作日志记录(excel导出)", produces = "application/octet-stream", tags = {})
    @ApiOperationSupport(order = 6)
    @PostMapping("/sysOperLogExport")
    public void sysOperLogExport(HttpServletResponse response, @Valid @RequestBody GetSysOperLogListParam getSysOperLogListParam) throws IOException {
        TableRecordVo<SysOperLogInfoVo> list = sysOperLogServiceProcess.getSysOperLogList(getSysOperLogListParam);
        List<EasyExcelEntity<SysOperLogInfoVo>> tList = new ArrayList<>();
        EasyExcelEntity<SysOperLogInfoVo> easyExcelEntity = new EasyExcelEntity<>();
        easyExcelEntity.setSheetName("操作日志记录");
        easyExcelEntity.setClazz(SysOperLogInfoVo.class);
        easyExcelEntity.setData(list.getRecords());
        tList.add(easyExcelEntity);
        EasyExcelUtils.webExport(response, "操作日志记录导出", tList);
    }

//    @ApiOperation(value = "操作日志记录(excel导入)", notes = "操作日志记录(excel导入)", tags = {})
//    @ApiOperationSupport(order = 7)
//    @PostMapping("/sysOperLogImport")
//    public R sysOperLogImport(@RequestParam("file") MultipartFile multipartFile) throws IOException {
//        List<AeSysOperLogParam> dataList = EasyExcel.read(multipartFile.getInputStream()).sheet(0).head(AeSysOperLogParam.class).doReadSync();
//        sysOperLogServiceProcess.sysOperLogAddBatch(dataList);
//        return R.ok(null, "导入成功");
//    }
//
//    @ApiOperation(value = "操作日志记录(vue资源下载)", notes = "操作日志记录(vue资源下载)", tags = {})
//    @ApiOperationSupport(order = 99)
//    @GetMapping(value = "/getSysOperLogVue", produces = "application/octet-stream")
//    public void getSysOperLogVue(HttpServletResponse response) {
//        try {
//            ServletOutputStream out = response.getOutputStream();
//            response.setContentType("application/octet-stream");
//            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("sys-oper-log.zip", "utf-8"));
//            ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(out);
//            zaos.setUseZip64(Zip64Mode.AsNeeded);
//
//            org.springframework.core.io.Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(null)
//            .getResources("classpath:vue//sys-oper-log/*");
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

    @ApiOperation(value = "操作日志记录(清空全部)", notes = "操作日志记录(清空全部)")
    @ApiOperationSupport(order = 5)
    @DeleteMapping("/clearAll")
    public R clearAll() {
        sysOperLogServiceProcess.truncateTable();
        return R.ok(null, "获取成功");
    }
}
