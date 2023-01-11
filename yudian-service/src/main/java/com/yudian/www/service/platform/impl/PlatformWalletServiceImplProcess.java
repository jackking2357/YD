package com.yudian.www.service.platform.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.config.exceptions.ServiceException;
import com.yudian.www.entity.platform.PlatformWallet;
import com.yudian.www.service.platform.IPlatformWalletService;
import com.yudian.www.service.platform.IPlatformWalletServiceProcess;
import com.yudian.www.service.platform.param.AePlatformWalletParam;
import com.yudian.www.service.platform.param.GetPlatformWalletListParam;
import com.yudian.www.service.platform.vo.PlatformWalletInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 平台收款钱包 服务实现类-流程
 *
 * @author yudian
 * @since 2023-01-06
 */
@Service
public class PlatformWalletServiceImplProcess implements IPlatformWalletServiceProcess {

    @Resource
    private IPlatformWalletService platformWalletService;

    /**
     * 添加
     *
     * @param aePlatformWalletParam
     */
    @Override
    public void platformWalletAdd(AePlatformWalletParam aePlatformWalletParam) {
        aePlatformWalletParam.initParam();
        this.checkParam(aePlatformWalletParam);
        PlatformWallet platformWallet = new PlatformWallet();
        BeanUtils.copyProperties(aePlatformWalletParam, platformWallet);
        platformWallet.setQrCodePhoto(JSON.toJSONString(aePlatformWalletParam.getQrCodePhoto()));
        boolean save = platformWalletService.save(platformWallet);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    @Override
    public void platformWalletAddBatch(List<AePlatformWalletParam> aePlatformWalletParamList) {
        List<PlatformWallet> platformWalletList = aePlatformWalletParamList.stream()
                .map(aePlatformWalletParam -> {
                    PlatformWallet platformWallet = new PlatformWallet();
                    BeanUtils.copyProperties(aePlatformWalletParam, platformWallet);
                    platformWallet.setQrCodePhoto(JSON.toJSONString(aePlatformWalletParam.getQrCodePhoto()));
                    return platformWallet;
                }).collect(Collectors.toList());
        boolean save = platformWalletService.saveBatch(platformWalletList);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    /**
     * 编辑
     *
     * @param aePlatformWalletParam
     */
    @Override
    public void platformWalletEdit(AePlatformWalletParam aePlatformWalletParam) {
        aePlatformWalletParam.initParam();
        this.checkParam(aePlatformWalletParam);
        PlatformWallet platformWallet = platformWalletService.getById(aePlatformWalletParam.getPlatformWalletId());
        if (null == platformWallet) {
            throw new ServiceException("记录不存在");
        }
        BeanUtils.copyProperties(aePlatformWalletParam, platformWallet);
        platformWallet.setQrCodePhoto(JSON.toJSONString(aePlatformWalletParam.getQrCodePhoto()));
        boolean update = platformWalletService.updateById(platformWallet);
        if (!update) {
            throw new ServiceException("编辑失败");
        }
    }

    /**
     * 删除
     *
     * @param platformWalletIds
     */
    @Override
    public void platformWalletRemove(Long[] platformWalletIds) {
        int count = platformWalletService.count();
        if (count == 1 || platformWalletIds.length - count <= 1) {
            throw new ServiceException("至少保留一种收款方式");
        }
        boolean remove = platformWalletService.removeByIds(Arrays.asList(platformWalletIds));
        if (!remove) {
            throw new ServiceException("删除失败");
        }
    }

    /**
     * 分页查询
     *
     * @param getPlatformWalletListParam
     * @return
     */
    @Override
    public TableRecordVo<PlatformWalletInfoVo> getPlatformWalletList(GetPlatformWalletListParam getPlatformWalletListParam) {
        LambdaQueryWrapper<PlatformWallet> queryWrapper = new QueryWrapper<PlatformWallet>().lambda();
        queryWrapper
                .ge(null != getPlatformWalletListParam.getStartDateTime(), PlatformWallet::getCreateDatetime, getPlatformWalletListParam.getStartDateTime())
                .le(null != getPlatformWalletListParam.getEndDateTime(), PlatformWallet::getCreateDatetime, getPlatformWalletListParam.getEndDateTime());

        if (StringUtils.isNotBlank(getPlatformWalletListParam.getOrderByColumn()) && StringUtils.isNotBlank(getPlatformWalletListParam.getIsAsc())) {
            queryWrapper.last("order by " + getPlatformWalletListParam.getOrderBy());
        }

        IPage<PlatformWallet> page = platformWalletService.page(new Page<>(getPlatformWalletListParam.getPageNo(), getPlatformWalletListParam.getPageSize()), queryWrapper);

        List<PlatformWallet> records = page.getRecords();

        List<PlatformWalletInfoVo> platformWalletInfoVoList = this.entityToVo(records);

        TableRecordVo tableRecordVo = new TableRecordVo<>();
        tableRecordVo.setRecords(platformWalletInfoVoList);
        tableRecordVo.setTotal(page.getTotal());
        tableRecordVo.setPages(page.getPages());
        return tableRecordVo;
    }

    /**
     * 明细
     *
     * @param platformWalletId
     * @return
     */
    @Override
    public PlatformWalletInfoVo getPlatformWalletDetail(Long platformWalletId) {
        PlatformWallet platformWallet = platformWalletService.getById(platformWalletId);
        if (null == platformWallet) {
            throw new ServiceException("记录不存在");
        }
        List<PlatformWalletInfoVo> platformWalletInfoVos = this.entityToVo(Arrays.asList(platformWallet));
        return platformWalletInfoVos.stream().findFirst().orElse(null);
    }

    /**
     * 实体类转VO
     *
     * @param records
     * @return
     */
    private List<PlatformWalletInfoVo> entityToVo(List<PlatformWallet> records) {
        return records.stream()
                .map(platformWallet -> {
                    PlatformWalletInfoVo platformWalletInfoVo = new PlatformWalletInfoVo();
                    BeanUtils.copyProperties(platformWallet, platformWalletInfoVo);
                    return platformWalletInfoVo;
                }).collect(Collectors.toList());
    }

    /**
     * 新增或者编辑时统一校验
     *
     * @param aePlatformWalletParam
     */
    private void checkParam(AePlatformWalletParam aePlatformWalletParam) {

    }
}