import request from '@/utils/request'

/**
 * @description 平台用户流水(列表)
 * @param {Object} data 携带的数据
 */
export function getAccountOutinList (data = {}) {
  return request({
    url: '/yudian-back//account-outin/getAccountOutinList',
    method: 'post',
    data
  })
}

/**
 * @description 平台用户流水(明细)
 * @param {Object} businessResultPlanId 平台用户流水id
 */
export function getAccountOutinDetail (accountOutinId) {
  return request({
    url: `/yudian-back//account-outin/getAccountOutinDetail/${accountOutinId}`,
    method: 'get'
  })
}

/**
 * @description 平台用户流水(添加)
 * @param {Object} data 携带的数据
 */
export function accountOutinAdd (data = {}) {
  return request({
    url: '/yudian-back//account-outin/accountOutinAdd',
    method: 'post',
    data
  })
}

/**
 * @description 平台用户流水(编辑)
 * @param {Object} data 携带的数据
 */
export function accountOutinEdit (data = {}) {
  return request({
    url: '/yudian-back//account-outin/accountOutinEdit',
    method: 'put',
    data
  })
}

/**
 * @description 平台用户流水(删除)
 * @param {Object} accountOutinIds 平台用户流水id
 */
export function accountOutinRemove (accountOutinIds) {
  return request({
    url: `/yudian-back//account-outin/accountOutinRemove/${accountOutinIds}`,
    method: 'delete'
  })
}

/**
 * @description 平台用户流水(excel导出)
 * @param {Object} data 携带的数据
 */
export function accountOutinExport( data = {}) {
  return request({
    url: `/yudian-back//account-outin/accountOutinExport`,
    method: 'post',
    data: data,
    responseType: 'arraybuffer'
  })
}
