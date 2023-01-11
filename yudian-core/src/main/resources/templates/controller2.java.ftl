package ${package.Controller};

<#if swagger2>
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
</#if>
import com.alibaba.excel.EasyExcel;
import com.yudian.common.entity.R;
import com.yudian.common.utils.easyexcel.EasyExcelEntity;
import com.yudian.common.utils.easyexcel.EasyExcelUtils;
import com.yudian.www.base.EditDomain;
import com.yudian.www.base.TableRecordVo;
import ${package.Service}.param.Ae${entity}Param;
import ${package.Service}.param.Get${entity}ListParam;
import ${package.Service}.${table.serviceName}Process;
import ${package.Service}.vo.${entity}InfoVo;
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
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
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
 * ${table.comment!?trim} 前端控制器
 *
 * @author ${author}
 * @since ${date}
 */
<#if swagger2>
@Api(tags = "${table.comment!?trim}")
</#if>
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
    class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
    <#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
    <#else>
public class ${table.controllerName} {
    </#if>

    @Resource
    private ${table.serviceName}Process ${table.serviceName?substring(1)?uncap_first}Process;

    @ApiOperation(value = "${table.comment!?trim}(添加)", notes = "${table.comment!?trim}(添加)", tags = {})
    @ApiOperationSupport(order = 1)
    @PostMapping("/${entity?uncap_first}Add")
    public R ${entity?uncap_first}Add(@Valid @RequestBody Ae${entity}Param ae${entity}Param) {
        ${table.serviceName?substring(1)?uncap_first}Process.${entity?uncap_first}Add(ae${entity}Param);
        return R.ok(null, "新增成功");
    }

    @ApiOperation(value = "${table.comment!?trim}(编辑)", notes = "${table.comment!?trim}(编辑)", tags = {})
    @ApiOperationSupport(order = 2)
    @PutMapping("/${entity?uncap_first}Edit")
    public R ${entity?uncap_first}Edit(@Validated({EditDomain.class}) @RequestBody Ae${entity}Param ae${entity}Param) {
        ${table.serviceName?substring(1)?uncap_first}Process.${entity?uncap_first}Edit(ae${entity}Param);
        return R.ok(null, "修改成功");
    }

    @ApiOperation(value = "${table.comment!?trim}(删除)", notes = "${table.comment!?trim}(删除)", tags = {})
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/${entity?uncap_first}Remove/{<#list table.fields as field><#if field.keyFlag>${field.propertyName}</#if></#list>s}")
    public R ${entity?uncap_first}Remove(@ApiParam(name = "<#list table.fields as field><#if field.keyFlag>${field.propertyName}</#if></#list>s", value = "${table.comment!?trim}id", required = true) @PathVariable <#list table.fields as field><#if field.keyFlag>${field.propertyType}[] ${field.propertyName}s</#if></#list>) {
        ${table.serviceName?substring(1)?uncap_first}Process.${entity?uncap_first}Remove(<#list table.fields as field><#if field.keyFlag>${field.propertyName}s</#if></#list>);
        return R.ok(null, "删除成功");
    }

    @ApiOperation(value = "${table.comment!?trim}(列表)", notes = "${table.comment!?trim}(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/get${entity}List")
    public R<TableRecordVo<${entity}InfoVo>> get${entity}List(@Valid @RequestBody Get${entity}ListParam get${entity}ListParam) {
        TableRecordVo<${entity}InfoVo> tableRecordVo = ${table.serviceName?substring(1)?uncap_first}Process.get${entity}List(get${entity}ListParam);
        return R.ok(tableRecordVo, "获取成功");
    }

    @ApiOperation(value = "${table.comment!?trim}(明细)", notes = "${table.comment!?trim}(明细)", tags = {})
    @ApiOperationSupport(order = 5)
    @GetMapping("/get${entity}Detail/{<#list table.fields as field><#if field.keyFlag>${field.propertyName}</#if></#list>}")
    public R<${entity}InfoVo> get${entity}Detail(@ApiParam(name = "<#list table.fields as field><#if field.keyFlag>${field.propertyName}</#if></#list>", value = "${table.comment!?trim}id", required = true) @PathVariable <#list table.fields as field><#if field.keyFlag>${field.propertyType} ${field.propertyName}</#if></#list>) {
        ${entity}InfoVo ${entity?uncap_first}InfoVo = ${table.serviceName?substring(1)?uncap_first}Process.get${entity}Detail(<#list table.fields as field><#if field.keyFlag>${field.propertyName}</#if></#list>);
        return R.ok(${entity?uncap_first}InfoVo, "获取成功");
    }

    @ApiOperation(value = "${table.comment!?trim}(excel导出)", notes = "${table.comment!?trim}(excel导出)", produces = "application/octet-stream", tags = {})
    @ApiOperationSupport(order = 6)
    @PostMapping("/${entity?uncap_first}Export")
    public void ${entity?uncap_first}Export(HttpServletResponse response, @Valid @RequestBody Get${entity}ListParam get${entity}ListParam) throws IOException {
        TableRecordVo<${entity}InfoVo> list = ${table.serviceName?substring(1)?uncap_first}Process.get${entity}List(get${entity}ListParam);
        List<EasyExcelEntity<${entity}InfoVo>> tList = new ArrayList<>();
        EasyExcelEntity<${entity}InfoVo> easyExcelEntity = new EasyExcelEntity<>();
        easyExcelEntity.setSheetName("${table.comment!?trim}");
        easyExcelEntity.setClazz(${entity}InfoVo.class);
        easyExcelEntity.setData(list.getRecords());
        tList.add(easyExcelEntity);
        EasyExcelUtils.webExport(response, "${table.comment!?trim}导出", tList);
    }

    @ApiOperation(value = "${table.comment!?trim}(excel导入)", notes = "${table.comment!?trim}(excel导入)", tags = {})
    @ApiOperationSupport(order = 7)
    @PostMapping("/${entity?uncap_first}Import")
    public R ${entity?uncap_first}Import(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        List<Ae${entity}Param> dataList = EasyExcel.read(multipartFile.getInputStream()).sheet(0).head(Ae${entity}Param.class).doReadSync();
        ${table.serviceName?substring(1)?uncap_first}Process.${entity?uncap_first}AddBatch(dataList);
        return R.ok(null, "导入成功");
    }

    @ApiOperation(value = "${table.comment!?trim}(vue资源下载)", notes = "${table.comment!?trim}(vue资源下载)", tags = {})
    @ApiOperationSupport(order = 99)
    @GetMapping(value = "/get${entity}Vue", produces = "application/octet-stream")
    public void get${entity}Vue(HttpServletResponse response) {
        try {
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>.zip", "utf-8"));
            ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(out);
            zaos.setUseZip64(Zip64Mode.AsNeeded);

            org.springframework.core.io.Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(null)
            .getResources("classpath:vue/${cfg.moduleName}/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>/*");
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
</#if>