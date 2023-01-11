package com.yudian.www.service.robot.param;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yudian.www.base.EditDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 机器人
 *
 * @author yudian
 * @since 2023-01-05
 */
@Data
@ApiModel(description = "机器人")
@ExcelIgnoreUnannotated
public class AeRobotParam {

    /**
     * 机器人id
     * bigint(20) 20
     */
    @ApiModelProperty(value = "机器人id(新增不用传，修改要传)", required = false, position = 0)
    @NotNull(groups = {EditDomain.class}, message = "请传入机器人id")
    private Long robotId;

    /**
     * 机器人封面
     * varchar(512) 512
     */
    @ApiModelProperty(value = "机器人封面", required = true, position = 1)
    @NotNull(message = "机器人封面参数必传")
    @ExcelProperty(value = "机器人封面")
    private List<String> robotPhoto;

    /**
     * 机器人描述
     * varchar(1024) 1024
     */
    @ApiModelProperty(value = "机器人描述", required = true, position = 2)
    @Length(max = 1024, message = "机器人描述参数过长")
    @NotBlank(message = "机器人描述参数必传")
    @ExcelProperty(value = "机器人描述")
    private String robotDesc;

    /**
     * 机器人价格
     * decimal(10,2) 10,2
     */
    @ApiModelProperty(value = "机器人价格", required = true, position = 3)
    @NotNull(message = "机器人价格参数必传")
    @ExcelProperty(value = "机器人价格")
    private BigDecimal robotPrice;

    /**
     * 存在天数
     */
    @ApiModelProperty(value = "存在天数")
    private Integer existDay;

    public void initParam() {

    }
}