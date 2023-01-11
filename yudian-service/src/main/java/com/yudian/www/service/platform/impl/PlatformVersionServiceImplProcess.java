package com.yudian.www.service.platform.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.config.exceptions.ServiceException;
import com.yudian.www.entity.platform.PlatformVersion;
import com.yudian.www.service.platform.param.AePlatformVersionParam;
import com.yudian.www.service.platform.param.GetPlatformVersionListParam;
import com.yudian.www.service.platform.IPlatformVersionService;
import com.yudian.www.service.platform.IPlatformVersionServiceProcess;
import com.yudian.www.service.platform.vo.PlatformVersionInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
* 平台版本 服务实现类-流程
 *
 * @author yudian
 * @since 2023-01-05
 */
@Service
public class PlatformVersionServiceImplProcess implements IPlatformVersionServiceProcess {

    @Resource
    private IPlatformVersionService platformVersionService;

    /**
     * 添加
     * @param aePlatformVersionParam
     */
    @Override
    public void platformVersionAdd(AePlatformVersionParam aePlatformVersionParam) {
        aePlatformVersionParam.initParam();
        this.checkParam(aePlatformVersionParam);
        PlatformVersion platformVersion = new PlatformVersion();
        BeanUtils.copyProperties(aePlatformVersionParam, platformVersion);
        boolean save = platformVersionService.save(platformVersion);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    @Override
    public void platformVersionAddBatch(List<AePlatformVersionParam> aePlatformVersionParamList) {
        List<PlatformVersion> platformVersionList = aePlatformVersionParamList.stream()
            .map(aePlatformVersionParam -> {
                PlatformVersion platformVersion = new PlatformVersion();
                BeanUtils.copyProperties(aePlatformVersionParam, platformVersion);
                return platformVersion;
            }).collect(Collectors.toList());
        boolean save = platformVersionService.saveBatch(platformVersionList);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    /**
     * 编辑
     * @param aePlatformVersionParam
     */
    @Override
    public void platformVersionEdit(AePlatformVersionParam aePlatformVersionParam) {
        aePlatformVersionParam.initParam();
        this.checkParam(aePlatformVersionParam);
        PlatformVersion platformVersion = platformVersionService.getById(aePlatformVersionParam.getPlatformVersionId());
        if (null == platformVersion) {
            throw new ServiceException("记录不存在");
        }
        BeanUtils.copyProperties(aePlatformVersionParam, platformVersion);
        boolean update = platformVersionService.updateById(platformVersion);
        if (!update) {
            throw new ServiceException("编辑失败");
        }
    }

    /**
     * 删除
     * @param platformVersionIds
     */
    @Override
    public void platformVersionRemove(Integer[] platformVersionIds) {
        boolean remove = platformVersionService.removeByIds(Arrays.asList(platformVersionIds));
        if (!remove) {
            throw new ServiceException("删除失败");
        }
    }

    /**
     * 分页查询
     * @param getPlatformVersionListParam
     * @return
     */
    @Override
    public TableRecordVo<PlatformVersionInfoVo> getPlatformVersionList(GetPlatformVersionListParam getPlatformVersionListParam) {
        LambdaQueryWrapper<PlatformVersion> queryWrapper = new QueryWrapper<PlatformVersion>().lambda();
        queryWrapper
                .ge(null != getPlatformVersionListParam.getStartDateTime(), PlatformVersion::getCreateDatetime, getPlatformVersionListParam.getStartDateTime())
                .le(null != getPlatformVersionListParam.getEndDateTime(), PlatformVersion::getCreateDatetime, getPlatformVersionListParam.getEndDateTime());

        if (StringUtils.isNotBlank(getPlatformVersionListParam.getOrderByColumn()) && StringUtils.isNotBlank(getPlatformVersionListParam.getIsAsc())) {
            queryWrapper.last("order by " + getPlatformVersionListParam.getOrderBy());
        }

        IPage<PlatformVersion> page = platformVersionService.page(new Page<>(getPlatformVersionListParam.getPageNo(), getPlatformVersionListParam.getPageSize()), queryWrapper);

        List<PlatformVersion> records = page.getRecords();

        List<PlatformVersionInfoVo> platformVersionInfoVoList = this.entityToVo(records);

        TableRecordVo tableRecordVo = new TableRecordVo<>();
        tableRecordVo.setRecords(platformVersionInfoVoList);
        tableRecordVo.setTotal(page.getTotal());
        tableRecordVo.setPages(page.getPages());
        return tableRecordVo;
    }

    /**
     * 明细
     * @param platformVersionId
     * @return
     */
    @Override
    public PlatformVersionInfoVo getPlatformVersionDetail(Integer platformVersionId) {
        PlatformVersion platformVersion = platformVersionService.getById(platformVersionId);
        if (null == platformVersion) {
            throw new ServiceException("记录不存在");
        }
        List<PlatformVersionInfoVo> platformVersionInfoVos = this.entityToVo(Arrays.asList(platformVersion));
        return platformVersionInfoVos.stream().findFirst().orElse(null);
    }

    /**
    * 实体类转VO
    *
    * @param records
    * @return
    */
    private List<PlatformVersionInfoVo> entityToVo(List<PlatformVersion> records) {
        return records.stream()
            .map(platformVersion -> {
                PlatformVersionInfoVo platformVersionInfoVo = new PlatformVersionInfoVo();
                BeanUtils.copyProperties(platformVersion, platformVersionInfoVo);
                return platformVersionInfoVo;
            }).collect(Collectors.toList());
    }

    /**
     * 新增或者编辑时统一校验
     *
     * @param aePlatformVersionParam
     */
    private void checkParam(AePlatformVersionParam aePlatformVersionParam) {

    }
}