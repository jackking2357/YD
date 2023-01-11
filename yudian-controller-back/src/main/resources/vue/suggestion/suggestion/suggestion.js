import request from '@/utils/request'

/**
 * @description 建议档案(列表)
 * @param {Object} data 携带的数据
 */
export function getSuggestionList (data = {}) {
  return request({
    url: '/yudian-back//suggestion/getSuggestionList',
    method: 'post',
    data
  })
}

/**
 * @description 建议档案(明细)
 * @param {Object} businessResultPlanId 建议档案id
 */
export function getSuggestionDetail (suggestionId) {
  return request({
    url: `/yudian-back//suggestion/getSuggestionDetail/${suggestionId}`,
    method: 'get'
  })
}

/**
 * @description 建议档案(添加)
 * @param {Object} data 携带的数据
 */
export function suggestionAdd (data = {}) {
  return request({
    url: '/yudian-back//suggestion/suggestionAdd',
    method: 'post',
    data
  })
}

/**
 * @description 建议档案(编辑)
 * @param {Object} data 携带的数据
 */
export function suggestionEdit (data = {}) {
  return request({
    url: '/yudian-back//suggestion/suggestionEdit',
    method: 'put',
    data
  })
}

/**
 * @description 建议档案(删除)
 * @param {Object} suggestionIds 建议档案id
 */
export function suggestionRemove (suggestionIds) {
  return request({
    url: `/yudian-back//suggestion/suggestionRemove/${suggestionIds}`,
    method: 'delete'
  })
}

/**
 * @description 建议档案(excel导出)
 * @param {Object} data 携带的数据
 */
export function suggestionExport( data = {}) {
  return request({
    url: `/yudian-back//suggestion/suggestionExport`,
    method: 'post',
    data: data,
    responseType: 'arraybuffer'
  })
}
