package com.yudian.www.base;

import com.yudian.common.utils.MyStringUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "基础查询", description = "基础查询实体")
public class BaseParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "当前页数", example = "1", required = true, position = 1)
    @NotNull(message = "当前页数不能为空")
    @Min(value = 1, message = "最小当前页数为1")
    private Integer pageNo;

    @ApiModelProperty(value = "每页显示数", example = "10", required = true, position = 1)
    @NotNull(message = "每页显示数不能为空")
    @Min(value = -1, message = "最小每页显示数为1")
    @Max(value = 500, message = "最大每页显示数为500")
    private Integer pageSize;

    @ApiModelProperty(value = "创建时间（yyyy-MM-dd HH:mm:ss）", position = 999)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startDateTime;

    @ApiModelProperty(value = "结束时间（yyyy-MM-dd HH:mm:ss）", position = 999)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endDateTime;

    @ApiModelProperty(value = "排序规则（ascending|descending）", position = 999)
    private String isAsc = "asc";

    @ApiModelProperty(value = "排序字段", position = 999)
    private String orderByColumn;

    @ApiModelProperty(value = "是否缓存数据", position = 999, hidden = true)
    private Boolean isCache = false;

    @ApiModelProperty(value = "缓存区域", hidden = true)
    private Long cacheArea = null;


    public String getOrderBy() {
        if (StringUtils.isBlank(orderByColumn)) {
            return "";
        }
        return " " + this.orderByColumn + " " + this.isAsc;
    }

    public void setOrderByColumn(String orderByColumn) {
        if (StringUtils.isNotBlank(orderByColumn)) {
            this.orderByColumn = MyStringUtils.toUnderScoreCase(orderByColumn);
        }
    }

    public void setIsAsc(String isAsc) {
        if (StringUtils.isNotBlank(isAsc)) {

            if ("ascending".equals(isAsc)) {
                isAsc = "asc";
            } else if ("descending".equals(isAsc)) {
                isAsc = "desc";
            }
            this.isAsc = isAsc;
        }
    }
}
