package ${package.Service};

import ${package.Service}.param.Ae${entity}Param;
import ${package.Service}.param.Get${entity}ListParam;
import ${package.Service}.vo.${entity}InfoVo;
import com.yudian.www.base.TableRecordVo;
import java.util.List;
/**
* ${table.comment!?trim} 服务类-流程
 *
 * @author ${author}
 * @since ${date}
 */
public interface ${table.serviceName}Process {

    /**
     * 添加
     * @param ae${entity}Param
     */
    void ${entity?uncap_first}Add(Ae${entity}Param ae${entity}Param);

    /**
     * 批量创建
     *
     * @param ae${entity}ParamList
     */
    void ${entity?uncap_first}AddBatch(List<Ae${entity}Param> ae${entity}ParamList);

    /**
     * 编辑
     * @param ae${entity}Param
     */
    void ${entity?uncap_first}Edit(Ae${entity}Param ae${entity}Param);

    /**
     * 删除
     * @param <#list table.fields as field><#if field.keyFlag>${field.propertyName}s</#if></#list>
     */
    void ${entity?uncap_first}Remove(<#list table.fields as field><#if field.keyFlag>${field.propertyType}[] ${field.propertyName}s</#if></#list>);

    /**
     * 分页查询
     * @param get${entity}ListParam
     * @return
     */
    TableRecordVo<${entity}InfoVo> get${entity}List(Get${entity}ListParam get${entity}ListParam);

    /**
     * 明细
     * @param <#list table.fields as field><#if field.keyFlag>${field.propertyName}</#if></#list>
     * @return
     */
    ${entity}InfoVo get${entity}Detail(<#list table.fields as field><#if field.keyFlag>${field.propertyType} ${field.propertyName}</#if></#list>);
}
