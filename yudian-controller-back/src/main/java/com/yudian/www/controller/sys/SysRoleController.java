package com.yudian.www.controller.sys;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.yudian.common.entity.R;
import com.yudian.www.base.EditDomain;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.project.annotation.Log;
import com.yudian.www.project.annotation.enums.BusinessType;
import com.yudian.www.service.sys.ISysRoleServiceProcess;
import com.yudian.www.service.sys.param.AeSysRoleParam;
import com.yudian.www.service.sys.param.GetSysRoleListParam;
import com.yudian.www.service.sys.vo.SysRoleInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(tags = "角色信息表")
@RestController
@RequestMapping("/system/sys-role")
public class SysRoleController {

    @Resource
    private ISysRoleServiceProcess sysRoleServiceProcess;

    @SaCheckPermission(value = {"system:role:add"}, orRole = "admin", mode = SaMode.OR)
    @Log(title = "角色管理", businessType = BusinessType.INSERT)
    @ApiOperation(value = "角色信息表(添加)", notes = "角色信息表(添加)", tags = {})
    @ApiOperationSupport(order = 1)
    @PostMapping("/sysRoleAdd")
    public R sysRoleAdd(@Valid @RequestBody AeSysRoleParam aeSysRoleParam) {
        sysRoleServiceProcess.sysRoleAdd(aeSysRoleParam);
        return R.ok(null, "新增成功");
    }

    @SaCheckPermission(value = {"system:role:edit"}, orRole = "admin", mode = SaMode.OR)
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @ApiOperation(value = "角色信息表(编辑)", notes = "角色信息表(编辑)", tags = {})
    @ApiOperationSupport(order = 2)
    @PutMapping("/sysRoleEdit")
    public R sysRoleEdit(@Validated({EditDomain.class}) @RequestBody AeSysRoleParam aeSysRoleParam) {
        sysRoleServiceProcess.sysRoleEdit(aeSysRoleParam);
        return R.ok(null, "修改成功");
    }

    @SaCheckPermission(value = {"system:role:remove"}, orRole = "admin", mode = SaMode.OR)
    @Log(title = "角色管理", businessType = BusinessType.DELETE)
    @ApiOperation(value = "角色信息表(删除)", notes = "角色信息表(删除)", tags = {})
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/sysRoleRemove/{roleIds}")
    public R sysRoleRemove(@ApiParam(name = "roleIds", value = "角色信息表id", required = true) @PathVariable Long[] roleIds) {
        sysRoleServiceProcess.sysRoleRemove(roleIds);
        return R.ok(null, "删除成功");
    }

    @SaCheckPermission(value = {"system:role:list", "system:user:list", "merchant:list"}, orRole = "admin", mode = SaMode.OR)
    @ApiOperation(value = "角色信息表(列表)", notes = "角色信息表(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getSysRoleList")
    public R<TableRecordVo<SysRoleInfoVo>> getSysRoleList(@Valid @RequestBody GetSysRoleListParam getSysRoleListParam) {
        TableRecordVo<SysRoleInfoVo> tableRecordVo = sysRoleServiceProcess.getSysRoleList(getSysRoleListParam);
        return R.ok(tableRecordVo, "获取成功");
    }

    @SaCheckPermission(value = {"system:role:query"}, orRole = "admin", mode = SaMode.OR)
    @ApiOperation(value = "角色信息表(明细)", notes = "角色信息表(明细)", tags = {})
    @ApiOperationSupport(order = 5)
    @GetMapping("/getSysRoleDetail/{roleId}")
    public R<SysRoleInfoVo> getSysRoleDetail(@ApiParam(name = "roleId", value = "角色信息表id", required = true) @PathVariable Long roleId) {
        SysRoleInfoVo sysRoleInfoVo = sysRoleServiceProcess.getSysRoleDetail(roleId);
        return R.ok(sysRoleInfoVo, "获取成功");
    }

    @SaCheckPermission(value = {"system:role:edit"}, orRole = "admin", mode = SaMode.OR)
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @ApiOperation(value = "角色状态修改", notes = "角色状态修改")
    @ApiOperationSupport(order = 6)
    @DynamicParameters(properties = {
            @DynamicParameter(name = "roleId", value = "角色id", required = true),
            @DynamicParameter(name = "roleStatus", value = "状态", required = true)
    })
    @PutMapping("/changeRoleStatus")
    public R changeRoleStatus(@RequestBody JSONObject changeRoleStatusParam) {
        Long roleId = changeRoleStatusParam.getLong("roleId");
        Boolean roleStatus = changeRoleStatusParam.getBoolean("roleStatus");
        if (null == roleId || null == roleStatus) {
            return R.error(null, "参数错误");
        }
        sysRoleServiceProcess.changeRoleStatus(roleId, roleStatus);
        return R.ok(null, "修改成功");
    }

    @SaCheckPermission(value = {"system:role:edit"}, orRole = "admin", mode = SaMode.OR)
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @ApiOperation(value = "角色数据权限修改", notes = "角色数据权限修改")
    @ApiOperationSupport(order = 6)
    @DynamicParameters(properties = {
            @DynamicParameter(name = "roleId", value = "角色id", required = true),
            @DynamicParameter(name = "dataScope", value = "数据范围：1=全部数据权限；2=商家数据权限；", required = true)
    })
    @PutMapping("/changeDataScope")
    public R changeDataScope(@RequestBody JSONObject changeDataScopeParam) {
        Long roleId = changeDataScopeParam.getLong("roleId");
        String dataScope = changeDataScopeParam.getString("dataScope");
        if (null == roleId || StringUtils.isBlank(dataScope)) {
            return R.error(null, "参数错误");
        }
        sysRoleServiceProcess.changeDataScope(roleId, dataScope);
        return R.ok(null, "修改成功");
    }

//    @ApiOperation(value = "角色信息表(excel导出)", notes = "角色信息表(excel导出)", produces = "application/octet-stream", tags = {})
//    @ApiOperationSupport(order = 6)
//    @PostMapping("/sysRoleExport")
//    public void sysRoleExport(HttpServletResponse response, @Valid @RequestBody GetSysRoleListParam getSysRoleListParam) throws IOException {
//        TableRecordVo<SysRoleInfoVo> list = sysRoleServiceProcess.getSysRoleList(getSysRoleListParam);
//        List<EasyExcelEntity<SysRoleInfoVo>> tList = new ArrayList<>();
//        EasyExcelEntity<SysRoleInfoVo> easyExcelEntity = new EasyExcelEntity<>();
//        easyExcelEntity.setSheetName("角色信息表");
//        easyExcelEntity.setClazz(SysRoleInfoVo.class);
//        easyExcelEntity.setData(list.getRecords());
//        tList.add(easyExcelEntity);
//        EasyExcelUtils.webExport(response, "角色信息表导出", tList);
//    }
//
//    @ApiOperation(value = "角色信息表(excel导入)", notes = "角色信息表(excel导入)", tags = {})
//    @ApiOperationSupport(order = 7)
//    @PostMapping("/sysRoleImport")
//    public R sysRoleImport(@RequestParam("file") MultipartFile multipartFile) throws IOException {
//        List<AeSysRoleParam> dataList = EasyExcel.read(multipartFile.getInputStream()).sheet(0).head(AeSysRoleParam.class).doReadSync();
//        sysRoleServiceProcess.sysRoleAddBatch(dataList);
//        return R.ok(null, "导入成功");
//    }
//
//    @ApiOperation(value = "角色信息表(vue资源下载)", notes = "角色信息表(vue资源下载)", tags = {})
//    @ApiOperationSupport(order = 99)
//    @GetMapping(value = "/getSysRoleVue", produces = "application/octet-stream")
//    public void getSysRoleVue(HttpServletResponse response) {
//        try {
//            ServletOutputStream out = response.getOutputStream();
//            response.setContentType("application/octet-stream");
//            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("sys-role.zip", "utf-8"));
//            ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(out);
//            zaos.setUseZip64(Zip64Mode.AsNeeded);
//
//            org.springframework.core.io.Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(null)
//            .getResources("classpath:vue//sys-role/*");
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
