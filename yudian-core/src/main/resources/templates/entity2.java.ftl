package ${package.Entity};

<#list table.importPackages as pkg>
import ${pkg};
</#list>
import com.baomidou.mybatisplus.annotation.TableId;
<#--<#if swagger2>-->
<#--import io.swagger.annotations.ApiModel;-->
<#--import io.swagger.annotations.ApiModelProperty;-->
<#--</#if>-->
<#if entityLombokModel>
import lombok.*;
</#if>

/**
 * ${table.comment!?trim}
 *
 * @author ${author}
 * @since ${date}
 */
<#if entityLombokModel>
@Data
    <#if superEntityClass??>
//@EqualsAndHashCode(callSuper = true)
    <#else>
@EqualsAndHashCode(callSuper = false)
    </#if>
//@Builder
//使用后添加一个构造函数，该构造函数含有所有已声明字段属性参数
//@AllArgsConstructor
//使用后创建一个无参构造函数
//@NoArgsConstructor
</#if>
<#if table.convert>
@TableName("`${table.name}`")
</#if>
<#if swagger2>
<#--@ApiModel(value = "${entity}对象", description = "${table.comment!?trim}")-->
</#if>
<#if superEntityClass??>
public class ${entity} extends ${superEntityClass}<#if activeRecord><${entity}></#if> {
<#elseif activeRecord>
    public class ${entity} extends Model<${entity}> {
<#else>
    public class ${entity} implements Serializable {
</#if>

<#if entitySerialVersionUID>
    private static final long serialVersionUID = 1L;
</#if>

<#if entityColumnConstant>
    <#list table.fields as field>
        public static final String ${field.name?upper_case} = "${field.name}";

    </#list>
</#if>
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>

    <#if field.comment!?length gt 0>
        <#if swagger2>
<#--            <#if "create_time" == field.name || "update_time" == field.name || "tb_status" == field.name>-->

<#--            <#else>-->
    /**
     * ${field.comment}
     * ${field.type} ${field.type?substring(field.type?index_of("(")+1,field.type?length - 1)}
     */
<#--    @ApiModelProperty(value = "${field.comment}")-->
<#--            </#if>-->
        <#else>
<#--            <#if "create_time" == field.name || "update_time" == field.name || "tb_status" == field.name>-->

<#--            <#else>-->
    /**
     * ${field.comment}
     * ${field.type} ${field.type?substring(field.type?index_of("(")+1,field.type?length - 1)}
     */
<#--            </#if>-->
        </#if>
    </#if>
    <#if field.keyFlag>
    <#-- 主键 -->
        <#if field.keyIdentityFlag>
    @TableId(value = "${field.name}", type = IdType.AUTO)
        <#elseif idType??>
    @TableId(value = "${field.name}", type = IdType.${idType})
        <#elseif field.convert>
    @TableId("${field.name}")
        <#elseif field.type == 'bigint(20)'>
    @TableId
    </#if>
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
    </#if>
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