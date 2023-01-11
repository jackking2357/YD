package com.yudian.www.controller.suggestion;

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
import com.yudian.www.service.suggestion.param.AeSuggestionParam;
import com.yudian.www.service.suggestion.param.GetSuggestionListParam;
import com.yudian.www.service.suggestion.ISuggestionServiceProcess;
import com.yudian.www.service.suggestion.vo.SuggestionInfoVo;
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
 * 建议档案 前端控制器
 *
 * @author yudian
 * @since 2023-01-05
 */
@Api(tags = "建议档案")
@RestController
@RequestMapping("//suggestion")
public class SuggestionController {

    @Resource
    private ISuggestionServiceProcess suggestionServiceProcess;

    @ApiOperation(value = "建议档案(添加)", notes = "建议档案(添加)", tags = {})
    @ApiOperationSupport(order = 1)
    @PostMapping("/suggestionAdd")
    public R suggestionAdd(@Valid @RequestBody AeSuggestionParam aeSuggestionParam) {
        suggestionServiceProcess.suggestionAdd(aeSuggestionParam);
        return R.ok(null, "新增成功");
    }

    @ApiOperation(value = "建议档案(编辑)", notes = "建议档案(编辑)", tags = {})
    @ApiOperationSupport(order = 2)
    @PutMapping("/suggestionEdit")
    public R suggestionEdit(@Validated({EditDomain.class}) @RequestBody AeSuggestionParam aeSuggestionParam) {
        suggestionServiceProcess.suggestionEdit(aeSuggestionParam);
        return R.ok(null, "修改成功");
    }

    @ApiOperation(value = "建议档案(删除)", notes = "建议档案(删除)", tags = {})
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/suggestionRemove/{suggestionIds}")
    public R suggestionRemove(@ApiParam(name = "suggestionIds", value = "建议档案id", required = true) @PathVariable Long[] suggestionIds) {
        suggestionServiceProcess.suggestionRemove(suggestionIds);
        return R.ok(null, "删除成功");
    }

    @ApiOperation(value = "建议档案(列表)", notes = "建议档案(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getSuggestionList")
    public R<TableRecordVo<SuggestionInfoVo>> getSuggestionList(@Valid @RequestBody GetSuggestionListParam getSuggestionListParam) {
        TableRecordVo<SuggestionInfoVo> tableRecordVo = suggestionServiceProcess.getSuggestionList(getSuggestionListParam);
        return R.ok(tableRecordVo, "获取成功");
    }

    @ApiOperation(value = "建议档案(明细)", notes = "建议档案(明细)", tags = {})
    @ApiOperationSupport(order = 5)
    @GetMapping("/getSuggestionDetail/{suggestionId}")
    public R<SuggestionInfoVo> getSuggestionDetail(@ApiParam(name = "suggestionId", value = "建议档案id", required = true) @PathVariable Long suggestionId) {
        SuggestionInfoVo suggestionInfoVo = suggestionServiceProcess.getSuggestionDetail(suggestionId);
        return R.ok(suggestionInfoVo, "获取成功");
    }

    @ApiOperation(value = "建议档案(excel导出)", notes = "建议档案(excel导出)", produces = "application/octet-stream", tags = {})
    @ApiOperationSupport(order = 6)
    @PostMapping("/suggestionExport")
    public void suggestionExport(HttpServletResponse response, @Valid @RequestBody GetSuggestionListParam getSuggestionListParam) throws IOException {
        TableRecordVo<SuggestionInfoVo> list = suggestionServiceProcess.getSuggestionList(getSuggestionListParam);
        List<EasyExcelEntity<SuggestionInfoVo>> tList = new ArrayList<>();
        EasyExcelEntity<SuggestionInfoVo> easyExcelEntity = new EasyExcelEntity<>();
        easyExcelEntity.setSheetName("建议档案");
        easyExcelEntity.setClazz(SuggestionInfoVo.class);
        easyExcelEntity.setData(list.getRecords());
        tList.add(easyExcelEntity);
        EasyExcelUtils.webExport(response, "建议档案导出", tList);
    }

    @ApiOperation(value = "建议档案(excel导入)", notes = "建议档案(excel导入)", tags = {})
    @ApiOperationSupport(order = 7)
    @PostMapping("/suggestionImport")
    public R suggestionImport(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        List<AeSuggestionParam> dataList = EasyExcel.read(multipartFile.getInputStream()).sheet(0).head(AeSuggestionParam.class).doReadSync();
        suggestionServiceProcess.suggestionAddBatch(dataList);
        return R.ok(null, "导入成功");
    }

    @ApiOperation(value = "建议档案(vue资源下载)", notes = "建议档案(vue资源下载)", tags = {})
    @ApiOperationSupport(order = 99)
    @GetMapping(value = "/getSuggestionVue", produces = "application/octet-stream")
    public void getSuggestionVue(HttpServletResponse response) {
        try {
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("suggestion.zip", "utf-8"));
            ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(out);
            zaos.setUseZip64(Zip64Mode.AsNeeded);

            org.springframework.core.io.Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(null)
            .getResources("classpath:vue/suggestion/suggestion/*");
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
