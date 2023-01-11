package com.yudian.www.service.suggestion.param;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yudian.www.base.EditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 建议档案
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "建议档案")
@ExcelIgnoreUnannotated
public class AeSuggestionParam {

    /**
     * 建议id
     * bigint(20) 20
     */
    @ApiModelProperty(value = "建议id(新增不用传，修改要传)", required = false, position = 0)
    @NotNull(groups = {EditDomain.class}, message = "请传入建议id")
    private Long suggestionId;

    /**
     * 内容
     * varchar(512) 512
     */
    @ApiModelProperty(value = "内容", required = true, position = 1)
    @Length(max = 512, message = "内容参数过长")
    @NotBlank(message = "内容参数必传")
    @ExcelProperty(value = "内容")
    private String suggestionContent;

    /**
     * 照片（JSON数组）
     * varchar(512) 512
     */
    @ApiModelProperty(value = "照片（JSON数组）", required = true, position = 2)
    @NotNull(message = "照片参数必传")
    @ExcelProperty(value = "照片（JSON数组）")
    private List<String> suggestionPhotos;

    /**
     * 处理状态：1=待处理；2=已处理；3=处理中
     * int(1) 1
     */
    @ApiModelProperty(value = "处理状态：1=待处理；2=已处理；3=处理中", required = true, hidden = true, position = 3)
    @ExcelProperty(value = "处理状态：1=待处理；2=已处理；3=处理中")
    private Integer suggestionStatus;

    public void initParam() {

    }
}