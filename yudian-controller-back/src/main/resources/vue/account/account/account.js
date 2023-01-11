import request from '@/utils/request'

/**
 * @description 平台用户(列表)
 * @param {Object} data 携带的数据
 */
export function getAccountList (data = {}) {
  return request({
    url: '/yudian-back//account/getAccountList',
    method: 'post',
    data
  })
}

/**
 * @description 平台用户(明细)
 * @param {Object} businessResultPlanId 平台用户id
 */
export function getAccountDetail (accountId) {
  return request({
    url: `/yudian-back//account/getAccountDetail/${accountId}`,
    method: 'get'
  })
}

/**
 * @description 平台用户(添加)
 * @param {Object} data 携带的数据
 */
export function accountAdd (data = {}) {
  return request({
    url: '/yudian-back//account/accountAdd',
    method: 'post',
    data
  })
}

/**
 * @description 平台用户(编辑)
 * @param {Object} data 携带的数据
 */
export function accountEdit (data = {}) {
  return request({
    url: '/yudian-back//account/accountEdit',
    method: 'put',
    data
  })
}

/**
 * @description 平台用户(删除)
 * @param {Object} accountIds 平台用户id
 */
export function accountRemove (accountIds) {
  return request({
    url: `/yudian-back//account/accountRemove/${accountIds}`,
    method: 'delete'
  })
}

/**
 * @description 平台用户(excel导出)
 * @param {Object} data 携带的数据
 */
export function accountExport( data = {}) {
  return request({
    url: `/yudian-back//account/accountExport`,
    method: 'post',
    data: data,
    responseType: 'arraybuffer'
  })
}
