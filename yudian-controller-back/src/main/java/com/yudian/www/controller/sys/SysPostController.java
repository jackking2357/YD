package com.yudian.www.controller.sys;

import com.alibaba.excel.EasyExcel;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.yudian.common.entity.R;
import com.yudian.common.utils.easyexcel.EasyExcelEntity;
import com.yudian.common.utils.easyexcel.EasyExcelUtils;
import com.yudian.www.base.EditDomain;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.service.sys.ISysPostServiceProcess;
import com.yudian.www.service.sys.param.AeSysPostParam;
import com.yudian.www.service.sys.param.GetSysPostListParam;
import com.yudian.www.service.sys.vo.SysPostInfoVo;
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

@Api(tags = "岗位信息表")
@RestController
@RequestMapping("/system/sys-post")
public class SysPostController {

    @Resource
    private ISysPostServiceProcess sysPostServiceProcess;

    @ApiOperation(value = "岗位信息表(添加)", notes = "岗位信息表(添加)", tags = {})
    @ApiOperationSupport(order = 1)
    @PostMapping("/sysPostAdd")
    public R sysPostAdd(@Valid @RequestBody AeSysPostParam aeSysPostParam) {
        sysPostServiceProcess.sysPostAdd(aeSysPostParam);
        return R.ok(null, "新增成功");
    }

    @ApiOperation(value = "岗位信息表(编辑)", notes = "岗位信息表(编辑)", tags = {})
    @ApiOperationSupport(order = 2)
    @PutMapping("/sysPostEdit")
    public R sysPostEdit(@Validated({EditDomain.class}) @RequestBody AeSysPostParam aeSysPostParam) {
        sysPostServiceProcess.sysPostEdit(aeSysPostParam);
        return R.ok(null, "修改成功");
    }

    @ApiOperation(value = "岗位信息表(删除)", notes = "岗位信息表(删除)", tags = {})
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/sysPostRemove/{postIds}")
    public R sysPostRemove(@ApiParam(name = "postIds", value = "岗位信息表id", required = true) @PathVariable Long[] postIds) {
        sysPostServiceProcess.sysPostRemove(postIds);
        return R.ok(null, "删除成功");
    }

    @ApiOperation(value = "岗位信息表(列表)", notes = "岗位信息表(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getSysPostList")
    public R<TableRecordVo<SysPostInfoVo>> getSysPostList(@Valid @RequestBody GetSysPostListParam getSysPostListParam) {
        TableRecordVo<SysPostInfoVo> tableRecordVo = sysPostServiceProcess.getSysPostList(getSysPostListParam);
        return R.ok(tableRecordVo, "获取成功");
    }

    @ApiOperation(value = "岗位信息表(明细)", notes = "岗位信息表(明细)", tags = {})
    @ApiOperationSupport(order = 5)
    @GetMapping("/getSysPostDetail/{postId}")
    public R<SysPostInfoVo> getSysPostDetail(@ApiParam(name = "postId", value = "岗位信息表id", required = true) @PathVariable Long postId) {
        SysPostInfoVo sysPostInfoVo = sysPostServiceProcess.getSysPostDetail(postId);
        return R.ok(sysPostInfoVo, "获取成功");
    }

    @ApiOperation(value = "岗位信息表(excel导出)", notes = "岗位信息表(excel导出)", produces = "application/octet-stream", tags = {})
    @ApiOperationSupport(order = 6)
    @PostMapping("/sysPostExport")
    public void sysPostExport(HttpServletResponse response, @Valid @RequestBody GetSysPostListParam getSysPostListParam) throws IOException {
        TableRecordVo<SysPostInfoVo> list = sysPostServiceProcess.getSysPostList(getSysPostListParam);
        List<EasyExcelEntity<SysPostInfoVo>> tList = new ArrayList<>();
        EasyExcelEntity<SysPostInfoVo> easyExcelEntity = new EasyExcelEntity<>();
        easyExcelEntity.setSheetName("岗位信息表");
        easyExcelEntity.setClazz(SysPostInfoVo.class);
        easyExcelEntity.setData(list.getRecords());
        tList.add(easyExcelEntity);
        EasyExcelUtils.webExport(response, "岗位信息表导出", tList);
    }

    @ApiOperation(value = "岗位信息表(excel导入)", notes = "岗位信息表(excel导入)", tags = {})
    @ApiOperationSupport(order = 7)
    @PostMapping("/sysPostImport")
    public R sysPostImport(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        List<AeSysPostParam> dataList = EasyExcel.read(multipartFile.getInputStream()).sheet(0).head(AeSysPostParam.class).doReadSync();
        sysPostServiceProcess.sysPostAddBatch(dataList);
        return R.ok(null, "导入成功");
    }

    @ApiOperation(value = "岗位信息表(vue资源下载)", notes = "岗位信息表(vue资源下载)", tags = {})
    @ApiOperationSupport(order = 99)
    @GetMapping(value = "/getSysPostVue", produces = "application/octet-stream")
    public void getSysPostVue(HttpServletResponse response) {
        try {
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("sys-post.zip", "utf-8"));
            ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(out);
            zaos.setUseZip64(Zip64Mode.AsNeeded);

            org.springframework.core.io.Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(null)
                    .getResources("classpath:vue/sys/sys-post/*");
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
