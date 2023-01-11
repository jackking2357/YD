package com.yudian.www.service.account.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.config.exceptions.ServiceException;
import com.yudian.www.entity.account.AccountRobotAccelerator;
import com.yudian.www.service.account.IAccountRobotAcceleratorService;
import com.yudian.www.service.account.IAccountRobotAcceleratorServiceProcess;
import com.yudian.www.service.account.param.AeAccountRobotAcceleratorParam;
import com.yudian.www.service.account.param.GetAccountRobotAcceleratorListParam;
import com.yudian.www.service.account.vo.AccountRobotAcceleratorInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 平台用户机器人加速器 服务实现类-流程
 *
 * @author yudian
 * @since 2023-01-07
 */
@Service
public class AccountRobotAcceleratorServiceImplProcess implements IAccountRobotAcceleratorServiceProcess {

    @Resource
    private IAccountRobotAcceleratorService accountRobotAcceleratorService;

    /**
     * 添加
     *
     * @param aeAccountRobotAcceleratorParam
     */
    @Override
    public void accountRobotAcceleratorAdd(AeAccountRobotAcceleratorParam aeAccountRobotAcceleratorParam) {
        aeAccountRobotAcceleratorParam.initParam();
        this.checkParam(aeAccountRobotAcceleratorParam);
        AccountRobotAccelerator accountRobotAccelerator = new AccountRobotAccelerator();
        BeanUtils.copyProperties(aeAccountRobotAcceleratorParam, accountRobotAccelerator);
        boolean save = accountRobotAcceleratorService.save(accountRobotAccelerator);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    @Override
    public void accountRobotAcceleratorAddBatch(List<AeAccountRobotAcceleratorParam> aeAccountRobotAcceleratorParamList) {
        List<AccountRobotAccelerator> accountRobotAcceleratorList = aeAccountRobotAcceleratorParamList.stream()
                .map(aeAccountRobotAcceleratorParam -> {
                    AccountRobotAccelerator accountRobotAccelerator = new AccountRobotAccelerator();
                    BeanUtils.copyProperties(aeAccountRobotAcceleratorParam, accountRobotAccelerator);
                    return accountRobotAccelerator;
                }).collect(Collectors.toList());
        boolean save = accountRobotAcceleratorService.saveBatch(accountRobotAcceleratorList);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    /**
     * 编辑
     *
     * @param aeAccountRobotAcceleratorParam
     */
    @Override
    public void accountRobotAcceleratorEdit(AeAccountRobotAcceleratorParam aeAccountRobotAcceleratorParam) {
        aeAccountRobotAcceleratorParam.initParam();
        this.checkParam(aeAccountRobotAcceleratorParam);
        AccountRobotAccelerator accountRobotAccelerator = accountRobotAcceleratorService.getById(aeAccountRobotAcceleratorParam.getAccountRobotAcceleratorId());
        if (null == accountRobotAccelerator) {
            throw new ServiceException("记录不存在");
        }
        BeanUtils.copyProperties(aeAccountRobotAcceleratorParam, accountRobotAccelerator);
        boolean update = accountRobotAcceleratorService.updateById(accountRobotAccelerator);
        if (!update) {
            throw new ServiceException("编辑失败");
        }
    }

    /**
     * 删除
     *
     * @param accountRobotAcceleratorIds
     */
    @Override
    public void accountRobotAcceleratorRemove(Long[] accountRobotAcceleratorIds) {
        boolean remove = accountRobotAcceleratorService.removeByIds(Arrays.asList(accountRobotAcceleratorIds));
        if (!remove) {
            throw new ServiceException("删除失败");
        }
    }

    /**
     * 分页查询
     *
     * @param getAccountRobotAcceleratorListParam
     * @return
     */
    @Override
    public TableRecordVo<AccountRobotAcceleratorInfoVo> getAccountRobotAcceleratorList(GetAccountRobotAcceleratorListParam getAccountRobotAcceleratorListParam) {
        Long accountId = getAccountRobotAcceleratorListParam.getAccountId();

        LambdaQueryWrapper<AccountRobotAccelerator> queryWrapper = new QueryWrapper<AccountRobotAccelerator>().lambda();
        queryWrapper.eq(null != accountId, AccountRobotAccelerator::getAccountId, accountId);
        queryWrapper
                .ge(null != getAccountRobotAcceleratorListParam.getStartDateTime(), AccountRobotAccelerator::getCreateDatetime, getAccountRobotAcceleratorListParam.getStartDateTime())
                .le(null != getAccountRobotAcceleratorListParam.getEndDateTime(), AccountRobotAccelerator::getCreateDatetime, getAccountRobotAcceleratorListParam.getEndDateTime());

        if (StringUtils.isNotBlank(getAccountRobotAcceleratorListParam.getOrderByColumn()) && StringUtils.isNotBlank(getAccountRobotAcceleratorListParam.getIsAsc())) {
            queryWrapper.last("order by " + getAccountRobotAcceleratorListParam.getOrderBy());
        }

        IPage<AccountRobotAccelerator> page = accountRobotAcceleratorService.page(new Page<>(getAccountRobotAcceleratorListParam.getPageNo(), getAccountRobotAcceleratorListParam.getPageSize()), queryWrapper);

        List<AccountRobotAccelerator> records = page.getRecords();

        List<AccountRobotAcceleratorInfoVo> accountRobotAcceleratorInfoVoList = this.entityToVo(records);

        TableRecordVo tableRecordVo = new TableRecordVo<>();
        tableRecordVo.setRecords(accountRobotAcceleratorInfoVoList);
        tableRecordVo.setTotal(page.getTotal());
        tableRecordVo.setPages(page.getPages());
        return tableRecordVo;
    }

    /**
     * 明细
     *
     * @param accountRobotAcceleratorId
     * @return
     */
    @Override
    public AccountRobotAcceleratorInfoVo getAccountRobotAcceleratorDetail(Long accountRobotAcceleratorId) {
        AccountRobotAccelerator accountRobotAccelerator = accountRobotAcceleratorService.getById(accountRobotAcceleratorId);
        if (null == accountRobotAccelerator) {
            throw new ServiceException("记录不存在");
        }
        List<AccountRobotAcceleratorInfoVo> accountRobotAcceleratorInfoVos = this.entityToVo(Arrays.asList(accountRobotAccelerator));
        return accountRobotAcceleratorInfoVos.stream().findFirst().orElse(null);
    }

    /**
     * 实体类转VO
     *
     * @param records
     * @return
     */
    private List<AccountRobotAcceleratorInfoVo> entityToVo(List<AccountRobotAccelerator> records) {
        return records.stream()
                .map(accountRobotAccelerator -> {
                    AccountRobotAcceleratorInfoVo accountRobotAcceleratorInfoVo = new AccountRobotAcceleratorInfoVo();
                    BeanUtils.copyProperties(accountRobotAccelerator, accountRobotAcceleratorInfoVo);
                    return accountRobotAcceleratorInfoVo;
                }).collect(Collectors.toList());
    }

    /**
     * 新增或者编辑时统一校验
     *
     * @param aeAccountRobotAcceleratorParam
     */
    private void checkParam(AeAccountRobotAcceleratorParam aeAccountRobotAcceleratorParam) {

    }
}