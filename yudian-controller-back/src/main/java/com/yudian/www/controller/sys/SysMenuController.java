package com.yudian.www.controller.sys;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.yudian.auth.utils.LoginUtils;
import com.yudian.common.entity.R;
import com.yudian.www.base.EditDomain;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.base.TreeSelect;
import com.yudian.www.entity.sys.SysMenu;
import com.yudian.www.service.sys.ISysMenuServiceProcess;
import com.yudian.www.service.sys.param.AeSysMenuParam;
import com.yudian.www.service.sys.param.GetSysMenuListParam;
import com.yudian.www.service.sys.vo.SysMenuInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "菜单权限表")
@RestController
@RequestMapping("/system/sys-menu")
public class SysMenuController {

    @Resource
    private ISysMenuServiceProcess sysMenuServiceProcess;

    @SaCheckPermission(value = {"system:menu:add"}, orRole = "admin", mode = SaMode.OR)
    @ApiOperation(value = "菜单权限表(添加)", notes = "菜单权限表(添加)", tags = {})
    @ApiOperationSupport(order = 1)
    @PostMapping("/sysMenuAdd")
    public R sysMenuAdd(@Valid @RequestBody AeSysMenuParam aeSysMenuParam) {
        sysMenuServiceProcess.sysMenuAdd(aeSysMenuParam);
        return R.ok(null, "新增成功");
    }

    @SaCheckPermission(value = {"system:menu:edit"}, orRole = "admin", mode = SaMode.OR)
    @ApiOperation(value = "菜单权限表(编辑)", notes = "菜单权限表(编辑)", tags = {})
    @ApiOperationSupport(order = 2)
    @PutMapping("/sysMenuEdit")
    public R sysMenuEdit(@Validated({EditDomain.class}) @RequestBody AeSysMenuParam aeSysMenuParam) {
        sysMenuServiceProcess.sysMenuEdit(aeSysMenuParam);
        return R.ok(null, "修改成功");
    }

    @SaCheckPermission(value = {"system:menu:remove"}, orRole = "admin", mode = SaMode.OR)
    @ApiOperation(value = "菜单权限表(删除)", notes = "菜单权限表(删除)", tags = {})
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/sysMenuRemove/{menuId}")
    public R sysMenuRemove(@ApiParam(name = "menuId", value = "菜单权限表id", required = true) @PathVariable Long menuId) {
        sysMenuServiceProcess.sysMenuRemove(menuId);
        return R.ok(null, "删除成功");
    }

    @SaCheckPermission(value = {"system:menu:list"}, orRole = "admin", mode = SaMode.OR)
    @ApiOperation(value = "菜单权限表(列表)", notes = "菜单权限表(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getSysMenuList")
    public R<TableRecordVo<SysMenuInfoVo>> getSysMenuList(@Valid @RequestBody GetSysMenuListParam getSysMenuListParam) {
        TableRecordVo<SysMenuInfoVo> tableRecordVo = sysMenuServiceProcess.getSysMenuList(getSysMenuListParam);
        return R.ok(tableRecordVo, "获取成功");
    }

    @SaCheckPermission(value = {"system:menu:query"}, orRole = "admin", mode = SaMode.OR)
    @ApiOperation(value = "菜单权限表(明细)", notes = "菜单权限表(明细)", tags = {})
    @ApiOperationSupport(order = 5)
    @GetMapping("/getSysMenuDetail/{menuId}")
    public R<SysMenuInfoVo> getSysMenuDetail(@ApiParam(name = "menuId", value = "菜单权限表id", required = true) @PathVariable Long menuId) {
        SysMenuInfoVo sysMenuInfoVo = sysMenuServiceProcess.getSysMenuDetail(menuId);
        return R.ok(sysMenuInfoVo, "获取成功");
    }

    @ApiOperation(value = "角色ID查询菜单下拉树结构", notes = "角色ID查询菜单下拉树结构")
    @ApiOperationSupport(order = 9)
    @GetMapping("/roleMenuTreeSelect/{roleId}")
    public R<Map<String, Object>> roleMenuTreeSelect(@ApiParam(name = "roleId", value = "角色id", required = true) @PathVariable Long roleId) {
        List<SysMenu> sysMenuList = sysMenuServiceProcess.selectMenuList(LoginUtils.getUserId());
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("menus", sysMenuServiceProcess.buildMenuTreeSelect(sysMenuList));
        resultMap.put("checkedKeys", sysMenuServiceProcess.selectMenuListByRoleId(roleId));
        return R.ok(resultMap, "获取成功");
    }

    @ApiOperation(value = "菜单下拉树结构", notes = "菜单下拉树结构")
    @ApiOperationSupport(order = 10)
    @GetMapping("/treeSelect")
    public R<List<TreeSelect>> treeSelect() {
        List<SysMenu> sysMenuList = sysMenuServiceProcess.selectMenuList(LoginUtils.getUserId());
        List<TreeSelect> treeSelects = sysMenuServiceProcess.buildMenuTreeSelect(sysMenuList);
        return R.ok(treeSelects, "获取成功");
    }

//    @SaCheckPermission(value = {"system:menu:export"}, orRole = "admin", mode = SaMode.OR)
//    @ApiOperation(value = "菜单权限表(excel导出)", notes = "菜单权限表(excel导出)", produces = "application/octet-stream", tags = {})
//    @ApiOperationSupport(order = 6)
//    @PostMapping("/sysMenuExport")
//    public void sysMenuExport(HttpServletResponse response, @Valid @RequestBody GetSysMenuListParam getSysMenuListParam) throws IOException {
//        TableRecordVo<SysMenuInfoVo> list = sysMenuServiceProcess.getSysMenuList(getSysMenuListParam);
//        List<EasyExcelEntity<SysMenuInfoVo>> tList = new ArrayList<>();
//        EasyExcelEntity<SysMenuInfoVo> easyExcelEntity = new EasyExcelEntity<>();
//        easyExcelEntity.setSheetName("菜单权限表");
//        easyExcelEntity.setClazz(SysMenuInfoVo.class);
//        easyExcelEntity.setData(list.getRecords());
//        tList.add(easyExcelEntity);
//        EasyExcelUtils.webExport(response, "菜单权限表导出", tList);
//    }
//
//    @ApiOperation(value = "菜单权限表(excel导入)", notes = "菜单权限表(excel导入)", tags = {})
//    @ApiOperationSupport(order = 7)
//    @PostMapping("/sysMenuImport")
//    public R sysMenuImport(@RequestParam("file") MultipartFile multipartFile) throws IOException {
//        List<AeSysMenuParam> dataList = EasyExcel.read(multipartFile.getInputStream()).sheet(0).head(AeSysMenuParam.class).doReadSync();
//        sysMenuServiceProcess.sysMenuAddBatch(dataList);
//        return R.ok(null, "导入成功");
//    }
//
//    @ApiOperation(value = "菜单权限表(vue资源下载)", notes = "菜单权限表(vue资源下载)", tags = {})
//    @ApiOperationSupport(order = 99)
//    @GetMapping(value = "/getSysMenuVue", produces = "application/octet-stream")
//    public void getSysMenuVue(HttpServletResponse response) {
//        try {
//            ServletOutputStream out = response.getOutputStream();
//            response.setContentType("application/octet-stream");
//            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("sys-menu.zip", "utf-8"));
//            ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(out);
//            zaos.setUseZip64(Zip64Mode.AsNeeded);
//
//            org.springframework.core.io.Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(null)
//                    .getResources("classpath:vue//sys-menu/*");
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
