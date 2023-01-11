package com.yudian.www.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.config.exceptions.ServiceException;
import com.yudian.www.entity.sys.SysRoleMenu;
import com.yudian.www.service.sys.ISysRoleMenuService;
import com.yudian.www.service.sys.ISysRoleMenuServiceProcess;
import com.yudian.www.service.sys.param.AeSysRoleMenuParam;
import com.yudian.www.service.sys.param.GetSysRoleMenuListParam;
import com.yudian.www.service.sys.vo.SysRoleMenuInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色和菜单关联表 服务实现类-流程
 *

 * @since 2022-01-06
 */
@Service
public class SysRoleMenuServiceImplProcess implements ISysRoleMenuServiceProcess {

    @Resource
    private ISysRoleMenuService sysRoleMenuService;

    /**
     * 添加
     *
     * @param aeSysRoleMenuParam
     */
    @Override
    public void sysRoleMenuAdd(AeSysRoleMenuParam aeSysRoleMenuParam) {
        aeSysRoleMenuParam.initParam();
        this.checkParam(aeSysRoleMenuParam);
        SysRoleMenu sysRoleMenu = new SysRoleMenu();
        BeanUtils.copyProperties(aeSysRoleMenuParam, sysRoleMenu);
        boolean save = sysRoleMenuService.save(sysRoleMenu);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    @Override
    public void sysRoleMenuAddBatch(List<AeSysRoleMenuParam> aeSysRoleMenuParamList) {
        List<SysRoleMenu> sysRoleMenuList = aeSysRoleMenuParamList.stream()
                .map(aeSysRoleMenuParam -> {
                    SysRoleMenu sysRoleMenu = new SysRoleMenu();
                    BeanUtils.copyProperties(aeSysRoleMenuParam, sysRoleMenu);
                    return sysRoleMenu;
                }).collect(Collectors.toList());
        boolean save = sysRoleMenuService.saveBatch(sysRoleMenuList);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    /**
     * 编辑
     *
     * @param aeSysRoleMenuParam
     */
    @Override
    public void sysRoleMenuEdit(AeSysRoleMenuParam aeSysRoleMenuParam) {
        aeSysRoleMenuParam.initParam();
        this.checkParam(aeSysRoleMenuParam);
        SysRoleMenu sysRoleMenu = sysRoleMenuService.getById(aeSysRoleMenuParam.getRoleId());
        if (null == sysRoleMenu) {
            throw new ServiceException("记录不存在");
        }
        BeanUtils.copyProperties(aeSysRoleMenuParam, sysRoleMenu);
        boolean update = sysRoleMenuService.updateById(sysRoleMenu);
        if (!update) {
            throw new ServiceException("编辑失败");
        }
    }

    /**
     * 删除
     *
     * @param roleIds
     */
    @Override
    public void sysRoleMenuRemove(Long[] roleIds) {
        boolean remove = sysRoleMenuService.removeByIds(Arrays.asList(roleIds));
        if (!remove) {
            throw new ServiceException("删除失败");
        }
    }

    /**
     * 分页查询
     *
     * @param getSysRoleMenuListParam
     * @return
     */
    @Override
    public TableRecordVo<SysRoleMenuInfoVo> getSysRoleMenuList(GetSysRoleMenuListParam getSysRoleMenuListParam) {
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new QueryWrapper<SysRoleMenu>().lambda();
//        queryWrapper.orderBy(StringUtils.isNotBlank(getSysRoleMenuListParam.getSort()), "asc".equals(getSysRoleMenuListParam.getSort()), SysRoleMenu::getRoleId);

        IPage<SysRoleMenu> page = sysRoleMenuService.page(new Page<>(getSysRoleMenuListParam.getPageNo(), getSysRoleMenuListParam.getPageSize()), queryWrapper);

        List<SysRoleMenu> records = page.getRecords();

        List<SysRoleMenuInfoVo> sysRoleMenuInfoVoList = this.entityToVo(records);

        TableRecordVo tableRecordVo = new TableRecordVo<>();
        tableRecordVo.setRecords(sysRoleMenuInfoVoList);
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
    public SysRoleMenuInfoVo getSysRoleMenuDetail(Long roleId) {
        SysRoleMenu sysRoleMenu = sysRoleMenuService.getById(roleId);
        if (null == sysRoleMenu) {
            throw new ServiceException("记录不存在");
        }
        List<SysRoleMenuInfoVo> sysRoleMenuInfoVos = this.entityToVo(Arrays.asList(sysRoleMenu));
        return sysRoleMenuInfoVos.stream().findFirst().orElse(null);
    }

    /**
     * 实体类转VO
     *
     * @param records
     * @return
     */
    private List<SysRoleMenuInfoVo> entityToVo(List<SysRoleMenu> records) {
        return records.stream()
                .map(sysRoleMenu -> {
                    SysRoleMenuInfoVo sysRoleMenuInfoVo = new SysRoleMenuInfoVo();
                    BeanUtils.copyProperties(sysRoleMenu, sysRoleMenuInfoVo);
                    return sysRoleMenuInfoVo;
                }).collect(Collectors.toList());
    }

    /**
     * 新增或者编辑时统一校验
     *
     * @param aeSysRoleMenuParam
     */
    private void checkParam(AeSysRoleMenuParam aeSysRoleMenuParam) {

    }
}