package com.yudian.www.service.account.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.config.exceptions.ServiceException;
import com.yudian.www.entity.account.AccountRobotWork;
import com.yudian.www.service.account.param.AeAccountRobotWorkParam;
import com.yudian.www.service.account.param.GetAccountRobotWorkListParam;
import com.yudian.www.service.account.IAccountRobotWorkService;
import com.yudian.www.service.account.IAccountRobotWorkServiceProcess;
import com.yudian.www.service.account.vo.AccountRobotWorkInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
* 平台用户机器人工作记录 服务实现类-流程
 *
 * @author yudian
 * @since 2023-01-05
 */
@Service
public class AccountRobotWorkServiceImplProcess implements IAccountRobotWorkServiceProcess {

    @Resource
    private IAccountRobotWorkService accountRobotWorkService;

    /**
     * 添加
     * @param aeAccountRobotWorkParam
     */
    @Override
    public void accountRobotWorkAdd(AeAccountRobotWorkParam aeAccountRobotWorkParam) {
        aeAccountRobotWorkParam.initParam();
        this.checkParam(aeAccountRobotWorkParam);
        AccountRobotWork accountRobotWork = new AccountRobotWork();
        BeanUtils.copyProperties(aeAccountRobotWorkParam, accountRobotWork);
        boolean save = accountRobotWorkService.save(accountRobotWork);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    @Override
    public void accountRobotWorkAddBatch(List<AeAccountRobotWorkParam> aeAccountRobotWorkParamList) {
        List<AccountRobotWork> accountRobotWorkList = aeAccountRobotWorkParamList.stream()
            .map(aeAccountRobotWorkParam -> {
                AccountRobotWork accountRobotWork = new AccountRobotWork();
                BeanUtils.copyProperties(aeAccountRobotWorkParam, accountRobotWork);
                return accountRobotWork;
            }).collect(Collectors.toList());
        boolean save = accountRobotWorkService.saveBatch(accountRobotWorkList);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    /**
     * 编辑
     * @param aeAccountRobotWorkParam
     */
    @Override
    public void accountRobotWorkEdit(AeAccountRobotWorkParam aeAccountRobotWorkParam) {
        aeAccountRobotWorkParam.initParam();
        this.checkParam(aeAccountRobotWorkParam);
        AccountRobotWork accountRobotWork = accountRobotWorkService.getById(aeAccountRobotWorkParam.getAccountRobotWorkId());
        if (null == accountRobotWork) {
            throw new ServiceException("记录不存在");
        }
        BeanUtils.copyProperties(aeAccountRobotWorkParam, accountRobotWork);
        boolean update = accountRobotWorkService.updateById(accountRobotWork);
        if (!update) {
            throw new ServiceException("编辑失败");
        }
    }

    /**
     * 删除
     * @param accountRobotWorkIds
     */
    @Override
    public void accountRobotWorkRemove(Long[] accountRobotWorkIds) {
        boolean remove = accountRobotWorkService.removeByIds(Arrays.asList(accountRobotWorkIds));
        if (!remove) {
            throw new ServiceException("删除失败");
        }
    }

    /**
     * 分页查询
     * @param getAccountRobotWorkListParam
     * @return
     */
    @Override
    public TableRecordVo<AccountRobotWorkInfoVo> getAccountRobotWorkList(GetAccountRobotWorkListParam getAccountRobotWorkListParam) {
        LambdaQueryWrapper<AccountRobotWork> queryWrapper = new QueryWrapper<AccountRobotWork>().lambda();
        queryWrapper
                .ge(null != getAccountRobotWorkListParam.getStartDateTime(), AccountRobotWork::getCreateDatetime, getAccountRobotWorkListParam.getStartDateTime())
                .le(null != getAccountRobotWorkListParam.getEndDateTime(), AccountRobotWork::getCreateDatetime, getAccountRobotWorkListParam.getEndDateTime());

        if (StringUtils.isNotBlank(getAccountRobotWorkListParam.getOrderByColumn()) && StringUtils.isNotBlank(getAccountRobotWorkListParam.getIsAsc())) {
            queryWrapper.last("order by " + getAccountRobotWorkListParam.getOrderBy());
        }

        IPage<AccountRobotWork> page = accountRobotWorkService.page(new Page<>(getAccountRobotWorkListParam.getPageNo(), getAccountRobotWorkListParam.getPageSize()), queryWrapper);

        List<AccountRobotWork> records = page.getRecords();

        List<AccountRobotWorkInfoVo> accountRobotWorkInfoVoList = this.entityToVo(records);

        TableRecordVo tableRecordVo = new TableRecordVo<>();
        tableRecordVo.setRecords(accountRobotWorkInfoVoList);
        tableRecordVo.setTotal(page.getTotal());
        tableRecordVo.setPages(page.getPages());
        return tableRecordVo;
    }

    /**
     * 明细
     * @param accountRobotWorkId
     * @return
     */
    @Override
    public AccountRobotWorkInfoVo getAccountRobotWorkDetail(Long accountRobotWorkId) {
        AccountRobotWork accountRobotWork = accountRobotWorkService.getById(accountRobotWorkId);
        if (null == accountRobotWork) {
            throw new ServiceException("记录不存在");
        }
        List<AccountRobotWorkInfoVo> accountRobotWorkInfoVos = this.entityToVo(Arrays.asList(accountRobotWork));
        return accountRobotWorkInfoVos.stream().findFirst().orElse(null);
    }

    /**
    * 实体类转VO
    *
    * @param records
    * @return
    */
    private List<AccountRobotWorkInfoVo> entityToVo(List<AccountRobotWork> records) {
        return records.stream()
            .map(accountRobotWork -> {
                AccountRobotWorkInfoVo accountRobotWorkInfoVo = new AccountRobotWorkInfoVo();
                BeanUtils.copyProperties(accountRobotWork, accountRobotWorkInfoVo);
                return accountRobotWorkInfoVo;
            }).collect(Collectors.toList());
    }

    /**
     * 新增或者编辑时统一校验
     *
     * @param aeAccountRobotWorkParam
     */
    private void checkParam(AeAccountRobotWorkParam aeAccountRobotWorkParam) {

    }
}