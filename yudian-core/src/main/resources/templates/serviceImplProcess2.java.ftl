package ${package.ServiceImpl};

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yudian.www.base.TableRecordVo;
import com.yudian.www.config.exceptions.ServiceException;
import ${package.Entity}.${entity};
import ${package.Service}.param.Ae${entity}Param;
import ${package.Service}.param.Get${entity}ListParam;
import ${package.Service}.${table.serviceName};
import ${package.Service}.${table.serviceName}Process;
import ${package.Service}.vo.${entity}InfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
* ${table.comment!?trim} 服务实现类-流程
 *
 * @author ${author}
 * @since ${date}
 */
@Service
public class ${table.serviceImplName}Process implements ${table.serviceName}Process {

    @Resource
    private ${table.serviceName} ${table.serviceName?substring(1)?uncap_first};

    /**
     * 添加
     * @param ae${entity}Param
     */
    @Override
    public void ${entity?uncap_first}Add(Ae${entity}Param ae${entity}Param) {
        ae${entity}Param.initParam();
        this.checkParam(ae${entity}Param);
        ${entity} ${entity?uncap_first} = new ${entity}();
        BeanUtils.copyProperties(ae${entity}Param, ${entity?uncap_first});
        boolean save = ${table.serviceName?substring(1)?uncap_first}.save(${entity?uncap_first});
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    @Override
    public void ${entity?uncap_first}AddBatch(List<Ae${entity}Param> ae${entity}ParamList) {
        List<${entity}> ${entity?uncap_first}List = ae${entity}ParamList.stream()
            .map(ae${entity}Param -> {
                ${entity} ${entity?uncap_first} = new ${entity}();
                BeanUtils.copyProperties(ae${entity}Param, ${entity?uncap_first});
                return ${entity?uncap_first};
            }).collect(Collectors.toList());
        boolean save = ${table.serviceName?substring(1)?uncap_first}.saveBatch(${entity?uncap_first}List);
        if (!save) {
            throw new ServiceException("保存失败");
        }
    }

    /**
     * 编辑
     * @param ae${entity}Param
     */
    @Override
    public void ${entity?uncap_first}Edit(Ae${entity}Param ae${entity}Param) {
        ae${entity}Param.initParam();
        this.checkParam(ae${entity}Param);
        ${entity} ${entity?uncap_first} = ${table.serviceName?substring(1)?uncap_first}.getById(ae${entity}Param.get<#list table.fields as field><#if field.keyFlag>${field.propertyName?cap_first}</#if></#list>());
        if (null == ${entity?uncap_first}) {
            throw new ServiceException("记录不存在");
        }
        BeanUtils.copyProperties(ae${entity}Param, ${entity?uncap_first});
        boolean update = ${table.serviceName?substring(1)?uncap_first}.updateById(${entity?uncap_first});
        if (!update) {
            throw new ServiceException("编辑失败");
        }
    }

    /**
     * 删除
     * @param <#list table.fields as field><#if field.keyFlag>${field.propertyName}s</#if></#list>
     */
    @Override
    public void ${entity?uncap_first}Remove(<#list table.fields as field><#if field.keyFlag>${field.propertyType}[] ${field.propertyName}s</#if></#list>) {
        boolean remove = ${table.serviceName?substring(1)?uncap_first}.removeByIds(Arrays.asList(<#list table.fields as field><#if field.keyFlag>${field.propertyName}s</#if></#list>));
        if (!remove) {
            throw new ServiceException("删除失败");
        }
    }

    /**
     * 分页查询
     * @param get${entity}ListParam
     * @return
     */
    @Override
    public TableRecordVo<${entity}InfoVo> get${entity}List(Get${entity}ListParam get${entity}ListParam) {
        LambdaQueryWrapper<${entity}> queryWrapper = new QueryWrapper<${entity}>().lambda();
        queryWrapper
                .ge(null != get${entity}ListParam.getStartDateTime(), ${entity}::getCreateDatetime, get${entity}ListParam.getStartDateTime())
                .le(null != get${entity}ListParam.getEndDateTime(), ${entity}::getCreateDatetime, get${entity}ListParam.getEndDateTime());

        if (StringUtils.isNotBlank(get${entity}ListParam.getOrderByColumn()) && StringUtils.isNotBlank(get${entity}ListParam.getIsAsc())) {
            queryWrapper.last("order by " + get${entity}ListParam.getOrderBy());
        }

        IPage<${entity}> page = ${table.serviceName?substring(1)?uncap_first}.page(new Page<>(get${entity}ListParam.getPageNo(), get${entity}ListParam.getPageSize()), queryWrapper);

        List<${entity}> records = page.getRecords();

        List<${entity}InfoVo> ${entity?uncap_first}InfoVoList = this.entityToVo(records);

        TableRecordVo tableRecordVo = new TableRecordVo<>();
        tableRecordVo.setRecords(${entity?uncap_first}InfoVoList);
        tableRecordVo.setTotal(page.getTotal());
        tableRecordVo.setPages(page.getPages());
        return tableRecordVo;
    }

    /**
     * 明细
     * @param <#list table.fields as field><#if field.keyFlag>${field.propertyName}</#if></#list>
     * @return
     */
    @Override
    public ${entity}InfoVo get${entity}Detail(<#list table.fields as field><#if field.keyFlag>${field.propertyType} ${field.propertyName}</#if></#list>) {
        ${entity} ${entity?uncap_first} = ${table.serviceName?substring(1)?uncap_first}.getById(<#list table.fields as field><#if field.keyFlag>${field.propertyName}</#if></#list>);
        if (null == ${entity?uncap_first}) {
            throw new ServiceException("记录不存在");
        }
        List<${entity}InfoVo> ${entity?uncap_first}InfoVos = this.entityToVo(Arrays.asList(${entity?uncap_first}));
        return ${entity?uncap_first}InfoVos.stream().findFirst().orElse(null);
    }

    /**
    * 实体类转VO
    *
    * @param records
    * @return
    */
    private List<${entity}InfoVo> entityToVo(List<${entity}> records) {
        return records.stream()
            .map(${entity?uncap_first} -> {
                ${entity}InfoVo ${entity?uncap_first}InfoVo = new ${entity}InfoVo();
                BeanUtils.copyProperties(${entity?uncap_first}, ${entity?uncap_first}InfoVo);
                return ${entity?uncap_first}InfoVo;
            }).collect(Collectors.toList());
    }

    /**
     * 新增或者编辑时统一校验
     *
     * @param ae${entity}Param
     */
    private void checkParam(Ae${entity}Param ae${entity}Param) {

    }
}