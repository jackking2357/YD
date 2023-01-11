package com.yudian.www.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.config.exceptions.ServiceException;
import com.yudian.www.entity.sys.SysUserRole;
import com.yudian.www.service.sys.ISysUserRoleService;
import com.yudian.www.service.sys.ISysUserRoleServiceProcess;
import com.yudian.www.service.sys.param.AeSysUserRoleParam;
import com.yudian.www.service.sys.param.GetSysUserRoleListParam;
import com.yudian.www.service.sys.vo.SysUserRoleInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SysUserRoleServiceImplProcess implements ISysUserRoleServiceProcess {

    @Resource
    private ISysUserRoleService sysUserRoleService;


    @Override
    public void sysUserRoleAdd(AeSysUserRoleParam aeSysUserRoleParam) {
        aeSysUserRoleParam.initParam();
        this.checkParam(aeSysUserRoleParam);
        SysUserRole sysUserRole = new SysUserRole();
        BeanUtils.copyProperties(aeSysUserRoleParam, sysUserRole);
        boolean save = sysUserRoleService.save(sysUserRole);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    @Override
    public void sysUserRoleAddBatch(List<AeSysUserRoleParam> aeSysUserRoleParamList) {
        List<SysUserRole> sysUserRoleList = aeSysUserRoleParamList.stream()
                .map(aeSysUserRoleParam -> {
                    SysUserRole sysUserRole = new SysUserRole();
                    BeanUtils.copyProperties(aeSysUserRoleParam, sysUserRole);
                    return sysUserRole;
                }).collect(Collectors.toList());
        boolean save = sysUserRoleService.saveBatch(sysUserRoleList);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }


    @Override
    public void sysUserRoleEdit(AeSysUserRoleParam aeSysUserRoleParam) {
        aeSysUserRoleParam.initParam();
        this.checkParam(aeSysUserRoleParam);
        SysUserRole sysUserRole = sysUserRoleService.getById(aeSysUserRoleParam.getUserId());
        if (null == sysUserRole) {
            throw new ServiceException("记录不存在");
        }
        BeanUtils.copyProperties(aeSysUserRoleParam, sysUserRole);
        boolean update = sysUserRoleService.updateById(sysUserRole);
        if (!update) {
            throw new ServiceException("编辑失败");
        }
    }


    @Override
    public void sysUserRoleRemove(Long[] userIds) {
        boolean remove = sysUserRoleService.removeByIds(Arrays.asList(userIds));
        if (!remove) {
            throw new ServiceException("删除失败");
        }
    }


    @Override
    public TableRecordVo<SysUserRoleInfoVo> getSysUserRoleList(GetSysUserRoleListParam getSysUserRoleListParam) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<SysUserRole>().lambda();


        IPage<SysUserRole> page = sysUserRoleService.page(new Page<>(getSysUserRoleListParam.getPageNo(), getSysUserRoleListParam.getPageSize()), queryWrapper);

        List<SysUserRole> records = page.getRecords();

        List<SysUserRoleInfoVo> sysUserRoleInfoVoList = this.entityToVo(records);

        TableRecordVo tableRecordVo = new TableRecordVo<>();
        tableRecordVo.setRecords(sysUserRoleInfoVoList);
        tableRecordVo.setTotal(page.getTotal());
        tableRecordVo.setPages(page.getPages());
        return tableRecordVo;
    }


    @Override
    public SysUserRoleInfoVo getSysUserRoleDetail(Long userId) {
        SysUserRole sysUserRole = sysUserRoleService.getById(userId);
        if (null == sysUserRole) {
            throw new ServiceException("记录不存在");
        }
        List<SysUserRoleInfoVo> sysUserRoleInfoVos = this.entityToVo(Arrays.asList(sysUserRole));
        return sysUserRoleInfoVos.stream().findFirst().orElse(null);
    }


    private List<SysUserRoleInfoVo> entityToVo(List<SysUserRole> records) {
        return records.stream()
                .map(sysUserRole -> {
                    SysUserRoleInfoVo sysUserRoleInfoVo = new SysUserRoleInfoVo();
                    BeanUtils.copyProperties(sysUserRole, sysUserRoleInfoVo);
                    return sysUserRoleInfoVo;
                }).collect(Collectors.toList());
    }

    /**
     * 新增或者编辑时统一校验
     *
     * @param aeSysUserRoleParam
     */
    private void checkParam(AeSysUserRoleParam aeSysUserRoleParam) {

    }
}