package ${package.Service}.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
<#list table.importPackages as pkg>
    <#if !pkg?contains("com.baomidou.mybatisplus") && !pkg?contains(".BaseEntity")>
import ${pkg};
    </#if>
</#list>
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
@ApiModel(description = "${table.comment!?trim}")
</#if>
@ExcelIgnoreUnannotated
public class ${entity}InfoVo {
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>

    <#if field.comment!?length gt 0>
        <#if swagger2>
            <#if "create_time" == field.name || "update_time" == field.name || "tb_status" == field.name>
            <#else>
    /**
     * ${field.comment}
     */
    @ApiModelProperty(value = "${field.comment}", dataType="${field.propertyType}")
            </#if>
            <#if "datetime" == field.type>
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
            </#if>
        <#else>
            <#if "create_time" == field.name || "update_time" == field.name || "tb_status" == field.name>
            <#else>
    /**
     * ${field.comment}
     */
            </#if>
        </#if>
    </#if>
    <#if field.keyFlag>
    <#-- 普通字段 -->
    <#elseif field.fill??>
    <#-- -----   存在字段填充设置   ----->
        <#if field.convert>
    @TableField(value = "${field.name}", fill = FieldFill.${field.fill})
        <#else>
    @TableField(fill = FieldFill.${field.fill})
        </#if>
    <#elseif field.convert>
    @TableField("${field.name}")
    </#if>
<#-- 乐观锁注解 -->
    <#if (versionFieldName!"") == field.name>
    @Version
    </#if>
<#-- 逻辑删除注解 -->
    <#if (logicDeleteFieldName!"") == field.name>
    @TableLogic
    </#if>
<#--    <#if "create_time" == field.name || "update_time" == field.name || "tb_status" == field.name>-->
<#--    <#else>-->
    <#if field.propertyType!="Long" && !field.propertyName?ends_with("Id")>
    @ExcelProperty(value = "${field.comment}")
    </#if>
    private ${field.propertyType} ${field.propertyName};
<#--    </#if>-->
</#list>
<#------------  END 字段循环遍历  ---------->

<#if !entityLombokModel>
    <#list table.fields as field>
        <#if field.propertyType == "boolean">
            <#assign getprefix="is"/>
        <#else>
            <#assign getprefix="get"/>
        </#if>
        public ${field.propertyType} ${getprefix}${field.capitalName}() {
        return ${field.propertyName};
        }

        <#if entityBuilderModel>
            public ${entity} set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
        <#else>
            public void set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
        </#if>
        this.${field.propertyName} = ${field.propertyName};
        <#if entityBuilderModel>
            return this;
        </#if>
        }
    </#list>
</#if>
<#if activeRecord>
    @Override
    protected Serializable pkVal() {
    <#if keyPropertyName??>
        return this.${keyPropertyName};
    <#else>
        return null;
    </#if>`
    }
</#if>
<#if !entityLombokModel>
    @Override
    public String toString() {
    return "${entity}{" +
    <#list table.fields as field>
        <#if field_index==0>
            "${field.propertyName}=" + ${field.propertyName} +
        <#else>
            ", ${field.propertyName}=" + ${field.propertyName} +
        </#if>
    </#list>
    "}";
    }
</#if>
}