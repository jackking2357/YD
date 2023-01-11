package com.yudian.www.service.suggestion.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.config.exceptions.ServiceException;
import com.yudian.www.entity.suggestion.Suggestion;
import com.yudian.www.service.suggestion.ISuggestionService;
import com.yudian.www.service.suggestion.ISuggestionServiceProcess;
import com.yudian.www.service.suggestion.param.AeSuggestionParam;
import com.yudian.www.service.suggestion.param.GetSuggestionListParam;
import com.yudian.www.service.suggestion.vo.SuggestionInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 建议档案 服务实现类-流程
 *
 * @author yudian
 * @since 2023-01-05
 */
@Service
public class SuggestionServiceImplProcess implements ISuggestionServiceProcess {

    @Resource
    private ISuggestionService suggestionService;

    /**
     * 添加
     *
     * @param aeSuggestionParam
     */
    @Override
    public void suggestionAdd(AeSuggestionParam aeSuggestionParam) {
        aeSuggestionParam.initParam();
        this.checkParam(aeSuggestionParam);
        Suggestion suggestion = new Suggestion();
        BeanUtils.copyProperties(aeSuggestionParam, suggestion);
        suggestion.setSuggestionPhotos(JSON.toJSONString(aeSuggestionParam.getSuggestionPhotos()));
        boolean save = suggestionService.save(suggestion);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    @Override
    public void suggestionAddBatch(List<AeSuggestionParam> aeSuggestionParamList) {
        List<Suggestion> suggestionList = aeSuggestionParamList.stream()
                .map(aeSuggestionParam -> {
                    Suggestion suggestion = new Suggestion();
                    BeanUtils.copyProperties(aeSuggestionParam, suggestion);
                    return suggestion;
                }).collect(Collectors.toList());
        boolean save = suggestionService.saveBatch(suggestionList);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    /**
     * 编辑
     *
     * @param aeSuggestionParam
     */
    @Override
    public void suggestionEdit(AeSuggestionParam aeSuggestionParam) {
        aeSuggestionParam.initParam();
        this.checkParam(aeSuggestionParam);
        Suggestion suggestion = suggestionService.getById(aeSuggestionParam.getSuggestionId());
        if (null == suggestion) {
            throw new ServiceException("记录不存在");
        }
        BeanUtils.copyProperties(aeSuggestionParam, suggestion);
        suggestion.setSuggestionPhotos(JSON.toJSONString(aeSuggestionParam.getSuggestionPhotos()));
        boolean update = suggestionService.updateById(suggestion);
        if (!update) {
            throw new ServiceException("编辑失败");
        }
    }

    /**
     * 删除
     *
     * @param suggestionIds
     */
    @Override
    public void suggestionRemove(Long[] suggestionIds) {
        boolean remove = suggestionService.removeByIds(Arrays.asList(suggestionIds));
        if (!remove) {
            throw new ServiceException("删除失败");
        }
    }

    /**
     * 分页查询
     *
     * @param getSuggestionListParam
     * @return
     */
    @Override
    public TableRecordVo<SuggestionInfoVo> getSuggestionList(GetSuggestionListParam getSuggestionListParam) {
        LambdaQueryWrapper<Suggestion> queryWrapper = new QueryWrapper<Suggestion>().lambda();
        queryWrapper
                .ge(null != getSuggestionListParam.getStartDateTime(), Suggestion::getCreateDatetime, getSuggestionListParam.getStartDateTime())
                .le(null != getSuggestionListParam.getEndDateTime(), Suggestion::getCreateDatetime, getSuggestionListParam.getEndDateTime());

        if (StringUtils.isNotBlank(getSuggestionListParam.getOrderByColumn()) && StringUtils.isNotBlank(getSuggestionListParam.getIsAsc())) {
            queryWrapper.last("order by " + getSuggestionListParam.getOrderBy());
        }

        IPage<Suggestion> page = suggestionService.page(new Page<>(getSuggestionListParam.getPageNo(), getSuggestionListParam.getPageSize()), queryWrapper);

        List<Suggestion> records = page.getRecords();

        List<SuggestionInfoVo> suggestionInfoVoList = this.entityToVo(records);

        TableRecordVo tableRecordVo = new TableRecordVo<>();
        tableRecordVo.setRecords(suggestionInfoVoList);
        tableRecordVo.setTotal(page.getTotal());
        tableRecordVo.setPages(page.getPages());
        return tableRecordVo;
    }

    /**
     * 明细
     *
     * @param suggestionId
     * @return
     */
    @Override
    public SuggestionInfoVo getSuggestionDetail(Long suggestionId) {
        Suggestion suggestion = suggestionService.getById(suggestionId);
        if (null == suggestion) {
            throw new ServiceException("记录不存在");
        }
        List<SuggestionInfoVo> suggestionInfoVos = this.entityToVo(Arrays.asList(suggestion));
        return suggestionInfoVos.stream().findFirst().orElse(null);
    }

    /**
     * 实体类转VO
     *
     * @param records
     * @return
     */
    private List<SuggestionInfoVo> entityToVo(List<Suggestion> records) {
        return records.stream()
                .map(suggestion -> {
                    SuggestionInfoVo suggestionInfoVo = new SuggestionInfoVo();
                    BeanUtils.copyProperties(suggestion, suggestionInfoVo);
                    return suggestionInfoVo;
                }).collect(Collectors.toList());
    }

    /**
     * 新增或者编辑时统一校验
     *
     * @param aeSuggestionParam
     */
    private void checkParam(AeSuggestionParam aeSuggestionParam) {

    }
}