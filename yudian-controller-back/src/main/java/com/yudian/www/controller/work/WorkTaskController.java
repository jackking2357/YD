package com.yudian.www.controller.work;

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
import com.yudian.www.service.work.param.AeWorkTaskParam;
import com.yudian.www.service.work.param.GetWorkTaskListParam;
import com.yudian.www.service.work.IWorkTaskServiceProcess;
import com.yudian.www.service.work.vo.WorkTaskInfoVo;
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
 * 任务 前端控制器
 *
 * @author yudian
 * @since 2023-01-05
 */
@Api(tags = "任务")
@RestController
@RequestMapping("//work-task")
public class WorkTaskController {

    @Resource
    private IWorkTaskServiceProcess workTaskServiceProcess;

    @ApiOperation(value = "任务(添加)", notes = "任务(添加)", tags = {})
    @ApiOperationSupport(order = 1)
    @PostMapping("/workTaskAdd")
    public R workTaskAdd(@Valid @RequestBody AeWorkTaskParam aeWorkTaskParam) {
        workTaskServiceProcess.workTaskAdd(aeWorkTaskParam);
        return R.ok(null, "新增成功");
    }

    @ApiOperation(value = "任务(编辑)", notes = "任务(编辑)", tags = {})
    @ApiOperationSupport(order = 2)
    @PutMapping("/workTaskEdit")
    public R workTaskEdit(@Validated({EditDomain.class}) @RequestBody AeWorkTaskParam aeWorkTaskParam) {
        workTaskServiceProcess.workTaskEdit(aeWorkTaskParam);
        return R.ok(null, "修改成功");
    }

    @ApiOperation(value = "任务(删除)", notes = "任务(删除)", tags = {})
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/workTaskRemove/{workTaskIds}")
    public R workTaskRemove(@ApiParam(name = "workTaskIds", value = "任务id", required = true) @PathVariable Long[] workTaskIds) {
        workTaskServiceProcess.workTaskRemove(workTaskIds);
        return R.ok(null, "删除成功");
    }

    @ApiOperation(value = "任务(列表)", notes = "任务(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getWorkTaskList")
    public R<TableRecordVo<WorkTaskInfoVo>> getWorkTaskList(@Valid @RequestBody GetWorkTaskListParam getWorkTaskListParam) {
        TableRecordVo<WorkTaskInfoVo> tableRecordVo = workTaskServiceProcess.getWorkTaskList(getWorkTaskListParam);
        return R.ok(tableRecordVo, "获取成功");
    }

    @ApiOperation(value = "任务(明细)", notes = "任务(明细)", tags = {})
    @ApiOperationSupport(order = 5)
    @GetMapping("/getWorkTaskDetail/{workTaskId}")
    public R<WorkTaskInfoVo> getWorkTaskDetail(@ApiParam(name = "workTaskId", value = "任务id", required = true) @PathVariable Long workTaskId) {
        WorkTaskInfoVo workTaskInfoVo = workTaskServiceProcess.getWorkTaskDetail(workTaskId);
        return R.ok(workTaskInfoVo, "获取成功");
    }

    @ApiOperation(value = "任务(excel导出)", notes = "任务(excel导出)", produces = "application/octet-stream", tags = {})
    @ApiOperationSupport(order = 6)
    @PostMapping("/workTaskExport")
    public void workTaskExport(HttpServletResponse response, @Valid @RequestBody GetWorkTaskListParam getWorkTaskListParam) throws IOException {
        TableRecordVo<WorkTaskInfoVo> list = workTaskServiceProcess.getWorkTaskList(getWorkTaskListParam);
        List<EasyExcelEntity<WorkTaskInfoVo>> tList = new ArrayList<>();
        EasyExcelEntity<WorkTaskInfoVo> easyExcelEntity = new EasyExcelEntity<>();
        easyExcelEntity.setSheetName("任务");
        easyExcelEntity.setClazz(WorkTaskInfoVo.class);
        easyExcelEntity.setData(list.getRecords());
        tList.add(easyExcelEntity);
        EasyExcelUtils.webExport(response, "任务导出", tList);
    }

    @ApiOperation(value = "任务(excel导入)", notes = "任务(excel导入)", tags = {})
    @ApiOperationSupport(order = 7)
    @PostMapping("/workTaskImport")
    public R workTaskImport(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        List<AeWorkTaskParam> dataList = EasyExcel.read(multipartFile.getInputStream()).sheet(0).head(AeWorkTaskParam.class).doReadSync();
        workTaskServiceProcess.workTaskAddBatch(dataList);
        return R.ok(null, "导入成功");
    }

    @ApiOperation(value = "任务(vue资源下载)", notes = "任务(vue资源下载)", tags = {})
    @ApiOperationSupport(order = 99)
    @GetMapping(value = "/getWorkTaskVue", produces = "application/octet-stream")
    public void getWorkTaskVue(HttpServletResponse response) {
        try {
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("work-task.zip", "utf-8"));
            ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(out);
            zaos.setUseZip64(Zip64Mode.AsNeeded);

            org.springframework.core.io.Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(null)
            .getResources("classpath:vue/work/work-task/*");
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
