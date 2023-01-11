package com.yudian.www.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.config.exceptions.ServiceException;
import com.yudian.www.entity.sys.SysOperLog;
import com.yudian.www.mapper.sys.SysOperLogMapper;
import com.yudian.www.service.sys.ISysOperLogService;
import com.yudian.www.service.sys.ISysOperLogServiceProcess;
import com.yudian.www.service.sys.param.AeSysOperLogParam;
import com.yudian.www.service.sys.param.GetSysOperLogListParam;
import com.yudian.www.service.sys.vo.SysOperLogInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SysOperLogServiceImplProcess implements ISysOperLogServiceProcess {

    @Resource
    private ISysOperLogService sysOperLogService;


    @Override
    public void sysOperLogAdd(AeSysOperLogParam aeSysOperLogParam) {
        aeSysOperLogParam.initParam();
        this.checkParam(aeSysOperLogParam);
        SysOperLog sysOperLog = new SysOperLog();
        BeanUtils.copyProperties(aeSysOperLogParam, sysOperLog);
        boolean save = sysOperLogService.save(sysOperLog);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    @Override
    public void sysOperLogAddBatch(List<AeSysOperLogParam> aeSysOperLogParamList) {
        List<SysOperLog> sysOperLogList = aeSysOperLogParamList.stream()
                .map(aeSysOperLogParam -> {
                    SysOperLog sysOperLog = new SysOperLog();
                    BeanUtils.copyProperties(aeSysOperLogParam, sysOperLog);
                    return sysOperLog;
                }).collect(Collectors.toList());
        boolean save = sysOperLogService.saveBatch(sysOperLogList);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }


    @Override
    public void sysOperLogEdit(AeSysOperLogParam aeSysOperLogParam) {
        aeSysOperLogParam.initParam();
        this.checkParam(aeSysOperLogParam);
        SysOperLog sysOperLog = sysOperLogService.getById(aeSysOperLogParam.getOperId());
        if (null == sysOperLog) {
            throw new ServiceException("记录不存在");
        }
        BeanUtils.copyProperties(aeSysOperLogParam, sysOperLog);
        boolean update = sysOperLogService.updateById(sysOperLog);
        if (!update) {
            throw new ServiceException("编辑失败");
        }
    }


    @Override
    public void sysOperLogRemove(Long[] operIds) {
        boolean remove = sysOperLogService.removeByIds(Arrays.asList(operIds));
        if (!remove) {
            throw new ServiceException("删除失败");
        }
    }


    @Override
    public TableRecordVo<SysOperLogInfoVo> getSysOperLogList(GetSysOperLogListParam getSysOperLogListParam) {
        LambdaQueryWrapper<SysOperLog> queryWrapper = new QueryWrapper<SysOperLog>().lambda();
        queryWrapper
                .likeRight(StringUtils.isNotBlank(getSysOperLogListParam.getOperName()), SysOperLog::getOperName, getSysOperLogListParam.getOperName())
                .likeRight(StringUtils.isNotBlank(getSysOperLogListParam.getTitle()), SysOperLog::getTitle, getSysOperLogListParam.getTitle())
                .eq(null != getSysOperLogListParam.getBusinessType(), SysOperLog::getBusinessType, getSysOperLogListParam.getBusinessType())
                .eq(null != getSysOperLogListParam.getStatus(), SysOperLog::getStatus, getSysOperLogListParam.getStatus())
                .ge(null != getSysOperLogListParam.getStartDateTime(), SysOperLog::getOperTime, getSysOperLogListParam.getStartDateTime())
                .le(null != getSysOperLogListParam.getEndDateTime(), SysOperLog::getOperTime, getSysOperLogListParam.getEndDateTime());

        if (StringUtils.isNotBlank(getSysOperLogListParam.getOrderByColumn()) && StringUtils.isNotBlank(getSysOperLogListParam.getIsAsc())) {
            queryWrapper.last("order by " + getSysOperLogListParam.getOrderBy());
        }

        IPage<SysOperLog> page = sysOperLogService.page(new Page<>(getSysOperLogListParam.getPageNo(), getSysOperLogListParam.getPageSize()), queryWrapper);

        List<SysOperLog> records = page.getRecords();

        List<SysOperLogInfoVo> sysOperLogInfoVoList = this.entityToVo(records);

        TableRecordVo tableRecordVo = new TableRecordVo<>();
        tableRecordVo.setRecords(sysOperLogInfoVoList);
        tableRecordVo.setTotal(page.getTotal());
        tableRecordVo.setPages(page.getPages());
        return tableRecordVo;
    }

    /**
     * 明细
     *
     * @param operId
     * @return
     */
    @Override
    public SysOperLogInfoVo getSysOperLogDetail(Long operId) {
        SysOperLog sysOperLog = sysOperLogService.getById(operId);
        if (null == sysOperLog) {
            throw new ServiceException("记录不存在");
        }
        List<SysOperLogInfoVo> sysOperLogInfoVos = this.entityToVo(Arrays.asList(sysOperLog));
        return sysOperLogInfoVos.stream().findFirst().orElse(null);
    }

    @Override
    public void truncateTable() {
        SysOperLogMapper baseMapper = (SysOperLogMapper) sysOperLogService.getBaseMapper();
        baseMapper.truncateTable();
    }

    /**
     * 实体类转VO
     *
     * @param records
     * @return
     */
    private List<SysOperLogInfoVo> entityToVo(List<SysOperLog> records) {
        return records.stream()
                .map(sysOperLog -> {
                    SysOperLogInfoVo sysOperLogInfoVo = new SysOperLogInfoVo();
                    BeanUtils.copyProperties(sysOperLog, sysOperLogInfoVo);
                    return sysOperLogInfoVo;
                }).collect(Collectors.toList());
    }

    /**
     * 新增或者编辑时统一校验
     *
     * @param aeSysOperLogParam
     */
    private void checkParam(AeSysOperLogParam aeSysOperLogParam) {

    }
}