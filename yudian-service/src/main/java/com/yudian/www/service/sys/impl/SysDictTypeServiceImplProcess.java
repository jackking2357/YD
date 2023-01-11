package com.yudian.www.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.config.exceptions.ServiceException;
import com.yudian.www.entity.sys.SysDictData;
import com.yudian.www.entity.sys.SysDictType;
import com.yudian.www.service.sys.ISysDictDataService;
import com.yudian.www.service.sys.ISysDictTypeService;
import com.yudian.www.service.sys.ISysDictTypeServiceProcess;
import com.yudian.www.service.sys.param.AeSysDictTypeParam;
import com.yudian.www.service.sys.param.GetSysDictTypeListParam;
import com.yudian.www.service.sys.vo.SysDictTypeInfoVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典类型表（暂未使用） 服务实现类-流程
 *

 * @since 2022-02-15
 */
@Service
@RequiredArgsConstructor
public class SysDictTypeServiceImplProcess implements ISysDictTypeServiceProcess {

    private final ISysDictTypeService sysDictTypeService;
    private final ISysDictDataService sysDictDataService;

    /**
     * 添加
     *
     * @param aeSysDictTypeParam
     */
    @Override
    public void sysDictTypeAdd(AeSysDictTypeParam aeSysDictTypeParam) {
        aeSysDictTypeParam.initParam();
        this.checkParam(aeSysDictTypeParam);
        SysDictType sysDictType = new SysDictType();
        BeanUtils.copyProperties(aeSysDictTypeParam, sysDictType);
        boolean save = sysDictTypeService.save(sysDictType);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    @Override
    public void sysDictTypeAddBatch(List<AeSysDictTypeParam> aeSysDictTypeParamList) {
        List<SysDictType> sysDictTypeList = aeSysDictTypeParamList.stream()
                .map(aeSysDictTypeParam -> {
                    SysDictType sysDictType = new SysDictType();
                    BeanUtils.copyProperties(aeSysDictTypeParam, sysDictType);
                    return sysDictType;
                }).collect(Collectors.toList());
        boolean save = sysDictTypeService.saveBatch(sysDictTypeList);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    /**
     * 编辑
     *
     * @param aeSysDictTypeParam
     */
    @Override
    public void sysDictTypeEdit(AeSysDictTypeParam aeSysDictTypeParam) {
        aeSysDictTypeParam.initParam();
        this.checkParam(aeSysDictTypeParam);
        SysDictType sysDictType = sysDictTypeService.getById(aeSysDictTypeParam.getDictId());
        if (null == sysDictType) {
            throw new ServiceException("记录不存在");
        }
        BeanUtils.copyProperties(aeSysDictTypeParam, sysDictType);
        boolean update = sysDictTypeService.updateById(sysDictType);
        if (!update) {
            throw new ServiceException("编辑失败");
        }
    }

    /**
     * 删除
     *
     * @param dictIds
     */
    @Override
    public void sysDictTypeRemove(Long[] dictIds) {
        for (Long dictId : dictIds) {
            SysDictType sysDictType = sysDictTypeService.getById(dictId);
            if (null == sysDictType) {
                continue;
            }
            String dictKey = sysDictType.getDictKey();
            if (StringUtils.isBlank(dictKey)) {
                continue;
            }
            int count = sysDictDataService.count(Wrappers.<SysDictData>lambdaQuery()
                    .eq(SysDictData::getDictKey, dictKey));
            if (0 != count) {
                throw new ServiceException("存在下级字典数据，无法删除");
            }
        }

        boolean remove = sysDictTypeService.removeByIds(Arrays.asList(dictIds));
        if (!remove) {
            throw new ServiceException("删除失败");
        }
    }

    /**
     * 分页查询
     *
     * @param getSysDictTypeListParam
     * @return
     */
    @Override
    public TableRecordVo<SysDictTypeInfoVo> getSysDictTypeList(GetSysDictTypeListParam getSysDictTypeListParam) {
        String dictName = getSysDictTypeListParam.getDictName();
        String dictKey = getSysDictTypeListParam.getDictKey();
        Integer dictStatus = getSysDictTypeListParam.getDictStatus();

        LambdaQueryWrapper<SysDictType> queryWrapper = new QueryWrapper<SysDictType>().lambda();
        queryWrapper.like(StringUtils.isNotBlank(dictName), SysDictType::getDictName, dictName);
        queryWrapper.like(StringUtils.isNotBlank(dictKey), SysDictType::getDictKey, dictKey);
        queryWrapper.like(null != dictStatus, SysDictType::getDictStatus, dictStatus);
        queryWrapper
                .ge(null != getSysDictTypeListParam.getStartDateTime(), SysDictType::getCreateDatetime, getSysDictTypeListParam.getStartDateTime())
                .le(null != getSysDictTypeListParam.getEndDateTime(), SysDictType::getCreateDatetime, getSysDictTypeListParam.getEndDateTime());

        if (StringUtils.isNotBlank(getSysDictTypeListParam.getOrderByColumn()) && StringUtils.isNotBlank(getSysDictTypeListParam.getIsAsc())) {
            queryWrapper.last("order by " + getSysDictTypeListParam.getOrderBy());
        }

        IPage<SysDictType> page = sysDictTypeService.page(new Page<>(getSysDictTypeListParam.getPageNo(), getSysDictTypeListParam.getPageSize()), queryWrapper);

        List<SysDictType> records = page.getRecords();

        List<SysDictTypeInfoVo> sysDictTypeInfoVoList = this.entityToVo(records);

        TableRecordVo tableRecordVo = new TableRecordVo<>();
        tableRecordVo.setRecords(sysDictTypeInfoVoList);
        tableRecordVo.setTotal(page.getTotal());
        tableRecordVo.setPages(page.getPages());
        return tableRecordVo;
    }

    /**
     * 明细
     *
     * @param dictId
     * @return
     */
    @Override
    public SysDictTypeInfoVo getSysDictTypeDetail(Long dictId) {
        SysDictType sysDictType = sysDictTypeService.getById(dictId);
        if (null == sysDictType) {
            throw new ServiceException("记录不存在");
        }
        List<SysDictTypeInfoVo> sysDictTypeInfoVos = this.entityToVo(Arrays.asList(sysDictType));
        return sysDictTypeInfoVos.stream().findFirst().orElse(null);
    }

    /**
     * 实体类转VO
     *
     * @param records
     * @return
     */
    private List<SysDictTypeInfoVo> entityToVo(List<SysDictType> records) {
        return records.stream()
                .map(sysDictType -> {
                    SysDictTypeInfoVo sysDictTypeInfoVo = new SysDictTypeInfoVo();
                    BeanUtils.copyProperties(sysDictType, sysDictTypeInfoVo);
                    return sysDictTypeInfoVo;
                }).collect(Collectors.toList());
    }

    /**
     * 新增或者编辑时统一校验
     *
     * @param aeSysDictTypeParam
     */
    private void checkParam(AeSysDictTypeParam aeSysDictTypeParam) {

    }
}