export default ({ request }) => ({
  /**
   * @description ${table.comment!?trim}(列表)
   * @param {Object} data 携带的数据
   */
  get${entity}List (data = {}) {
    return request({
      url: '${cfg.requestProjectPath}/<#if package.ModuleName??>${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>/get${entity}List',
      method: 'post',
      data
    })
  },

  /**
   * @description ${table.comment!?trim}(明细)
   * @param {Object} businessResultPlanId ${table.comment!?trim}id
   */
  get${entity}Detail (<#list table.fields as field><#if field.keyFlag>${field.propertyName}</#if></#list>s) {
    return request({
      url: `${cfg.requestProjectPath}/<#if package.ModuleName??>${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>/get${entity}Detail/${'$'}{<#list table.fields as field><#if field.keyFlag>${field.propertyName}</#if></#list>s}`,
      method: 'get'
    })
  },

  /**
   * @description ${table.comment!?trim}(添加)
   * @param {Object} data 携带的数据
   */
  ${entity?uncap_first}Add (data = {}) {
    return request({
      url: '${cfg.requestProjectPath}/<#if package.ModuleName??>${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>/${entity?uncap_first}Add',
      method: 'post',
      data
    })
  },

  /**
   * @description ${table.comment!?trim}(编辑)
   * @param {Object} data 携带的数据
   */
  ${entity?uncap_first}Edit (data = {}) {
    return request({
      url: '${cfg.requestProjectPath}/<#if package.ModuleName??>${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>/${entity?uncap_first}Edit',
      method: 'put',
      data
    })
  },

  /**
   * @description ${table.comment!?trim}(删除)
   * @param {Object} <#list table.fields as field><#if field.keyFlag>${field.propertyName}</#if></#list>s ${table.comment!?trim}id
   */
  ${entity?uncap_first}Remove (<#list table.fields as field><#if field.keyFlag>${field.propertyName}</#if></#list>s) {
    return request({
      url: `${cfg.requestProjectPath}/<#if package.ModuleName??>${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>/${entity?uncap_first}Remove/${'$'}{<#list table.fields as field><#if field.keyFlag>${field.propertyName}</#if></#list>s}`,
      method: 'delete'
    })
  },

  /**
   * @description ${table.comment!?trim}(excel导出)
   * @param {Object} data 携带的数据
   */
  ${entity?uncap_first}Export( data = {}) {
    return request({
      url: `${cfg.requestProjectPath}/<#if package.ModuleName??>${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>/${entity?uncap_first}Export`,
      method: 'post',
      data: data,
      responseType: 'arraybuffer'
    })
  }
})
