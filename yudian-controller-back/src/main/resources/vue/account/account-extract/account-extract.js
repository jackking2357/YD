import request from '@/utils/request'

/**
 * @description 平台用户提取记录(列表)
 * @param {Object} data 携带的数据
 */
export function getAccountExtractList (data = {}) {
  return request({
    url: '/yudian-back//account-extract/getAccountExtractList',
    method: 'post',
    data
  })
}

/**
 * @description 平台用户提取记录(明细)
 * @param {Object} businessResultPlanId 平台用户提取记录id
 */
export function getAccountExtractDetail (accountExtractId) {
  return request({
    url: `/yudian-back//account-extract/getAccountExtractDetail/${accountExtractId}`,
    method: 'get'
  })
}

/**
 * @description 平台用户提取记录(添加)
 * @param {Object} data 携带的数据
 */
export function accountExtractAdd (data = {}) {
  return request({
    url: '/yudian-back//account-extract/accountExtractAdd',
    method: 'post',
    data
  })
}

/**
 * @description 平台用户提取记录(编辑)
 * @param {Object} data 携带的数据
 */
export function accountExtractEdit (data = {}) {
  return request({
    url: '/yudian-back//account-extract/accountExtractEdit',
    method: 'put',
    data
  })
}

/**
 * @description 平台用户提取记录(删除)
 * @param {Object} accountExtractIds 平台用户提取记录id
 */
export function accountExtractRemove (accountExtractIds) {
  return request({
    url: `/yudian-back//account-extract/accountExtractRemove/${accountExtractIds}`,
    method: 'delete'
  })
}

/**
 * @description 平台用户提取记录(excel导出)
 * @param {Object} data 携带的数据
 */
export function accountExtractExport( data = {}) {
  return request({
    url: `/yudian-back//account-extract/accountExtractExport`,
    method: 'post',
    data: data,
    responseType: 'arraybuffer'
  })
}
