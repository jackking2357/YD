package com.yudian.www.controller.sys;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.alibaba.fastjson.JSONObject;
import com.yudian.common.entity.R;
import com.yudian.common.utils.SecurityUtils;
import com.yudian.www.base.AddDomain;
import com.yudian.www.base.EditDomain;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.project.annotation.Log;
import com.yudian.www.project.annotation.enums.BusinessType;
import com.yudian.www.service.sys.ISysUserServiceProcess;
import com.yudian.www.service.sys.param.AeSysUserParam;
import com.yudian.www.service.sys.param.GetSysUserListParam;
import com.yudian.www.service.sys.vo.SysUserInfoVo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "用户信息表")
@RestController
@RequestMapping("/system/sys-user")
@RequiredArgsConstructor
public class SysUserController {

    private final ISysUserServiceProcess sysUserServiceProcess;

    @SaCheckPermission(value = {"system:user:add"}, orRole = "admin", mode = SaMode.OR)
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @ApiOperation(value = "用户信息表(添加)", notes = "用户信息表(添加)", tags = {})
    @ApiOperationSupport(order = 1)
    @PostMapping("/sysUserAdd")
    public R sysUserAdd(@Validated({AddDomain.class}) @RequestBody AeSysUserParam aeSysUserParam) {
        aeSysUserParam.setPassword(SecurityUtils.encryptPassword(aeSysUserParam.getPassword()));
        sysUserServiceProcess.sysUserAdd(aeSysUserParam);
        return R.ok(null, "新增成功");
    }

    @SaCheckPermission(value = {"system:user:edit"}, orRole = "admin", mode = SaMode.OR)
    @ApiOperation(value = "用户信息表(编辑)", notes = "用户信息表(编辑)", tags = {})
    @ApiOperationSupport(order = 2)
    @PutMapping("/sysUserEdit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    public R sysUserEdit(@Validated({EditDomain.class}) @RequestBody AeSysUserParam aeSysUserParam) {
        sysUserServiceProcess.sysUserEdit(aeSysUserParam);
        return R.ok(null, "修改成功");
    }

    @SaCheckPermission(value = {"system:user:remove"}, orRole = "admin", mode = SaMode.OR)
    @ApiOperation(value = "用户信息表(删除)", notes = "用户信息表(删除)", tags = {})
    @ApiOperationSupport(order = 3)
    @DeleteMapping("/sysUserRemove/{userIds}")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    public R sysUserRemove(@ApiParam(name = "userIds", value = "用户信息表id", required = true) @PathVariable Long[] userIds) {
        sysUserServiceProcess.sysUserRemove(1, userIds);
        return R.ok(null, "删除成功");
    }

    @SaCheckPermission(value = {"system:user:list"}, orRole = "admin", mode = SaMode.OR)
    @ApiOperation(value = "用户信息表(列表)", notes = "用户信息表(列表)", tags = {})
    @ApiOperationSupport(order = 4)
    @PostMapping("/getSysUserList")
    public R<TableRecordVo<SysUserInfoVo>> getSysUserList(@Valid @RequestBody GetSysUserListParam getSysUserListParam) {
        TableRecordVo<SysUserInfoVo> tableRecordVo = sysUserServiceProcess.getSysUserList(1, getSysUserListParam);
        return R.ok(tableRecordVo, "获取成功");
    }

    @SaCheckPermission(value = {"system:user:query"}, orRole = "admin", mode = SaMode.OR)
    @ApiOperation(value = "用户信息表(明细)", notes = "用户信息表(明细)", tags = {})
    @ApiOperationSupport(order = 5)
    @GetMapping("/getSysUserDetail/{userId}")
    public R<SysUserInfoVo> getSysUserDetail(@ApiParam(name = "userId", value = "用户信息表id", required = true) @PathVariable Long userId) {
        return R.ok((SysUserInfoVo) sysUserServiceProcess.getSysUserDetail(1, userId), "获取成功");
    }

    @SaCheckPermission(value = {"system:user:resetPwd"}, orRole = "admin", mode = SaMode.OR)
    @ApiOperation(value = "账号密码修改", notes = "账号密码修改")
    @ApiOperationSupport(order = 6)
    @DynamicParameters(name = "resetUserPwdParam", properties = {
            @DynamicParameter(name = "userId", value = "用户id", required = true),
            @DynamicParameter(name = "password", value = "新密码", required = true)
    })
    @Log(title = "员工管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetUserPwd")
    public R resetUserPwd(@RequestBody JSONObject resetUserPwdParam) {
        Long userId = resetUserPwdParam.getLong("userId");
        String password = resetUserPwdParam.getString("password");
        if (null == userId || StringUtils.isBlank(password)) {
            return R.error(null, "参数错误");
        }
        sysUserServiceProcess.resetUserPwd(1, userId, SecurityUtils.encryptPassword(password));
        return R.ok(null, "修改成功");
    }

//    @ApiOperation(value = "用户信息表(excel导出)", notes = "用户信息表(excel导出)", produces = "application/octet-stream", tags = {})
//    @ApiOperationSupport(order = 6)
//    @PostMapping("/sysUserExport")
//    public void sysUserExport(HttpServletResponse response, @Valid @RequestBody GetSysUserListParam getSysUserListParam) throws IOException {
//        TableRecordVo<SysUserInfoVo> list = sysUserServiceProcess.getSysUserList(getSysUserListParam);
//        List<EasyExcelEntity<SysUserInfoVo>> tList = new ArrayList<>();
//        EasyExcelEntity<SysUserInfoVo> easyExcelEntity = new EasyExcelEntity<>();
//        easyExcelEntity.setSheetName("用户信息表");
//        easyExcelEntity.setClazz(SysUserInfoVo.class);
//        easyExcelEntity.setData(list.getRecords());
//        tList.add(easyExcelEntity);
//        EasyExcelUtils.webExport(response, "用户信息表导出", tList);
//    }
//
//    @ApiOperation(value = "用户信息表(excel导入)", notes = "用户信息表(excel导入)", tags = {})
//    @ApiOperationSupport(order = 7)
//    @PostMapping("/sysUserImport")
//    public R sysUserImport(@RequestParam("file") MultipartFile multipartFile) throws IOException {
//        List<AeSysUserParam> dataList = EasyExcel.read(multipartFile.getInputStream()).sheet(0).head(AeSysUserParam.class).doReadSync();
//        sysUserServiceProcess.sysUserAddBatch(dataList);
//        return R.ok(null, "导入成功");
//    }
//
//    @ApiOperation(value = "用户信息表(vue资源下载)", notes = "用户信息表(vue资源下载)", tags = {})
//    @ApiOperationSupport(order = 99)
//    @GetMapping(value = "/getSysUserVue", produces = "application/octet-stream")
//    public void getSysUserVue(HttpServletResponse response) {
//        try {
//            ServletOutputStream out = response.getOutputStream();
//            response.setContentType("application/octet-stream");
//            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("sys-user.zip", "utf-8"));
//            ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(out);
//            zaos.setUseZip64(Zip64Mode.AsNeeded);
//
//            org.springframework.core.io.Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(null)
//                    .getResources("classpath:vue//sys-user/*");
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
