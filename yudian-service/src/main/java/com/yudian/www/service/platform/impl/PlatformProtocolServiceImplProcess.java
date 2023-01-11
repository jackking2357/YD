package com.yudian.www.service.platform.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.config.exceptions.ServiceException;
import com.yudian.www.entity.platform.PlatformProtocol;
import com.yudian.www.service.platform.param.AePlatformProtocolParam;
import com.yudian.www.service.platform.param.GetPlatformProtocolListParam;
import com.yudian.www.service.platform.IPlatformProtocolService;
import com.yudian.www.service.platform.IPlatformProtocolServiceProcess;
import com.yudian.www.service.platform.vo.PlatformProtocolInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
* 平台协议 服务实现类-流程
 *
 * @author yudian
 * @since 2023-01-05
 */
@Service
public class PlatformProtocolServiceImplProcess implements IPlatformProtocolServiceProcess {

    @Resource
    private IPlatformProtocolService platformProtocolService;

    /**
     * 添加
     * @param aePlatformProtocolParam
     */
    @Override
    public void platformProtocolAdd(AePlatformProtocolParam aePlatformProtocolParam) {
        aePlatformProtocolParam.initParam();
        this.checkParam(aePlatformProtocolParam);
        PlatformProtocol platformProtocol = new PlatformProtocol();
        BeanUtils.copyProperties(aePlatformProtocolParam, platformProtocol);
        boolean save = platformProtocolService.save(platformProtocol);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    @Override
    public void platformProtocolAddBatch(List<AePlatformProtocolParam> aePlatformProtocolParamList) {
        List<PlatformProtocol> platformProtocolList = aePlatformProtocolParamList.stream()
            .map(aePlatformProtocolParam -> {
                PlatformProtocol platformProtocol = new PlatformProtocol();
                BeanUtils.copyProperties(aePlatformProtocolParam, platformProtocol);
                return platformProtocol;
            }).collect(Collectors.toList());
        boolean save = platformProtocolService.saveBatch(platformProtocolList);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    /**
     * 编辑
     * @param aePlatformProtocolParam
     */
    @Override
    public void platformProtocolEdit(AePlatformProtocolParam aePlatformProtocolParam) {
        aePlatformProtocolParam.initParam();
        this.checkParam(aePlatformProtocolParam);
        PlatformProtocol platformProtocol = platformProtocolService.getById(aePlatformProtocolParam.getPlatformProtocolId());
        if (null == platformProtocol) {
            throw new ServiceException("记录不存在");
        }
        BeanUtils.copyProperties(aePlatformProtocolParam, platformProtocol);
        boolean update = platformProtocolService.updateById(platformProtocol);
        if (!update) {
            throw new ServiceException("编辑失败");
        }
    }

    /**
     * 删除
     * @param platformProtocolIds
     */
    @Override
    public void platformProtocolRemove(Long[] platformProtocolIds) {
        boolean remove = platformProtocolService.removeByIds(Arrays.asList(platformProtocolIds));
        if (!remove) {
            throw new ServiceException("删除失败");
        }
    }

    /**
     * 分页查询
     * @param getPlatformProtocolListParam
     * @return
     */
    @Override
    public TableRecordVo<PlatformProtocolInfoVo> getPlatformProtocolList(GetPlatformProtocolListParam getPlatformProtocolListParam) {
        LambdaQueryWrapper<PlatformProtocol> queryWrapper = new QueryWrapper<PlatformProtocol>().lambda();
        queryWrapper
                .ge(null != getPlatformProtocolListParam.getStartDateTime(), PlatformProtocol::getCreateDatetime, getPlatformProtocolListParam.getStartDateTime())
                .le(null != getPlatformProtocolListParam.getEndDateTime(), PlatformProtocol::getCreateDatetime, getPlatformProtocolListParam.getEndDateTime());

        if (StringUtils.isNotBlank(getPlatformProtocolListParam.getOrderByColumn()) && StringUtils.isNotBlank(getPlatformProtocolListParam.getIsAsc())) {
            queryWrapper.last("order by " + getPlatformProtocolListParam.getOrderBy());
        }

        IPage<PlatformProtocol> page = platformProtocolService.page(new Page<>(getPlatformProtocolListParam.getPageNo(), getPlatformProtocolListParam.getPageSize()), queryWrapper);

        List<PlatformProtocol> records = page.getRecords();

        List<PlatformProtocolInfoVo> platformProtocolInfoVoList = this.entityToVo(records);

        TableRecordVo tableRecordVo = new TableRecordVo<>();
        tableRecordVo.setRecords(platformProtocolInfoVoList);
        tableRecordVo.setTotal(page.getTotal());
        tableRecordVo.setPages(page.getPages());
        return tableRecordVo;
    }

    /**
     * 明细
     * @param platformProtocolId
     * @return
     */
    @Override
    public PlatformProtocolInfoVo getPlatformProtocolDetail(Long platformProtocolId) {
        PlatformProtocol platformProtocol = platformProtocolService.getById(platformProtocolId);
        if (null == platformProtocol) {
            throw new ServiceException("记录不存在");
        }
        List<PlatformProtocolInfoVo> platformProtocolInfoVos = this.entityToVo(Arrays.asList(platformProtocol));
        return platformProtocolInfoVos.stream().findFirst().orElse(null);
    }

    /**
    * 实体类转VO
    *
    * @param records
    * @return
    */
    private List<PlatformProtocolInfoVo> entityToVo(List<PlatformProtocol> records) {
        return records.stream()
            .map(platformProtocol -> {
                PlatformProtocolInfoVo platformProtocolInfoVo = new PlatformProtocolInfoVo();
                BeanUtils.copyProperties(platformProtocol, platformProtocolInfoVo);
                return platformProtocolInfoVo;
            }).collect(Collectors.toList());
    }

    /**
     * 新增或者编辑时统一校验
     *
     * @param aePlatformProtocolParam
     */
    private void checkParam(AePlatformProtocolParam aePlatformProtocolParam) {

    }
}