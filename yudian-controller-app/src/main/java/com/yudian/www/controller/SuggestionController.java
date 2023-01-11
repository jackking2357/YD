package com.yudian.www.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.yudian.common.entity.R;
import com.yudian.www.service.suggestion.ISuggestionServiceProcess;
import com.yudian.www.service.suggestion.param.AeSuggestionParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(tags = "建议反馈")
@RestController
@RequestMapping("/suggestion")
public class SuggestionController {

    @Resource
    private ISuggestionServiceProcess suggestionServiceProcess;

    @ApiOperation(value = "建议档案(添加)", notes = "建议档案(添加)", tags = {})
    @ApiOperationSupport(order = 1)
    @PostMapping("/suggestionAdd")
    public R suggestionAdd(@Valid @RequestBody AeSuggestionParam aeSuggestionParam) {
        aeSuggestionParam.setSuggestionStatus(1);
        suggestionServiceProcess.suggestionAdd(aeSuggestionParam);
        return R.ok(null, "新增成功");
    }
}
