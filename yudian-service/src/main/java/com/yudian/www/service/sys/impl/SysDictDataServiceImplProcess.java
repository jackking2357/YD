package com.yudian.www.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.config.exceptions.ServiceException;
import com.yudian.www.entity.sys.SysDictData;
import com.yudian.www.service.sys.ISysDictDataService;
import com.yudian.www.service.sys.ISysDictDataServiceProcess;
import com.yudian.www.service.sys.param.AeSysDictDataParam;
import com.yudian.www.service.sys.param.GetSysDictDataListParam;
import com.yudian.www.service.sys.vo.SysDictDataInfoVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典数据表 服务实现类-流程
 *
 * @since 2022-02-15
 */
@Service
@RequiredArgsConstructor
public class SysDictDataServiceImplProcess implements ISysDictDataServiceProcess {

    private final ISysDictDataService sysDictDataService;

    /**
     * 添加
     *
     * @param aeSysDictDataParam
     */
    @Override
    public void sysDictDataAdd(AeSysDictDataParam aeSysDictDataParam) {
        aeSysDictDataParam.initParam();
        this.checkParam(aeSysDictDataParam);
        SysDictData sysDictData = new SysDictData();
        BeanUtils.copyProperties(aeSysDictDataParam, sysDictData);
        boolean save = sysDictDataService.save(sysDictData);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    @Override
    public void sysDictDataAddBatch(List<AeSysDictDataParam> aeSysDictDataParamList) {
        List<SysDictData> sysDictDataList = aeSysDictDataParamList.stream()
                .map(aeSysDictDataParam -> {
                    SysDictData sysDictData = new SysDictData();
                    BeanUtils.copyProperties(aeSysDictDataParam, sysDictData);
                    return sysDictData;
                }).collect(Collectors.toList());
        boolean save = sysDictDataService.saveBatch(sysDictDataList);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    /**
     * 编辑
     *
     * @param aeSysDictDataParam
     */
    @Override
    public void sysDictDataEdit(AeSysDictDataParam aeSysDictDataParam) {
        aeSysDictDataParam.initParam();
        this.checkParam(aeSysDictDataParam);
        SysDictData sysDictData = sysDictDataService.getById(aeSysDictDataParam.getDictDataId());
        if (null == sysDictData) {
            throw new ServiceException("记录不存在");
        }
        BeanUtils.copyProperties(aeSysDictDataParam, sysDictData);
        boolean update = sysDictDataService.updateById(sysDictData);
        if (!update) {
            throw new ServiceException("编辑失败");
        }
    }

    /**
     * 删除
     *
     * @param dictDataIds
     */
    @Override
    public void sysDictDataRemove(Long[] dictDataIds) {
        for (Long dictDataId : dictDataIds) {
            SysDictData service = sysDictDataService.getById(dictDataId);
            if (null != service) {

            }
        }
        boolean remove = sysDictDataService.removeByIds(Arrays.asList(dictDataIds));
        if (!remove) {
            throw new ServiceException("删除失败");
        }
    }

    /**
     * 分页查询
     *
     * @param getSysDictDataListParam
     * @return
     */
    @Override
    public TableRecordVo<SysDictDataInfoVo> getSysDictDataList(GetSysDictDataListParam getSysDictDataListParam) {
        String dictKey = getSysDictDataListParam.getDictKey();
        LambdaQueryWrapper<SysDictData> queryWrapper = new QueryWrapper<SysDictData>().lambda();
        queryWrapper.eq(StringUtils.isNotBlank(dictKey), SysDictData::getDictKey, dictKey);
        queryWrapper
                .ge(null != getSysDictDataListParam.getStartDateTime(), SysDictData::getCreateDatetime, getSysDictDataListParam.getStartDateTime())
                .le(null != getSysDictDataListParam.getEndDateTime(), SysDictData::getCreateDatetime, getSysDictDataListParam.getEndDateTime());

        if (StringUtils.isNotBlank(getSysDictDataListParam.getOrderByColumn()) && StringUtils.isNotBlank(getSysDictDataListParam.getIsAsc())) {
            queryWrapper.last("order by " + getSysDictDataListParam.getOrderBy());
        }

        IPage<SysDictData> page = sysDictDataService.page(new Page<>(getSysDictDataListParam.getPageNo(), getSysDictDataListParam.getPageSize()), queryWrapper);

        List<SysDictData> records = page.getRecords();

        List<SysDictDataInfoVo> sysDictDataInfoVoList = this.entityToVo(records);

        TableRecordVo tableRecordVo = new TableRecordVo<>();
        tableRecordVo.setRecords(sysDictDataInfoVoList);
        tableRecordVo.setTotal(page.getTotal());
        tableRecordVo.setPages(page.getPages());
        return tableRecordVo;
    }

    /**
     * 明细
     *
     * @param dictDataId
     * @return
     */
    @Override
    public SysDictDataInfoVo getSysDictDataDetail(Long dictDataId) {
        SysDictData sysDictData = sysDictDataService.getById(dictDataId);
        if (null == sysDictData) {
            throw new ServiceException("记录不存在");
        }
        List<SysDictDataInfoVo> sysDictDataInfoVos = this.entityToVo(Arrays.asList(sysDictData));
        return sysDictDataInfoVos.stream().findFirst().orElse(null);
    }

    /**
     * 实体类转VO
     *
     * @param records
     * @return
     */
    private List<SysDictDataInfoVo> entityToVo(List<SysDictData> records) {
        return records.stream()
                .map(sysDictData -> {
                    SysDictDataInfoVo sysDictDataInfoVo = new SysDictDataInfoVo();
                    BeanUtils.copyProperties(sysDictData, sysDictDataInfoVo);
                    return sysDictDataInfoVo;
                }).collect(Collectors.toList());
    }

    /**
     * 新增或者编辑时统一校验
     *
     * @param aeSysDictDataParam
     */
    private void checkParam(AeSysDictDataParam aeSysDictDataParam) {
        int count = sysDictDataService.count(Wrappers.<SysDictData>lambdaQuery()
                .ne(null != aeSysDictDataParam.getDictDataId(), SysDictData::getDictDataId, aeSysDictDataParam.getDictDataId())
                .eq(SysDictData::getDictKey, aeSysDictDataParam.getDictKey())
                .eq(SysDictData::getDictLabel, aeSysDictDataParam.getDictLabel()));
        if (0 != count) {
            throw new ServiceException("该类型下已存在相同字典键值，请更换");
        }
    }
}