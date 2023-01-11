import request from '@/utils/request'

/**
 * @description 平台用户证件(列表)
 * @param {Object} data 携带的数据
 */
export function getAccountCertList (data = {}) {
  return request({
    url: '/yudian-back//account-cert/getAccountCertList',
    method: 'post',
    data
  })
}

/**
 * @description 平台用户证件(明细)
 * @param {Object} businessResultPlanId 平台用户证件id
 */
export function getAccountCertDetail (accountCertId) {
  return request({
    url: `/yudian-back//account-cert/getAccountCertDetail/${accountCertId}`,
    method: 'get'
  })
}

/**
 * @description 平台用户证件(添加)
 * @param {Object} data 携带的数据
 */
export function accountCertAdd (data = {}) {
  return request({
    url: '/yudian-back//account-cert/accountCertAdd',
    method: 'post',
    data
  })
}

/**
 * @description 平台用户证件(编辑)
 * @param {Object} data 携带的数据
 */
export function accountCertEdit (data = {}) {
  return request({
    url: '/yudian-back//account-cert/accountCertEdit',
    method: 'put',
    data
  })
}

/**
 * @description 平台用户证件(删除)
 * @param {Object} accountCertIds 平台用户证件id
 */
export function accountCertRemove (accountCertIds) {
  return request({
    url: `/yudian-back//account-cert/accountCertRemove/${accountCertIds}`,
    method: 'delete'
  })
}

/**
 * @description 平台用户证件(excel导出)
 * @param {Object} data 携带的数据
 */
export function accountCertExport( data = {}) {
  return request({
    url: `/yudian-back//account-cert/accountCertExport`,
    method: 'post',
    data: data,
    responseType: 'arraybuffer'
  })
}
