package com.yudian.www.controller.robot;

import com.alibaba.excel.EasyExcel;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.yudian.common.entity.R;
import com.yudian.common.utils.easyexcel.EasyExcelEntity;
import com.yudian.common.utils.easyexcel.EasyExcelUtils;
import com.yudian.www.base.EditDomain;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.service.robot.IRobotServiceProcess;
import com.yudian.www.service.robot.param.AeRobotParam;
import com.yudian.www.service.robot.param.GetRobotListParam;
import com.yudian.www.service.robot.vo.RobotInfoVo;
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
 * 机器人 前端控制器
 *
 * @author yudian
 * @since 2023-01-05
 */
@Api(tags = "机器人")
@RestController
@RequestMapping("//robot")
public class RobotController {

    @Resource
    private IRobotServiceProcess robotServiceProcess;

    @ApiOperation(value = "机器人(添加)", notes = "机器人(添加)", tags = {})
    @ApiOperationSupport(order = 1)
    @PostMapping("/robotAdd")
    public R robotAdd(@Valid @RequestBody AeRobotParam aeRobotParam) {
        robotServiceProcess.robotAdd(aeRobotParam);
        return R.ok(null, "新增成功");
    }

    @ApiOperation(value = "机器人(编辑)", notes = "机器人(编辑)", tags = {})
    @ApiOperationSupport(order = 2)
    @PutMapping("/robotEdit")
    public R robotEdit(@Validated({EditDomain.class}) @RequestBody AeRobotParam aeRobotParam) {
        robotServiceProcess.robotEdit(aeRobotParam);
        return R.ok(null, "修改成功");
    }

    @ApiOperation(value = "机器人(删除)", notes = "机器人(删除)", tags = {})
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/robotRemove/{robotIds}")
    public R robotRemove(@ApiParam(name = "robotIds", value = "机器人id", required = true) @PathVariable Long[] robotIds) {
        robotServiceProcess.robotRemove(robotIds);
        return R.ok(null, "删除成功");
    }

    @ApiOperation(value = "机器人(列表)", notes = "机器人(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getRobotList")
    public R<TableRecordVo<RobotInfoVo>> getRobotList(@Valid @RequestBody GetRobotListParam getRobotListParam) {
        TableRecordVo<RobotInfoVo> tableRecordVo = robotServiceProcess.getRobotList(getRobotListParam);
        return R.ok(tableRecordVo, "获取成功");
    }

    @ApiOperation(value = "机器人(明细)", notes = "机器人(明细)", tags = {})
    @ApiOperationSupport(order = 5)
    @GetMapping("/getRobotDetail/{robotId}")
    public R<RobotInfoVo> getRobotDetail(@ApiParam(name = "robotId", value = "机器人id", required = true) @PathVariable Long robotId) {
        RobotInfoVo robotInfoVo = robotServiceProcess.getRobotDetail(robotId);
        return R.ok(robotInfoVo, "获取成功");
    }

    @ApiOperation(value = "机器人(excel导出)", notes = "机器人(excel导出)", produces = "application/octet-stream", tags = {})
    @ApiOperationSupport(order = 6)
    @PostMapping("/robotExport")
    public void robotExport(HttpServletResponse response, @Valid @RequestBody GetRobotListParam getRobotListParam) throws IOException {
        TableRecordVo<RobotInfoVo> list = robotServiceProcess.getRobotList(getRobotListParam);
        List<EasyExcelEntity<RobotInfoVo>> tList = new ArrayList<>();
        EasyExcelEntity<RobotInfoVo> easyExcelEntity = new EasyExcelEntity<>();
        easyExcelEntity.setSheetName("机器人");
        easyExcelEntity.setClazz(RobotInfoVo.class);
        easyExcelEntity.setData(list.getRecords());
        tList.add(easyExcelEntity);
        EasyExcelUtils.webExport(response, "机器人导出", tList);
    }

    @ApiOperation(value = "机器人(excel导入)", notes = "机器人(excel导入)", tags = {})
    @ApiOperationSupport(order = 7)
    @PostMapping("/robotImport")
    public R robotImport(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        List<AeRobotParam> dataList = EasyExcel.read(multipartFile.getInputStream()).sheet(0).head(AeRobotParam.class).doReadSync();
        robotServiceProcess.robotAddBatch(dataList);
        return R.ok(null, "导入成功");
    }

//    @ApiOperation(value = "机器人(vue资源下载)", notes = "机器人(vue资源下载)", tags = {})
//    @ApiOperationSupport(order = 99)
//    @GetMapping(value = "/getRobotVue", produces = "application/octet-stream")
//    public void getRobotVue(HttpServletResponse response) {
//        try {
//            ServletOutputStream out = response.getOutputStream();
//            response.setContentType("application/octet-stream");
//            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("robot.zip", "utf-8"));
//            ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(out);
//            zaos.setUseZip64(Zip64Mode.AsNeeded);
//
//            org.springframework.core.io.Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(null)
//            .getResources("classpath:vue/robot/robot/*");
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
