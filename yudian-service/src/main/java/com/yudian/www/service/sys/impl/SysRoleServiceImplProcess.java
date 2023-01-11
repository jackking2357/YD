package com.yudian.www.service.sys.impl;

import cn.dev33.satoken.session.SaSessionCustomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.config.exceptions.ServiceException;
import com.yudian.www.entity.sys.SysMenu;
import com.yudian.www.entity.sys.SysRole;
import com.yudian.www.entity.sys.SysRoleMenu;
import com.yudian.www.entity.sys.SysUserRole;
import com.yudian.www.service.sys.*;
import com.yudian.www.service.sys.param.AeSysRoleParam;
import com.yudian.www.service.sys.param.GetSysRoleListParam;
import com.yudian.www.service.sys.vo.SysRoleInfoVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色信息表 服务实现类-流程
 *

 * @since 2022-01-06
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImplProcess implements ISysRoleServiceProcess {

    private final ISysRoleService sysRoleService;
    private final ISysUserRoleService sysUserRoleService;
    private final ISysMenuService sysMenuService;
    private final ISysRoleMenuService sysRoleMenuService;

    /**
     * 添加
     *
     * @param aeSysRoleParam
     */
    @Override
    public void sysRoleAdd(AeSysRoleParam aeSysRoleParam) {
        Set<Long> menuIds = aeSysRoleParam.getMenuIds();
        String roleKey = aeSysRoleParam.getRoleKey();
        this.checkMenuIds(menuIds);
        this.checkRoleKey(roleKey);

        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(aeSysRoleParam, sysRole);
        boolean save = sysRoleService.save(sysRole);
        if (!save) {
            throw new ServiceException("保存失败");
        }
        saveBathRoleMenu(menuIds, sysRole);
    }

    @Override
    public void sysRoleAddBatch(List<AeSysRoleParam> aeSysRoleParamList) {
        List<SysRole> sysRoleList = aeSysRoleParamList.stream()
                .map(aeSysRoleParam -> {
                    SysRole sysRole = new SysRole();
                    BeanUtils.copyProperties(aeSysRoleParam, sysRole);
                    return sysRole;
                }).collect(Collectors.toList());
        boolean save = sysRoleService.saveBatch(sysRoleList);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    private void checkRoleKey(String roleKey) {
        SysRole dbSysRole = sysRoleService.getOne(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getRoleKey, roleKey));
        if (null != dbSysRole) {
            throw new ServiceException("权限字符已存在");
        }
    }

    /**
     * 编辑
     *
     * @param aeSysRoleParam
     */
    @Override
    public void sysRoleEdit(AeSysRoleParam aeSysRoleParam) {
        SysRole sysRole = sysRoleService.getById(aeSysRoleParam.getRoleId());
        if (null == sysRole) {
            throw new ServiceException("记录不存在");
        }
        Set<Long> menuIds = aeSysRoleParam.getMenuIds();
        String roleKey = aeSysRoleParam.getRoleKey();
        this.checkMenuIds(menuIds);
        if (!sysRole.getRoleKey().equals(roleKey)) {
            this.checkRoleKey(roleKey);
        }

        BeanUtils.copyProperties(aeSysRoleParam, sysRole);
        boolean update = sysRoleService.updateById(sysRole);
        if (!update) {
            throw new ServiceException("编辑失败");
        }
        SaSessionCustomUtil.deleteSessionById("role-" + sysRole.getRoleKey());
        // 删除原来绑定
        sysRoleMenuService.remove(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getRoleId, sysRole.getRoleId()));
        saveBathRoleMenu(menuIds, sysRole);
    }

    /**
     * 删除
     *
     * @param roleIds
     */
    @Override
    public void sysRoleRemove(Long[] roleIds) {
        for (Long roleId : roleIds) {
            if (roleId <= 10L) {
                throw new ServiceException("存在系统内置默认角色，无法删除");
            }
            int count = sysUserRoleService.count(Wrappers.<SysUserRole>lambdaQuery()
                    .eq(SysUserRole::getRoleId, roleId));
            if (0 != count) {
                throw new ServiceException("已有账号绑定改角色，无法删除");
            }
        }
        boolean remove = sysRoleService.removeByIds(Arrays.asList(roleIds));
        if (!remove) {
            throw new ServiceException("删除失败");
        }
    }

    /**
     * 分页查询
     *
     * @param getSysRoleListParam
     * @return
     */
    @Override
    public TableRecordVo<SysRoleInfoVo> getSysRoleList(GetSysRoleListParam getSysRoleListParam) {
        LambdaQueryWrapper<SysRole> queryWrapper = new QueryWrapper<SysRole>().lambda();
        queryWrapper
                .eq(null != getSysRoleListParam.getRoleStatus(), SysRole::getRoleStatus, getSysRoleListParam.getRoleStatus())
                .like(StringUtils.isNotBlank(getSysRoleListParam.getRoleKey()), SysRole::getRoleKey, getSysRoleListParam.getRoleKey())
                .like(StringUtils.isNotBlank(getSysRoleListParam.getRoleName()), SysRole::getRoleName, getSysRoleListParam.getRoleName())
                .ge(null != getSysRoleListParam.getStartDateTime(), SysRole::getCreateDatetime, getSysRoleListParam.getStartDateTime())
                .le(null != getSysRoleListParam.getEndDateTime(), SysRole::getCreateDatetime, getSysRoleListParam.getEndDateTime())
                .orderByAsc(SysRole::getRoleSort);

        IPage<SysRole> page = sysRoleService.page(new Page<>(getSysRoleListParam.getPageNo(), getSysRoleListParam.getPageSize()), queryWrapper);

        List<SysRole> records = page.getRecords();

        List<SysRoleInfoVo> sysRoleInfoVoList = this.entityToVo(records);

        TableRecordVo tableRecordVo = new TableRecordVo<>();
        tableRecordVo.setRecords(sysRoleInfoVoList);
        tableRecordVo.setTotal(page.getTotal());
        tableRecordVo.setPages(page.getPages());
        return tableRecordVo;
    }

    /**
     * 明细
     *
     * @param roleId
     * @return
     */
    @Override
    public SysRoleInfoVo getSysRoleDetail(Long roleId) {
        SysRole sysRole = sysRoleService.getById(roleId);
        if (null == sysRole) {
            throw new ServiceException("记录不存在");
        }
        List<SysRoleInfoVo> sysRoleInfoVos = this.entityToVo(Arrays.asList(sysRole));
        return sysRoleInfoVos.stream().findFirst().orElse(null);
    }

    @Override
    public void changeRoleStatus(Long roleId, Boolean roleStatus) {
        SysRole sysRole = sysRoleService.getById(roleId);
        if (null == sysRole) {
            throw new ServiceException("记录不存在");
        }
        if (sysRole.getRoleStatus().equals(roleStatus)) {
            return;
        }
        sysRoleService.update(Wrappers.<SysRole>lambdaUpdate()
                .set(SysRole::getRoleStatus, roleStatus)
                .eq(SysRole::getRoleId, roleId));
    }

    @Override
    public void changeDataScope(Long roleId, String dataScope) {
        SysRole sysRole = sysRoleService.getById(roleId);
        if (null == sysRole) {
            throw new ServiceException("记录不存在");
        }
        if (sysRole.getDataScope().equals(dataScope)) {
            return;
        }
        sysRoleService.update(Wrappers.<SysRole>lambdaUpdate()
                .set(SysRole::getDataScope, dataScope)
                .eq(SysRole::getRoleId, roleId));
    }

    /**
     * 实体类转VO
     *
     * @param records
     * @return
     */
    private List<SysRoleInfoVo> entityToVo(List<SysRole> records) {
        return records.stream()
                .map(sysRole -> {
                    SysRoleInfoVo sysRoleInfoVo = new SysRoleInfoVo();
                    BeanUtils.copyProperties(sysRole, sysRoleInfoVo);
                    return sysRoleInfoVo;
                }).collect(Collectors.toList());
    }

    /**
     * 新增或者编辑时统一校验
     *
     * @param aeSysRoleParam
     */
    private void checkParam(AeSysRoleParam aeSysRoleParam) {

    }

    /**
     * 检测菜单id
     *
     * @param menuIds
     */
    private void checkMenuIds(Set<Long> menuIds) {
        if (0 != menuIds.size()) {
            Set<Long> dbMenuIdSet = sysMenuService.list(Wrappers.<SysMenu>lambdaQuery()
                    .select(SysMenu::getMenuId)
                    .in(SysMenu::getMenuId, menuIds))
                    .stream().map(SysMenu::getMenuId)
                    .collect(Collectors.toSet());
            if (dbMenuIdSet.size() != menuIds.size()) {
                throw new ServiceException("有菜单id不存在，请刷新页面重新创建");
            }
        }
    }

    /**
     * 批量保存角色菜单绑定关系
     *
     * @param menuIds
     * @param sysRole
     */
    private void saveBathRoleMenu(Set<Long> menuIds, SysRole sysRole) {
        List<SysRoleMenu> sysRoleMenuList = menuIds.stream().map(menuId -> {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(sysRole.getRoleId());
            sysRoleMenu.setMenuId(menuId);
            return sysRoleMenu;
        }).collect(Collectors.toList());
        if (0 != sysRoleMenuList.size()) {
            sysRoleMenuService.saveBatch(sysRoleMenuList);
        }
    }
}