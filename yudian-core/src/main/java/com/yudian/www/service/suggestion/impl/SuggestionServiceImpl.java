package com.yudian.www.service.suggestion.impl;

import com.yudian.www.entity.suggestion.Suggestion;
import com.yudian.www.mapper.suggestion.SuggestionMapper;
import com.yudian.www.service.suggestion.ISuggestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
* 建议档案 服务实现类
 *
 * @author yudian
 * @since 2023-01-05
 */
@Service
public class SuggestionServiceImpl extends ServiceImpl<SuggestionMapper, Suggestion> implements ISuggestionService {

    @Resource
    private SuggestionMapper suggestionMapper;

}
