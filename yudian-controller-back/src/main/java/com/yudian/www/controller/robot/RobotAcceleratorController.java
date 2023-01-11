package com.yudian.www.controller.robot;

import com.alibaba.excel.EasyExcel;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.yudian.common.entity.R;
import com.yudian.common.utils.easyexcel.EasyExcelEntity;
import com.yudian.common.utils.easyexcel.EasyExcelUtils;
import com.yudian.www.base.EditDomain;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.service.robot.IRobotAcceleratorServiceProcess;
import com.yudian.www.service.robot.param.AeRobotAcceleratorParam;
import com.yudian.www.service.robot.param.GetRobotAcceleratorListParam;
import com.yudian.www.service.robot.vo.RobotAcceleratorInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 机器人加速器 前端控制器
 *
 * @author yudian
 * @since 2023-01-05
 */
@Api(tags = "机器人加速器")
@RestController
@RequestMapping("//robot-accelerator")
public class RobotAcceleratorController {

    @Resource
    private IRobotAcceleratorServiceProcess robotAcceleratorServiceProcess;

    @ApiOperation(value = "机器人加速器(添加)", notes = "机器人加速器(添加)", tags = {})
    @ApiOperationSupport(order = 1)
    @PostMapping("/robotAcceleratorAdd")
    public R robotAcceleratorAdd(@Valid @RequestBody AeRobotAcceleratorParam aeRobotAcceleratorParam) {
        robotAcceleratorServiceProcess.robotAcceleratorAdd(aeRobotAcceleratorParam);
        return R.ok(null, "新增成功");
    }

    @ApiOperation(value = "机器人加速器(编辑)", notes = "机器人加速器(编辑)", tags = {})
    @ApiOperationSupport(order = 2)
    @PutMapping("/robotAcceleratorEdit")
    public R robotAcceleratorEdit(@Validated({EditDomain.class}) @RequestBody AeRobotAcceleratorParam aeRobotAcceleratorParam) {
        robotAcceleratorServiceProcess.robotAcceleratorEdit(aeRobotAcceleratorParam);
        return R.ok(null, "修改成功");
    }

    @ApiOperation(value = "机器人加速器(删除)", notes = "机器人加速器(删除)", tags = {})
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/robotAcceleratorRemove/{robotAcceleratorIds}")
    public R robotAcceleratorRemove(@ApiParam(name = "robotAcceleratorIds", value = "机器人加速器id", required = true) @PathVariable Long[] robotAcceleratorIds) {
        robotAcceleratorServiceProcess.robotAcceleratorRemove(robotAcceleratorIds);
        return R.ok(null, "删除成功");
    }

    @ApiOperation(value = "机器人加速器(列表)", notes = "机器人加速器(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getRobotAcceleratorList")
    public R<TableRecordVo<RobotAcceleratorInfoVo>> getRobotAcceleratorList(@Valid @RequestBody GetRobotAcceleratorListParam getRobotAcceleratorListParam) {
        TableRecordVo<RobotAcceleratorInfoVo> tableRecordVo = robotAcceleratorServiceProcess.getRobotAcceleratorList(getRobotAcceleratorListParam);
        return R.ok(tableRecordVo, "获取成功");
    }

    @ApiOperation(value = "机器人加速器(明细)", notes = "机器人加速器(明细)", tags = {})
    @ApiOperationSupport(order = 5)
    @GetMapping("/getRobotAcceleratorDetail/{robotAcceleratorId}")
    public R<RobotAcceleratorInfoVo> getRobotAcceleratorDetail(@ApiParam(name = "robotAcceleratorId", value = "机器人加速器id", required = true) @PathVariable Long robotAcceleratorId) {
        RobotAcceleratorInfoVo robotAcceleratorInfoVo = robotAcceleratorServiceProcess.getRobotAcceleratorDetail(robotAcceleratorId);
        return R.ok(robotAcceleratorInfoVo, "获取成功");
    }

    @ApiOperation(value = "机器人加速器(excel导出)", notes = "机器人加速器(excel导出)", produces = "application/octet-stream", tags = {})
    @ApiOperationSupport(order = 6)
    @PostMapping("/robotAcceleratorExport")
    public void robotAcceleratorExport(HttpServletResponse response, @Valid @RequestBody GetRobotAcceleratorListParam getRobotAcceleratorListParam) throws IOException {
        TableRecordVo<RobotAcceleratorInfoVo> list = robotAcceleratorServiceProcess.getRobotAcceleratorList(getRobotAcceleratorListParam);
        List<EasyExcelEntity<RobotAcceleratorInfoVo>> tList = new ArrayList<>();
        EasyExcelEntity<RobotAcceleratorInfoVo> easyExcelEntity = new EasyExcelEntity<>();
        easyExcelEntity.setSheetName("机器人加速器");
        easyExcelEntity.setClazz(RobotAcceleratorInfoVo.class);
        easyExcelEntity.setData(list.getRecords());
        tList.add(easyExcelEntity);
        EasyExcelUtils.webExport(response, "机器人加速器导出", tList);
    }

    @ApiOperation(value = "机器人加速器(excel导入)", notes = "机器人加速器(excel导入)", tags = {})
    @ApiOperationSupport(order = 7)
    @PostMapping("/robotAcceleratorImport")
    public R robotAcceleratorImport(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        List<AeRobotAcceleratorParam> dataList = EasyExcel.read(multipartFile.getInputStream()).sheet(0).head(AeRobotAcceleratorParam.class).doReadSync();
        robotAcceleratorServiceProcess.robotAcceleratorAddBatch(dataList);
        return R.ok(null, "导入成功");
    }

//    @ApiOperation(value = "机器人加速器(vue资源下载)", notes = "机器人加速器(vue资源下载)", tags = {})
//    @ApiOperationSupport(order = 99)
//    @GetMapping(value = "/getRobotAcceleratorVue", produces = "application/octet-stream")
//    public void getRobotAcceleratorVue(HttpServletResponse response) {
//        try {
//            ServletOutputStream out = response.getOutputStream();
//            response.setContentType("application/octet-stream");
//            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("robot-accelerator.zip", "utf-8"));
//            ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(out);
//            zaos.setUseZip64(Zip64Mode.AsNeeded);
//
//            org.springframework.core.io.Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(null)
//            .getResources("classpath:vue/robot/robot-accelerator/*");
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
