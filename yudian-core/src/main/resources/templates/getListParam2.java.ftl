package ${package.Service}.param;

import com.yudian.www.base.BaseParam;
import java.util.Set;
<#if swagger2>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if entityLombokModel>
import lombok.Data;
</#if>

/**
 * ${table.comment!?trim}
 *
 * @author ${author}
 * @since ${date}
 */
<#if entityLombokModel>
@Data
</#if>
<#if swagger2>
@ApiModel(description = "获取${table.comment!?trim}列表参数")
</#if>
public class Get${entity}ListParam extends BaseParam {

    @ApiModelProperty(value = "${table.comment!?trim}id列表", required = false, position = 1)
    private Set<Long> <#list table.fields as field><#if field.keyFlag> ${field.propertyName}s</#if></#list>;

}