package com.yudian.www.service.suggestion;

import com.yudian.www.service.suggestion.param.AeSuggestionParam;
import com.yudian.www.service.suggestion.param.GetSuggestionListParam;
import com.yudian.www.service.suggestion.vo.SuggestionInfoVo;
import com.yudian.www.base.TableRecordVo;
import java.util.List;
/**
* 建议档案 服务类-流程
 *
 * @author yudian
 * @since 2023-01-05
 */
public interface ISuggestionServiceProcess {

    /**
     * 添加
     * @param aeSuggestionParam
     */
    void suggestionAdd(AeSuggestionParam aeSuggestionParam);

    /**
     * 批量创建
     *
     * @param aeSuggestionParamList
     */
    void suggestionAddBatch(List<AeSuggestionParam> aeSuggestionParamList);

    /**
     * 编辑
     * @param aeSuggestionParam
     */
    void suggestionEdit(AeSuggestionParam aeSuggestionParam);

    /**
     * 删除
     * @param suggestionIds
     */
    void suggestionRemove(Long[] suggestionIds);

    /**
     * 分页查询
     * @param getSuggestionListParam
     * @return
     */
    TableRecordVo<SuggestionInfoVo> getSuggestionList(GetSuggestionListParam getSuggestionListParam);

    /**
     * 明细
     * @param suggestionId
     * @return
     */
    SuggestionInfoVo getSuggestionDetail(Long suggestionId);
}
